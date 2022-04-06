package com.example.jobs.service;

import com.example.jobs.entity.User;
import com.example.jobs.entity.UserType;
import com.example.jobs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
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


    public User save(User user){
        user.setUserType(UserType.USER);
        return userRepository.save(user);
    }}
