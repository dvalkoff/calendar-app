package ru.valkov.calendarapp.meeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;
import ru.valkov.calendarapp.openapi.model.PeriodicityResponse;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MeetingMapperTest {
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private MeetingMapper meetingMapper;

    @Test
    void mapFromRequestToEntity() {
        // given
        MeetingRequest request = givenMeetingRequest();
        User user = givenUser();
        String groupId = "groupId";
        // when
        Meeting mappedMeeting = meetingMapper.map(request, user, Optional.of(groupId));
        // then
        assertThat(mappedMeeting).isNotNull();
        assertThat(mappedMeeting.getName()).isEqualTo(request.getName());
        assertThat(mappedMeeting.getGroupId()).isEqualTo(groupId);
        assertThat(mappedMeeting.getOwner()).isEqualTo(user);
    }

    @Test
    void mapAllFromEntityToResponse() {
        // given
        Set<Meeting> meetings = Set.of(givenMeeting());
        // when
        List<MeetingResponse> mappedMeetings = meetingMapper.mapAll(meetings);
        // then
        assertThat(meetings.size()).isEqualTo(mappedMeetings.size());
        verify(userMapper).map(nullable(User.class));
    }

    @Test
    void testMap1() {
        // given

        // when

        // then
    }

    @Test
    void mapPeriodicity() {
        // given

        // when

        // then
    }

    @Test
    void testMapPeriodicity() {
        // given

        // when

        // then
    }

    @Test
    void testMapPeriodicity1() {
        // given

        // when

        // then
    }

    @Test
    void mapAllFromEntitiesToResponse() {
        // given
        List<Meeting> meetings = List.of(givenMeeting());
        // when
        List<MeetingResponse> mappedMeetings = meetingMapper.mapAll(meetings);
        // then
        assertThat(meetings.size()).isEqualTo(mappedMeetings.size());
        verify(userMapper).map(nullable(User.class));
    }

    @Test
    void mapAllFromResponsesToEntities() {
        // given
        User user = givenUser();
        List<MeetingResponse> responses = List.of(givenMeetingResponse());
        // when
        Set<Meeting> meetings = meetingMapper.mapAll(responses, user);
        // then
        assertThat(meetings.size()).isEqualTo(responses.size());
    }


    private Meeting givenMeeting() {
        return Meeting.builder()
                .groupId("group_id")
                .beginDateTime(LocalDateTime.of(2022, 1, 1,11,0,0,0))
                .endDateTime(LocalDateTime.of(2022, 1, 1,12,0,0,0))
                .until(LocalDate.of(2022, 3, 1))
                .periodicity(Periodicity.MONTH)
                .build();
    }

    private MeetingRequest givenMeetingRequest() {
        return new MeetingRequest()
                .beginDateTime(LocalDateTime.of(2022, 1, 1,11,0,0,0).atOffset(ZoneOffset.UTC))
                .endDateTime(LocalDateTime.of(2022, 1, 1,12,0,0,0).atOffset(ZoneOffset.UTC))
                .until(LocalDate.of(2022, 3, 1))
                .periodicity(PeriodicityResponse.MONTH);
    }

    private MeetingResponse givenMeetingResponse() {
        return new MeetingResponse()
                .beginDateTime(LocalDateTime.of(2022, 1, 1,11,0,0,0).atOffset(ZoneOffset.UTC))
                .endDateTime(LocalDateTime.of(2022, 1, 1,12,0,0,0).atOffset(ZoneOffset.UTC))
                .until(LocalDate.of(2022, 3, 1))
                .periodicity(PeriodicityResponse.MONTH);
    }

    private User givenUser() {
        return User
                .builder()
                .id(1L)
                .password("password")
                .email("email")
                .firstName("firstName")
                .middleName("middleName")
                .lastName("lastName")
                .build();
    }


}