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
import java.util.Optional;

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
        Optional<Job> byId = jobRepository.findById(id);
        if (byId.isPresent()) {
            jobRepository.deleteById(id);
        }
    }

    public Job findById(int id) {
        return jobRepository.getById(id);
    }

    public Page<Job> findAll(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    public List<Job> findAll() {
        return jobRepository.findAll();
    }
}
