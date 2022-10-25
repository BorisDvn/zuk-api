package com.thb.zukapi;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import com.thb.zukapi.models.*;
import com.thb.zukapi.repositories.*;
import com.thb.zukapi.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.thb.zukapi.config.audit.SpringSecurityAuditorAware;
import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.services.AdminService;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class ZukApiApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ManagerService managerService;

    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private AdminService adminService;

    @Autowired
    private FileRepository fileRepository;

    // Auditing Config
    @Bean
    public AuditorAware<String> auditorAware() {
        return new SpringSecurityAuditorAware();
    }

    public static void main(String[] args) {
        SpringApplication.run(ZukApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Save roles only one type
        Role role1 = new Role();
        role1.setId(UUID.randomUUID());
        role1.setName(RoleType.ADMIN);
        if (!roleRepository.existsByName(role1.getName()))
            roleRepository.save(role1);

        Role role2 = new Role();
        role2.setId(UUID.randomUUID());
        role2.setName(RoleType.HELPER);
        if (!roleRepository.existsByName(role2.getName()))
            roleRepository.save(role2);

        Role role3 = new Role();
        role3.setId(UUID.randomUUID());
        role3.setName(RoleType.MANAGER);
        if (!roleRepository.existsByName(role3.getName()))
            roleRepository.save(role3);

        Role role4 = new Role();
        role4.setId(UUID.randomUUID());
        role4.setName(RoleType.SEEKER);
        if (!roleRepository.existsByName(role4.getName()))
            roleRepository.save(role4);

        PersonWriteTO admin = new PersonWriteTO();
        admin.setEmail("admin-default@local.com");
        admin.setLastname("12345");
        admin.setFirstname("admin");
        admin.setAddress("addres");
        admin.setPhone("123456789");
        admin.setPassword("admin-default");
        admin.setRole("ADMIN");
        admin.setDob(LocalDate.now());
        admin.setGender(Gender.M);
        if (!adminRepo.findByEmail(admin.getEmail()).isPresent()) {
            adminService.addAdmin(admin);
        }

        // Manager
        PersonWriteTO manager = new PersonWriteTO();
        manager.setEmail("manager-default@local.com");
        manager.setLastname("default");
        manager.setFirstname("manager");
        manager.setAddress("addres");
        manager.setPhone("123456789");
        manager.setPassword("manager-default");
        manager.setRole("MANAGER");
        manager.setDob(LocalDate.now());
        manager.setGender(Gender.F);
        if (!managerRepository.findByEmail(manager.getEmail()).isPresent()) {
            managerService.addManager(manager);
        }

        // Create Default Category
        // Files
        File fileShoes = new File(null,
                "https://www.dropbox.com/s/yst40ux7dbvuq7i/93ebe910-9044-11ec-bfeb-e43d41b3a94d.jpg?raw=1", "Shoes");
        File fileClothes = new File(null,
                "https://www.dropbox.com/s/nnusvn9zbpmwjci/istockphoto-653003428-612x612.jpg?raw=1", "Clothes");
        File fileBooks = new File(null,
                "https://www.dropbox.com/s/ng3wkjaw15hidgz/f1b8d870-3653-11ea-b6c4-9d9f41070527.cf.jpg?raw=1", "Books");
        File fileTranslation = new File(null,
                "https://www.dropbox.com/s/2zl8t4on6gzr6ef/top-7-books-that-changed-the-world.jpg?raw=1",
                "Translation");

        // Categories
        Category shoes = new Category(null, fileShoes, "Shoes", null);
        Category clothes = new Category(null, fileClothes, "Clothes", null);
        Category books = new Category(null, fileBooks, "Books", null);
        Category translation = new Category(null, fileTranslation, "Translation", null);

        // find Category by name
        Boolean defaultCategories = categoryRepository.findAll()
                .stream()
                .map(category -> category.getName())
                .collect(Collectors.toList())
                .containsAll(Arrays.asList("Shoes", "Clothes", "Books", "Translation"));

        if (!defaultCategories) {
            fileRepository.saveAll(Arrays.asList(fileShoes, fileClothes, fileBooks, fileTranslation));
            categoryRepository.saveAll(Arrays.asList(shoes, clothes, books, translation));
        }

    }

}
