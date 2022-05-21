package ru.valkov.calendarapp.invite;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.valkov.calendarapp.openapi.controller.InvitationsApi;
import ru.valkov.calendarapp.openapi.model.InvitationStatusResponse;
import ru.valkov.calendarapp.openapi.model.InviteRequest;
import ru.valkov.calendarapp.openapi.model.InviteResponse;

@Controller
@RequiredArgsConstructor
public class InvitationController implements InvitationsApi {
    private final InvitationService invitationService;

    @Override
    public ResponseEntity<Object> createInvitation(Long userId, String meetingGroupId, InviteRequest inviteRequest) {
        Long body = invitationService.createInvitation(userId, meetingGroupId, inviteRequest);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<InviteResponse> getInvitationByUserIdAndMeetingId(Long userId, String meetingGroupId) {
        InviteResponse body = invitationService.getByUserIdAndMeetingId(userId, meetingGroupId);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> updateInvitationByUserIdAndMeetingId(Long userId, String meetingGroupId, InvitationStatusResponse invitationStatus) {
        invitationService.updateInvitationByUserIdAndMeetingId( userId, meetingGroupId, invitationStatus);
        return ResponseEntity.ok().build();
    }
}
