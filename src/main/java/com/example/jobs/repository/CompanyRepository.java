package com.example.jobs.repository;

import com.example.jobs.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Integer> {

//    List<Company> findAll(User user);
}
