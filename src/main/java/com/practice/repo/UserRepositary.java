package com.practice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.model.User;

public interface UserRepositary extends JpaRepository<User, Integer> {
	
	Optional<User> findByUserName(String username);

}
