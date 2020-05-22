package com.online.hotel.arlear.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import com.online.hotel.arlear.enums.UserType;

import lombok.Data;

@Entity
@Data
public class UserHotel {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long idUser;
	private String name;
	private String surname;
	private String userName;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;

}
