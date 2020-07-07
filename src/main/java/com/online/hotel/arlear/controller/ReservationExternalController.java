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
import com.online.hotel.arlear.dto.Reservation2DTO;
import com.online.hotel.arlear.dto.ReservationCheckIn;
import com.online.hotel.arlear.dto.ReservationCreateDTO;
import com.online.hotel.arlear.dto.ReservationDTO;
import com.online.hotel.arlear.dto.ReservationDTORooms;
import com.online.hotel.arlear.dto.ReservationEx2DTO;
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
import com.online.hotel.arlear.exception.ErrorTools;
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
	private TicketService ticketService;
	
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
			response=ErrorTools.searchError("");
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
			int cantidadAdultos = reservationRoom.getAdultsCuantity();
			List<Room> roomAvailable= reservationService.FilterRoomAvailable(reservationRoom, room,cantidadAdultos);
			if(roomAvailable!=null) {
				if(roomAvailable.isEmpty()) {					
					response=ErrorTools.searchError("No hay habitaciones disponibles de categoria "+reservationRoom.getRoom()+", para las fechas seleccionadas");
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
				}
				else {
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(roomAvailable);
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
			}		
	}
	
	//Busqueda por fecha de inicio y fecha de fin
	@PostMapping(value="/getDates")
	public ResponseEntity<?> getReservationsByDate(@RequestBody ReservationFind reservation) {
		ResponseDTO response=new ResponseDTO();
		List<String> errors = Validation.applyValidationReservaDates(reservation);
		if(errors.size()==0) {
			Reservation reserv=objectConverter.converter(reservation);
			List<Reservation> reservlist= reservationService.FilterReservationDates(reserv);
			if(reservlist!=null) {				
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(reservlist);
			}
			else {				
				response=ErrorTools.searchError("");				
			}
		}
		else {
			response=ErrorTools.listErrors(errors);
			//response=findList(errors);			
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

	@GetMapping(value="{idReservation}")
	public ResponseEntity<?> getReservation(@PathVariable Long idReservation) {
		ResponseDTO response = new ResponseDTO();
		Reservation reservation=reservationService.find(idReservation);
		if (reservation!=null) {
			ReservationDTO reservationDTO = objectConverter.converter(reservationService.find(idReservation));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body((reservationDTO));
		}
		else {
			response=ErrorTools.searchError("No existe ninguna Reserva  con el id: "+idReservation);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));		
	}

	@PostMapping
	public ResponseEntity<?> createReservation(@RequestBody ReservationEx2DTO reservationDTO) {		
		ResponseDTO response = new ResponseDTO();
		/*ResponseDTO responseRoom = new ResponseDTO();
		ResponseDTO responseContact = new ResponseDTO();*/
		List<String> errors = Validation.applyValidationReservationEx2(reservationDTO);		
		if(errors.size()==0) {			
			Reservation reservation = objectConverter.converter(reservationDTO);			
			Long id = reservationService.createReservation(reservation);
			if(id !=null) {
				Double Sign=reservation.getSign();
				Room room = roomService.findByRoomNumber((reservationDTO.getRoom()));
				if(room!=null) {
					Double price=room.getPrice().doubleValue();
					Double Days=calculateDays(reservation.getBeginDate(),reservation.getEndDate());
					Double totalPrice=price*Days;
					Double resto=price-Sign;				
					reservation.setPrice(totalPrice);
					reservation.setSign(Sign);
					reservation.setRoom(room);				
					if(resto==0.0) {
						reservation.setReservationStatus(ReservationStatus.RESERVADA_PAGADA);
					}
					else {
						reservation.setReservationStatus(ReservationStatus.RESERVADA_SEÑADA);
					}				
					reservationService.setRoomReservation(reservation);
					response= ErrorTools.updateOk(". A la Reserva "+reservation.getId()+" se asigno la Habitación  "+reservation.getRoom().getRoomNumber());
					List<ErrorGeneric> errorsContact = Validation.applyValidationContact(reservationDTO.getContact());
					if (errorsContact.size()==0) {
						Contact contacto = objectConverter.converter(reservationDTO.getContact());
						if(reservationService.update(contacto,reservation.getId())) {						
							//Reservation reservation=reservationService.findID(id);
							Ticket ticketOne=ticketService.findByConctactDocument(contacto.getDocumentNumber());					
							if(ticketOne!=null && ticketOne.getStatus().equals(TicketStatus.ABIERTO)) {
								TransactiontDTO transaction= new TransactiontDTO();
								transaction.setDocument(contacto.getDocumentNumber());
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
								transaction.setDocument(contacto.getDocumentNumber());
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
							response=ErrorTools.createOk("Contacto");
						}else {
						response=ErrorTools.createError("Contacto. Ya existe el contacto. Pero no hay coincidencia en los datos actuales con los datos en la base.");
						}
					}else {
						response = new ResponseDTO("OK",
								   ErrorMessages.CREATE_ERROR.getCode(),
								   errors.toString());
					}
				}else {
					response= ErrorTools.updateError(". No existe la habitacion "+reservation.getRoom());
				}
				response=ErrorTools.createOk("La Reservacion:"+" "+reservation.getId());
				notificationService.create(reservation);
			}else {
				response= ErrorTools.createError("No se pudo crear la Reserva");
			}
		}
		else {
			response=ErrorTools.listErrors(errors);
		}		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
		
	@PutMapping
	public ResponseEntity<?> updateReservation(@RequestBody ReservationUpdateDTO reservationUpdateDTO) {
		ResponseDTO response = new ResponseDTO();
		ReservationCreateDTO reservationCreateDTO = reservationUpdateDTO;
		List<String> errors = Validation.applyValidationReservation(reservationCreateDTO);		
		if(errors.size()==0) {			
			Reservation reservation = objectConverter.converter(reservationUpdateDTO);
			if(reservationService.update(reservationUpdateDTO.getId(),reservation)) {
				response= ErrorTools.updateOk("la Reserva :"+" "+reservationUpdateDTO.getId());
			}else {
				response= ErrorTools.updateError("La Reserva No Existe");
			}
		}else {
			response=ErrorTools.listErrors(errors);
		}		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping(value="{idReservation}")
	public ResponseEntity<?> deleteReservation(@PathVariable Long idReservation) {
		ResponseDTO response = new ResponseDTO();		
		if(!reservationService.delete(idReservation)) {
			response = ErrorTools.deleteError("la Reserva. No Existe");
		}
		else {
			response = ErrorTools.deleteOk("la Reserva");
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	
	private Double calculateDays(LocalDate beginDate, LocalDate endDate) {
		Double days= (double) DAYS.between(beginDate, endDate);
		return days;
	}

	
}
