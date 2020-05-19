package com.online.hotel.arlear.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.online.hotel.arlear.enums.CardType;
import com.online.hotel.arlear.enums.DocumentType;
import com.online.hotel.arlear.enums.GenderType;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomAditionals;
import com.online.hotel.arlear.enums.RoomCategory;
import com.online.hotel.arlear.enums.RoomStatus;
import com.online.hotel.arlear.enums.RoomType;
import com.online.hotel.arlear.model.Card;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.model.Room;

public class CargaInicial {
	
	public static List<Room> createRoom(){
		Room room = new Room();
		Room room1 = new Room();
		
		room.setCapacity(2);
		room.setId(1L);
		room.setCategory(RoomCategory.BRONCE);
		room.setFloor(1);
		room.setPrice(500);
		room.setRoomNumber(230);
		room.setRoomStatus(RoomStatus.DISPONIBLE);
		room.setType(RoomType.DOBLE);
		
		room1.setCapacity(3);
		room1.setId(2L);
		room1.setCategory(RoomCategory.ORO);
		room1.setFloor(3);
		room1.setPrice(800);
		room1.setRoomNumber(231);
		room1.setRoomStatus(RoomStatus.DISPONIBLE);
		room1.setType(RoomType.TRIPLE);
		
		return Arrays.asList(room,room1);
	}
	private static List<Contact> createContact() {
		Card card = new Card();
		
		card.setCardNumber("4454432343232345");
		card.setCardType(CardType.CREDITO);
		card.setCodeSecurity(1234);
		card.setExpirationDate(LocalDate.of(2020, 12, 12));
		Contact contact = new Contact();
		contact.setCard(card);
		contact.setDocumentNumber(322344543);
		contact.setDocumentType(DocumentType.DNI);
		contact.setGender(GenderType.MASCULINO);
		contact.setMail("pepito@gmail.com");
		contact.setName("CARLOS");
		contact.setPhone(23534234);
		contact.setSurname("PEREZ");
		
		Card card2 = new Card();
		
		card.setCardNumber("4321232343232345");
		card.setCardType(CardType.DEBITO);
		card.setCodeSecurity(1234);
		card.setExpirationDate(LocalDate.of(2021, 12, 12));
		
		Contact contact2 = new Contact();
		
		contact2.setCard(card2);
		contact2.setDocumentNumber(322344543);
		contact2.setDocumentType(DocumentType.DNI);
		contact2.setGender(GenderType.MASCULINO);
		contact2.setMail("pepito@gmail.com");
		contact2.setName("CARLOS");
		contact2.setPhone(23534234);
		contact2.setSurname("PEREZ");
		return Arrays.asList(contact,contact2);
	}
	public static List<Reservation> createReservation(List<Room> rooms){
	
		List<Contact> contacts = createContact();
		Reservation reservation = new Reservation();
		
		reservation.setAditionals(RoomAditionals.A);
		reservation.setAdultsCuantity(2);
		reservation.setBeginDate(LocalDate.now());
		reservation.setChildsCuantity(1);
		reservation.setContact(contacts.get(0));
		reservation.setEndDate(LocalDate.of(2020, 05, 20));
		reservation.setPrice(1093.0);
		reservation.setReservationType(ReservationType.DESAYUNO_INCLUIDO);
		reservation.setRoom(rooms.get(0));
		reservation.setSign(200.0);
		
		
		Reservation reservation1 = new Reservation();
		
		reservation1.setAditionals(RoomAditionals.A);
		reservation1.setAdultsCuantity(1);
		reservation1.setBeginDate(LocalDate.of(2020,06,10));
		reservation1.setChildsCuantity(1);
		reservation1.setContact(contacts.get(1));
		reservation1.setEndDate(LocalDate.of(2020, 06, 19));
		reservation1.setPrice(1093.0);
		reservation1.setReservationType(ReservationType.DESAYUNO_INCLUIDO);
		reservation1.setRoom(rooms.get(1));
		reservation1.setSign(200.0);
		return Arrays.asList(reservation,reservation1);
	}
	

}
