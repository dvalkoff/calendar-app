package ru.valkov.calendarapp.mail.sender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.valkov.calendarapp.invite.Invitation;
import ru.valkov.calendarapp.invite.InvitationStatus;
import ru.valkov.calendarapp.mail.message.EmailMessageGenerator;
import ru.valkov.calendarapp.meeting.Meeting;
import ru.valkov.calendarapp.user.User;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailSenderAdapterTest {
    @Mock
    private EmailSender emailSender;
    @Mock
    private EmailMessageGenerator emailMessageGenerator;
    @InjectMocks
    private EmailSenderAdapter emailSenderAdapter;

    @Test
    void sendInvitationEmails() {
        // given
        List<Invitation> invitations = List.of(givenInvitation());
        when(emailMessageGenerator.generateMeetingInvitationMessage(any(), any()))
                .thenReturn("message");
        // when
        emailSenderAdapter.sendInvitationEmails(invitations);
        // then
        verify(emailSender).sendEmail("email", "message");
    }

    @Test
    void sendInvitationEmail() {
        // given
        Invitation invitation = givenInvitation();
        when(emailMessageGenerator.generateMeetingInvitationMessage(any(), any()))
                .thenReturn("message");
        // when
        emailSenderAdapter.sendInvitationEmail(invitation);
        // then
        verify(emailSender).sendEmail("email", "message");
    }

    private Invitation givenInvitation() {
        return Invitation
                .builder()
                .id(1L)
                .invitedUser(User.builder().email("email").build())
                .meetings(Set.of(new Meeting()))
                .invitationStatus(InvitationStatus.QUESTIONABLE)
                .build();
    }
}