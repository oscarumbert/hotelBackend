package com.online.hotel.arlear.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.dto.ReservationDTORooms;
import com.online.hotel.arlear.dto.ReservationOpenDTO;
import com.online.hotel.arlear.enums.ReservationStatus;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomCategory;
import com.online.hotel.arlear.enums.RoomStatus;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Guest;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.model.Room;
import com.online.hotel.arlear.repository.ReservationRepository;
import com.online.hotel.arlear.repository.RoomRepository;

@Service
public class ReservationService implements ServiceGeneric<Reservation>{
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private TicketService ticketService;
	
	
	@Override
	public boolean create(Reservation entity) {
		return reservationRepository.save(entity) != null;

	}

	public Long createReservation(Reservation entity) {
		entity.setReservationStatus(ReservationStatus.EN_ESPERA);
		return reservationRepository.save(entity).getId();

	}
	
	public void setRoomReservation(Reservation reserv) {
		reservationRepository.save(reserv);		
	}

	public boolean update(Long id, Reservation entity) {
		if(find(id).equals(null)){
			return false;
		}
		else {
			
			Reservation reservation= find(id);
			//reservation.setRoom(entity.getRoom());
			reservation.setBeginDate(entity.getBeginDate());
			reservation.setEndDate(entity.getEndDate());
			reservation.setAdultsCuantity(entity.getAdultsCuantity());
			reservation.setChildsCuantity(entity.getChildsCuantity());
			reservation.setReservationType(entity.getReservationType());
			reservation.setAditionals(entity.getAditionals());
			//reservation.setContact(entity.getContact());
			reservation.setPrice(entity.getPrice());
			reservation.setSign(entity.getSign());
			//reservation.setPromotion(entity.getPromotion());
			reservationRepository.save(reservation);
			return true;
		}
	}
	

	@Override
	public boolean delete(Long id) {
		if(findID(id)==null) {
			return false;
		}
		else {
			reservationRepository.deleteById(id);
			return true;
		}
	}

	@Override
	public List<Reservation> find() {
		// TODO Auto-generated method stub
		System.out.println("entro");
		return reservationRepository.findAll();
	}

	/*public List<Reservation> FilterReservationIdDate(Reservation reserv) {
		if(reserv.getBeginDate()==null && reserv.getId()!=null ) {
			return findIDElements(reserv.getId());
			
		}
		else if(reserv.getBeginDate()!=null && reserv.getId()==null) {
			return findBeingDates(reserv.getBeginDate().toString());
		}
		else if(reserv.getBeginDate()!= null && reserv.getId()!=null) {
			return findReservation(reserv.getId(),reserv.getBeginDate());
		}
		return null;
	}*/
	
	public List<Reservation> FilterReservationDates(Reservation reserv) {
		if(reserv.getBeginDate()!=null && reserv.getEndDate()!=null ) {
			return findDataRange(reserv.getBeginDate(),reserv.getEndDate());
			
		}		
		return null;
	}

	private List<Reservation> findDataRange(LocalDate beginDate, LocalDate endDate) {
		return reservationRepository.findAll().stream().filter(
				p -> p.getBeginDate().isAfter(beginDate) && p.getEndDate().isBefore(endDate)).collect(Collectors.toList());		
	}

	/*private List<Reservation> findReservation(Long id, LocalDate beginDate) {
		return reservationRepository.findAll().stream().filter(p -> (p.getId().equals(id) && p.getBeginDate().equals(beginDate))).collect(Collectors.toList());
	}

	private List<Reservation> findBeingDates(String beginDate) {
		return reservationRepository.findAll().stream().filter(
				p -> p.getBeginDate().equals(beginDate)).collect(Collectors.toList());
	}

	private List<Reservation> findIDElements(Long id) {
		return reservationRepository.findAll().stream().filter(
				p -> p.getId().equals(id)).collect(Collectors.toList());
	}*/

