package com.online.hotel.arlear.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.online.hotel.arlear.dto.ContactDTO;
import com.online.hotel.arlear.dto.ContactFindDTO;
import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ReservationCreateDTO;
import com.online.hotel.arlear.dto.ReservationDTO;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.enums.DocumentType;
import com.online.hotel.arlear.enums.GenderType;
import com.online.hotel.arlear.enums.Section;
import com.online.hotel.arlear.exception.ErrorGeneric;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.exception.ExceptionUnique;
import com.online.hotel.arlear.model.Address;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.model.Subsidiary;
import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.service.ContactService;
import com.online.hotel.arlear.service.ReservationService;
import com.online.hotel.arlear.service.RoomService;
import com.online.hotel.arlear.util.Validation;


@RestController
@RequestMapping("/reservation")
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private ObjectConverter objectConverter;
	@Autowired
	private ContactService contactService;
	
	@GetMapping
	public ResponseEntity<?> getReservations() {
		List<ReservationDTO> reservationsDTO = new ArrayList<ReservationDTO>();
		reservationService.find().stream().forEach(p -> reservationsDTO.add(objectConverter.converter(p)));
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(reservationsDTO);
	}
	@GetMapping(value="{idReservation}")
	public ReservationDTO getReservation(@PathVariable Long idReservation) {
		
		
		ReservationDTO reservationDTO = objectConverter.converter(reservationService.findID(idReservation));
		return reservationDTO;
		//return ResponseEntity.ok(reservationDTO);
	}

	@PostMapping
	public ResponseEntity<?> createReservation(@RequestBody ReservationCreateDTO reservationDTO) {
		
		ResponseDTO response = new ResponseDTO();		
		//validacion
		//List<String> errors = new ArrayList<String>();
		List<String> errors = Validation.applyValidationReservation(reservationDTO);
		
		if(errors.size()==0) {
			
			Reservation reservation = objectConverter.converter(reservationDTO);
			reservation.setRoom(roomService.findByRoomNumber(reservationDTO.getRoomNumber()));
			if(reservationService.create(reservation)) {
				response = new ResponseDTO("OK",
										   ErrorMessages.CREATE_OK.getCode(),
										   ErrorMessages.CREATE_OK.getDescription("reservacion"));
			}else {
				response = new ResponseDTO("OK",
						   ErrorMessages.CREATE_ERROR.getCode(),
						   ErrorMessages.CREATE_ERROR.getDescription("reservacion"));
			}
		}else {
			response.setStatus("Error");
			response.setMessage(errors.toString());
		}
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping(value="{idReservation}")
	public ResponseEntity<?> updateReservation(@PathVariable Long idReservation, @RequestBody ReservationDTO reservationDTO) {
		ResponseDTO response = new ResponseDTO();
		Reservation reservation = objectConverter.converter(reservationDTO);
		//return ResponseEntity.status(HttpStatus.OK).body(response);
		
		//validacion
		
		if (reservationService.update(idReservation,reservation)) {
			response.setStatus("OK");
			response.setMessage("Se modificó la Reservacion correctamente");
		}else {
			response.setStatus("Error");
			response.setMessage("No se pudo modificar la Reservación");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@DeleteMapping(value="{idReservation}")
	public ResponseEntity<?> deleteReservation() {
		ResponseDTO response = null;
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PostMapping(value="/simulacionReservacion")
	public ResponseEntity<?> simulacionReservatcion() {
		ResponseDTO response = null;

		
		//validacion
		Address address = new Address();
		address.setName("Peralta Ramos");
		address.setNumber(1234);
		
		Subsidiary subsidiary = new Subsidiary();
		subsidiary.setAddress(address);
		subsidiary.setCuit(2323424);
		subsidiary.setMail("Hotel@gmail.com");
		subsidiary.setName("Hotel pepito");
		subsidiary.setPhone(53455);
		
		Transaction transaction = new Transaction();
		transaction.setAmount(43534.0);
		transaction.setDate(LocalDateTime.now());
		transaction.setDescription("Reserva de salon");
		transaction.setElement("Evento");
		transaction.setSection(Section.SALON);
		
		Contact contact = new Contact();
		contact.setDocumentNumber(34567890);
		contact.setDocumentType(DocumentType.DNI);
		contact.setGender(GenderType.MASCULINO);
		contact.setMail("oscar@gmail.com");
		contact.setName("oscar");
		contact.setPhone(53434543);
		contact.setSurname("umbert");
		
		Ticket ticket = new Ticket();
		ticket.setDate(LocalDateTime.now());
		ticket.setSubsidiary(subsidiary);
		ticket.setTransaction(Arrays.asList(transaction));
		ticket.setContact(contact);
	
		
		
		
		if(contactService.create(contact)) {
			response = new ResponseDTO("OK",
									   ErrorMessages.CREATE_OK.getCode(),
									   ErrorMessages.CREATE_OK.getDescription("prueba"));
		}else {
			response = new ResponseDTO("OK",
					   ErrorMessages.CREATE_ERROR.getCode(),
					   ErrorMessages.CREATE_ERROR.getDescription("prueba"));
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping(value="/contact")
	public ResponseEntity<?> createContact(@RequestBody ContactDTO contactDTO) {
		ResponseDTO response = null;
		try {
			
			List<ErrorGeneric> errors = Validation.applyValidationContact(contactDTO);

			if(errors.size() == 0) {
				if (contactService.createContact(objectConverter.converter(contactDTO))) {
					response = new ResponseDTO("OK",
							   ErrorMessages.CREATE_OK.getCode(),
							   ErrorMessages.CREATE_OK.getDescription("Contact"));
				}else {
					response = new ResponseDTO("OK",
							   ErrorMessages.CREATE_ERROR.getCode(),
							   ErrorMessages.CREATE_ERROR.getDescription("Contact"));
				}
			}else {
				response = new ResponseDTO("OK",
						   ErrorMessages.CREATE_ERROR.getCode(),
						   errors.toString());
			}
			
		} catch (ExceptionUnique e) {
			response = new ResponseDTO("OK",
					   ErrorMessages.CREATE_ERROR_UNIQUE.getCode(),
					  e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	@PostMapping(value="/getContact")
	public ResponseEntity<?> findContact(@RequestBody ContactFindDTO contactDto) {
		ResponseDTO response = null;
	
			List<ErrorGeneric> errors = Validation.applyValidationContactFind(contactDto);

			if(errors.size() == 0) {
				Contact contact = contactService.findUnique(contactDto);
				if (contact != null) {
					ContactDTO contactResponse = objectConverter.converter(contact);
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(contactResponse);

				}else {
					response = new ResponseDTO("OK",
							   ErrorMessages.FIND_ERROR.getCode(),
							   ErrorMessages.FIND_ERROR.getDescription("Contact"));
				}
			}else {
				response = new ResponseDTO("OK",
						   ErrorMessages.FIND_ERROR.getCode(),
						   errors.toString());
			}
			
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		
	}
	
	

}
