package com.example.jobs.controller;

import com.example.jobs.entity.Resume;
import com.example.jobs.entity.User;
import com.example.jobs.entity.UserType;
import com.example.jobs.repository.UserRepository;
import com.example.jobs.sequrity.CurrentUser;
import com.example.jobs.service.UserService;
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
public class UserController {


    private final UserService userService;
    private final UserRepository userRepository;

    @Value("${images.upload.path}")
    public String imagePath;
    @Value("${files.upload.path}")
    public String filePath;

    @GetMapping("/user/register")
    public String registerPage() {
        return "signup";
    }

    @PostMapping("/user/register")
    public String addUser(@ModelAttribute User user, @RequestParam("fileName") MultipartFile uploadedFile,
                          @RequestParam("picName") MultipartFile uploadedImageFile) throws IOException {
        userService.save(user);
        userService.saveUserImage(uploadedImageFile, user);
        userService.saveResumeFile(uploadedFile, user);

        return "redirect:/user/login";
    }
    @PostMapping("/user/downloadResumeFile")
    public String addResumeDoc(@ModelAttribute User user,@RequestParam("fileName") MultipartFile uploadedFile) throws IOException {
        userService.saveResumeFile(uploadedFile,user);
        return "profile";
    }

    @GetMapping("/successLogin")
    public String successLogin(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null) {
            return "redirect:/";
        }
        User user = currentUser.getUser();
        if (user.getUserType() == UserType.ADMIN) {
            return "redirect:/user/adminProfile";
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
        return "redirect:/user/profile";
    }

    @GetMapping(value = "/getUserImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        InputStream inputStream = new FileInputStream(imagePath + picName);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/user/deleteAccount{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping(value = "/getFile", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    byte[] getFile(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(filePath + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/user/profile")
    public String profilePage(@ModelAttribute CurrentUser currentUser, @ModelAttribute Resume resume, ModelMap map){
        map.addAttribute("currentUser",currentUser);
        map.addAttribute("resume",resume);
        return "profile";
    }

}
