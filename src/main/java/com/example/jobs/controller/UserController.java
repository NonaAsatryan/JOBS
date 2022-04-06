package com.example.jobs.controller;

import com.example.jobs.entity.User;
import com.example.jobs.repository.UserRepository;
import com.example.jobs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/user/register")
    public String registerPage() {
        return "signup";
    }

    @PostMapping("/user/register")
    public String addUser(@ModelAttribute User user, ModelMap map) {
        userService.save(user);
        return "redirect:/user/login";
    }
    @GetMapping("/user/login")
    public String userLoginPage(){
        return "signin";
    }
    @PostMapping("/user/login")
    public String userLogin(@ModelAttribute User user,ModelMap map){
        map.addAttribute("user",user);
        return "redirect:/user/profile";
    }
//    @GetMapping("/user/profile")
//    public String profilePage(){
//        return "profile";
//    }

}
