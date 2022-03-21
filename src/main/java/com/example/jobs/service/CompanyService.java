package com.example.jobs.service;

import com.example.jobs.entity.Company;
import com.example.jobs.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public List<Company> findAll() {
       return companyRepository.findAll();

    }

    public void deleteById(int id) {
        companyRepository.deleteById(id);

    }

    public void save(Company company) {
        companyRepository.save(company);
    }

    public Company getById(int id) {
        return companyRepository.getById(id);
    }

    public Optional<Company> findById(int id){
        return companyRepository.findById(id);
    }
}
