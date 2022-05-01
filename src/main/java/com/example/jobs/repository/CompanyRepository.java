package com.example.jobs.repository;

import com.example.jobs.entity.Company;
import com.example.jobs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Integer> {

    List<Company> findByUser_Id(int userId);
//    List<Company> findCompaniesBy
}
