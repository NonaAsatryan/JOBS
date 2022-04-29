package com.example.jobs.service;

import com.example.jobs.dto.CreateJobRequest;
import com.example.jobs.entity.*;
import com.example.jobs.repository.CategoryRepository;
import com.example.jobs.repository.CompanyRepository;
import com.example.jobs.repository.JobRepository;
import com.example.jobs.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class JobServiceTest {

    @Autowired
    private JobService jobService = Mockito.mock(JobService.class);
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private JobRepository jobRepository;


    private CreateJobRequest createJobRequest;
    private User user;
    private Category category;
    private Company company;

    @BeforeEach
    void beforeAllUsr() {
        user = new User();
        user.setName("poxos");
        user.setSurname("poxosyan");
        user.setEmail("poxos@mail.com");
        user.setPassword("poxos");
        user.setGender(Gender.FEMALE);
        user.setPhone("21541541");
        user.setUserType(UserType.EMPLOYER);
        user.setActive(true);
    }

    @BeforeEach
    void beforeAllCategory() {
        category = new Category();
        category.setId(1);
        category.setName("HR/Org. Development");
    }

    @BeforeEach
    void beforeAllCompany() {
        company = new Company();
        company.setId(1);
        company.setName("ITSpase Academy");
        company.setCity("Gyumri");
        company.setAddress("hghfhcf");
        company.setEmail("ITSpaseAcademy@mail.com");
    }

    @BeforeEach
    void beforeAll() {
        createJobRequest = new CreateJobRequest();
        createJobRequest.setId(1);
        createJobRequest.setName("developer");
        createJobRequest.setDescription("hgv hgvhg ghvgv");
        createJobRequest.setJobType(JobType.FUll_TIME);
        createJobRequest.setCategoryId(1);
        createJobRequest.setCompanyId(1);
        createJobRequest.setSalary(125644);
    }

    @Test
    void save() {
        Job job = mapper.map(createJobRequest, Job.class);
        JpaObjectRetrievalFailureException thrown = assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            jobService.save(job, user, 1, 1);
        });
        assertEquals("Unable to find com.example.jobs.entity.Category with id 1; nested exception is javax.persistence.EntityNotFoundException: Unable to find com.example.jobs.entity.Category with id 1",
                thrown.getMessage());
    }
//    @Test
//    void save_nn() {
//        Job job = mapper.map(createJobRequest, Job.class);
//        Mockito.when(categoryRepository.getById(1)).thenReturn(category);
//        Mockito.when(companyRepository.getById(1)).thenReturn(company);
//            jobService.save(job, user, 1, 1);
//            assertEquals("developer",job.getName());
//    }

    @Test
    void save_job() {
        companyRepository.save(company);
        categoryRepository.save(category);
        userRepository.save(user);
        Job job = mapper.map(createJobRequest, Job.class);
        Job save = jobService.save(job, user, category.getId(), company.getId());
        assertEquals("developer", save.getName());
    }

    @Test
    void save_null() {
        companyRepository.save(company);
        categoryRepository.save(category);
        userRepository.save(user);
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> {
            jobService.save(null, user, category.getId(), company.getId());
        });
    }

    @Test
    void deleteById() {
        Job job = mapper.map(createJobRequest, Job.class);
        companyRepository.save(company);
        categoryRepository.save(category);
        userRepository.save(user);
        jobService.save(job, user, category.getId(), company.getId());
        jobService.deleteById(job.getId());
        Optional<Job> byId = jobRepository.findById(job.getId());
        assertFalse(byId.isPresent());
    }
//    @Test()
//    void delete_notFound() {
//        jobService.deleteById(254);
//
////        EmptyResultDataAccessException thrown = assertThrows(EmptyResultDataAccessException.class, () -> {
////        });
////        assertEquals("Expected org.springframework.dao.EmptyResultDataAccessException to be thrown, but nothing was thrown.", thrown.getMessage());
//    }

}