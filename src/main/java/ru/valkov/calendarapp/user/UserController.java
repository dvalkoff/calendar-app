package ru.valkov.calendarapp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.valkov.calendarapp.openapi.controller.UsersApi;
import ru.valkov.calendarapp.openapi.model.UserRequest;
import ru.valkov.calendarapp.openapi.model.UserResponse;

import java.util.List;

import static ru.valkov.calendarapp.exceptions.ExceptionWrapper.wrap;
import static ru.valkov.calendarapp.exceptions.ExceptionWrapper.wrapWithoutResult;

@Controller
@RequiredArgsConstructor
public class UserController implements UsersApi {
    private final UserService userService;

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
}
