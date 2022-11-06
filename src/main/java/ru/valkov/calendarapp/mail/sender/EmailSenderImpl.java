package ru.valkov.calendarapp.mail.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(String to, String emailMessage) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setText(emailMessage, false);
            messageHelper.setTo(to);
            messageHelper.setFrom(from);
            messageHelper.setSubject("Invitation on a meeting");

            log.info("Sending email to {} from {} message = {}", to, from, emailMessage);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
