package com.example.jobs.service;

import com.example.jobs.entity.Category;
import com.example.jobs.entity.Company;
import com.example.jobs.entity.User;
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
    public List<Company> findByUserId(int id) {
        return companyRepository.findByUser_Id(id);
    }

    public Company deleteById(int id) {
        companyRepository.deleteById(id);
        return null;

    }
    public Company add(Company company, User user) {
        company.setUser(user);
//        company.setCategory(category);
        companyRepository.save(company);
        return company;
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
