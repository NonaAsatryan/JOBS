package com.example.jobs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    private int id;
    @NotEmpty(message = "name should be not empty")
    private String name;
    @Email
    private String email;
    private String phone;
    private String city;
    @NotEmpty(message = "password should be not empty")
    private String password;

}