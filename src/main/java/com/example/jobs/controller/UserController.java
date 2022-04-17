package com.example.jobs.controller;

import com.example.jobs.dto.CreateCompanyRequest;
import com.example.jobs.dto.CreateJobRequest;
import com.example.jobs.entity.*;
import com.example.jobs.repository.UserRepository;
import com.example.jobs.sequrity.CurrentUser;
import com.example.jobs.service.ResumeService;
import com.example.jobs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
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
public class UserController {


    private final UserService userService;

    private final ResumeService resumeService;


    @Value("${images.upload.path}")
    public String imagePath;
    @Value("${files.upload.path}")
    public String filePath;

    @GetMapping("/user/register")
    public String registerPage() {
        return "signup";
    }
    @GetMapping("/user/employerRegister")
    public String employerRegisterPage() {
        return "signup";
    }

//    @GetMapping("/user/companyRegister")
//    public String companyRegisterPage(){
//        return "signup";
//    }
//    @PostMapping("/user/companyRegister")
//    public String CompanyRegister(@ModelAttribute User user, @ModelAttribute Company company){
//
//
//    }

    @PostMapping("/user/register")
    public String addUser(@ModelAttribute User user,
                          @RequestParam("picName") MultipartFile uploadedImageFile) throws IOException {
        userService.save(user);
        userService.saveUserImage(uploadedImageFile, user);
//        userService.saveResumeFile(uploadedFile, user);

        return "redirect:/user/login";
    }
    @PostMapping("/user/employerRegister")
    public String addEmployer(@ModelAttribute User user,
                          @RequestParam("picName") MultipartFile uploadedImageFile) throws IOException {
        userService.save(user);
        userService.saveUserImage(uploadedImageFile, user);
//        userService.saveResumeFile(uploadedFile, user);

        return "redirect:/user/login";
    }

    @GetMapping("/successLogin")
    public String successLogin(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null) {
            return "redirect:/";
        }
        User user = currentUser.getUser();
        if (user.getUserType() == UserType.ADMIN) {
            return "redirect:/user/adminProfile";
        }
        if (user.getUserType() == UserType.EMPLOYER) {
            return "redirect:/user/employerProfile";
        } else {
            return "redirect:/user/profile";
        }
    }


    @GetMapping("/user/login")
    public String userLoginPage() {
        return "signin";
    }

    @PostMapping("/user/login")
    public String userLogin(@ModelAttribute CurrentUser currentUser, ModelMap map) {
        map.addAttribute("currentUser", currentUser);
        return "redirect:/successLogin";
    }

    @GetMapping(value = "/getUserImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        InputStream inputStream = new FileInputStream(imagePath + picName);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/user/deleteAccount")
    public String deleteUserPage(@ModelAttribute CurrentUser currentUser, ModelMap map) {
        map.addAttribute("currentUser", currentUser);
        return "delete-account";

    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/";

    }


    @GetMapping("/user/profile")
    public String profilePage(@ModelAttribute CurrentUser currentUser, ModelMap map) {
        map.addAttribute("currentUser", currentUser);
        Resume resume=resumeService.findByUserId(currentUser.getUser().getId());
        if (resume!=null){
        map.addAttribute("resume", resume);}
        return "profile";
    }
    @GetMapping("/user/adminProfile")
    public String adminProfilePage(@ModelAttribute CurrentUser currentUser, @ModelAttribute Resume resume, ModelMap map) {
        map.addAttribute("currentUser", currentUser);
        map.addAttribute("resume", resume);
        return "admin-profile";
    }
    @GetMapping("/user/employerProfile")
    public String employerProfilePage(@ModelAttribute CurrentUser currentUser, @ModelAttribute Resume resume, ModelMap map) {
        map.addAttribute("currentUser", currentUser);
        map.addAttribute("resume", resume);
        return "employer-profile";
    }


    @GetMapping("/user/profileDetails")
    public String profileDetailsPage() {
        return "profileDetails";
    }

    @GetMapping("/bookmark")
    public String bookmarkPage() {
        return "bookmark";
    }

}
