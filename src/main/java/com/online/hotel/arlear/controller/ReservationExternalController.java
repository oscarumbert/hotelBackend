package com.online.hotel.arlear.controller;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
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
import com.online.hotel.arlear.dto.ReportDTO;
import com.online.hotel.arlear.dto.RerservationRoomDTO;
import com.online.hotel.arlear.dto.ReservationCheckIn;
import com.online.hotel.arlear.dto.ReservationCreateDTO;
import com.online.hotel.arlear.dto.ReservationDTO;
import com.online.hotel.arlear.dto.ReservationDTORooms;
import com.online.hotel.arlear.dto.ReservationFind;
import com.online.hotel.arlear.dto.ReservationOpenDTO;
import com.online.hotel.arlear.dto.ReservationUpdateDTO;
import com.online.hotel.arlear.dto.ResponseCreateReservation;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.dto.TicketDTO;
import com.online.hotel.arlear.dto.TransactiontDTO;
import com.online.hotel.arlear.enums.DocumentType;
import com.online.hotel.arlear.enums.GenderType;
import com.online.hotel.arlear.enums.ReservationStatus;
import com.online.hotel.arlear.enums.RoomCategory;
import com.online.hotel.arlear.enums.Section;
import com.online.hotel.arlear.enums.TicketStatus;
import com.online.hotel.arlear.enums.TransactionStatus;
import com.online.hotel.arlear.exception.ErrorGeneric;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Address;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.model.Room;
import com.online.hotel.arlear.model.Subsidiary;
import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.service.ContactService;
import com.online.hotel.arlear.service.NotificationService;
import com.online.hotel.arlear.service.ReservationService;
import com.online.hotel.arlear.service.RoomService;
import com.online.hotel.arlear.service.SampleJobService;
import com.online.hotel.arlear.service.TicketService;
import com.online.hotel.arlear.service.TransactionService;
import com.online.hotel.arlear.util.Validation;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/reservationExternal")
public class ReservationExternalController {
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private ObjectConverter objectConverter;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private SampleJobService sampleJobService;
	
	@Autowired
	private JavaMailSender javaMailSender; 
	
	@Autowired
	private NotificationService notificationService; 
	
