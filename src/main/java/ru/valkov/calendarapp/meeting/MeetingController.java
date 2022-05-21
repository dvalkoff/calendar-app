package ru.valkov.calendarapp.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.valkov.calendarapp.openapi.controller.MeetingsApi;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;

import java.time.OffsetDateTime;
import java.util.List;

import static ru.valkov.calendarapp.exceptions.ExceptionWrapper.wrap;
import static ru.valkov.calendarapp.exceptions.ExceptionWrapper.wrapWithoutResult;

@Controller
@RequiredArgsConstructor
public class MeetingController implements MeetingsApi {
    private final MeetingService meetingService;

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
}
