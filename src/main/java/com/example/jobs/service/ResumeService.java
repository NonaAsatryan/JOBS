package com.example.jobs.service;

import com.example.jobs.entity.Company;
import com.example.jobs.entity.Resume;
import com.example.jobs.entity.User;
import com.example.jobs.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeService {
    @Value("${images.upload.path}")
    public String imagePath;
    @Value("${files.upload.path}")
    public String filePath;


    private final ResumeRepository resumeRepository;

    public Resume save(Resume resume) {

        return resumeRepository.save(resume);
    }

    public void addResume(Resume resume, MultipartFile uploadedImageFile,MultipartFile uploadedFile,  User user) throws IOException {
        resume.setUser(user);
        saveFiles(uploadedFile,uploadedImageFile, resume);

    }

//    public void saveResumeImage(MultipartFile uploadedImageFile, Resume resume) throws IOException {
//        if (!uploadedImageFile.isEmpty()) {
//            String picture = System.currentTimeMillis() + "_" + uploadedImageFile.getOriginalFilename();
//            File newFile = new File(imagePath + picture);
//            uploadedImageFile.transferTo(newFile);
//            resume.setPicUrl(picture);
//        }
//
//        resumeRepository.save(resume);
//    }

    public void saveFiles(MultipartFile uploadedFile,MultipartFile uploadedImageFile, Resume resume) throws IOException {
        if (!uploadedFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + uploadedFile.getOriginalFilename();
            File newFile = new File(filePath + fileName);
            uploadedFile.transferTo(newFile);
            resume.setResumeFile(fileName);
        }   if (!uploadedImageFile.isEmpty()) {
            String picture = System.currentTimeMillis() + "_" + uploadedImageFile.getOriginalFilename();
            File newFile = new File(imagePath + picture);
            uploadedImageFile.transferTo(newFile);
            resume.setPicUrl(picture);
        }

        resumeRepository.save(resume);
    }

    public Resume findByUserId(int id) {
        return resumeRepository.findByUser_Id(id);

    }

    public Resume getById(int id) {
        return resumeRepository.getById(id);
    }
}
