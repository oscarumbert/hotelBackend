package com.online.hotel.arlear.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

	public boolean update(Long id, Reservation entity) {
		if(findID(id).equals(null)){
			return false;
		}
		else {
			Reservation reservation= findID(id);
			reservation.setRoom(entity.getRoom());
			reservation.setBeginDate(entity.getBeginDate());
			reservation.setEndDate(entity.getEndDate());
			reservation.setAdultsCuantity(entity.getAdultsCuantity());
			reservation.setChildsCuantity(entity.getChildsCuantity());
			reservation.setReservationType(entity.getReservationType());
			reservation.setAditionals(entity.getAditionals());
			reservation.setContact(entity.getContact());
			reservation.setPrice(entity.getPrice());
			reservation.setSign(entity.getSign());
			//reservation.setPromotion(entity.getPromotion());
			reservationRepository.save(reservation);
			return true;
		}
	}
	
	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Reservation> find() {
		// TODO Auto-generated method stub
		System.out.println("entro");
		return reservationRepository.findAll();
	}

	public List<Reservation> FilterUserById(Reservation reserv) {
		if(reserv.getBeginDate()==null && reserv.getId()!=null ) {
			return findIDElements(reserv.getId());
			
		}
		else if(reserv.getBeginDate()!=null && reserv.getId()==null) {
			return findBeingDates(reserv.getBeginDate());
		}
		else if(reserv.getBeginDate()!= null && reserv.getId()!=null) {
			return findReservation(reserv.getBeginDate(),reserv.getId());
		}
		
		return null;
	}
	
	public List<Reservation> FilterUserByDate(Reservation reserv) {
		if(reserv.getBeginDate()!=null && reserv.getId()!=null ) {
			return findDataRange(reserv.getBeginDate(),reserv.getEndDate());
			
		}		
		return null;
	}

	private List<Reservation> findDataRange(LocalDate beginDate, LocalDate endDate) {
		return reservationRepository.findAll().stream().filter(
				p -> p.getBeginDate().isAfter(beginDate) && p.getEndDate().isBefore(endDate)).collect(Collectors.toList());		
	}

	private List<Reservation> findReservation(LocalDate beginDate, Long id) {
		return reservationRepository.findAll().stream().filter(p -> (p.getId().equals(id) && p.getBeginDate().equals(beginDate))).collect(Collectors.toList());
	}

	private List<Reservation> findBeingDates(LocalDate beginDate) {
		return reservationRepository.findAll().stream().filter(p -> p.getBeginDate().equals(beginDate)).collect(Collectors.toList());
	}

	private List<Reservation> findIDElements(Long id) {
		return reservationRepository.findAll().stream().filter(p -> p.getId().equals(id)).collect(Collectors.toList());
	}

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

	

	@Override
	public Reservation find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
