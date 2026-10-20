
package com.lewistms.loginserviceformicroservice.controller;

import com.lewistms.loginserviceformicroservice.entity.UserEntity;
import com.lewistms.loginserviceformicroservice.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserGraphQLController {

    private final UserService userService;

    public UserGraphQLController(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    public List<UserEntity> users() {
        return userService.getAllUsers();
    }

    @QueryMapping
    public UserEntity userById(@Argument Long id) {
        return userService.getUserById(id);
    }
}
