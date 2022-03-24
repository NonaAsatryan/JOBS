package com.example.jobs.service;

import com.example.jobs.entity.Job;
import com.example.jobs.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Job save(Job job) {
        return jobRepository.save(job);
    }

    public void deleteById(int id) {
        jobRepository.deleteById(id);
    }

    public Job findById(int id) {
        return jobRepository.getById(id);
    }

    public List<Job> findAll() {
        return jobRepository.findAll();
    }
}
