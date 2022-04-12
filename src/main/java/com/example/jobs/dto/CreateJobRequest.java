package com.example.jobs.dto;


import com.example.jobs.entity.Category;
import com.example.jobs.entity.Company;
import com.example.jobs.entity.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobRequest {


    private int id;
    private String name;
    private int categoryId;
    private String description;
    private double salary;
    private JobType jobType;
    private int companyId;


}
