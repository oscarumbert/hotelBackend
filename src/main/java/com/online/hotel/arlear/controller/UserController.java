package com.online.hotel.arlear.controller;

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
import com.online.hotel.arlear.exception.ErrorTools;
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
	
	//Metodo para el acceso de usuarios
	@PostMapping(value="/userLogin")
	public ResponseEntity<?> userLogin(@RequestBody UserDTOLogin user) {
		ResponseDTO response=new ResponseDTO();
		UserHotel userHotel = objectConverter.converter(user);
		Long userList= userService.FindUser(userHotel);
		
		if(userList!=null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userList);
		}
		else{ 
				response = ErrorTools.userNoexist();
				return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	}
	
	//Metodo para obtener nombre y tipo de usuario
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
			response = ErrorTools.searchError("");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	}
	
	//Metodo para obtener todo los usuarios
	@PostMapping(value="/getAll")
	public ResponseEntity<?> getUsersAll() {
		ResponseDTO response=new ResponseDTO();
		List<UserHotel> users= userService.find();
		if(users.isEmpty()) {
			response = ErrorTools.searchError("");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		}
		else {
		
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(users);
		}
	}
	
	//Metodo para obtener con el ID, un usuario
	@GetMapping(value="{idUser}")
	public ResponseEntity<?> getUser(@PathVariable Long idUser) {
		ResponseDTO response = new ResponseDTO();
		UserHotel userModel=userService.findID(idUser);
		if(userModel!=null) {
			UserDTO userDTO=objectConverter.converter(userModel);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDTO);
		}
		else {
			response = ErrorTools.searchError("No existe ningun usuario con el id: "+idUser);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	}
	
	//Metodo para crear un usuario.
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
		ResponseDTO response = new ResponseDTO();	
		List<String> errors = Validation.applyValidationUser(userDTO);
		if(errors.size()==0) {
			UserHotel user = objectConverter.converter(userDTO);
			
			if(userService.create(user)) { 
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
			response=ErrorTools.listErrors(errors);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Metodo para modificar un usuario
	@PutMapping
	public ResponseEntity<?> updateUser (@RequestBody UserDTOUpdate userDtoUP) {
		
		ResponseDTO response = new ResponseDTO();
		UserDTO userdto= userDtoUP;
		List<String> errors = Validation.applyValidationUser(userdto);
		
		if(errors.size()==0) {		
			UserHotel user = objectConverter.converter(userDtoUP);
			if(userService.update(userDtoUP.getIdUser(),user)) {
					response= ErrorTools.updateOk("el usuario "+userDtoUP.getName());
			}
			else{
				response= ErrorTools.updateError("el usuario. ID No existe");
			}		
		}	
		else{
			response=ErrorTools.listErrors(errors);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Metodo para eliminar un usuario, con el ID
	@DeleteMapping(value="{idUser}")
	public ResponseEntity<?> deleteUser(@PathVariable Long idUser) {
		ResponseDTO response = new ResponseDTO();
		if(!userService.delete(idUser)) {
			response=ErrorTools.deleteError("el usuario. ID incorrecto");
		}
		else{
			response=ErrorTools.deleteOk("el usuario");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
	
}
