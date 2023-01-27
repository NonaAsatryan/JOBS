package com.example.jobs.dto;


import com.example.jobs.entity.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobRequest {


    private int id;
    @NotEmpty(message = "Name can't be empty")
    private String name;
    private int categoryId;
    private String description;
    private double salary;
    private JobType jobType;
    private int companyId;


}
