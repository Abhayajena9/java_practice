package com.practice.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.model.User;
import com.practice.model.UserRequest;
import com.practice.model.UserResponse;
import com.practice.service.IUserServive;
import com.practice.util.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserRestController {
	@Autowired
    private IUserServive service;
	@Autowired
	private JwtUtil util;
	@Autowired
	private AuthenticationManager authManager;
   
  @PostMapping("/save")
  public ResponseEntity<String> saveUser(@RequestBody User user){
	  System.out.println("controller:"+user);
	  Integer id=service.saveUser(user);
	  String body="User '"+id+"' Saved";
	return ResponseEntity.ok(body);
	}
  
  @PostMapping("/login")
  public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request){
	  //TODO: Validate Un/pwd with database
	  System.out.println("nnnnnnnnnnnnnnnnn"+request);
	  authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
	  
	  String token = util.generateToken(request.getUserName());
	  return ResponseEntity.ok(new UserResponse(token, "Success!"));
  }
  
  //After Login
  @PostMapping("/welcome")
  public ResponseEntity<String> accessData(Principal p){
	  return ResponseEntity.ok("Hello User!"+p.getName());
  }
}
