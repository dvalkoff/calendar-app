package ru.valkov.calendarapp.meeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.valkov.calendarapp.invite.InvitationRepository;
import ru.valkov.calendarapp.mail.sender.EmailSenderAdapter;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.PeriodicityResponse;
import ru.valkov.calendarapp.openapi.model.UserResponse;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserMapper;
import ru.valkov.calendarapp.user.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MeetingService.class})
class MeetingServiceTest {
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private MeetingRepository meetingRepository;
    @MockBean
    private MeetingMapper meetingMapper;
    @MockBean
    private InvitationRepository invitationRepository;
    @MockBean
    private EmailSenderAdapter emailSenderAdapter;
    @Autowired
    private MeetingService meetingService;

    @Test
    void createMeeting() {
        // given
        Long userId = 1L;
        User user = givenUser();
        MeetingRequest request = givenMeetingRequest();
        UserResponse response = new UserResponse();
        Meeting meeting = givenMeeting();
        when(userService.getById(userId)).thenReturn(response);
        when(userMapper.map(response)).thenReturn(user);
        when(meetingMapper.map(request, user, Optional.empty())).thenReturn(meeting);
        // when
        String groupId = meetingService.createMeeting(userId, request);
        // then
        assertThat(groupId).isEqualTo(meeting.getGroupId());
        InOrder order = inOrder(userService, userMapper, meetingMapper);
        order.verify(userService, times(1)).getById(userId);
        order.verify(userMapper, times(1)).map(any(UserResponse.class));
        order.verify(meetingMapper, times(1)).map(request, user, Optional.empty());
        meetingRepository.saveAll(anyList());
    }

    @Test
    void deleteByOwnerIdAndGroupId() {
        // given
        Long ownerId = 1L;
        String groupId = "groupId";
        // when
        meetingService.deleteByOwnerIdAndGroupId(ownerId, groupId);
        // then
        verify(meetingRepository, times(1))
                .deleteByOwnerIdAndGroupId(ownerId, groupId);
    }

    private User givenUser() {
        return User.builder().build();
    }

    private MeetingRequest givenMeetingRequest() {
        return new MeetingRequest()
                .beginDateTime(OffsetDateTime.of(2022, 1, 1,11,0,0,0, ZoneOffset.UTC))
                .endDateTime(OffsetDateTime.of(2022, 1, 1,12,0,0,0, ZoneOffset.UTC))
                .until(LocalDate.of(2022, 3, 1))
                .name("Daily meeting")
                .periodicity(PeriodicityResponse.MONTH)
                .location("tinkoff.zoom.ru");
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
}