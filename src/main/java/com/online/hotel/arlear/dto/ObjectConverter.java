package com.online.hotel.arlear.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.model.UserHotel;

import lombok.Data;

@Component
@Data
public class ObjectConverter {

	private final ModelMapper modelMapper;
	
	public Reservation converter(ReservationDTO obj) {
		
		return modelMapper.map(obj, Reservation.class);
	}
	public Reservation converter(ReservationCreateDTO obj) {
		
		return modelMapper.map(obj, Reservation.class);
	}
	public ReservationDTO converter(Reservation obj) {
		
		return modelMapper.map(obj, ReservationDTO.class);
	}
	
	public UserHotel converter(UserDTO obj) {
		return modelMapper.map(obj, UserHotel.class);
	}
	
	public UserDTO converter(UserHotel obj) {
		
		return modelMapper.map(obj, UserDTO.class);
	}
	
	public Ticket converter(TicketDTO obj) {
		
		return modelMapper.map(obj, Ticket.class);
	}
	public TicketDTO converter(Ticket obj) {
		
		return modelMapper.map(obj, TicketDTO.class);
	}
	public Transaction converter(TransactiontDTO obj) {
		
		return modelMapper.map(obj, Transaction.class);
	}
	public TransactiontDTO converter(Transaction obj) {
		
		return modelMapper.map(obj, TransactiontDTO.class);
	}
	public Contact converter(ContactDTO obj) {
		
		return modelMapper.map(obj, Contact.class);
	}
	public ContactDTO converter(Contact obj) {
		
		return modelMapper.map(obj, ContactDTO.class);
	}
}
