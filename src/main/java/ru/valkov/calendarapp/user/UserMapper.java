package ru.valkov.calendarapp.user;

import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.openapi.model.UserRequest;
import ru.valkov.calendarapp.openapi.model.UserResponse;

@Component
public class UserMapper {

    public User map(UserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName( ))
                .build();
    }

    public UserResponse map(User user) {
        return new UserResponse()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName());
    }

    public User map(UserResponse response) {
        return User.builder()
                .id(response.getId())
                .email(response.getEmail())
                .firstName(response.getFirstName())
                .middleName(response.getMiddleName())
                .lastName(response.getLastName( ))
                .build();
    }
}
