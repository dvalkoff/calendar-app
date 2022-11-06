package ru.valkov.calendarapp.mail.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.invite.InvitationStatus;
import ru.valkov.calendarapp.meeting.Meeting;
import ru.valkov.calendarapp.user.User;

@Component
public class EmailMessageGenerator {
    @Value("${basePath}")
    private String basePath;

    public String generateMeetingInvitationMessage(User invitedUser, Meeting meeting) {
        String messageTemplate = """
                Hey! You're invited on the meeting %s.
                start-time: %s
                end-time: %s
                location: %s
                description: %s
                periodicity: %s
                inviter email: %s
                
                Follow a link to confirm your invitation:
                * Accept: %s
                * Reject: %s
                * Questionable: %s
                """;
        String invitationPath = generateInvitationPath(invitedUser, meeting);
        return String.format(messageTemplate,
                meeting.getName(),
                meeting.getBeginDateTime().toLocalTime(),
                meeting.getEndDateTime().toLocalTime(),
                meeting.getLocation(),
                meeting.getDescription(),
                meeting.getPeriodicity().name(),
                meeting.getOwner().getEmail(),
                invitationPath + InvitationStatus.ACCEPTED.name(),
                invitationPath + InvitationStatus.REJECTED.name(),
                invitationPath + InvitationStatus.QUESTIONABLE.name()
                );
    }

    private String generateInvitationPath(User invitedUser, Meeting meeting) {
        return basePath +
                String.format(
                        "/users/%s/meetings/%s/invitations?invitationStatus=",
                        invitedUser.getId(), meeting.getGroupId());
    }
}
