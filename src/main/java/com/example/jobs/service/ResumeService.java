package com.example.jobs.service;

import com.example.jobs.entity.Resume;
import com.example.jobs.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public Resume save(Resume resume){
        return resumeRepository.save(resume);
    }

}
