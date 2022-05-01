package com.example.jobs.controller;

import com.example.jobs.entity.Resume;
import com.example.jobs.repository.ResumeRepository;
import com.example.jobs.sequrity.CurrentUser;
import com.example.jobs.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class ResumeController {


    private final ResumeService resumeService;
    @Value("${images.upload.path}")
    public String imagePath;
    @Value("${files.upload.path}")
    public String filePath;

    @GetMapping("/resume")
    public String resumePage(ModelMap map, @ModelAttribute CurrentUser currentUser) {
        Resume resume = resumeService.findByUserId(currentUser.getUser().getId());
        
        if (resume != null) {
            map.addAttribute("resume", resume);

        }
        return "resume";
    }

    @GetMapping("/resume/post")
    public String postResumePage(@ModelAttribute CurrentUser currentUser, ModelMap map) {
        map.addAttribute("currentUser", currentUser);
        return "post-resume";
    }

    @PostMapping("/resume/post")
    public String postResume(@ModelAttribute Resume resume, @ModelAttribute CurrentUser currentUser,
                             @RequestParam("picture") MultipartFile uploadedImageFile,
                             @RequestParam("fileName") MultipartFile uploadedFile,
                             ModelMap map) throws IOException {
        map.addAttribute("resume", resume);
        map.addAttribute("currentUser", currentUser);
        resumeService.addResume(resume, uploadedImageFile, uploadedFile, currentUser.getUser());

        return "resume";
    }

    @PostMapping("/resume/edit")
    public String editResume(@ModelAttribute Resume resume, @ModelAttribute CurrentUser currentUser,
                             @RequestParam("picture") MultipartFile uploadedImageFile,
                             @RequestParam("fileName") MultipartFile uploadedFile,
                             ModelMap map) throws IOException {
        map.addAttribute("resume", resume);
        map.addAttribute("currentUser", currentUser);
        resumeService.addResume(resume, uploadedImageFile, uploadedFile, currentUser.getUser());

        return "resume";
    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@RequestParam("picture") String picture) throws IOException {
        InputStream inputStream = new FileInputStream(imagePath + picture);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping(value = "/getFile", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    byte[] getFile(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(filePath + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/resume/edit")
    public String resumeEditPage(ModelMap map, @ModelAttribute CurrentUser currentUser) {
        Resume resume = resumeService.findByUserId(currentUser.getUser().getId());
        if (resume != null) {
            map.addAttribute("resume", resume);
        }
        return "edit-resume";
    }

    @GetMapping("/resume/update/{id}")
    public String resumeEdit(ModelMap map, @PathVariable("id") int id) {

        map.addAttribute("resume", resumeService.getById(id));
        return "edit-resume";

    }

}

