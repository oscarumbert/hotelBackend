package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Reservation> find() {
		// TODO Auto-generated method stub
		System.out.println("entro");
		return reservationRepository.findAll();
	}

	@Override
	public boolean update(Reservation entity) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Reservation> FilterUser(Reservation entity) {
		// TODO Auto-generated method stub
		return null;
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
