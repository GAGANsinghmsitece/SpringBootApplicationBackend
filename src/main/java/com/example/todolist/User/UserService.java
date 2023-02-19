package com.example.todolist.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse fetchAllUsers() {
        List<User> data = userRepository.findAll();
        return UserResponse
                .builder()
                .status(true)
                .users(data)
                .build();
    }

    public boolean isUserAlreadyExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

}
