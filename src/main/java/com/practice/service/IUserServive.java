package com.practice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.practice.model.User;

@Service
public interface IUserServive {
   public Integer saveUser(User user);
   Optional<User> findByUserName(String username);
}
