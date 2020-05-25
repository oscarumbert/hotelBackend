package com.online.hotel.arlear.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.model.Reservation;

@Service
public class SampleJobService {
	
	@Autowired
	private JavaMailSender javaMailSender; 
	
	@Autowired
	private ReservationService reservationService;

	
	@Scheduled(cron="0 0 12 1/1 * *") //todos los dias a las 12:00hs
	//@Scheduled(cron="0 0/1 * 1/1 * *")//cada minuto
	public void sendNotification() {
    	
    	System.out.println("Prueba");
    	
		SimpleMailMessage msg = new SimpleMailMessage();
	    
	    Reservation reservation=reservationService.find(3L);
		
	    msg.setTo(reservation.getContact().getMail());

		msg.setSubject("Notificacion de reserva OnlineHotel");
		
		msg.setText("Estimado/a cliente; \n \t \t \t \t  Usted tiene una reserva para "	+ 
				reservation.getAdultsCuantity()+" adulto/s y "+reservation.getChildsCuantity() +
				" niño/s, desde "+ reservation.getBeginDate()+" hasta " + reservation.getEndDate()+
				". \n \n Administracion de OnlineHotel \n E-mail:onlinehotelpremium@gmail.com \n T:xxxxxxxxx \n Dirección:xxxxxx");
		
		javaMailSender.send(msg);
		
		System.out.println("Enviado");
    }
	
	  @Scheduled(cron="0 0 12 1/1 * *") //todos los dias a las 12:00hs
	 //@Scheduled(cron="0 0/1 * 1/1 * *")//cada minuto
	  public void sendPoll() {
	    	
	    	System.out.println("Prueba");
	    	
			SimpleMailMessage msg = new SimpleMailMessage();
		    
		    Reservation reservation=reservationService.find(3L);
			
		    msg.setTo(reservation.getContact().getMail());

			msg.setSubject("Notificacion de Encuesta OnlineHotel");
			
			msg.setText("Estimado/a cliente;\n \t \t \t \t Queriamos saber acerca de su experiencia durante su estadia, "
					+ "para ello le pedimos que conteste una breve encuesta haciendo click en link:"
					+ " https://online-hotel-frontend.herokuapp.com/reservations. \n \n Administracion de OnlineHotel"
					+ " \n E-mail:onlinehotelpremium@gmail.com \n T:xxxxxxxxx \n Dirección:xxxxxx");
			
			javaMailSender.send(msg);
			
			System.out.println("Enviado");
	    }

}