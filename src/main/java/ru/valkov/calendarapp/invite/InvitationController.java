package ru.valkov.calendarapp.invite;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.valkov.calendarapp.openapi.controller.InvitationsApi;
import ru.valkov.calendarapp.openapi.model.InvitationStatusResponse;
import ru.valkov.calendarapp.openapi.model.InviteRequest;
import ru.valkov.calendarapp.openapi.model.InviteResponse;

import static ru.valkov.calendarapp.exceptions.ExceptionWrapper.wrap;
import static ru.valkov.calendarapp.exceptions.ExceptionWrapper.wrapWithoutResult;

@Controller
@RequiredArgsConstructor
public class InvitationController implements InvitationsApi {
    private final InvitationService invitationService;

    @Override
    public ResponseEntity<Object> createInvitation(Long userId, String meetingGroupId, InviteRequest inviteRequest) {
        return wrap(invitationService::createInvitation, userId, meetingGroupId, inviteRequest);
    }

    @Override
    public ResponseEntity<InviteResponse> getInvitationByUserIdAndMeetingId(Long userId, String meetingGroupId) {
        return wrap(invitationService::getByUserIdAndMeetingId, userId, meetingGroupId);
    }

    @Override
    public ResponseEntity<Void> updateInvitationByUserIdAndMeetingId(Long userId, String meetingGroupId, InvitationStatusResponse invitationStatus) {
        return wrapWithoutResult(invitationService::updateInvitationByUserIdAndMeetingId, userId, meetingGroupId, invitationStatus);
    }
}
