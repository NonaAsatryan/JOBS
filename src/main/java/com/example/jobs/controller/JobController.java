package com.example.jobs.controller;

import com.example.jobs.entity.Category;
import com.example.jobs.entity.Job;
import com.example.jobs.service.CategoryService;
import com.example.jobs.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final CategoryService categoryService;

    @GetMapping("/job/post")
    public String postPage(ModelMap map) {
        List<Category> categoryList = categoryService.findAll();
        map.addAttribute("categoryList", categoryList);
        return "post";
    }

    @PostMapping("/job/post")
    public String jobAddPost(@ModelAttribute Job job) {
        jobService.save(job);
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


}
