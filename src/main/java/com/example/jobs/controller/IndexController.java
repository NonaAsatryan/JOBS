package com.example.jobs.controller;

import com.example.jobs.entity.Category;
import com.example.jobs.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final CategoryService categoryService;


    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/job/post")
    public String postPage(ModelMap map) {
        List<Category> categoryList = categoryService.findAll();
        map.addAttribute("categoryList", categoryList);
        return "post";
    }

    @GetMapping("/resume")
    public String resumePage() {
        return "resume";
    }

    @GetMapping("/user/register")
    public String registerPage() {
        return "signup";
    }

    @GetMapping("/user/signin")
    public String signinPage() {
        return "signin";
    }

    @GetMapping("/job/details")
    public String detailsPage() {
        return "details";
    }

    @GetMapping("/job/jobList")
    public String jobListPage() {
        return "job-list";
    }

    @GetMapping("/job/jobDetails")
    public String jobDetailsPage() {
        return "job-details";
    }

    @GetMapping("/resume/post")
    public String postResumePage() {
        return "post-resume";
    }

    @GetMapping("/user/profile")
    public String userProfilePage() {
        return "profile";
    }

    @GetMapping("/resume/edit")
    public String editResumePage() {
        return "edit-resume";
    }

    @GetMapping("/user/profileDetails")
    public String profileDetailsPage() {
        return "profileDetails";
    }

    @GetMapping("/bookmark")
    public String bookmarkPage() {
        return "bookmark";
    }

    @GetMapping("/job/appliedJob")
    public String appliedJobPage() {
        return "applied-job";
    }

    @GetMapping("/user/deleteAccount")
    public String deleteAccountPage() {
        return "delete-account";
    }
}