	public Reservation findID(Long id) {
		Optional<Reservation> optional = reservationRepository.findAll().stream().filter(p -> p.getId().equals(id)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	@Override
	public boolean update(Reservation entity) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean update(Contact entity,Long id) {
		// TODO Auto-generated method stub
		Contact ContactExist=contactService.findDuplicate(entity.getDocumentNumber(), entity.getGender(), entity.getDocumentType());
		Contact ContactActual=entity;
		
		Reservation reservation = findID(id);
		
		if(ContactExist!=null) {
			if(!ContactExist.getName().equals(ContactActual.getName()) || !ContactExist.getSurname().equals(ContactActual.getSurname())) {
				return false;
			}
			else {
				if(ContactActual.getCard().getCardNumber().equals(ContactExist.getCard().getCardNumber()) &&
					!ContactActual.getCard().getCodeSecurity().equals(ContactExist.getCard().getCodeSecurity())) {
					return false;
				}
				else {
					
						/*Ticket ticketOne=ticketService.findByConctactDocument(ContactExist.getDocumentNumber());
						if(ticketOne!=null) {
							ticketService.delete(ticketOne.getIdTicket());
						}*/
						ContactExist.getCard().setCardNumber(ContactActual.getCard().getCardNumber());
						ContactExist.getCard().setCardType(ContactActual.getCard().getCardType());
						ContactExist.getCard().setCodeSecurity(ContactActual.getCard().getCodeSecurity());
						ContactExist.getCard().setExpirationDate(ContactActual.getCard().getExpirationDate());
						ContactExist.getCard().setNameOwner(ContactActual.getCard().getNameOwner());
						contactService.update(ContactExist);
						reservation.setContact(ContactExist);
						reservationRepository.save(reservation);
						return true;
					}
			}
		}
		else {
			reservation.setContact(ContactActual);
			 reservationRepository.save(reservation);
			 return true;
		}
	}
	
	public boolean update(List<Guest> entities,Long id) {
		// TODO Auto-generated method stub
		Reservation reservation = find(id);
		reservation.setGuests(entities);
		return reservationRepository.save(reservation)!=null;
	}
	
	

	@Override
	public Reservation find(Long id) {
		// TODO Auto-generated method stub
		Optional<Reservation> optional = reservationRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	/*public boolean CheckIn(Reservation reserva, Double debt) {
		if(verificateCheckIn(reserva.getId(),debt)) {
			return true;
		}
		return false;
	}*/
	
	public boolean verificateCheckIn(Long id, Double debt) {
		Optional<Reservation> optional = reservationRepository.findById(id);
		if(optional.isPresent()) {
			Double sign=optional.get().getSign();
			//Double priceTotal= optional.get().getPrice();
			Reservation reserva=optional.get();
			Room room=roomRepository.findById(reserva.getRoom().getId()).get();
			reserva.setSign(debt+sign);
			reserva.setReservationStatus(ReservationStatus.EN_CURSO);
			room.setRoomStatus(RoomStatus.OCUPADA);
			reservationRepository.save(reserva);
			roomRepository.save(room);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean verificateCheckOut(Long id) {
		Optional<Reservation> optional = reservationRepository.findById(id);
		if(optional.isPresent()) {
			Reservation reserva=optional.get();
			Room room=roomRepository.findById(reserva.getRoom().getId()).get();
			reserva.setReservationStatus(ReservationStatus.CERRADA);
			room.setRoomStatus(RoomStatus.DISPONIBLE);
			reservationRepository.save(reserva);
			roomRepository.save(room);
			return true;
		}
		else {
			return false;
		}
	}

	public List<Reservation> FindReservationOpen(){
		
		List<Reservation> listOne= ReservationOpen(reservationRepository.findAll());
		
		if(!listOne.isEmpty()) {
			return listOne;
		}
		else {
			return null;
		}		
	}
	
	private List<Reservation> ReservationOpen(List<Reservation> reservation) {
		List<Reservation> reservationOpen=new ArrayList<Reservation>();
		
		for(int i=0;i<reservation.size();i++) {
			if(reservation.get(i).getReservationStatus().equals(ReservationStatus.EN_CURSO) && reservation.get(i).getRoom().getRoomStatus().equals(RoomStatus.OCUPADA)) {
				reservationOpen.add(reservation.get(i));
			}
		}

		if(!reservationOpen.isEmpty()) {
			return reservationOpen;
		}
		else {
			return reservationOpen;
		}
	}

	public List<Room> FilterRoomAvailable(ReservationDTORooms reservationRoom, RoomCategory room) {
		
		List<Reservation> reservation=reservationRepository.findAll().stream().filter(
				p -> (p.getBeginDate().equals(reservationRoom.getBeginDate()) || p.getBeginDate().isAfter(reservationRoom.getBeginDate()))
				&& (p.getEndDate().isBefore(reservationRoom.getEndDate()) || p.getEndDate().equals(reservationRoom.getEndDate()))).collect(Collectors.toList());		
		
		if(!reservation.isEmpty()) {
			List<Room> roomList=filterRoomsReservation(reservation);
			List<Room> roomFinalList=filterRooms(roomList);
			roomFinalList.removeIf(f -> !f.getCategory().equals(room));

			return roomFinalList;
		}
		else {
			return null;
		}
		
	}

	private List<Room> filterRoomsReservation(List<Reservation> reservation) {
		List<Room> roomList=new ArrayList<Room>();
		for(int i=0; i<reservation.size(); i++) {
			roomList.add(reservation.get(i).getRoom());
		}
		return roomList;
	}

	private List<Room> filterRooms(List<Room> roomList) {
		List<Room> roomFinal= roomService.find();
		for(int i=0;i<roomList.size();i++) {
			Room room=roomList.get(i);
			for(int j=0;j<roomFinal.size();j++) {
				if(roomFinal.get(j).equals(room)) {
					roomFinal.remove(j);
				}
			}
		}
		
	
		return roomFinal;
	}
	
	/*private boolean verificateTotalPrice(Double price, Double sign, Double debt) {
		if(debt==0.0 && price==sign) {
				return true;
		}
		else if(price==(sign+debt)){
			return true;
		}
		else {
			return false;
		}
	}*/

}
