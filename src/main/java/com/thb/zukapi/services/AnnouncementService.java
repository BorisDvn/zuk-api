package com.thb.zukapi.services;

import org.springframework.stereotype.Service;

@Service
public class AnnouncementService {
    /*private final Logger logger = LoggerFactory.getLogger(AnnouncementService.class);

    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement getAnzeige(UUID id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Announcement with id: " + id));
    }

    public List<Announcement> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Announcement> pagedResult = announcementRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Announcement addAnzeige(Announcement announcement) {

        Announcement newAnnouncement = new Announcement();

        if (announcement.getHelper() != null) {
            newAnnouncement.setType(ErstellerStatus.HELFER);
        } else {
            newAnnouncement.setType(ErstellerStatus.FLUECHTLING);
        }

        newAnnouncement.setAnnouncementDate(LocalDateTime.now());
        // todo bilder als link?
        newAnnouncement.setDescription(announcement.getDescription());
        newAnnouncement.setStatus(announcement.getStatus());

        // todo FK
        return announcementRepository.save(newAnnouncement);
    }

    public Announcement updateAnzeige(Announcement announcement) {

        Announcement announcementToUpdate = getAnzeige(announcement.getId());

        if (announcement.getType() != null)
            announcement.setType(announcement.getType());
        if (announcement.getDatum() != null) //todo bilder
            announcement.setDatum(announcement.getDatum());
        if (announcement.getBilds() != null)
            announcement.setBilds(announcement.getBilds());
        if (announcement.getDescription() != null)
            announcement.setDescription(announcement.getDescription());

        // todo FK

        return announcementRepository.save(announcementToUpdate);
    }

    public ResponseEntity<String> deleteAnzeigeById(UUID id) {
        Announcement announcementToDelete = getAnzeige(id);

        announcementRepository.deleteById(announcementToDelete.getId());
        // log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

*/
}
