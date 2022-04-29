package com.example.jobs.service;

import com.example.jobs.entity.*;
import com.example.jobs.repository.CategoryRepository;
import com.example.jobs.repository.CompanyRepository;
import com.example.jobs.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Page<Job> findAll(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    public List<Job> search(String keyword) {
        List<Job> all = jobRepository.findAll();
        List<Job> findByKeyword = new ArrayList<>();
        all.forEach(job -> {
            if (job.getName().contains(keyword)) {
                findByKeyword.add(job);
            }
        });
        return findByKeyword;
    }

    public Job getById(int id) {
        Optional<Job> byId = jobRepository.findById(id);
        return byId.orElse(null);
    }

    public void updateJob(Job job, int id, User user) {
        Optional<Job> byId = jobRepository.findById(id);
        if (byId.isPresent()) {
            Job job1 = byId.get();
            if (job.getName() != null) {
                job1.setName(job.getName());
            }
            if (job.getDescription() != null) {
                job1.setDescription(job.getDescription());
            }
            if (job.getJobType() != null) {
                job1.setJobType(job.getJobType());
            }
            if (job.getSalary() != 0) {
                job1.setSalary(job.getSalary());
            }
            if (job.getCompany() != null) {
                job1.setCompany(job.getCompany());
            }
            if (job.getCategory() != null) {
                job1.setCategory(job.getCategory());
            }

            jobRepository.save(job1);
        }
    }

}
