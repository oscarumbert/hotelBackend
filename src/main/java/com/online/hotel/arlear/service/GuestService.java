package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.online.hotel.arlear.model.Guest;

import com.online.hotel.arlear.repository.GuestRepository;


@Service
public class GuestService implements ServiceGeneric<Guest>{
	@Autowired 
	private GuestRepository guestRepository;
	
	@Override
	public boolean create(Guest entity) {
		if(guestDuplicate(entity.getName(), entity.getSurname(), entity.getDocumentNumber())) {
			return false;
		}
		else {
			guestRepository.save(entity);
			return true;
		}
	}
	
	public boolean guestDuplicate(String name, String surname, Double doumentNumber) {
		if(findNameGuest(name)!=null && findSurnameGuest(surname)!=null && findDocumentNumberGuest(doumentNumber)!=null) {
			return true;
		}
		else {
			return false;
		}
		
	}

	public Guest findNameGuest(String name) {
		Optional<Guest> optional = guestRepository.findAll().stream().filter(p -> p.getName().equals(name)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Guest findSurnameGuest(String surname) {
		Optional<Guest> optional = guestRepository.findAll().stream().filter(p -> p.getSurname().equals(surname)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Guest findDocumentNumberGuest(Double documentNumber) {
		Optional<Guest> optional = guestRepository.findAll().stream().filter(p -> p.getDocumentNumber().equals(documentNumber)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	
	/*
	public List<Guest> findName(String name) {
		return guestRepository.findAll().stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
	}

	public List<Guest> findSurname(String surname) {
		return guestRepository.findAll().stream().filter(p -> p.getSurname().equals(surname)).collect(Collectors.toList());
	}

	public List<Guest> findDocumentNumber(String documentNumber) {
		return guestRepository.findAll().stream().filter(p -> p.getName().equals(documentNumber)).collect(Collectors.toList());
	}*/
	
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
