package com.example.userservice.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserServiceException extends RuntimeException{

    private String message;
}
