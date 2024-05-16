package com.example.firebase.controllers;

import com.example.firebase.models.User;
import com.example.firebase.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public String createUser(@RequestBody User user) throws ExecutionException, InterruptedException {
        return userService.addUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) throws ExecutionException, InterruptedException {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable String id, @RequestBody User user) throws ExecutionException, InterruptedException {
        user.setId(id);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) throws ExecutionException, InterruptedException {
        return userService.deleteUser(id);
    }
}
