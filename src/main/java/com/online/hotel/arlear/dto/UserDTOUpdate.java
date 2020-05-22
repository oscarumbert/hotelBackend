package com.online.hotel.arlear.dto;

import lombok.Data;

@Data
public class UserDTOUpdate {
	private Long idUser;
	private String name;
	private String surname;
	private String userName;
	private String password;
	private String userType;
	
}
