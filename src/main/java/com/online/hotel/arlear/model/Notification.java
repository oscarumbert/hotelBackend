package com.online.hotel.arlear.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.online.hotel.arlear.enums.NotificationType;

import java.time.LocalDate;
import lombok.Data;

@Entity
@Data
public class Notification {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idNotification;
	
	private String message;
	private LocalDate sendDate;
	private String receive;
	private String status;
	private String status_description;
	
	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;

}
