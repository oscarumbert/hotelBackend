package com.online.hotel.arlear.controller;

import java.io.IOException;

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
import com.online.hotel.arlear.dto.TicketDTO;
import com.online.hotel.arlear.dto.TicketDTOFind;
import com.online.hotel.arlear.dto.TransactionDTOFind;
import com.online.hotel.arlear.dto.TransactiontDTO;
import com.online.hotel.arlear.enums.TicketStatus;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Subsidiary;
import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.service.ContactService;
import com.online.hotel.arlear.service.TicketService;
import com.online.hotel.arlear.service.TransactionService;
import com.online.hotel.arlear.service.SubsidiaryService;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/ticket")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private ObjectConverter objectConverter;
	@Autowired
	private SubsidiaryService subsidiaryService;

	@GetMapping
	public ResponseEntity<?> getTickets() {
		List<TicketDTOFind> ticketsDTOFind = new ArrayList<TicketDTOFind>();
		ticketService.find().stream().forEach(p -> ticketsDTOFind.add(objectConverter.converterFind(p)));
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketsDTOFind);
	}
	
	@GetMapping(value="{document}")
	public ResponseEntity<?> getTicket(@PathVariable Integer document) {
		
		TicketDTO ticketDTO = objectConverter.converter(ticketService.findByConctact(document));
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketDTO);
	}
	
	@PostMapping
	public ResponseEntity<?> createTicket(@RequestBody TicketDTO ticketDTO) {
		ResponseDTO response = new ResponseDTO();
		Ticket ticket = objectConverter.converter(ticketDTO);
		Subsidiary subsidiary = subsidiaryService.find(1L);
		ticket.setSubsidiary(subsidiary);
		
		if(ticketService.create(ticket)) {
			response.setStatus("OK");
			response.setMessage("Se dio de alta el ticket correctamente");
		}else {
			response.setStatus("Error");
			response.setMessage("No se pudo dar de alta el ticket");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateReservation(@RequestBody TicketDTO ticketDTO) {
		ResponseDTO response = null;
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping(value="{idTicket}")
	public ResponseEntity<?> deleteReservation(@PathVariable Long idTicket) {
		ResponseDTO response = null;
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
		
	@GetMapping("/transaction")
	public ResponseEntity<?> getTransactions() {
		List<TransactionDTOFind> transactionDTOFind = new ArrayList<TransactionDTOFind>();
		transactionService.find().stream().forEach(p -> transactionDTOFind.add(objectConverter.converterFind(p)));
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(transactionDTOFind);
	}
	
	@GetMapping(value="/transaction/{idTransaction}")
	public ResponseEntity<?> getTransaction(@PathVariable Long idTransaction) {
		
		TransactiontDTO transactionDTO = objectConverter.converter(transactionService.findID(idTransaction));
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(transactionDTO);
	}
	
	@PostMapping("/transaction")
	public ResponseEntity<?> createTransaction(@RequestBody TransactiontDTO transactionDTO) {
		ResponseDTO response = new ResponseDTO();
		Transaction transaction = objectConverter.converter(transactionDTO);
		
		Ticket ticket = ticketService.findByConctact(transactionDTO.getDocument());
		ticket.getTransaction().add(transaction);
		
		if(ticketService.update(ticket)) {
			response = new ResponseDTO("OK",
					   ErrorMessages.CREATE_OK.getCode(),
					   ErrorMessages.CREATE_OK.getDescription("transaccion"));
		}else {
			response = new ResponseDTO("Error",
					   ErrorMessages.CREATE_ERROR.getCode(),
					   ErrorMessages.CREATE_ERROR.getDescription("transaccion"));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/Transaction")
	public ResponseEntity<?> updateReservation(@RequestBody TransactiontDTO transactionDTO) {
		ResponseDTO response = null;
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping(value = "/exportTicketClient/{client}", produces = "application/pdf")
	public ResponseEntity<?> exportTicketClient(@PathVariable Integer client) {
		System.out.println("********iniciando export");
		ResponseDTO response = new ResponseDTO();
		Contact contact=contactService.find(client.longValue());
		System.out.println("***********obteniendo contact");
		if(contact==null) {
			System.out.println("*******error en export");
			response = new ResponseDTO("ERROR",
					   ErrorMessages.SEARCH_ERROR.getCode(),
					   ErrorMessages.SEARCH_ERROR.getDescription("No existe ningun contacto con el id: "+client));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
		
		else {
			byte[] fileByte;
			
			try {
		System.out.println("*************inicio de ticket");
				fileByte = ticketService.generateReport(client,null);
			} catch (IOException | JRException e) {
				e.printStackTrace();
				return ResponseEntity.ok("No se pudo crear la factura del cliente");
			}
			Ticket ticket=ticketService.findByTicketOpen(contact.getDocumentNumber());
			ticket.setStatus(TicketStatus.CERRADO);
			ticketService.update(ticket);
			
			System.out.println("*********generando export");
			System.out.println(fileByte);
			return ResponseEntity.ok(fileByte);
		
		}

	}
	
	@GetMapping(value="/exportTicket/{sectionNumber}", produces = "application/pdf")
	public ResponseEntity<?> exportTicket(@PathVariable Integer sectionNumber) throws IOException {
		byte[] fileByte;
		
		try {
			
			fileByte = ticketService.generateReport(null,sectionNumber);
		} catch (IOException | JRException e) {
			return ResponseEntity.ok("No se pudo crear la factura del contador");
		}
		
		return ResponseEntity.ok(fileByte);
	}
}
