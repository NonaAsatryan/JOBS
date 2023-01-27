package com.example.jobs.service;

import com.example.jobs.entity.Gender;
import com.example.jobs.entity.User;
import com.example.jobs.entity.UserType;
import com.example.jobs.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private MultipartFile multipartFile;

    private User user;

    @BeforeEach
    void beforeAll() {
        user = new User();
        user.setName("poxos");
        user.setSurname("poxosyan");
        user.setEmail("poxos@mail.com");
        user.setPassword("poxos");
        user.setGender(Gender.FEMALE);
        user.setPhone("21541541");
        user.setUserType(UserType.USER);
        user.setActive(true);
    }

    @Test
    void save() {
        userService.save(user);
        assertEquals(1, userRepository.count());
        Optional<User> byId = userRepository.findById(user.getId());
        assertTrue(byId.isPresent());
        assertEquals("poxos", byId.get().getName());
        assertEquals("poxos@mail.com", byId.get().getEmail());

    }


    @Test
    void save_null() {
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> {
            userService.save(null);
        });
    }

    @Test
    void deleteById() {
        userService.save(user);
        userService.deleteById(user.getId());
        Optional<User> byId = userRepository.findById(user.getId());
        assertFalse(byId.isPresent());
    }

    @Test()
    void delete_notFound() {
        EmptyResultDataAccessException thrown = assertThrows(EmptyResultDataAccessException.class, () -> {
            User user1 = userService.deleteById(254);
            assertNull(user1);
        });
        assertEquals("No class com.example.jobs.entity.User entity with id 254 exists!", thrown.getMessage());
    }

    @Test
    void saveUserImage()  {
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> {
            userService.saveUserImage(multipartFile, user);
        });
    }

}