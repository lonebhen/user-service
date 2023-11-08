package com.example.userservice.service;

import com.example.userservice.dto.CreateUserDto;
import com.example.userservice.dto.EditPasswordDto;
import com.example.userservice.dto.EditUserDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(String userId);

    UserDto createUser(CreateUserDto createUserDto);

    UserDto updateUser(EditUserDto editUserDto);

    void deleteUser(String userId);

    void editPassword(EditPasswordDto editPasswordDto);



}
