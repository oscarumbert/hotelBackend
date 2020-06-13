package com.online.hotel.arlear.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ResponseCreateReservation;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.dto.RoomDTO;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Room;
import com.online.hotel.arlear.service.RoomService;

@RestController
@RequestMapping("/room")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@Autowired
	private ObjectConverter objectConverter;
	
	@PostMapping
	public ResponseEntity<?> createRoom(@RequestBody RoomDTO roomDTO) {
		ResponseDTO response;

		Room room = objectConverter.converter(roomDTO);
		if(roomService.create(room)) {
			response = new ResponseCreateReservation(room.getId(),"OK",
					   ErrorMessages.CREATE_OK.getCode(),
					   ErrorMessages.CREATE_OK.getDescription("room"));
		}
		else {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.CREATE_ERROR.getCode(),
					   ErrorMessages.CREATE_ERROR.getDescription("room"));
		}
	
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		
	}
}
