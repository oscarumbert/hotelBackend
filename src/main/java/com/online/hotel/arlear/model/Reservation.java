package com.online.hotel.arlear.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomAditionals;

import lombok.Data;

@Entity
@Data
public class Reservation {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Room room;	
	private LocalDate beginDate;
	private LocalDate endDate;
	@Min(1)
	@NotNull
	private Integer adultsCuantity;
	private Integer childsCuantity;
	
	@Enumerated(EnumType.STRING)
	private ReservationType reservationType;
	
	@Enumerated(EnumType.STRING)
	private RoomAditionals aditionals;
	
	@OneToOne(cascade= CascadeType.ALL)
	private Contact contact;//id contacto asociado
	
	private Double price;
	private Double sign;//seña
	//private Payment payment;(solo con tarjeta?)
	//private Long card;//(si medio de pago es TC o TD)
	
	/*@OneToOne(cascade= CascadeType.ALL)
	private Promotion promotion;*/
	
	//@Enumerated(EnumType.STRING)
	//private ReservationStatus status;
	
	
}
