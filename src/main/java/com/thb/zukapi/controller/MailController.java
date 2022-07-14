package com.thb.zukapi.controller;

import com.thb.zukapi.models.Email;
import com.thb.zukapi.services.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "*")
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @Operation(summary = "Send email")
    @PostMapping(value = "")
    public ResponseEntity<String> sendEmail(@Parameter(name = "mail", description = "Email to send") @RequestPart Email mail,
                                            @Parameter(name = "Files", description = "Liste of attachment") @RequestPart(required = false) List<MultipartFile> files) {
        return mailService.sendMail(mail, files);
    }
}
