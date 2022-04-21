package com.example.jobs.controller;

import com.example.jobs.entity.Resume;
import com.example.jobs.entity.User;
import com.example.jobs.entity.UserType;
import com.example.jobs.sequrity.CurrentUser;
import com.example.jobs.service.MailService;
import com.example.jobs.service.ResumeService;
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

import javax.mail.MessagingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    private final ResumeService resumeService;
    private final MailService mailService;


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

    @PostMapping("/user/register")
    public String addUser(@ModelAttribute User user,
                          @RequestParam("picName") MultipartFile uploadedImageFile, Locale locale) throws IOException, MessagingException {
        user.setActive(false);
        user.setToken(UUID.randomUUID().toString());
        user.setTokenCreatedDate(LocalDateTime.now());

        userService.save(user);
        userService.saveUserImage(uploadedImageFile, user);
//        userService.saveResumeFile(uploadedFile, user);

        mailService.sendHtmlEmail(user.getEmail(), "Welcome"+user.getSurname(),user,
                        "  http://localhost:8080/user/activate?token=" + user.getToken(),"verify_template",locale);

        return "redirect:/user/login";
    }

    @GetMapping("/user/activate")
    public String activateUser(ModelMap map, @RequestParam String token) {
        Optional<User> user = userService.findByToken(token);
        if (!user.isPresent()) {
            map.addAttribute("message", "User does not exists");
            return "activate_user";
        }
        User userFromDb = user.get();
        if (userFromDb.isActive()) {
            map.addAttribute("message", "User already active! ");
            return "activate_user";
        }
        userFromDb.setActive(true);
        userFromDb.setToken(null);
        userFromDb.setTokenCreatedDate(null);
        userService.edit(userFromDb);
        map.addAttribute("message", "User activate, please login!");
        return "activate_user";
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
        Resume resume = resumeService.findByUserId(currentUser.getUser().getId());
        if (resume != null) {
            map.addAttribute("resume", resume);
        }
        return "profile";
    }

    @GetMapping("/user/adminProfile")
    public String adminProfilePage(@ModelAttribute CurrentUser currentUser, ModelMap map) {
        map.addAttribute("currentUser", currentUser);
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

    @GetMapping("/user/remove/{id}")
    public String deleteUserByAdmin(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/users";

    }

    @GetMapping("/users")
    public String usersPage(@ModelAttribute CurrentUser currentUser, ModelMap map) {
        List<User> users = userService.findAll();
        map.addAttribute("users", users);
        return "users";
    }

}
