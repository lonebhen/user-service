package com.example.userservice.service.impl;

import com.example.userservice.dto.CreateUserDto;
import com.example.userservice.dto.EditPasswordDto;
import com.example.userservice.dto.EditUserDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.exception.UserServiceException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> modelMapper().map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(String userId) {

        Optional<User> userOptional = userRepository.findById(userId);

        if(!userOptional.isPresent()){
            throw new UserServiceException("There is no user with this id " + userId);
        }

        User user = userOptional.get();

        return modelMapper().map(user, UserDto.class);

    }

    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
        if (userRepository.existsByUsername(createUserDto.getUsername())) {
            throw new UserServiceException("Username is already taken");
        }


        User user = User.builder()
                .username(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .build();

        User savedUser = userRepository.save(user);

        return modelMapper().map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(EditUserDto editUserDto) {
        String userId = editUserDto.getId();
        String newUsername = editUserDto.getUsername();

        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserServiceException("User not found with this ID: " + userId);
        }

        User user = userOptional.get();
        user.setUsername(newUsername);
        User updatedUser = userRepository.save(user);

        return modelMapper().map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserServiceException("User not found with ID: " + userId);
        }

        userRepository.deleteById(userId);
    }

    @Override
    public void editPassword(EditPasswordDto editPasswordDto) {
        String userId = editPasswordDto.getId();
        String password = editPasswordDto.getPassword();
        String confirmPassword = editPasswordDto.getConfirmPassword();

        if (!password.equals(confirmPassword)) {
            throw new UserServiceException("Passwords do not match");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserServiceException("User not found with ID: " + userId);
        }

        User user = userOptional.get();
        user.setPassword(password);
        userRepository.save(user);
    }
}