	//obtiene todas las reservas
	@GetMapping
	public ResponseEntity<?> getReservations() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(reservationService.find());
	}
	
	@GetMapping(value="/getOpenReservations")
	public ResponseEntity<?> getReservationsOpen() {
		ResponseDTO response=new ResponseDTO();
		List<Reservation> reservations= reservationService.FindReservationOpen();
		if(reservations!=null) {
			List<ReservationOpenDTO> reservationsOpen=ReservationOpen(reservations);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(reservationsOpen);
		}
		else {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.SEARCH_ERROR.getCode(),
					   ErrorMessages.SEARCH_ERROR.getDescription(""));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		}
	
	}
	
	private List<ReservationOpenDTO> ReservationOpen(List<Reservation> reservation) {
		List<ReservationOpenDTO> reservationOpen=new ArrayList<ReservationOpenDTO>();
		for(int i=0;i<reservation.size();i++) {
			Reservation rev=reservation.get(i);
			ReservationOpenDTO revOpen=objectConverter.converterReservationOpen(rev);
			reservationOpen.add(revOpen);
		}
		return reservationOpen;
	}
	
	//Busqueda habitaciones disponibles
	@PostMapping(value="/getRoomsAvailable")
	public ResponseEntity<?> getRoomsAvailable(@RequestBody ReservationDTORooms reservationRoom) {
		ResponseDTO response=new ResponseDTO();
		//List<String> errors = Validation.applyValidationReservaDates(reservation);
			RoomCategory room= RoomCategory.valueOf(reservationRoom.getRoom());
			
			List<Room> roomAvailable= reservationService.FilterRoomAvailable(reservationRoom, room);
			if(roomAvailable!=null) {
				if(roomAvailable.isEmpty()) {
					response = new ResponseDTO("ERROR",
							   ErrorMessages.SEARCH_ERROR.getCode(),
							   ErrorMessages.SEARCH_ERROR.getDescription("No hay habitaciones disponibles de categoria: "+reservationRoom.getRoom()+", para las fechas seleccionadas"));
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
				}
				else {
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(roomAvailable);
				}
				
			}
			else {
				response = new ResponseDTO("ERROR",
						   ErrorMessages.SEARCH_ERROR.getCode(),
						   ErrorMessages.SEARCH_ERROR.getDescription(""));
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
			}
		/*if(errors.size()==0) {
			Reservation reserv=objectConverter.converter(reservation);
			List<Reservation> reservlist= reservationService.FilterReservationDates(reserv);
			if(reservlist!=null) {
				
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(reservlist);
			}
			else {
				response = new ResponseDTO("ERROR",
						   ErrorMessages.SEARCH_ERROR.getCode(),
						   ErrorMessages.SEARCH_ERROR.getDescription(""));
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
			}
		}
		else {
			response=findList(errors);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		}*/
		
	}
	
	//Busqueda por fecha de inicio y fecha de fin
	@PostMapping(value="/getDates")
	public ResponseEntity<?> getReservationsByDate(@RequestBody ReservationFind reservation) {
		ResponseDTO response=new ResponseDTO();
		List<String> errors = Validation.applyValidationReservaDates(reservation);
		//List<String> code= new ArrayList<>();
		//List<String> messages= new ArrayList<>();
		
		if(errors.size()==0) {
			Reservation reserv=objectConverter.converter(reservation);
			List<Reservation> reservlist= reservationService.FilterReservationDates(reserv);
			if(reservlist!=null) {
				
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(reservlist);
			}
			else {
				response = new ResponseDTO("ERROR",
						   ErrorMessages.SEARCH_ERROR.getCode(),
						   ErrorMessages.SEARCH_ERROR.getDescription(""));
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
			}
		}
		else {
			response=findList(errors);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		}
		
	}
	
	@GetMapping(value="{idReservation}")
	public ReservationDTO getReservation(@PathVariable Long idReservation) {

		ReservationDTO reservationDTO = objectConverter.converter(reservationService.find(idReservation));
		return reservationDTO;
		//return ResponseEntity.ok(reservationDTO);
	}

	@PostMapping
	public ResponseEntity<?> createReservation(@RequestBody ReservationCreateDTO reservationDTO) {
		
		ResponseCreateReservation response = new ResponseCreateReservation();		
		//validacion
		//List<String> errors = new ArrayList<String>();
		List<String> errors = Validation.applyValidationReservation(reservationDTO);
		
		if(errors.size()==0) {
			
			Reservation reservation = objectConverter.converter(reservationDTO);
			
			Long id = reservationService.createReservation(reservation);
			if(id !=null) {	
						response = new ResponseCreateReservation(id,"OK",
								   ErrorMessages.CREATE_OK.getCode(),
						   		   ErrorMessages.CREATE_OK.getDescription("reservacion"));
						notificationService.create(reservation);
			}
			
			else {
				response = new ResponseCreateReservation(0L,"Error",
						   ErrorMessages.CREATE_ERROR.getCode(),
						   ErrorMessages.CREATE_ERROR.getDescription("reservacion"));
			}
		}
		else {
			response.setStatus("Error");
			
			response.setMessage(errors.toString());
		}
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	/*public Boolean createTicket(TicketDTO ticketDTO) {
		Ticket ticket = objectConverter.converter(ticketDTO);
		if(ticketService.create(ticket)) {
			return true;
		}
		else {
			return false;
		}
	}*/
	
	/*public Boolean createTransaction(Transaction transaction) {
		
		if(transactionService.create(transaction)) {
			return true;
		}
		else {
			return false;
		}
}*/
	
	@PutMapping
	public ResponseEntity<?> updateReservation(@RequestBody ReservationUpdateDTO reservationUpdateDTO) {
	//public ResponseEntity<?> updateReservation(@RequestBody ReservationDTO reservationUpdateDTO) {
		ResponseDTO response = new ResponseDTO();
		//Reservation reservation = objectConverter.converter(reservationDTO);
		//return ResponseEntity.status(HttpStatus.OK).body(response);
		
		//validacion
		//ReservationDTO reservationCreateDTO = reservationUpdateDTO;
		ReservationCreateDTO reservationCreateDTO = reservationUpdateDTO;
		List<String> errors = Validation.applyValidationReservation(reservationCreateDTO);
		
		if(errors.size()==0) {
			
			Reservation reservation = objectConverter.converter(reservationUpdateDTO);
			//reservation.setRoom(roomService.findByRoomNumber(reservationDTO.getRoomNumber()));
			if(reservationService.update(reservationUpdateDTO.getId(),reservation)) {
				/*response = new ResponseDTO("OK",
										   ErrorMessages.CREATE_OK.getCode(),
										   ErrorMessages.CREATE_OK.getDescription("reservacion"));*/
				response.setStatus("OK");
				response.setMessage("Se modificó la Reservacion correctamente");
			}else {
				response = new ResponseDTO("OK",
						   ErrorMessages.UPDATE_ERROR.getCode(),
						   ErrorMessages.UPDATE_ERROR.getDescription("reservacion"));
			}
		}else {
			response.setStatus("Error");
			response.setMessage("No se pudo modificar la Reservación");
			response.setMessage(errors.toString());
		}
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
		/*if (reservationService.update(idReservation,reservation)) {
			response.setStatus("OK");
			response.setMessage("Se modificó la Reservacion correctamente");
		}else {
			response.setStatus("Error");
			response.setMessage("No se pudo modificar la Reservación");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}*/
	
	@DeleteMapping(value="{idReservation}")
	public ResponseEntity<?> deleteReservation(@PathVariable Long idReservation) {
		ResponseDTO response = new ResponseDTO();
		
		if(!reservationService.delete(idReservation)) {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.DELETED_ERROR.getCode(),
					   ErrorMessages.DELETED_ERROR.getDescription("la reserva. ID incorrecto"));
		}
		else {
			response = new ResponseDTO("OK",
					   ErrorMessages.DELETED_OK.getCode(),
					   ErrorMessages.DELETED_OK.getDescription("la reserva"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@PutMapping("/SetRoom")
	public ResponseEntity<?> createRoom(@RequestBody RerservationRoomDTO reservation) {
		ResponseDTO response = new ResponseDTO();
		Double Sign=reservation.getSign();
		Reservation reserv=reservationService.findID(reservation.getIdRerserva());
		
		if(reserv!=null) {
			Room room = roomService.findByRoomNumber(reservation.getRoomNumber());
			if(room!=null) {
				Double price=room.getPrice().doubleValue();
				Double Days=calculateDays(reserv.getBeginDate(),reserv.getEndDate());
				Double totalPrice=price*Days;
				Double resto=price-Sign;
				
				reserv.setPrice(totalPrice);
				reserv.setSign(Sign);
				reserv.setRoom(room);
				
				if(resto==0.0) {
					reserv.setReservationStatus(ReservationStatus.RESERVADA_PAGADA);
				}
				else {
					reserv.setReservationStatus(ReservationStatus.RESERVADA_SEÑADA);
				}
				
				reservationService.setRoomReservation(reserv);
				response = new ResponseDTO("OK",
						   ErrorMessages.UPDATE_OK.getCode(),
						   ErrorMessages.UPDATE_OK.getDescription("Reserva Room."));
			}
			else {
				response = new ResponseDTO("Error",
						   ErrorMessages.NULL.getCode(),
						   ErrorMessages.NULL.getDescription("No existe la habitacion."));
			}
		}
		else {
			response = new ResponseDTO("Error",
					   ErrorMessages.NULL.getCode(),
					   ErrorMessages.NULL.getDescription("No existe la reserva."));
		}
			
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	private Double calculateDays(LocalDate beginDate, LocalDate endDate) {
		Double days= (double) DAYS.between(beginDate, endDate);
		return days;
	}

	@PutMapping(value="/contact")
	public ResponseEntity<?> createContact(@RequestBody ContactDTO contactDTO) {
		ResponseDTO response = null;
			
			List<ErrorGeneric> errors = Validation.applyValidationContact(contactDTO);
			Long id=Long.parseLong(contactDTO.getIdReservation());
			if(errors.size() == 0) {
				Contact contact = objectConverter.converter(contactDTO);
				if(reservationService.update(contact,id)) {
						
						Reservation reservation=reservationService.findID(id);
						Ticket ticketOne=ticketService.findByConctactDocument(contact.getDocumentNumber());
					
						if(ticketOne!=null && ticketOne.getStatus().equals(TicketStatus.ABIERTO)) {
							TransactiontDTO transaction= new TransactiontDTO();
							transaction.setDocument(contact.getDocumentNumber());
							transaction.setAmount(reservation.getSign());
							transaction.setElement("Habitacion n°: "+reservation.getRoom().getRoomNumber());
							transaction.setDescription("Rerserva de Habitación (Seña). Id: "+reservation.getId());
							transaction.setTransactionStatus(TransactionStatus.PAGADO.toString());
							transaction.setNumberSection(reservation.getId());
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
							transaction.setAmount(reservation.getSign());
							transaction.setElement("Habitacion n°: "+reservation.getRoom().getRoomNumber());
							transaction.setDescription("Rerserva de Habitación (Seña). Id: "+reservation.getId());
							transaction.setTransactionStatus(TransactionStatus.PAGADO.toString());
							transaction.setNumberSection(reservation.getId());
							transaction.setSection(Section.HOTEL);
							transaction.setDate(java.time.LocalDateTime.now());
							
							List<TransactiontDTO> transactionList = new ArrayList<TransactiontDTO>();
							transactionList.add(transaction);
							
							ticketDTO.setTransaction(transactionList);
							
							Ticket ticket = objectConverter.converter(ticketDTO);
							ticket.setContact(reservation.getContact());
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
	
	private boolean verificateTotalPrice(Double price, Double sign, Double debt) {
		if(debt==0.0 && price==sign) {
				return true;
		}
		else if(price==(sign+debt)){
			return true;
		}
		else {
			return false;
		}
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
