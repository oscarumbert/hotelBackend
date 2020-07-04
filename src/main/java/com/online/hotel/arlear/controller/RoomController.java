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
import com.online.hotel.arlear.dto.RoomDTO;
import com.online.hotel.arlear.dto.RoomUpdateDTO;
import com.online.hotel.arlear.dto.RoomDTOFind;
import com.online.hotel.arlear.exception.ErrorTools;
import com.online.hotel.arlear.model.Room;
import com.online.hotel.arlear.service.RoomService;
import com.online.hotel.arlear.util.Validation;

@RestController
@RequestMapping("/")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@Autowired
	private ObjectConverter objectConverter;
	
	//Metodo para crear una habitacion
	@PostMapping(value="room")
	public ResponseEntity<?> createRoom(@RequestBody RoomDTO roomDTO) {
		ResponseDTO response;
		List<String> errors = Validation.applyValidationRoom(roomDTO);
		
		if(errors.size()==0) {
			Room room = objectConverter.converter(roomDTO);
			
			if(roomService.create(room)) {
				response=ErrorTools.createOk("room");
			}
			else {
				response= ErrorTools.createError("La habitacion:"+" "+roomDTO.getRoomNumber()+" ya se encuentra registrada");
			}
		}else {
			response=ErrorTools.listErrors(errors);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		
	}
	
	//Metodo para modificar una habitacion
	@PutMapping(value="room")
	public ResponseEntity<?> updateRoom(@RequestBody RoomUpdateDTO roomUpdateDto){
		ResponseDTO response = new ResponseDTO();
		//System.out.print(menucreate.toString());
		RoomDTO roomDTO = roomUpdateDto;
		
		List<String> errors = Validation.applyValidationRoom(roomDTO);
		if(errors.size()==0) {
			Room room=objectConverter.converter(roomDTO);
			
			if(roomService.update(roomUpdateDto.getIdRoom(),room)) {
				response= ErrorTools.updateOk("la habitación:"+" "+room.getRoomNumber());
			}
			else {
				response= ErrorTools.updateError("la habitación No existe");
			}
		}
		else {
			response=ErrorTools.listErrors(errors);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping(value="rooms")
	public ResponseEntity<?> getRooms() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(roomService.find());
	}
	
	@GetMapping(value="findRoom/{numberRoom}")
	public ResponseEntity<?> getRoom(@PathVariable Integer numberRoom) {
		ResponseDTO response = new ResponseDTO();
		Room room=roomService.findRoom(numberRoom);
		
		if(room!=null) {
			RoomDTOFind roomFind=objectConverter.converterRoomUnity(room);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(roomFind);
		}
		else {
			response = ErrorTools.searchError("No existe ninguna habitación número: "+numberRoom);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	}
	
	@DeleteMapping(value="room/{idRoom}")
	public ResponseEntity<?> deleteRoom(@PathVariable Long idRoom) {
		ResponseDTO response = new ResponseDTO();
		//validacion

		if(!roomService.delete(idRoom)) {
			response=ErrorTools.deleteError("la habitacion. ID incorrecto");
		}
		else{
			response=ErrorTools.deleteOk("la habitacion");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
}
