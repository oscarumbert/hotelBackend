package com.online.hotel.arlear.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Guest;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.model.Room;
import com.online.hotel.arlear.model.Survey;
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
	public Reservation converter(ReservationUpdateDTO obj) {
		
		return modelMapper.map(obj, Reservation.class);
	}
	public Reservation converter(ReservationCreateDTO obj) {
		
		return modelMapper.map(obj, Reservation.class);
	}
	public ReservationDTO converter(Reservation obj) {
		
		return modelMapper.map(obj, ReservationDTO.class);
	}
	
	public Reservation converter(ReservationFind obj) {

		return modelMapper.map(obj, Reservation.class);
	}
	
	public Reservation converter(ReservationCheckIn obj) {
		return modelMapper.map(obj, Reservation.class);
	}
	
	public ReservationOpenDTO converterReservationOpen(Reservation obj) {
		return modelMapper.map(obj, ReservationOpenDTO.class);
	}
	
	public UserHotel converter(UserDTO obj) {
		return modelMapper.map(obj, UserHotel.class);
	}
	
	public UserDTO converter(UserHotel obj) {
		
		return modelMapper.map(obj, UserDTO.class);
	}
	
	public UserHotel converter(UserDTOUpdate obj) {
		
		return modelMapper.map(obj, UserHotel.class);
	}
	
	public UserHotel converter(UserDTOfind obj) {

		return modelMapper.map(obj, UserHotel.class);
	}
	
	public UserHotel converter(UserDTOLogin obj) {
		return modelMapper.map(obj, UserHotel.class);
	}
	
	public Ticket converter(TicketDTO obj) {
		
		return modelMapper.map(obj, Ticket.class);
	}
	
	public Ticket converter(TicketDTOFind obj) {
		
		return modelMapper.map(obj, Ticket.class);
	}
	
	public TicketDTOFind converterFind(Ticket obj) {
		
		return modelMapper.map(obj, TicketDTOFind.class);
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
	public TransactionDTOFind converterFind(Transaction obj) {
		
		return modelMapper.map(obj, TransactionDTOFind.class);
	}
	
	public Product converter(ProductDTO obj) {
		
		return modelMapper.map(obj, Product.class);
	}
	public ProductDTO converter(Product obj) {
		
		return modelMapper.map(obj, ProductDTO.class);
	}	
	public Contact converter(ContactDTO obj) {
		
		return modelMapper.map(obj, Contact.class);
	}
	public ContactFindDTO converterDTO(ContactDTO obj) {
		
		return modelMapper.map(obj, ContactFindDTO.class);
	}
	public Product converter(ProductDTOUpdate obj) {
		
		return modelMapper.map(obj, Product.class);
	}
	
	public Product converter(ProductDTOfind obj) {

		return modelMapper.map(obj, Product.class);
	}
	
	public ContactDTO converter(Contact obj) {
		
		return modelMapper.map(obj, ContactDTO.class);
	}
	
	public Menu converter(MenuDTO obj) {
		return modelMapper.map(obj, Menu.class);
	}
	public Menu converter(MenuDTOfind obj) {
		return modelMapper.map(obj, Menu.class);
	}
	public MenuDTO converter(Menu obj) {
		return modelMapper.map(obj, MenuDTO.class);
	}
	
	public MenuDTOFindUnity converterMenuUnity(Menu obj) {
		return modelMapper.map(obj, MenuDTOFindUnity.class);	}

	public Guest converter(GuestDTO obj) {
		return modelMapper.map(obj,Guest.class);
	}

	public GuestDTO converter(Guest obj) {
		return modelMapper.map(obj, GuestDTO.class);
	}

	public Survey converter(SurveyDTO obj) {
		return modelMapper.map(obj, Survey.class);
	}
	
	public Survey converter(SurveyDTOfind obj) {
		return modelMapper.map(obj, Survey.class);
	}
	
	public SurveyDTO converter(Survey obj) {
		return modelMapper.map(obj, SurveyDTO.class);
	}
	public Room converter(RoomDTO obj) {
		
		return modelMapper.map(obj, Room.class);
	}
	
}
