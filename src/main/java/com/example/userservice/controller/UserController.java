package com.example.userservice.controller;


import com.example.userservice.dto.CreateUserDto;
import com.example.userservice.dto.EditPasswordDto;
import com.example.userservice.dto.EditUserDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserServiceImpl userService;



    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){

        log.info("REQUEST: Get all Users");
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);

    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId){

        log.info("REQUEST: Get a user with this id " + userId);

        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);


    }

    @PostMapping(value = "/create/user")
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto createUserDto) {

        log.info("REQUEST: Create User for " + createUserDto.getUsername());

        UserDto createdUser = userService.createUser(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

    }

    @DeleteMapping(value = "/user/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable String userId) {

        log.info("REQUEST: Delete User with ID " + userId);

        userService.deleteUser(userId);
        return ResponseEntity.ok("User with ID " + userId + " has been deleted successfully");

    }

    @PutMapping(value = "/user/edit-username")
    public ResponseEntity<UserDto> editUsername(@RequestBody EditUserDto editUserDto) {

        log.info("REQUEST: Edit User's Username - ID: {}, New Username: {}", editUserDto.getId(), editUserDto.getUsername());

        UserDto updatedUser = userService.updateUser(editUserDto);
        return ResponseEntity.ok(updatedUser);

    }

    @PutMapping(value = "/user/edit-password")
    public ResponseEntity<String> editPassword(@RequestBody EditPasswordDto editPasswordDto) {

        log.info("REQUEST: Edit User's Password - ID: {}", editPasswordDto.getId());


        userService.editPassword(editPasswordDto);
        return ResponseEntity.ok("Password Changed");

    }

}
