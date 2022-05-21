package ru.valkov.calendarapp.user;

import org.junit.jupiter.api.Test;
import ru.valkov.calendarapp.openapi.model.UserRequest;
import ru.valkov.calendarapp.openapi.model.UserResponse;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void mapFromRequestToEntity() {
        // given
        User expected = givenUser();
        UserRequest input = givenUserRequest();
        // when
        User actual = userMapper.map(input);
        // then
        assertThat(expected.getEmail()).isEqualTo(actual.getEmail());
        assertThat(expected.getPassword()).isEqualTo(actual.getPassword());
        assertThat(expected.getFirstName()).isEqualTo(actual.getFirstName());
        assertThat(expected.getMiddleName()).isEqualTo(actual.getMiddleName());
        assertThat(expected.getLastName()).isEqualTo(actual.getLastName());
    }

    @Test
    void mapFromEntityToResponse() {
        // given
        UserResponse expected = givenUserResponse();
        User input = givenUser();
        // when
        UserResponse actual = userMapper.map(input);
        // then
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void mapFromResponseToEntity() {
        // given
        User expected = givenUser();
        UserResponse input = givenUserResponse();
        // when
        User actual = userMapper.map(input);
        // then
        assertThat(expected.getId()).isEqualTo(actual.getId());
        assertThat(expected.getEmail()).isEqualTo(actual.getEmail());
        assertThat(expected.getFirstName()).isEqualTo(actual.getFirstName());
        assertThat(expected.getMiddleName()).isEqualTo(actual.getMiddleName());
        assertThat(expected.getLastName()).isEqualTo(actual.getLastName());
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

    private UserResponse givenUserResponse() {
        return new UserResponse()
                .id(1L)
                .email("email")
                .firstName("firstName")
                .middleName("middleName")
                .lastName("lastName");
    }

    private UserRequest givenUserRequest() {
        return new UserRequest()
                .password("password")
                .email("email")
                .firstName("firstName")
                .middleName("middleName")
                .lastName("lastName");
    }
}