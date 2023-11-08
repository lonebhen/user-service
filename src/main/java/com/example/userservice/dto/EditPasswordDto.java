package com.example.userservice.dto;


import lombok.Data;

@Data
public class EditPasswordDto {

    String id;
    String password;
    String confirmPassword;
}
