package com.myinnovate.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myinnovate.demo.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);
}
