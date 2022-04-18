package com.example.jobs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompanyRequest {
    private int id;
    private String name;
    private String address;
    private String email;
    private String mobileNumber;
//    private String companyPic;

}
