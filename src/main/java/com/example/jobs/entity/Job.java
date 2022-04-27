package com.example.jobs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "job")
public class Job {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    private double salary;

    @ManyToOne
    private Company company;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

}
