package com.online.hotel.arlear.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Detail {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id_etail;
	
	@OneToOne
	private Ticket id_ticket;
	
	@OneToOne
	private Item id_item;
	
	private Integer quantity;
	private Integer price;

}
