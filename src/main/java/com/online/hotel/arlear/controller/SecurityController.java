package com.online.hotel.arlear.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.dto.UserDTOLogin;
import com.online.hotel.arlear.dto.UserTest;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.UserHotel;
import com.online.hotel.arlear.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/")
public class SecurityController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ObjectConverter objectConverter;
	

	@PostMapping(value="/userTest")
	public ResponseEntity<?> getUsers(@RequestBody UserDTOLogin user) {
		ResponseDTO response=new ResponseDTO();
		//validacion
		UserHotel userHotel = objectConverter.converter(user);
		Long userList= userService.FindUser(userHotel);
		
		if(userList!=null) {
			String token = getJWTToken(user.getUserName());
			UserTest userTest = new UserTest();
			userTest.setUserName(user.getUserName());
			userTest.setToken(token);		
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userTest);
		}
		else{ 
				response = new ResponseDTO("ERROR",
						   ErrorMessages.USER_NOEXIST.getCode(),
						   ErrorMessages.USER_NOEXIST.getDescription(""));
				return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	}
	

	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List <GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
