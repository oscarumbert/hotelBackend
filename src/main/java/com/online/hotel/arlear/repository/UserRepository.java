package com.online.hotel.arlear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.online.hotel.arlear.model.UserHotel;

public interface UserRepository extends JpaRepository<UserHotel,Long>{
	
}

