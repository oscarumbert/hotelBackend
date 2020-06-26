package com.online.hotel.arlear.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.online.hotel.arlear.enums.OrderStatus;
import com.online.hotel.arlear.enums.OrderType;

import lombok.Data;

@Entity
@Data
public class OrderRestaurant{
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long idOrder;
	
	private Double priceTotal;
		
	@Enumerated(EnumType.STRING)
	private OrderType orderType;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@ManyToMany (cascade= CascadeType.ALL)
	private List<Product> product;	
	
	@ManyToMany (cascade= CascadeType.ALL)
	private List<Menu> menu;	
	
	private Long numberReservation;
	
}
