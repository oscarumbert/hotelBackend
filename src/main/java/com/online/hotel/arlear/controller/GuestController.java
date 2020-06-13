package com.online.hotel.arlear.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.hotel.arlear.dto.GuestDTO;
import com.online.hotel.arlear.dto.GuestsDTO;
import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Guest;
import com.online.hotel.arlear.service.GuestService;
import com.online.hotel.arlear.service.ReservationService;
import com.online.hotel.arlear.util.Validation;

@RestController
@RequestMapping("/")
public class GuestController {
	@Autowired
	private GuestService guestService;
	
	@Autowired
	private ObjectConverter objectConverter;
	
	@Autowired
	private ReservationService reservationService;
	
	@PostMapping(value="guest") 
	public ResponseEntity<?> createGuest(@RequestBody GuestDTO guestDTO){
		
		ResponseDTO response = new ResponseDTO();	
		
		List<String> errors = Validation.applyValidationGuest(guestDTO);
		
		if(errors.size()==0) {
			
			Guest guest=objectConverter.converter(guestDTO);
		
			if(guestService.create(guest)) { 
				response= new ResponseDTO("OK", 
										ErrorMessages.CREATE_OK.getCode(),
										ErrorMessages.CREATE_OK.getDescription("El huesped:"+" "+guestDTO.getName()+" "+guestDTO.getSurname()));
			}
			else {
				response= new ResponseDTO("ERROR", 
						ErrorMessages.CREATE_ERROR.getCode(),
						ErrorMessages.CREATE_ERROR.getDescription("El huesped:"+" "+guestDTO.getName()+" "+guestDTO.getSurname()+" ya se encuentra registrado"));
			}
		
		}
		else{
			response=findList(errors);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	@GetMapping(value="guests")
	public ResponseEntity<?> getAllGuests(){
	
		return ResponseEntity.status(HttpStatus.OK).body(guestService.find());

	}
	@PostMapping(value="guests") 
	public ResponseEntity<?> createGuest(@RequestBody GuestsDTO guestDTO){
		
		List<ResponseDTO> response = new ArrayList<ResponseDTO>();	
		List<Guest> guests = new ArrayList<Guest>();
		boolean allRegisterCorrect = true;

		for(GuestDTO guestDto: guestDTO.getGuest()) {
			List<String> errors = Validation.applyValidationGuest(guestDto);
			if(errors.size()==0) {
				
				Guest guest = objectConverter.converter(guestDto);
				guests.add(guest);
			
			}
			else{
				allRegisterCorrect = false;
				response.add(findList(errors));
			}
		}
		if(allRegisterCorrect) {
			for(Guest guest: guests) {
				guest = guestService.createReturnGuest(guest);
				
				if(guest.getId_guess() != null) { 
					response.add(new ResponseDTO("OK", 
							ErrorMessages.CREATE_OK.getCode(),
							ErrorMessages.CREATE_OK.getDescription("El huesped:"+" "+guest.getName()+" "+guest.getSurname())));
				}
				else {
					response.add(new ResponseDTO("ERROR", 
							ErrorMessages.CREATE_ERROR.getCode(),
							ErrorMessages.CREATE_ERROR.getDescription("El huesped:"+" "+guest.getName()+" "+guest.getSurname()+" ya se encuentra registrado")));
				}
			}
			
			reservationService.update(guests,guestDTO.getIdReservation());
			
		}
		
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	
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
}