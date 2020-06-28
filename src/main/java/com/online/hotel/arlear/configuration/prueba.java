package com.online.hotel.arlear.configuration;

import java.time.LocalDateTime;

public class prueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LocalDateTime begin = LocalDateTime.of(2020, 07, 12, 20, 33);
		LocalDateTime end = LocalDateTime.of(2020, 07, 12, 19, 33);
		System.out.println(begin.isBefore(end));
		
	}

}
