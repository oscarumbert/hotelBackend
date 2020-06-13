package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.online.hotel.arlear.dto.ContactFindDTO;
import com.online.hotel.arlear.dto.GuestDTO;
import com.online.hotel.arlear.dto.GuestFindDTO;
import com.online.hotel.arlear.enums.UserType;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.exception.ExceptionUnique;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Guest;
import com.online.hotel.arlear.model.UserHotel;
import com.online.hotel.arlear.repository.GuestRepository;


//falta codigo
@Service
public class GuestService implements ServiceGeneric<Guest>{
	@Autowired 
	private GuestRepository guestRepository;
	
	
	public boolean createGuest(Guest entity) throws ExceptionUnique {

		if(findUnique(new GuestFindDTO(entity.getName().toString(),
										 entity.getSurname().toString(),
										 entity.getDocumentNumber().toString())) != null) {
			throw new ExceptionUnique(ErrorMessages.CREATE_ERROR_UNIQUE.getDescription("Guest"));
			
		}else {
			
			return create(entity);
		}
	}
	
	public Guest findUnique(GuestFindDTO guest) {
		
		Optional<Guest> optional = find().stream().filter(p -> p.getName().toString().equals(guest.getName()) &&
															p.getSurname().toString().equals(guest.getSurname()) &&
															p.getDocumentNumber().toString().equals(guest.getDocumentNumber())).findAny();
		return optional.isPresent() ? optional.get():null;
		
	}

	@Override
	public boolean create(Guest entity) {
		return guestRepository.save(entity)!=null;
	}
	
	
	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Guest> find() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Guest entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Guest find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
