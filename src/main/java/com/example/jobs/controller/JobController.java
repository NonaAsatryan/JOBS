package com.example.jobs.controller;

import com.example.jobs.dto.CreateJobRequest;
import com.example.jobs.entity.Category;
import com.example.jobs.entity.Company;
import com.example.jobs.entity.Job;
import com.example.jobs.entity.User;
import com.example.jobs.sequrity.CurrentUser;
import com.example.jobs.service.CategoryService;
import com.example.jobs.service.CompanyService;
import com.example.jobs.service.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final CategoryService categoryService;
    private final CompanyService companyService;
    private final ModelMapper mapper;


    @GetMapping("/job/post")
    public String postPage(ModelMap map) {
        List<Category> categoryList = categoryService.findAll();
        map.addAttribute("categoryList", categoryList);
        map.addAttribute("companies", companyService.findAll());

        return "post";
    }

    @PostMapping("/job/post")
    public String jobAddPost(@ModelAttribute CreateJobRequest createJobRequest,
                             @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        Job job = mapper.map(createJobRequest, Job.class);

        jobService.save(job, user, createJobRequest.getCategoryId(), createJobRequest.getCompanyId());
        return "redirect:/";
    }

    @GetMapping("/job/details")
    public String detailsPage() {
        return "details";
    }

    @GetMapping("/job/jobDetails")
    public String jobDetailsPage() {
        return "job-details";
    }

    @GetMapping("/job/jobList")
    public String jobListPage() {
        return "job-list";
    }

    @GetMapping("/job/appliedJob")
    public String appliedJobPage() {
        return "applied-job";
    }



}
