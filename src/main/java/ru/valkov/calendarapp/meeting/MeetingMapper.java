package ru.valkov.calendarapp.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;
import ru.valkov.calendarapp.openapi.model.PeriodicityResponse;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserMapper;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class MeetingMapper {

    private final UserMapper userMapper;
    private final static ZoneOffset zone = ZoneOffset.UTC;

    public Meeting map(MeetingRequest request, User user, Optional<String> groupId) {
        return Meeting.builder()
                .groupId(groupId.orElse(UUID.randomUUID().toString()))
                .name(request.getName())
                .beginDateTime(request.getBeginDateTime().toLocalDateTime())
                .endDateTime(request.getEndDateTime().toLocalDateTime())
                .location(request.getLocation())
                .description(request.getDescription())
                .owner(user)
                .periodicity(mapPeriodicity(request))
                .until(request.getUntil())
                .build();
    }

    public List<MeetingResponse> mapAll(Set<Meeting> meetings) {
        return meetings
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public MeetingResponse map(Meeting meeting) {
        return new MeetingResponse()
                .id(meeting.getId())
                .groupId(meeting.getGroupId())
                .name(meeting.getName())
                .beginDateTime(meeting.getBeginDateTime().atOffset(zone))
                .endDateTime(meeting.getEndDateTime().atOffset(zone))
                .location(meeting.getLocation())
                .description(meeting.getDescription())
                .periodicity(mapPeriodicity(meeting))
                .until(meeting.getUntil())
                .owner(userMapper.map(meeting.getOwner()));
    }

    public Meeting map(MeetingResponse meetingResponse, User user) {
        return Meeting.builder()
                .id(meetingResponse.getId())
                .groupId(meetingResponse.getGroupId())
                .name(meetingResponse.getName())
                .beginDateTime(meetingResponse.getBeginDateTime().toLocalDateTime())
                .endDateTime(meetingResponse.getEndDateTime().toLocalDateTime())
                .location(meetingResponse.getLocation())
                .description(meetingResponse.getDescription())
                .owner(user)
                .periodicity(mapPeriodicity(meetingResponse))
                .until(meetingResponse.getUntil())
                .build();
    }

    public Periodicity mapPeriodicity(MeetingResponse meetingResponse) {
        return Periodicity.valueOf(meetingResponse.getPeriodicity().getValue());
    }

    public Periodicity mapPeriodicity(MeetingRequest meetingRequest) {
        return Periodicity.valueOf(meetingRequest.getPeriodicity().getValue());
    }

    public PeriodicityResponse mapPeriodicity(Meeting meeting) {
        return PeriodicityResponse.valueOf(meeting.getPeriodicity().name());
    }

    public List<MeetingResponse> mapAll(List<Meeting> meetings) {
        return meetings
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public Set<Meeting> mapAll(List<MeetingResponse> meetings, User user) {
        return meetings
                .stream()
                .map(meeting -> map(meeting, user))
                .collect(Collectors.toSet());
    }
}
