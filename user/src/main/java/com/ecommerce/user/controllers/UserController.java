package com.ecommerce.user.controllers;

import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private Object user;

    @GetMapping(path = "/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(),
                HttpStatus.OK);
    }

    @GetMapping(path = "/api/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {

        return userService.fetchUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/users")
    public ResponseEntity<String>  createUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return new ResponseEntity<>("User created",HttpStatus.CREATED);
    }
    @PutMapping(path = "/api/users/{id}")
    public ResponseEntity<String>  updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        userService.updateUser(id, userRequest);
        return new ResponseEntity<>("User updated",HttpStatus.OK);
    }
}
