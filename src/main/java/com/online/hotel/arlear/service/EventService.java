package com.online.hotel.arlear.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Event;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.repository.EventRepository;

@Service
public class EventService implements ServiceGeneric<Event>{
	@Autowired 
	private EventRepository eventRepository;
	
	@Autowired
	private ContactService contactService;
	

	public boolean create(Event entity) {
		return eventRepository.save(entity) != null;
	}
	
	public boolean eventDuplicate(LocalDateTime start, LocalDateTime end) {
		if(findStartDateHour(start)!=null && findEndDateHour(end)!=null) {
			return true;
		}
		else {
			return false;
		}
		
	}

	public Event findStartDateHour(LocalDateTime start) {
		Optional<Event> optional = eventRepository.findAll().stream().filter(p -> p.getStartDateHour().equals(start)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Event findEndDateHour(LocalDateTime end) {
		Optional<Event> optional = eventRepository.findAll().stream().filter(p -> p.getEndDateHour().equals(end)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public List<Event> findByRangeDate(LocalDateTime begin, LocalDateTime end) {
		if(end==null) {
			return eventRepository.findAll().stream()
					.filter(p -> p.getStartDateHour().isAfter(begin) || 
							p.getEndDateHour().isAfter(begin))
					.collect(Collectors.toList());
		}else {
			return eventRepository.findAll().stream()
					.filter(p -> (p.getStartDateHour().isAfter(begin) || 
								  p.getEndDateHour().isAfter(begin)) && 
								 (p.getStartDateHour().isBefore(end) || 
								  p.getEndDateHour().isBefore(end))
							).collect(Collectors.toList());
		}
		
		
	}
	public List<Event> findByEventType(String eventType) {
		return eventRepository.findAll().stream()
				.filter(p -> p.getEventType().toString().equals(eventType))
				.collect(Collectors.toList());
	
	}	
	
	public List<Event> findByFilter(String eventType,LocalDateTime begin, LocalDateTime end) {
	
		if(!eventType.equals("") && begin==null && end==null) {
			return findByEventType(eventType);
		}else if(!eventType.equals("") && begin!=null) {
			return findByRangeDate(begin,end).stream().filter(p -> p.getEventType().toString().equals(eventType)).collect(Collectors.toList());
		}else if(eventType.equals("") && begin!=null){
			return findByRangeDate(begin,end);
		}else if(eventType.equals("") && begin==null) {
			return find();
		}
		return null;
		
		
	}
	
	
	
	@Override
	public boolean delete(Long id) {
		if(find(id)==null) {
			return false;
		}
		else {
			eventRepository.deleteById(id);
			return true;
		}
	}

	@Override
	public List<Event> find() {
		return eventRepository.findAll();
	}
	
	public Event findID(Long id) {
		Optional<Event> optional = eventRepository.findAll().stream().filter(p -> p.getIdEvent().equals(id)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}

	public boolean update(Long id, Event entity) {
		if(find(id).equals(null)){
			return false;
		}
		else {
			
			Event event= find(id);
			//reservation.setRoom(entity.getRoom());
			event.setContact(entity.getContact());
			event.setEndDateHour(entity.getEndDateHour());
			event.setEventType(entity.getEventType());
			event.setGuests(entity.getGuests());
			event.setStartDateHour(entity.getStartDateHour());
			return true;
		}
	}
	
	public boolean update(Contact entity,Long id) {
		// TODO Auto-generated method stub
		Contact ContactExist=contactService.findDuplicate(entity.getDocumentNumber(), entity.getGender(), entity.getDocumentType());
		Contact ContactActual=entity;
		
		Event event = findID(id);
		
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
						event.setContact(ContactExist);
						eventRepository.save(event);
						return true;
					}
			}
		}
		else {
			event.setContact(ContactActual);
			 eventRepository.save(event);
			 return true;
		}
	}

	@Override
	public Event find(Long id) {
		Optional<Event> optional = eventRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}

	@Override
	public boolean update(Event entity) {
		// TODO Auto-generated method stub
		return false;
	}

}
