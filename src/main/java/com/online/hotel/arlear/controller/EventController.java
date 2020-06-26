package com.online.hotel.arlear.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.hotel.arlear.dto.EventDTO;
import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Event;
import com.online.hotel.arlear.service.ContactService;
import com.online.hotel.arlear.service.EventService;
import com.online.hotel.arlear.util.Validation;

@RestController
@RequestMapping("/")
public class EventController {
	@Autowired
	private EventService eventService;
	
	@Autowired
	private ObjectConverter objectConverter;
	
	@Autowired
	private ContactService contactService;


	@PostMapping
	public ResponseEntity<?> createEvent(@RequestBody EventDTO eventcreatre){
		ResponseDTO response = new ResponseDTO();
		
		
		List<String> errors = Validation.applyValidationEvent(eventcreatre);
		if(errors.size()==0) {
			
			Event event = objectConverter.converter(eventcreatre);
			
			if(eventService.create(event)) {
				response= new ResponseDTO("OK", 
										ErrorMessages.CREATE_OK.getCode(),
										ErrorMessages.CREATE_OK.getDescription("el evento de: "+ eventcreatre.getContact()));
			}
		}
		else{
			response.setStatus("ERROR");
			response.setCode(errors.get(0).toString());
			response.setMessage(errors.get(1).toString());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping(value="/getAll")
	public ResponseEntity<?> getEventsAll() {
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(eventService.find());
		
	}
}
