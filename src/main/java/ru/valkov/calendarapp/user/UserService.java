package ru.valkov.calendarapp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.valkov.calendarapp.exceptions.BadRequestException;
import ru.valkov.calendarapp.exceptions.NotFoundException;
import ru.valkov.calendarapp.openapi.model.UserRequest;
import ru.valkov.calendarapp.openapi.model.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Long createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BadRequestException("This email exists");
        }
        User user = userMapper.map(userRequest);
        userRepository.save(user);
        return user.getId();
    }

    public List<UserResponse> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::map)
                .collect(Collectors.toList());
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserResponse getById(Long userId) {
        return userRepository
                .findById(userId)
                .map(userMapper::map)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public void updateById(Long userId, UserRequest userRequest){
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BadRequestException("This email exists");
        }
        User updatedUser = userMapper.map(userRequest);
        updatedUser.setId(userId);
        userRepository.save(updatedUser);
    }

}
