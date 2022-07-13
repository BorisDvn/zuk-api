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

    @Operation(summary = "Send simple email without attachment")
    @PostMapping(value = "/simple-email")
    public ResponseEntity<String> sendAttachmentEmail(
            @Parameter(name = "mail", description = "mail to send") @RequestBody Email mail) {
        return mailService.sendMessage(mail);
    }

    @Operation(summary = "Send email with attachment")
    @PostMapping(value = "/attachment")
    public ResponseEntity<String> sendAttachmentEmail(@Parameter(name = "mail", description = "Email to send") @RequestPart Email mail,
                                                      @Parameter(name = "Files", description = "Liste of attachment") @RequestPart List<MultipartFile> files) {
        return mailService.sendMessageAttachment(mail, files);
    }
}
