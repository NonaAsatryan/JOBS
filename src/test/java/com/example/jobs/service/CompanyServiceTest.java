package com.example.jobs.service;

import com.example.jobs.entity.Company;
import com.example.jobs.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CompanyServiceTest {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    private Company company;

    @BeforeEach
    void beforeAllCompanies() {
        company = new Company();
        company.setId(1);
        company.setName("EPAM");
        company.setCity("Gyumri");
        company.setAddress("Gyumri");
        company.setEmail("epam@mail.com");
    }

    @Test
    void deleteById() {
        companyService.save(company);
        companyService.deleteById(company.getId());
        Optional<Company> companyById = companyRepository.findById(company.getId());
        assertFalse(companyById.isPresent());

    }
    @Test()
    void delete_notFound() {
        EmptyResultDataAccessException thrown = assertThrows(EmptyResultDataAccessException.class, () -> {
            Company deleteById = companyService.deleteById(100);
            assertNull(deleteById);
        });
        assertEquals("No class com.example.jobs.entity.Company entity with id 100 exists!", thrown.getMessage());
    }
    @Test
    void add() {

        companyService.save(company);
        assertEquals(1, companyRepository.count());
        Optional<Company> byId = companyRepository.findById(company.getId());
        assertTrue(byId.isPresent());
        assertEquals("EPAM", byId.get().getName());
        assertEquals("Gyumri", byId.get().getCity());
        assertEquals("epam@mail.com", byId.get().getEmail());

    }


}