package com.online.hotel.arlear;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


import com.online.hotel.arlear.service.ReservationService;
import com.online.hotel.arlear.service.RoomService;
import com.online.hotel.arlear.service.TicketService;
import com.online.hotel.arlear.service.UserService;
import com.online.hotel.arlear.util.LoadInitial;


@SpringBootApplication
@EnableScheduling
public class ArlearApplication {
	@Value("${database.url}")
	private String url;
	public static void main(String[] args) {
		SpringApplication.run(ArlearApplication.class, args);
	}
	@Bean
	public CommandLineRunner init(final RoomService roomService,
								  final ReservationService reservationService,
								  final TicketService ticketService) {
		return new CommandLineRunner() {
			public void run(String... strings) {
				System.out.println("Url *****"+url);
				if(roomService.find().size() == 0) {
					LoadInitial.createRoom().stream().forEach(p-> roomService.create(p));
					
					LoadInitial.createReservation(roomService.find()).stream().forEach(p-> reservationService.create(p));
					ticketService.create(LoadInitial.createTicket());
					
				}
				
			
				
			}
		};
	}
	
}
