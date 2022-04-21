package com.example.jobs.service;

import com.example.jobs.entity.*;
import com.example.jobs.repository.CategoryRepository;
import com.example.jobs.repository.CompanyRepository;
import com.example.jobs.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;


    public Job save(Job job, User user, int categoryId, int companyId) {
        Category category = categoryRepository.getById(categoryId);
        job.setCategory(category);
        Company company = companyRepository.getById(companyId);
        job.setCompany(company);
        job.setUser(user);

        return jobRepository.save(job);
    }

    public void deleteById(int id) {
        jobRepository.deleteById(id);
    }

    public Job findById(int id) {
        return jobRepository.getById(id);
    }

    public Page<Job> findAll(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }
}
