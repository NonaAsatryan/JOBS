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
@Table(name = "resume")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String personalDetails;
    private String education;
    private String workExperience;
    private String professionalSkills;
    private String qualifications;
    private String languageProficiency;
    private String awards;
    private String picUrl;

    @ManyToOne
    private User user;
}
