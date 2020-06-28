package com.online.hotel.arlear.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.hotel.arlear.dto.ContactDTO;
import com.online.hotel.arlear.dto.ContactFindDTO;
import com.online.hotel.arlear.dto.EventDTO;
import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.dto.TicketDTO;
import com.online.hotel.arlear.dto.TransactiontDTO;
import com.online.hotel.arlear.enums.Section;
import com.online.hotel.arlear.enums.TicketStatus;
import com.online.hotel.arlear.enums.TransactionStatus;
import com.online.hotel.arlear.exception.ErrorGeneric;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Event;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.service.ContactService;
import com.online.hotel.arlear.service.EventService;
import com.online.hotel.arlear.service.TicketService;
import com.online.hotel.arlear.service.TransactionService;
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
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private TransactionService transactionService;


	@PostMapping(value="event") 
	public ResponseEntity<?> createEvent(@RequestBody EventDTO eventcreatre){
		ResponseDTO response = new ResponseDTO();
		
		
		List<String> errors = Validation.applyValidationEvent(eventcreatre);
		if(errors.size()==0) {
			
			Event event = objectConverter.converter(eventcreatre);
			
			if(eventService.create(event)) {
				response= new ResponseDTO("OK", 
										ErrorMessages.CREATE_OK.getCode(),
										ErrorMessages.CREATE_OK.getDescription("el evento de: "+ eventcreatre.getEventType()));
			}
		}
		else{
			response.setStatus("ERROR");
			response.setCode(errors.get(0).toString());
			response.setMessage(errors.get(1).toString());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping(value="event/getAll")
	public ResponseEntity<?> getAllEvents() {
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(eventService.find());
		
	}
	
	@PutMapping(value="event/contact")
	public ResponseEntity<?> createContact(@RequestBody ContactDTO contactDTO) {
		ResponseDTO response = null;
			
			List<ErrorGeneric> errors = Validation.applyValidationContact(contactDTO);
			Long id=Long.parseLong(contactDTO.getIdEvent());
			if(errors.size() == 0) {
				Contact contact = objectConverter.converter(contactDTO);
				if(eventService.update(contact,id)) {
						
						Event event=eventService.findID(id);
						Ticket ticketOne=ticketService.findByConctactDocument(contact.getDocumentNumber());
					
						if(ticketOne!=null && ticketOne.getStatus().equals(TicketStatus.ABIERTO)) {
							TransactiontDTO transaction= new TransactiontDTO();
							transaction.setDocument(contact.getDocumentNumber());
							transaction.setAmount(null);
							transaction.setElement("Evento n°: "+event.getIdEvent());
							transaction.setDescription("Rerserva de Evento (Seña). Id: "+event.getIdEvent());
							transaction.setTransactionStatus(TransactionStatus.PAGADO.toString());
							transaction.setNumberSection(event.getIdEvent());
							transaction.setSection(Section.HOTEL);
							transaction.setDate(java.time.LocalDateTime.now());
							
							Transaction transactionModel=objectConverter.converter(transaction);
							
							ticketOne.getTransaction().add(transactionModel);
							ticketService.update(ticketOne);
						}
						
						if(ticketOne==null || ticketOne.getStatus().equals(TicketStatus.CERRADO)) {
							TicketDTO ticketDTO=new TicketDTO();
							ticketDTO.setDate(java.time.LocalDateTime.now());
				
							TransactiontDTO transaction= new TransactiontDTO();
							transaction.setDocument(contact.getDocumentNumber());
							transaction.setAmount(null);
							transaction.setElement("Evento n°: "+event.getIdEvent());
							transaction.setDescription("Rerserva de Evento (Seña). Id: "+event.getIdEvent());
							transaction.setTransactionStatus(TransactionStatus.PAGADO.toString());
							transaction.setNumberSection(event.getIdEvent());
							transaction.setSection(Section.HOTEL);
							transaction.setDate(java.time.LocalDateTime.now());
							
							List<TransactiontDTO> transactionList = new ArrayList<TransactiontDTO>();
							transactionList.add(transaction);
							
							ticketDTO.setTransaction(transactionList);
							
							Ticket ticket = objectConverter.converter(ticketDTO);
							ticket.setContact(event.getContact());
							ticket.setStatus(TicketStatus.ABIERTO);
							ticketService.create(ticket);
						}
						
						response = new ResponseDTO("OK",
							   ErrorMessages.CREATE_OK.getCode(),
							   ErrorMessages.CREATE_OK.getDescription("Contact"));
				}
				else {
					response = new ResponseDTO("OK",
							   ErrorMessages.UPDATE_ERROR.getCode(),
							   ErrorMessages.UPDATE_ERROR.getDescription("Contact. Ya existe el contacto. Pero no hay coincidencia en los datos actuales con los datos en la base."));
				}
			}else {
				response = new ResponseDTO("OK",
						   ErrorMessages.CREATE_ERROR.getCode(),
						   errors.toString());
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
	
	@DeleteMapping(value="event/{idEvent}")
	public ResponseEntity<?> deleteEvent(@PathVariable Long idEvent) {
		ResponseDTO response = new ResponseDTO();
		//validacion
		if(!eventService.delete(idEvent)) {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.DELETED_ERROR.getCode(),
					   ErrorMessages.DELETED_ERROR.getDescription("el evento. ID incorrecto"));
		}
		
		else	{
			response = new ResponseDTO("OK",
					   ErrorMessages.DELETED_OK.getCode(),
					   ErrorMessages.DELETED_OK.getDescription("el evento"));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
}
