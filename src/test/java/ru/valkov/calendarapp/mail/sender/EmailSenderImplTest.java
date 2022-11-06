package ru.valkov.calendarapp.mail.sender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {EmailSenderImpl.class}
)
@TestConfiguration(
        value = "spring.mail.username=mock@mail.ru"
)
class EmailSenderImplTest {
    @MockBean
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailSender emailSender;

    @Test
    void sendEmail() {
        // given
        String receiver = "receiver@mail.ru";
        String message = "message";
        when(javaMailSender.createMimeMessage())
                .thenReturn(new MimeMessage(Session.getInstance(new Properties())));
        // when
        emailSender.sendEmail(receiver, message);
        // then
        verify(javaMailSender).send(any(MimeMessage.class));
    }
}