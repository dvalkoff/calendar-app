package ru.valkov.calendarapp.invite;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.valkov.calendarapp.mail.sender.EmailSenderAdapter;
import ru.valkov.calendarapp.meeting.Meeting;
import ru.valkov.calendarapp.meeting.MeetingService;
import ru.valkov.calendarapp.openapi.model.*;
import ru.valkov.calendarapp.user.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {InvitationService.class}
)
class InvitationServiceTest {
    @MockBean
    private InvitationRepository invitationRepository;
    @MockBean
    private InvitationMapper invitationMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private MeetingService meetingService;
    @MockBean
    private EmailSenderAdapter emailSenderAdapter;
    @Autowired
    private InvitationService invitationService;

    Long ownerId = 1L;
    Long invitedUserId = 2L;
    String groupId = "groupId";

    @Test
    void createInvitation() {
        // given
        InviteRequest request = givenInviteRequest();
        Invitation givenInvitation = givenInvitation();
        List<MeetingResponse> meetings = ArgumentMatchers.anyList();
        UserResponse userResponse = ArgumentMatchers.any(UserResponse.class);
        when(meetingService.getByGroupIdAndOwnerId(ownerId, groupId))
                .thenReturn(meetings);
        when(userService.getById(invitedUserId))
                .thenReturn(userResponse);
        when(invitationMapper.map(userResponse, meetings))
                .thenReturn(givenInvitation);
        // when
        Long invitationId = invitationService.createInvitation(ownerId, groupId, request);
        // then
        assertThat(invitationId).isEqualTo(givenInvitation.getId());
        InOrder order = inOrder(meetingService, userService, invitationMapper, invitationRepository, emailSenderAdapter);
        order.verify(meetingService, times(1)).getByGroupIdAndOwnerId(ownerId, groupId);
        order.verify(userService, times(1)).getById(invitedUserId);
        order.verify(invitationMapper, times(1)).map(userResponse, meetings);
        order.verify(invitationRepository, times(1)).save(givenInvitation);
        order.verify(emailSenderAdapter, times(1)).sendInvitationEmail(givenInvitation);
    }

    private Invitation givenInvitation() {
        return Invitation
                .builder()
                .id(1L)
                .invitationStatus(InvitationStatus.QUESTIONABLE)
                .build();
    }

    private InviteRequest givenInviteRequest() {
        return new InviteRequest()
                .invitedUserId(invitedUserId);
    }

    @Test
    void updateInvitationByUserIdAndMeetingId() {
        // given
        InvitationStatusResponse statusResponse = InvitationStatusResponse.REJECTED;
        InvitationStatus status = InvitationStatus.REJECTED;
        Invitation invitation = givenInvitation();
        when(invitationMapper.mapInvitationStatus(statusResponse))
                .thenReturn(status);
        when(invitationRepository.findByMeetingGroupIdAndUserId(invitedUserId, groupId))
                .thenReturn(List.of(invitation));
        // when
        invitationService.updateInvitationByUserIdAndMeetingId(invitedUserId, groupId, statusResponse);
        // then
        assertThat(invitation.getInvitationStatus()).isEqualTo(status);
        InOrder order = inOrder(invitationMapper, invitationRepository);
        order.verify(invitationMapper, times(1)).mapInvitationStatus(statusResponse);
        order.verify(invitationRepository, times(1)).findByMeetingGroupIdAndUserId(invitedUserId, groupId);
    }

    @Test
    void getByUserIdAndMeetingId() {
        // given
        Long userId = 1L;
        String meetingGroup = "meetingGroup";
        Invitation invitation = givenInvitation();
        List<Invitation> invitations = List.of(invitation);
        when(invitationRepository.findByMeetingGroupIdAndUserId(userId, meetingGroup))
                .thenReturn(invitations);
        when(invitationMapper.map(invitation))
                .thenReturn(new InviteResponse());
        // when
        InviteResponse response = invitationService.getByUserIdAndMeetingId(userId, meetingGroup);
        // then
        assertThat(response).isNotNull();

    }
}