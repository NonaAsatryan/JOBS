package com.example.jobs.controller;

import com.example.jobs.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping("/job/post")
    public String postJob(ModelMap map) {
        return "post";
    }

}
