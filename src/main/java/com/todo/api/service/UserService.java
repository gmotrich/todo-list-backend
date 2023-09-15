package com.todo.api.service;

import com.todo.api.model.User;
import com.todo.api.model.request.UserRequest;
import com.todo.api.model.response.UserResponse;
import com.todo.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        UserResponse response = new UserResponse();
        response.setPassword(user.getPassword());
        return response;
    }

    public void updateUser(UserRequest newUser, Long id) {
        userRepository.findById(id)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newUser.getPassword()));
                    return userRepository.save(user);
                }).orElseThrow(NoSuchElementException::new);
        log.info("The user - {} has successfully changed the password", id);
    }

}
