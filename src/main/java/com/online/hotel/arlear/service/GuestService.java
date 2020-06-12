package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.online.hotel.arlear.model.Guest;
import com.online.hotel.arlear.model.UserHotel;
import com.online.hotel.arlear.repository.GuestRepository;


//falta codigo
@Service
public class GuestService implements ServiceGeneric<Guest>{
	@Autowired 
	private GuestRepository guestRepository;

	@Override
	public boolean create(Guest entity) {
		
					guestRepository.save(entity);
					return true;
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
