package com.online.hotel.arlear.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.hotel.arlear.model.Guest;

public interface GuestRepository  extends JpaRepository<Guest,Long> {

}