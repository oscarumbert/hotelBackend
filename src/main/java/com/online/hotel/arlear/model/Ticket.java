package com.online.hotel.arlear.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.online.hotel.arlear.enums.TicketStatus;

import lombok.Data;

@Entity
@Data
public class Ticket {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long idTicket; //numero de factura
	
	@OneToOne (cascade= CascadeType.ALL)
	private Subsidiary subsidiary;
	
	private LocalDateTime date; //habria que ver si es solo fecha o tmb hora
	
	@Enumerated(EnumType.STRING)
	private TicketStatus status;
	
	@OneToMany (cascade= CascadeType.ALL)
	private List<Transaction> transaction;
	@OneToOne (cascade= CascadeType.ALL)
	private Contact contact;
}
