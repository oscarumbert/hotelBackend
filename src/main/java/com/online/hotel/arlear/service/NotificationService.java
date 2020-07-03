package com.online.hotel.arlear.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.online.hotel.arlear.model.Notification;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.repository.NotificationRepository;


@Service
public class NotificationService implements ServiceGeneric<Notification>{
	
	@Autowired
	private NotificationRepository notificationRepository;

	public void create(Reservation reservation) {
		Notification entity= new Notification();
		entity.setReservation(reservation);
		entity.setSendDate(LocalDate.now());
		entity.setStatusSend(false);
		notificationRepository.save(entity);
	}

	@Override
	public List<Notification> find() {
		return notificationRepository.findAll();
	}

	@Override
	public Notification find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Notification findByReservation(Reservation reservation) {
		Optional<Notification> optional=notificationRepository.findAll().stream().filter(p-> p.getReservation().equals(reservation)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
		
	}
	
	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(Notification entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Notification entity) {
		entity.setStatusSend(true);
		notificationRepository.save(entity);
		return true;
	}


	
	
}
