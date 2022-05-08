package ru.valkov.calendarapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.valkov.calendarapp.meeting.MeetingService;
import ru.valkov.calendarapp.openapi.controller.UsersApi;
import ru.valkov.calendarapp.invite.InvitationService;
import ru.valkov.calendarapp.openapi.model.UserResponse;
import ru.valkov.calendarapp.openapi.model.UserRequest;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;
import ru.valkov.calendarapp.openapi.model.InviteRequest;
import ru.valkov.calendarapp.openapi.model.InviteResponse;
import ru.valkov.calendarapp.openapi.model.InvitationStatusResponse;
import ru.valkov.calendarapp.user.UserService;

import java.time.OffsetDateTime;
import java.util.List;

import static ru.valkov.calendarapp.exceptions.ExceptionWrapper.wrap;
import static ru.valkov.calendarapp.exceptions.ExceptionWrapper.wrapWithoutResult;

@RequiredArgsConstructor
@Controller
public class RestController implements UsersApi {
    private final UserService userService;
    private final MeetingService meetingService;
    private final InvitationService invitationService;

    /* Users REST methods */
    @Override
    public ResponseEntity<Object> createUser(UserRequest userRequest) {
        return wrap(userService::createUser, userRequest);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsers() {
        return wrap(userService::getUsers);
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Long userId) {
        return wrapWithoutResult(userService::deleteById, userId);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(Long userId) {
        return wrap(userService::getById, userId);
    }

    @Override
    public ResponseEntity<Void> updateUser(Long userId, UserRequest userRequest) {
        return wrapWithoutResult(userService::updateById, userId, userRequest);
    }

    /* Meetings REST methods */
    @Override
    public ResponseEntity<Object> createMeeting(Long usersId, MeetingRequest meetingRequest) {
        return wrap(meetingService::createMeeting, usersId, meetingRequest);
    }

    @Override
    public ResponseEntity<List<MeetingResponse>> getMeetings(Long usersId) {
        return wrap(meetingService::getMeetings, usersId);
    }

    @Override
    public ResponseEntity<Void> deleteMeetingByGroupId(Long usersId, String meetingGroupId) {
        return wrapWithoutResult(meetingService::deleteByOwnerIdAndGroupId, usersId,  meetingGroupId);
    }

    @Override
    public ResponseEntity<List<MeetingResponse>> getMeetingByGroupId(Long usersId, String meetingGroupId) {
        return wrap(meetingService::getByOwnerIdAndGroupId, usersId,  meetingGroupId);
    }

    @Override
    public ResponseEntity<Void> updateMeeting(Long usersId, String meetingGroupId, MeetingRequest meetingRequest) {
        return wrapWithoutResult(meetingService::updateByOwnerIdAndGroupId, usersId, meetingGroupId, meetingRequest);
    }

    @Override
    public ResponseEntity<List<MeetingResponse>> getMeetingsByStartTimeAndEndTime(Long userId, OffsetDateTime from, OffsetDateTime to) {
        return wrap(meetingService::getMeetingsByStartTimeAndEndTime, userId, from, to);
    }

    /* Invitations REST methods */
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
