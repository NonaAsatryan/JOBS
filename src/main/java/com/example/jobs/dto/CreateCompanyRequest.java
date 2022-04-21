package com.example.jobs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompanyRequest {
    private int id;

    @NotEmpty(message = "name should not be empty")
    private String name;
    private String address;
    @Email
    private String email;
    private String mobileNumber;
//    private String companyPic;

}
