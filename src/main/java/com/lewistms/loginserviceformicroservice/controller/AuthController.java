
package com.lewistms.loginserviceformicroservice.controller;

import com.lewistms.loginserviceformicroservice.entity.UserEntity;
import com.lewistms.loginserviceformicroservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody UserEntity user) {
        return userService.registerUser(user);
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
