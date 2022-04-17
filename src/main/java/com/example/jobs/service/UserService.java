package com.example.jobs.service;

import com.example.jobs.entity.User;
import com.example.jobs.entity.UserType;
import com.example.jobs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${images.upload.path}")
    public String imagePath;
    @Value("${files.upload.path}")
    public String filePath;
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
        if (user.getUserType() == UserType.EMPLOYER) {
            user.setUserType(UserType.EMPLOYER);
        }else {
            user.setUserType(UserType.USER);}
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    public void saveUserImage(MultipartFile uploadedImageFile, User user) throws IOException {
        if (!uploadedImageFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + uploadedImageFile.getOriginalFilename();
            File newFile = new File(imagePath + picName);
            uploadedImageFile.transferTo(newFile);
            user.setPicUrl(picName);

        }

        userRepository.save(user);
    }

//    public void saveResumeFile(MultipartFile uploadedFile, User user) throws IOException {
//        if (!uploadedFile.isEmpty()) {
//            String fileName = System.currentTimeMillis() + "_" + uploadedFile.getOriginalFilename();
//            File newFile = new File(filePath + fileName);
//            uploadedFile.transferTo(newFile);
//            user.setResume(fileName);
//        }
//
//        userRepository.save(user);
//    }

    public User deleteById(int id) {
        userRepository.deleteById(id);
        return null;
    }
}
