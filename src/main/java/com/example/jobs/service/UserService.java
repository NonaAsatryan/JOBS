package com.example.jobs.service;

import com.example.jobs.entity.User;
import com.example.jobs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Value("${jobs.upload.path}")
    public String imagePath;


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUserImage(MultipartFile uploadedFile, User user) throws IOException {
        if (!uploadedFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + uploadedFile.getOriginalFilename();
            File newFile = new File(imagePath + fileName);
            uploadedFile.transferTo(newFile);
            user.setPicUrl(fileName);
        }

        userRepository.save(user);
    }

}
