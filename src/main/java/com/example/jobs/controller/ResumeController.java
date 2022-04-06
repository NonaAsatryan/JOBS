package com.example.jobs.controller;

import com.example.jobs.entity.Resume;
import com.example.jobs.repository.ResumeRepository;
import com.example.jobs.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeRepository resumeRepository;
    private final ResumeService resumeService;

    @GetMapping("/resume")
    public String resumePage() {
        return "resume";
    }

    @GetMapping("/resume/post")
    public String postResumePage() {
        return "post-resume";
    }

    @PostMapping("/resume/post")
    public String postResume(@ModelAttribute Resume resume) {
        resumeService.save(resume);
        return "resume";
    }
}
