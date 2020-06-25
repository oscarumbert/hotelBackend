package com.online.hotel.arlear.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.hotel.arlear.model.Event;


public interface EventRepository extends JpaRepository<Event,Long> {
	
}
