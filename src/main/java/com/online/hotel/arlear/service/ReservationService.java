package com.online.hotel.arlear.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Guest;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.repository.ReservationRepository;

@Service
public class ReservationService implements ServiceGeneric<Reservation>{
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Override
	public boolean create(Reservation entity) {
		return reservationRepository.save(entity) != null;

	}
	public Long createReservation(Reservation entity) {
		return reservationRepository.save(entity).getId();

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
		Reservation reservation = find(id);
		reservation.setContact(entity);
		return reservationRepository.save(reservation)!=null;
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

	

}
