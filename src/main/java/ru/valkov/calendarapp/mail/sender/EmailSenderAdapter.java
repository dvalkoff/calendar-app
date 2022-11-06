package ru.valkov.calendarapp.mail.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.invite.Invitation;
import ru.valkov.calendarapp.mail.message.EmailMessageGenerator;

import java.util.List;

@Component
@EnableAsync
@RequiredArgsConstructor
public class EmailSenderAdapter {
    private final EmailSender emailSender;
    private final EmailMessageGenerator emailMessageGenerator;

    @Async
    public void sendInvitationEmail(Invitation invitation) {
        sendAsync(invitation);
    }

    @Async
    public void sendInvitationEmails(List<Invitation> invitations) {
        invitations.forEach(this::sendAsync);
    }

    private void sendAsync(Invitation invitation) {
        String message = emailMessageGenerator.generateMeetingInvitationMessage(
                invitation.getInvitedUser(),
                invitation.getMeetings().stream().findAny().get());
        emailSender.sendEmail(invitation.getInvitedUser().getEmail(), message);
    }
}
