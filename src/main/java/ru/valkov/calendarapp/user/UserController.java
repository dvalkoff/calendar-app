package ru.valkov.calendarapp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.valkov.calendarapp.openapi.controller.UsersApi;
import ru.valkov.calendarapp.openapi.model.UserRequest;
import ru.valkov.calendarapp.openapi.model.UserResponse;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController implements UsersApi {
    private final UserService userService;

    @Override
    public ResponseEntity<Object> createUser(UserRequest userRequest) {
        Long body = userService.createUser(userRequest);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> body = userService.getUsers();
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(Long userId) {
        UserResponse body = userService.getById(userId);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> updateUser(Long userId, UserRequest userRequest) {
        userService.updateById(userId, userRequest);
        return ResponseEntity.ok().build();
    }
}
