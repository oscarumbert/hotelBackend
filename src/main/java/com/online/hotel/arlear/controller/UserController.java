package com.online.hotel.arlear.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.dto.UserDTO;
import com.online.hotel.arlear.dto.UserDTOLogin;
import com.online.hotel.arlear.dto.UserDTOUpdate;
import com.online.hotel.arlear.dto.UserDTOfind;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.UserHotel;
import com.online.hotel.arlear.service.UserService;
import com.online.hotel.arlear.util.Validation;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ObjectConverter objectConverter;
	
	//User Login
	@PostMapping(value="/userLogin")
	public ResponseEntity<?> getUsers(@RequestBody UserDTOLogin user) {
		ResponseDTO response=new ResponseDTO();
		//validacion
		UserHotel userHotel = objectConverter.converter(user);
		Long userList= userService.FindUser(userHotel);
		
		if(userList!=null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userList);
		}
		else{ 
				response = new ResponseDTO("ERROR",
						   ErrorMessages.USER_NOEXIST.getCode(),
						   ErrorMessages.USER_NOEXIST.getDescription(""));
				return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	}
	
	//Para obtener por nombres
	@PostMapping(value="/get")
	public ResponseEntity<?> getUsers(@RequestBody UserDTOfind user) {
		ResponseDTO response=new ResponseDTO();
		//validacion
		UserHotel userHotel = objectConverter.converter(user);
		List<UserHotel> userList= userService.FilterUser(userHotel);
		
		if(userList!=null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userList);
		}
		else{ 
				response = new ResponseDTO("ERROR",
						   ErrorMessages.SEARCH_ERROR.getCode(),
						   ErrorMessages.SEARCH_ERROR.getDescription(""));
				return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	}
	
	@PostMapping(value="/getAll")
	public ResponseEntity<?> getUsersAll() {		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.find());	
	}
	
	@GetMapping(value="{idUser}")
	public UserDTO getUser(@PathVariable Long idUser) {
		UserDTO userDTO=objectConverter.converter(userService.findID(idUser));
		return userDTO;
	}
	
	//Metodo para crear el usuario.
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
		ResponseDTO response = new ResponseDTO();	
		List<String> errors = Validation.applyValidationUser(userDTO);
		//List<String> code= new ArrayList<>();
		//List<String> messages= new ArrayList<>();
		if(errors.size()==0) {
			
			UserHotel user = objectConverter.converter(userDTO);
			
			if(userService.create(user)) { //Si la creación del usuario es True, se crea el usuario.
				response= new ResponseDTO("OK", 
										ErrorMessages.CREATE_OK.getCode(),
										ErrorMessages.CREATE_OK.getDescription("el usuario:"+" "+userDTO.getName()+" "+userDTO.getSurname()));
			}
			else {
				response= new ResponseDTO("ERROR", 
						ErrorMessages.CREATE_ERROR.getCode(),
						ErrorMessages.CREATE_ERROR.getDescription("El usuario:"+" "+userDTO.getName()+" "+userDTO.getSurname()+" ya se encuentra registrado"));
			}
		}
		else{
			response=findList(errors);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Metodo para modificar el usuario
	@PutMapping
	public ResponseEntity<?> updateUser (@RequestBody UserDTOUpdate userDtoUP) {
		
		ResponseDTO response = new ResponseDTO();
		
		UserDTO userdto= userDtoUP;
		
		List<String> errors = Validation.applyValidationUser(userdto);
		//List<String> code= new ArrayList<>();
		//List<String> messages= new ArrayList<>();
		
		if(errors.size()==0) {		
			UserHotel user = objectConverter.converter(userDtoUP);
			if(userService.update(userDtoUP.getIdUser(),user)) {
					response= new ResponseDTO("OK", 
							ErrorMessages.UPDATE_OK.getCode(),
							ErrorMessages.UPDATE_OK.getDescription("el usuario "+userDtoUP.getName()));
					
			}
			else{
					response= new ResponseDTO("ERROR", 
					ErrorMessages.UPDATE_ERROR.getCode(),
					ErrorMessages.UPDATE_ERROR.getDescription("el usuario. ID No existe"));
				
			}		
		}	
		else{
			response=findList(errors);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Funcion para listar más de un error
	public ResponseDTO findList(List<?> errors){
		ResponseDTO response = new ResponseDTO();
		List<String> code= new ArrayList<>();
		List<String> messages= new ArrayList<>();
		int j=0;
		int i;
		for (i=0; i<errors.size();i=((2*i)/2)+2) {
			response= new ResponseDTO("ERROR",errors.get(j).toString(),errors.get(j+1).toString());
			code.add(response.getCode().toString());
			messages.add(response.getMessage().toString());
			j=((2*j)/2)+2;
		}
		response.setStatus("ERROR");
		response.setCode(code.toString());
		response.setMessage(messages.toString());
		return response;
	}
	
	@DeleteMapping(value="{idUser}")
	public ResponseEntity<?> deleteUser(@PathVariable Long idUser) {
		ResponseDTO response = new ResponseDTO();
		//validacion

		if(!userService.delete(idUser)) {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.DELETED_ERROR.getCode(),
					   ErrorMessages.DELETED_ERROR.getDescription("el usuario. ID incorrecto"));
		}
		
		else	{
			response = new ResponseDTO("OK",
					   ErrorMessages.DELETED_OK.getCode(),
					   ErrorMessages.DELETED_OK.getDescription("el usuario"));
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

}
