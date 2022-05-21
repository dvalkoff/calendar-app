package ru.valkov.calendarapp.invite;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.valkov.calendarapp.meeting.MeetingMapper;
import ru.valkov.calendarapp.openapi.model.InviteResponse;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;
import ru.valkov.calendarapp.openapi.model.PeriodicityResponse;
import ru.valkov.calendarapp.openapi.model.UserResponse;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InvitationMapperTest {
    @Mock
    private UserMapper userMapper;
    @Mock
    private MeetingMapper meetingMapper;
    @InjectMocks
    private InvitationMapper invitationMapper;

    @Test
    void mapToEntity() {
        // given
        MeetingResponse meetingResponse = givenMeetingResponse();
        UserResponse userResponse = givenUser();
        // when
        Invitation map = invitationMapper.map(userResponse, List.of(meetingResponse));
        // then
        assertThat(map).isNotNull();
        verify(userMapper).map(any(UserResponse.class));
    }

    @Test
    void mapToResponse() {
        // given
        Invitation invitation = Invitation.builder()
                .invitedUser(new User())
                .meetings(Set.of())
                .invitationStatus(InvitationStatus.QUESTIONABLE)
                .build();
        // when
        InviteResponse response = invitationMapper.map(invitation);
        // then
        assertThat(response).isNotNull();
        verify(userMapper).map(any(User.class));
        verify(meetingMapper).mapAll(anySet());
    }

    private MeetingResponse givenMeetingResponse() {
        return new MeetingResponse()
                .groupId("group_id")
                .beginDateTime(LocalDateTime.of(2022, 1, 1,11,0,0,0).atOffset(ZoneOffset.UTC))
                .endDateTime(LocalDateTime.of(2022, 1, 1,12,0,0,0).atOffset(ZoneOffset.UTC))
                .until(LocalDate.of(2022, 3, 1))
                .periodicity(PeriodicityResponse.MONTH);
    }

    private UserResponse givenUser() {
        return new UserResponse()
                .id(1L)
                .email("email");
    }
}