package com.practice.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.model.User;
import com.practice.repo.UserRepositary;
import com.practice.service.IUserServive;

@Service
public class UserServiceImpl implements IUserServive,UserDetailsService {

	@Autowired
	private UserRepositary repo;
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	@Override
	public Integer saveUser(User user) {
		//Encode password
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		System.out.println("Service---"+user);
		return repo.save(user).getId();
	}

	//get User by Username
	@Override
	public Optional<User> findByUserName(String username) {
		// TODO Auto-generated method stub
		return repo.findByUserName(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
	Optional<User>	opt =findByUserName(username);
	
	if(opt.isEmpty())
		 throw new UsernameNotFoundException("User not Exist");
	
	//Read user from Db
	   User user = opt.get();
		return new org.springframework.security.core.userdetails.User(username, 
				                                                      user.getPassword(), 
				                                                      user.getRoles().stream()
				                                                      .map(role->new SimpleGrantedAuthority(role))
				                                                      .collect(Collectors.toList()));
	}

}
