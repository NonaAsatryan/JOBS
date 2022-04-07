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

    @GetMapping("/")
    public String main() {
        return "index";
    }

//
//
//
//

//
//    @GetMapping("/resume/post")
//    public String postResumePage() {
//        return "post-resume";
//    }
//
    @GetMapping("/user/profile")
    public String userProfilePage() {
        return "profile";
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
