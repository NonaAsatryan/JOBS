package com.example.jobs.repository;

import com.example.jobs.entity.Category;
import com.example.jobs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

}
