package com.thb.zukapi.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thb.zukapi.config.auth.JwtUtils;
import com.thb.zukapi.dtos.admin.Admin2AdminReadTO;
import com.thb.zukapi.dtos.helper.Helper2HelperReadTO;
import com.thb.zukapi.dtos.manager.Manager2ManagerReadTO;
import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.dtos.seeker.Seeker2SeekerReadTO;
import com.thb.zukapi.dtos.user.SigninResponse;
import com.thb.zukapi.dtos.user.SigninTO;
import com.thb.zukapi.dtos.user.SignupTO;
import com.thb.zukapi.dtos.user.UpdatePasswordTO;
import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Role;
import com.thb.zukapi.models.User;
import com.thb.zukapi.repositories.AdminRepository;
import com.thb.zukapi.repositories.HelperRepository;
import com.thb.zukapi.repositories.ManagerRepository;
import com.thb.zukapi.repositories.SeekerRepository;
import com.thb.zukapi.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	@org.springframework.context.annotation.Lazy // to resolve cycle dependency Error
	private PasswordEncoder encoder;

	@Autowired
	private RoleService roleService;

	@Autowired
	@org.springframework.context.annotation.Lazy // to resolve cycle dependency Error
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private SeekerRepository seekerRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private HelperRepository helperRepository;

	public User getUser(UUID id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find User with id: " + id));
	}

	public List<User> getAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<User> pagedResult = userRepository.findAll(paging);

		return pagedResult.getContent();
	}

	// create a system user
	public User signUp(PersonWriteTO user) {

		if (userRepository.existsByEmail(user.getEmail())) {
			throw new ApiRequestException("Error: Email is already in use!");
		}

		User newUser = new User();

		newUser.setEmail(user.getEmail());
		newUser.setPassword(encoder.encode(user.getPassword()));
		newUser.setRoles(getRole(user));

		return userRepository.save(newUser);
	}

	// update a system user
	public User updateUser(SignupTO user) {

		User userToUpdate = getUser(user.getId());

		if (user.getEmail() != null && !user.getEmail().equals(userToUpdate.getEmail())) {
			if (!Objects.equals(user.getEmail(), userToUpdate.getEmail())
					&& !userRepository.existsByEmail(user.getEmail())) {
				userToUpdate.setEmail(user.getEmail());
			} else {
				throw new ApiRequestException("Email has already been taken");
			}
		}

		return userRepository.save(userToUpdate);
	}

	// delete a system user
	public ResponseEntity<String> deleteUserById(UUID id) {
		User userToDelete = getUser(id);

		userRepository.deleteById(userToDelete.getId());

		logger.info("User successfully deleted");
		return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
	}

	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true,
				true, true, getAuthorities(user));
	}

	public Collection<? extends GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		return authorities;
	}

	// to update the password
	public User updatePassword(UpdatePasswordTO updatePass) {

		User user = getUser(updatePass.getId());

		// control if the old password is correct before set the new
		if (updatePass.getNewPassword() != null && updatePass.getOldPassword() != null) {
			if (encoder.matches(updatePass.getOldPassword(), user.getPassword())) {
				user.setPassword(encoder.encode(updatePass.getNewPassword()));
			} else {
				throw new ApiRequestException("The old password is invalid");
			}
		} else {
			throw new ApiRequestException("The old and the new Password are required");
		}

		return user;
	}

	// build the roles
	private Set<Role> getRole(PersonWriteTO user) {
		String strRoles = user.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null || strRoles.isEmpty()) {
			throw new ApiRequestException("The new User should have a role type!");
		} else {
			Role role = roleService.getRole(strRoles);

			switch (role.getName()) {
			case SEEKER:
				roles.add(role);
				break;
			case HELPER:
				roles.add(role);
				break;
			case MANAGER:
				roles.add(role);
				roles.add(roleService.getRole("SEEKER"));
				roles.add(roleService.getRole("HELPER"));
				break;
			case ADMIN:
				roles.add(role);
				roles.add(roleService.getRole("MANAGER"));
				roles.add(roleService.getRole("SEEKER"));
				roles.add(roleService.getRole("HELPER"));
				break;
			}

		}

		return roles;
	}

	public ResponseEntity<?> signIn(SigninTO loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		List<GrantedAuthority> role = new ArrayList<>();
		roles.forEach($ -> role.add(new SimpleGrantedAuthority($)));

		Authentication authentication_ = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword(), role));

		SecurityContextHolder.getContext().setAuthentication(authentication_);

		return ResponseEntity.ok(new SigninResponse(jwt, userDetails.getUsername(), roles));

	}

	public ResponseEntity<?> getUserByMail(String email) {

		if (seekerRepository.findByEmail(email).isPresent())
			return ResponseEntity.ok(Seeker2SeekerReadTO.apply(seekerRepository.findByEmail(email).get()));

		if (managerRepository.findByEmail(email).isPresent())
			return ResponseEntity.ok(Manager2ManagerReadTO.apply(managerRepository.findByEmail(email).get()));

		if (adminRepository.findByEmail(email).isPresent())
			return ResponseEntity.ok(Admin2AdminReadTO.apply(adminRepository.findByEmail(email).get()));

		if (helperRepository.findByEmail(email).isPresent())
			return ResponseEntity.ok(Helper2HelperReadTO.apply(helperRepository.findByEmail(email).get()));

		return ResponseEntity.badRequest().build();
	}
}
