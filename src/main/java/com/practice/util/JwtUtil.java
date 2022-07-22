package com.practice.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    @Value("${app.secret}")
	private String secret;
    
    //Generate Token
    public String generateToken(String subject) {
    	System.out.println("gggggggg");
    	String token = Jwts.builder()
 	    	   .setSubject(subject)
 	    	   .setIssuer("AbhayaTech")
 	    	   .setIssuedAt(new Date(System.currentTimeMillis()))
 	    	   .setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(15)))
 	    	   .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
    	
    	System.out.println("token----"+token);
    	    	
    	return token;
    }
    
    //Read Token(Claims)
    
    public Claims getClaims(String token) {
    	
		return Jwts.parser()
   	         .setSigningKey(secret.getBytes())
   	         .parseClaimsJws(token)
   	         .getBody();
    	
    }
    
    //Read Exparation date
    public Date getExpDate(String token) {
    	return getClaims(token).getExpiration();
    }
    
    //Read Subject/username
    public String getUserName(String token) {
    	return getClaims(token).getSubject();    	
    }
    
    //Validate Exparation Date
    public boolean isTokenExp(String token) {
    	Date expDate = getExpDate(token);
    	return expDate.before(new Date(System.currentTimeMillis()));
    }
    
    // Validate username in token and database
    public boolean validateToken(String token,String username) {
    	String tokenUserName=getUserName(token);
    	return (tokenUserName.equals(username)&& !isTokenExp(token));
    }
    
    
    
    
}
