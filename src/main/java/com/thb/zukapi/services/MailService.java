package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String from;

    // simple mail
    public ResponseEntity<String> sendMessage(Email email) {

        try {
            MimeMessage message = emailSender.createMimeMessage();

            message.reply(email.isReply());

            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            helper.setFrom(from);
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getMessage());

            emailSender.send(message);
            logger.info("Email sent successfully:{} -> {} at {}", from, email.getTo(), LocalDateTime.now());

            return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error during email sending : {}", e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

    // Mail with multipartFile
    public ResponseEntity<String> sendMessageAttachment(Email email, List<MultipartFile> files) {
        try {
            MimeMessage message = emailSender.createMimeMessage();

            message.reply(email.isReply());

            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            helper.setFrom(from);
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getMessage());

            if (files != null) {
                for (MultipartFile multipartFile : files) {
                    helper.addAttachment(Objects.requireNonNull(multipartFile.getOriginalFilename()), multipartFile);
                }
            }

            emailSender.send(message);
            logger.info("Mail sent with attachment successfully:{} -> {} at {}", from, email.getTo(),
                    LocalDateTime.now());

            return new ResponseEntity<>("Mail sent successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error during mail sending : {}", e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

    // Mail with DataSource
    public ResponseEntity<String> sendMessageAttachmentDts(Email email, DataSource source) {
        try {
            MimeMessage message = emailSender.createMimeMessage();

            message.reply(email.isReply());

            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            helper.setFrom(from);
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getMessage());
            helper.addAttachment(source.getName(),  source);

            emailSender.send(message);
            logger.info("Message sent with attachment successfully:{} -> {} at {}", from, email.getTo(),
                    LocalDateTime.now());

            return new ResponseEntity<String>("Mail sent successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error during mail sending : {}", e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }
}
