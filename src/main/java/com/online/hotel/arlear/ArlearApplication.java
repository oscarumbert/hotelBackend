package com.online.hotel.arlear;

import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


import com.online.hotel.arlear.enums.CardType;
import com.online.hotel.arlear.enums.DocumentType;
import com.online.hotel.arlear.enums.GenderType;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomAditionals;
import com.online.hotel.arlear.enums.RoomCategory;
import com.online.hotel.arlear.enums.RoomStatus;
import com.online.hotel.arlear.enums.RoomType;
import com.online.hotel.arlear.enums.Section;
import com.online.hotel.arlear.model.Card;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.model.Room;
import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.service.ReservationService;
import com.online.hotel.arlear.service.RoomService;
import com.online.hotel.arlear.service.TicketService;
import com.online.hotel.arlear.service.UserService;
import com.online.hotel.arlear.util.CargaInicial;


@SpringBootApplication
@EnableScheduling
public class ArlearApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArlearApplication.class, args);
	}
	@Bean
	public CommandLineRunner init(final RoomService roomService,
								  final ReservationService reservationService) {
		return new CommandLineRunner() {
			public void run(String... strings) {
				
				if(roomService.find().size() == 0) {
					CargaInicial.createRoom().stream().forEach(p-> roomService.create(p));
					
					CargaInicial.createReservation(roomService.find()).stream().forEach(p-> reservationService.create(p));
					
				}
				
			
				
			}
		};
	}
	
}
