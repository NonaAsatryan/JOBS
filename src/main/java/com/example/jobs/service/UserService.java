package com.example.jobs.service;

import com.example.jobs.entity.User;
import com.example.jobs.entity.UserType;
import com.example.jobs.repository.UserRepository;
import com.example.jobs.sequrity.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//
//    @Value("${jobs.upload.path}")
//    public String imagePath;
//
//
//    public List<User> findAll() {
//        return userRepository.findAll();
//    }
//
//    public void saveUserImage(MultipartFile uploadedFile, User user) throws IOException {
//        if (!uploadedFile.isEmpty()) {
//            String fileName = System.currentTimeMillis() + "_" + uploadedFile.getOriginalFilename();
//            File newFile = new File(imagePath + fileName);
//            uploadedFile.transferTo(newFile);
//            user.setPicUrl(fileName);
//        }
//
//        userRepository.save(user);
//    }


    public User save(User user) {
        user.setUserType(UserType.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public void login(User user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
      if (byEmail.isPresent() && user.getPassword().matches(passwordEncoder.encode(user.getPassword()))){

      }
    }
}
