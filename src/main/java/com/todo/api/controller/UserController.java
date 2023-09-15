package com.todo.api.controller;

import com.todo.api.model.request.UserRequest;
import com.todo.api.model.response.UserResponse;
import com.todo.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public void updateUser(@RequestBody UserRequest newUser, @PathVariable Long id) {
        userService.updateUser(newUser, id);
    }
}
