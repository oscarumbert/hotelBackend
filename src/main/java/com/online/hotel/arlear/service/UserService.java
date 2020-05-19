package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.enums.UserType;
import com.online.hotel.arlear.model.UserHotel;
import com.online.hotel.arlear.repository.UserRepository;

@Service
public class UserService implements ServiceGeneric<UserHotel>{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean create(UserHotel entity) {
		return userRepository.save(entity) != null;
	}

	public boolean update(Long id, UserHotel entity) {
		if(findID(id).equals(null)){
			return false;
		}
		else {
			UserHotel user= findID(id);
			user.setName(entity.getName());
			user.setSurname(entity.getSurname());
			user.setUserName(entity.getUserName());
			user.setPassword(entity.getPassword());
			user.setUserType(entity.getUserType());
			userRepository.save(user);
			return true;
		}

	}
	@Override
	public boolean delete(Long id) {
		if(findID(id).equals(null)) {
			return false;
		}
		else {
			 userRepository.deleteById(id);
			return true;
		}
	}
	
	@Override
	public List<UserHotel> find() {
		return userRepository.findAll();
	}
	
	public UserHotel findID(Long id) {
		Optional<UserHotel> optional = userRepository.findAll().stream().filter(p -> p.getIdUser().equals(id)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public UserHotel findUserType(UserType user) {
		Optional<UserHotel> optional = userRepository.findAll().stream().filter(p -> p.getUserType().equals(user)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}

	public List<UserHotel> FilterUser(UserHotel user) {
		
		if(!user.getName().equals("") && user.getUserType() == null ) {
			return findName(user.getName());
		}
		else if(user.getUserType() != null && user.getName().equals("")) {
			return findType(user.getUserType());
		}
		else if(user.getUserType() != null && !user.getName().equals("")) {
			return findNameType(user.getName(),user.getUserType());
		}
		return null;
	}
	
	public List<UserHotel> findName(String name) {
		return userRepository.findAll().stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
	}

	public List<UserHotel> findType(UserType type) {
		return userRepository.findAll().stream().filter(p -> p.getUserType().equals(type)).collect(Collectors.toList());
	}

	public List<UserHotel> findNameType(String name, UserType type) {
		return userRepository.findAll().stream().filter(p -> p.getName().equals(name) && p.getUserType().equals(type)).collect(Collectors.toList());
	}

	@Override
	public boolean update(UserHotel entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserHotel find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


}
