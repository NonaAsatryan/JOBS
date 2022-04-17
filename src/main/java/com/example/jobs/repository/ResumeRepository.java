package com.example.jobs.repository;

import com.example.jobs.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    Resume findByUser_Id(int userId);
}
