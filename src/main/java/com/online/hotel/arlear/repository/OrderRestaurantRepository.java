package com.online.hotel.arlear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.online.hotel.arlear.model.OrderRestaurant;

public interface OrderRestaurantRepository extends JpaRepository<OrderRestaurant,Long> {

}
