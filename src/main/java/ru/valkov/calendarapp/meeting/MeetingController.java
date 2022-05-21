package ru.valkov.calendarapp.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.valkov.calendarapp.openapi.controller.MeetingsApi;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;

import java.time.OffsetDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MeetingController implements MeetingsApi {
    private final MeetingService meetingService;

    @Override
    public ResponseEntity<Object> createMeeting(Long usersId, MeetingRequest meetingRequest) {
        String body = meetingService.createMeeting(usersId, meetingRequest);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<MeetingResponse>> getMeetings(Long usersId) {
        List<MeetingResponse> body = meetingService.getMeetings(usersId);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> deleteMeetingByGroupId(Long usersId, String meetingGroupId) {
        meetingService.deleteByOwnerIdAndGroupId(usersId,  meetingGroupId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<MeetingResponse>> getMeetingByGroupId(Long usersId, String meetingGroupId) {
        List<MeetingResponse> body = meetingService.getByOwnerIdAndGroupId(usersId, meetingGroupId);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> updateMeeting(Long usersId, String meetingGroupId, MeetingRequest meetingRequest) {
        meetingService.updateByOwnerIdAndGroupId(usersId, meetingGroupId, meetingRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<MeetingResponse>> getMeetingsByStartTimeAndEndTime(Long userId, OffsetDateTime from, OffsetDateTime to) {
        List<MeetingResponse> body = meetingService.getMeetingsByStartTimeAndEndTime(userId, from, to);
        return ResponseEntity.ok(body);
    }
}
