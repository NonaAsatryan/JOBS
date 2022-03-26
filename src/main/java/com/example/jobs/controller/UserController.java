package com.example.jobs.controller;

import com.example.jobs.entity.User;
import com.example.jobs.repository.UserRepository;
import com.example.jobs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final UserRepository userRepository;
    @Value("${jobs.upload.path}")
    private String imagePath;

    @GetMapping("/users")
    public String users(ModelMap map){
        List<User> users = userService.findAll();
        map.addAttribute("users",users);
        return "user";
    }
    @GetMapping("/users/add")
    public String addUserPage(ModelMap map) {
        map.addAttribute("users", userService.findAll());
        return "addUser";
    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        InputStream inputStream = new FileInputStream(imagePath + picName);
        return IOUtils.toByteArray(inputStream);
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute User user, @RequestParam("picture") MultipartFile uploadedFile) throws IOException {
            userService.saveUserImage(uploadedFile, user);
        return "redirect:/users";
    }

    @GetMapping("/editUser/{id}")
    public String editUserPage(ModelMap map, @PathVariable("id") int id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            map.addAttribute("user", userById.get());
            return "addUser";
        } else {
            return "redirect:/users";
        }
    }
}
