package com.example.jobs.controller;

import com.example.jobs.dto.CreateJobRequest;
import com.example.jobs.entity.Category;
import com.example.jobs.entity.Job;
import com.example.jobs.entity.User;
import com.example.jobs.sequrity.CurrentUser;
import com.example.jobs.service.CategoryService;
import com.example.jobs.service.CompanyService;
import com.example.jobs.service.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
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
    public String jobAddPost(@ModelAttribute @Valid CreateJobRequest createJobRequest,
                             BindingResult bindingResult,
                             @ModelAttribute CurrentUser currentUser,
                             ModelMap map) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError allError : bindingResult.getAllErrors()) {
                errors.add(allError.getDefaultMessage());
            }
            map.addAttribute("errors",errors);
            return "post";
        } else {
            User user = currentUser.getUser();
            Job job = mapper.map(createJobRequest, Job.class);

            jobService.save(job, user, createJobRequest.getCategoryId(), createJobRequest.getCompanyId());
        }
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
