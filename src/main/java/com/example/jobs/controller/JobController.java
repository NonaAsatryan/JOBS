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
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
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
            bindingResult.getAllErrors().forEach(allError -> errors.add(allError.getDefaultMessage()));
            log.error("You have unfilled fields");
            map.addAttribute("errors", errors);
            return "post";
        } else {
            User user = currentUser.getUser();
            Job job = mapper.map(createJobRequest, Job.class);
            log.info("User with email: {}  is login and created a job", user.getEmail());

            jobService.save(job, user, createJobRequest.getCategoryId(), createJobRequest.getCompanyId());
        }
        return "redirect:/";
    }

    @GetMapping("/job/details")
    public String detailsPage() {
        return "details";
    }

    @GetMapping("/job/jobList/{keyword}")
    public String jobListPage(@RequestParam String keyword, ModelMap map) {
        List<Job> jobList = jobService.search(keyword);
        map.addAttribute("jobList", jobList);
        return "job-list";
    }

    @GetMapping("/job/jobDetails/{id}")
    public String jobDetailsPage(@PathVariable("id") int id, ModelMap map) {
        Job byId = jobService.getById(id);
        if (byId != null) {
            map.addAttribute("byId", byId);
            return "job-details";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/job/appliedJob")
    public String appliedJobPage() {
        return "applied-job";
    }

    @GetMapping("/editJob/{id}")
    public String updateJob(@ModelAttribute CreateJobRequest createJobRequest,ModelMap map, @PathVariable("id") int id,
                            @ModelAttribute CurrentUser currentUser) {
        Job job = mapper.map(createJobRequest, Job.class);
        if (job != null) {
            jobService.updateJob(job,id,currentUser.getUser());
            return "employer-profile";
        } else {
            return "redirect:/";
        }
    }
}
