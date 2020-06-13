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
		//Valida que no existan usuarios con el mismo nombre, apellido, nick, tipo de usuario
		if(userDuplicate(entity.getName(),entity.getSurname(),entity.getUserName(),entity.getUserType())) {
			return false;
		}
		else {
			//entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
			entity.setPassword(encryptPassword(entity.getPassword()));
			userRepository.save(entity);
			return true;
		}
		//return userRepository.save(entity) != null;
	}
	
	public String encryptPassword(String password) 
	{
		long n=Long.parseLong(password);
		String pass=Long.toString(n,4);
		long parse= Long.parseLong(pass, 4)*17*99999*7;
		String storagePassword= Long.toString(parse,16);
		return storagePassword;
		
	}
	
	
	public String desencryptPassword(String password) 
	{
		long parse=Long.parseLong(password, 16)/7/999999/17;
		String getPass=Long.toString(parse,4);
		long getPassword=Long.parseLong(getPass+"",4);
		return getPassword+"";
	}
	
	public boolean update(Long id, UserHotel entity) {
		
		if(findID(id)==null) {
			return false;
		}
		else {
			UserHotel user= findID(id);
			user.setName(entity.getName());
			user.setSurname(entity.getSurname());
			user.setUserName(entity.getUserName());
			user.setPassword(encryptPassword(entity.getPassword()));
			user.setUserType(entity.getUserType());
			userRepository.save(user);
			return true;
		} 		
	}
	
	@Override
	public boolean delete(Long id) {
		if(findID(id)==null) {
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
	
	public boolean userDuplicate(String Name, String surname, String username, UserType type) {
		if(findNameUser(Name)!=null && findSurnameUser(surname)!=null && findUserName(username)!=null && findUserType(type)!=null) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public UserHotel findID(Long id) {
		Optional<UserHotel> optional = userRepository.findAll().stream().filter(p -> p.getIdUser().equals(id)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public UserHotel findNameUser(String Name) {
		Optional<UserHotel> optional = userRepository.findAll().stream().filter(p -> p.getName().equals(Name)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public UserHotel findSurnameUser(String Surname) {
		Optional<UserHotel> optional = userRepository.findAll().stream().filter(p -> p.getSurname().equals(Surname)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public UserHotel findUserName(String userName) {
		Optional<UserHotel> optional = userRepository.findAll().stream().filter(p -> p.getUserName().equals(userName)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public UserHotel findPasswordUser(String password) {
		Optional<UserHotel> optional = userRepository.findAll().stream().filter(p -> p.getPassword().equals(encryptPassword(password))).findAny();
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
	
		if(user.getName()==null) {
			if(user.getUserType()!=null) {
				return findType(user.getUserType());
			}
			else {
				return null;
			}
		}
		else {
			//Todo equals
			if(!user.getName().equals("") && user.getUserType()==null) {
				return findName(user.getName());
			}
			
			else if(user.getUserType()!=null && user.getName().equals("")) {
				return findType(user.getUserType());
			}
			
			else if(user.getUserType()!= null && !user.getName().equals("")) {
				return findNameType(user.getName(),user.getUserType());
			}
			return null;
		}
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

	public Long findUser(String userName, String password) {
		Optional<UserHotel> optional= userRepository.findAll().stream().filter(p -> p.getUserName().equals(userName) && p.getPassword().equals(encryptPassword(password))).findAny();
		return optional.get().getIdUser();
	}
	
	@Override
	public UserHotel find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean update(UserHotel entity) {
		// TODO Auto-generated method stub
		return false;
	}

	public Long FindUser(UserHotel user) {
		if(user.getUserName()==null || user.getUserName().equals("") ||
				user.getPassword().equals("")||user.getPassword()==null) {
				return null;
		}
		else {
			return findUser(user.getUserName(),user.getPassword());
		}	
	}


}
