package com.example.jobs.controller;

import com.example.jobs.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
}
