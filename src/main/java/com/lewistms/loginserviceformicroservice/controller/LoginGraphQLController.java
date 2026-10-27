
package com.lewistms.loginserviceformicroservice.controller;

import com.lewistms.loginserviceformicroservice.entity.UserEntity;
import com.lewistms.loginserviceformicroservice.repository.UserRepository;
import graphql.schema.DataFetchingEnvironment;
import jakarta.servlet.http.HttpSession;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class LoginGraphQLController {

    private final UserRepository userRepository;

    public LoginGraphQLController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryMapping
    public UserEntity login(
            @Argument String username,
            @Argument String password) {

        UserEntity user = userRepository.findByUsername(username)
                .filter(existingUser ->
                        existingUser.getPassword().equals(password))
                .orElse(null);

        if (user != null) {
            HttpSession session = getSession(true);
            session.setAttribute("currentUserId", user.getId());
        }

        return user;
    }

    @QueryMapping
    public UserEntity currentUser() {
        HttpSession session = getSession(false);

        if (session == null) {
            return null;
        }

        Object userId = session.getAttribute("currentUserId");

        if (userId == null) {
            return null;
        }

        return userRepository.findById(
                Long.valueOf(userId.toString())
        ).orElse(null);
    }

    @MutationMapping
    public boolean logout() {
        HttpSession session = getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return true;
    }

    private HttpSession getSession(boolean create) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

        if (!(attributes instanceof ServletRequestAttributes servletAttributes)) {
            return null;
        }

        return create
                ? servletAttributes.getRequest().getSession(true)
                : servletAttributes.getRequest().getSession(false);
    }
}
