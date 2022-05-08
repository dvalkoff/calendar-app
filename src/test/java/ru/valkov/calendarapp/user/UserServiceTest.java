package ru.valkov.calendarapp.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.valkov.calendarapp.exceptions.BadRequestException;
import ru.valkov.calendarapp.exceptions.NotFoundException;
import ru.valkov.calendarapp.openapi.model.UserRequest;
import ru.valkov.calendarapp.openapi.model.UserResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {UserService.class}
)
class UserServiceTest {
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    void createUserUniqueEmail() {
        // given
        User user = givenUser();
        when(userRepository.existsByEmail(any()))
                .thenReturn(false);
        when(userMapper.map(any(UserRequest.class)))
                .thenReturn(user);
        // when
        Long userId = userService.createUser(new UserRequest());
        // then
        assertThat(userId).isEqualTo(user.getId());
        InOrder order = inOrder(userRepository, userMapper);
        order.verify(userRepository, times(1))
                .existsByEmail(any());
        order.verify(userMapper, times(1))
                .map(any(UserRequest.class));
        order.verify(userRepository, times(1))
                .save(user);
    }

    @Test
    void createUserNonUniqueEmail() {
        // given
        User user = givenUser();
        when(userRepository.existsByEmail(any()))
                .thenReturn(true);
        // when
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> userService.createUser(new UserRequest()));
        // then
        InOrder order = inOrder(userRepository, userMapper);
        order.verify(userRepository, times(1))
                .existsByEmail(any());
        order.verify(userMapper, never())
                .map(any(UserRequest.class));
        order.verify(userRepository, never())
                .save(user);
    }

    @Test
    void deleteById() {
        // given
        Long id = 1L;
        // when
        userService.deleteById(id);
        // then
        verify(userRepository, times(1))
                .deleteById(id);
    }

    @Test
    void getByIdWhenUserExists() {
        // given
        Long id = 1L;
        User user = givenUser();
        UserResponse response = new UserResponse();
        when(userRepository.findById(id))
                .thenReturn(Optional.of(givenUser()));
        when(userMapper.map(user))
                .thenReturn(response);
        // when
        UserResponse userResponse = userService.getById(id);
        // then
        assertThat(response).isEqualTo(userResponse);
        InOrder order = inOrder(userRepository, userMapper);
        order.verify(userRepository, times(1))
                .findById(id);
        order.verify(userMapper, times(1))
                .map(user);
    }

    @Test
    void getByIdWhenUserDoesNotExist() {
        // given
        Long id = 1L;
        User user = givenUser();
        UserResponse response = new UserResponse();
        when(userRepository.findById(id))
                .thenReturn(Optional.empty());
        when(userMapper.map(user))
                .thenReturn(response);
        // when
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userService.getById(id));
        // then
        InOrder order = inOrder(userRepository, userMapper);
        order.verify(userRepository, times(1)).findById(id);
        order.verify(userMapper, never()).map(user);
    }

    @Test
    void updateByIdWhenEmailIsUnique() {
        // given
        User user = givenUser();
        UserRequest request = new UserRequest().email(user.getEmail());
        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);
        when(userMapper.map(request)).thenReturn(user);
        // when
        userService.updateById(user.getId(), request);
        // then
        InOrder order = inOrder(userRepository, userMapper);
        order.verify(userRepository, times(1))
                .existsByEmail(any(String.class));
        order.verify(userMapper, times(1))
                .map(request);
        order.verify(userRepository, times(1))
                .save(user);
    }

    @Test
    void updateByIdWhenEmailIsNotUnique() {
        // given
        User user = givenUser();
        UserRequest request = new UserRequest().email(user.getEmail());
        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);
        when(userMapper.map(request)).thenReturn(user);
        // when
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> userService.updateById(user.getId(), request));
        // then
        InOrder order = inOrder(userRepository, userMapper);
        order.verify(userRepository, times(1))
                .existsByEmail(any(String.class));
        order.verify(userMapper, never())
                .map(request);
        order.verify(userRepository, never())
                .save(user);
    }

    private User givenUser() {
        return User
                .builder()
                .id(1L)
                .email("email")
                .build();
    }
}