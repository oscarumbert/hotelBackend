
package com.online.hotel.arlear.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import lombok.Data;

@Entity
@Data
public class Notification {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idNotification;
	
	private Long idReservation;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate sendDate;
	
	private Boolean statusSend;

}
