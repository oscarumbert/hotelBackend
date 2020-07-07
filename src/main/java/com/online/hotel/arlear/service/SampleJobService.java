package com.online.hotel.arlear.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.enums.GenderType;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Notification;
import com.online.hotel.arlear.model.Reservation;

import java.util.List;

@Service
public class SampleJobService {
	
	@Autowired
	private JavaMailSender javaMailSender; 
	
	@Autowired
	private ReservationService reservationService;

	@Autowired
	private NotificationService notificationService;

	@Scheduled(cron="0 0/5 * 1/1 * *")//5 minuto
	public void sendNotification() {
	    	
	    	System.out.println("Prueba");
	    	
			SimpleMailMessage msg = new SimpleMailMessage();
		    
			List<Reservation> reservas=reservationService.find();
			
			List<Notification> notifications=notificationService.find();

			System.out.println("comenzando");
			
		    for(Reservation reservation: reservas) {
		    	
				System.out.println("comenzando2");
		    	
		    	if(reservation.getBeginDate().plusDays(-2).equals(LocalDate.now()) ) { 
		    		
		    		System.out.println("comenzando3");
			        
		    	    for(Notification notification: notifications) {
		    	    	
		    	    	if(notification.getIdReservation().equals(reservation.getId()) && notification.getStatusSend()==false) {
		    	    		
		    	    		msg.setTo(reservation.getContact().getMail());

		    	    		msg.setSubject("Notificacion de Reserva OnlineHotel");
			
		    	    		msg.setText("Estimado/a cliente; \n \t \t \t \t  Usted tiene una reserva para "	+ 
		    	    				reservation.getAdultsCuantity()+" adulto/s y "+reservation.getChildsCuantity() +
		    	    				" niño/s, desde "+ reservation.getBeginDate()+" hasta " + reservation.getEndDate()+
		    	    				". \n \n Administracion de OnlineHotel \n E-mail:onlinehotelpremium@gmail.com \n Tel:112345632 \n Dirección: Calle falsa 1234");
			
		    	    		javaMailSender.send(msg);
		    	    		
		    	    		notificationService.update(notification);
		    	    		
		    	    		System.out.println("Enviado");
		    	    		
		    	    	}
		    	    	
		    		System.out.println("Salgo de notificacion");
		    		
		    	    }
		    	  
		    		}
		    	  System.out.println("Salgo de reserva");
	      }
		    System.out.println("Fin");
	  }

	public void sendMessageContactReservation(Contact contact, Long idReservation) {
		  String idForSend= encryptID(idReservation) ;
		  SimpleMailMessage msg = new SimpleMailMessage();
			   // Reservation reservation=reservationService.find(3L);		
				//contact.getMail()
			    msg.setTo(contact.getMail());
				msg.setSubject("Notificación de Encuesta OnlineHotel");
				//?id=639
				
				if(contact.getGender().equals(GenderType.FEMENINO)) {
					msg.setText("Estimada "+contact.getName()+";\n\t\t "
							+"Estamos encantados de hayas sido nuestra cliente. Esperamos volverte a ver pronto."
							+"\n\t\tQueríamos saber cómo fue tu experiencia durante tu estadia en nuestro Hotel, "
							+"para ello te pedimos que contestes una breve encuesta. Tu respuesta es muy valiosa para nosotros, ya que nos permitirá seguir mejorando. \n "
							+"\t\tPara acceder a la encuesta haga click en el siguiente link: \n"
							+"https://online-hotel-produccion.herokuapp.com/survey?idReservation="+idForSend
							+"\n\n¡Muchas gracias por confiar en nosotros!\n "
							+"Atentamente: "+"Administracion de OnlineHotel\n "
							+"\nE-mail: onlinehotelpremium@gmail.com" 
							+"\nTel: 112345632" 
							+"\nDirección: Calle falsa 1234");	
				}
				if(contact.getGender().equals(GenderType.MASCULINO)) {
					msg.setText("Estimado "+contact.getName()+";\n\t\t"
							+"Estamos encantados de hayas sido nuestro cliente. Esperamos volverte a ver pronto."
							+"\n\t\tQueríamos saber cómo fue tu experiencia durante tu estadia en nuestro Hotel, "
							+"para ello te pedimos que contestes una breve encuesta. Tu respuesta es muy valiosa para nosotros, ya que nos permitirá seguir mejorando. \n "
							+"\t\tPara acceder a la encuesta haga click en el siguiente link: \n"
							+"https://online-hotel-produccion.herokuapp.com/survey?idReservation="+idForSend
							+"\n\n¡Muchas gracias por confiar en nosotros!\n "
							+"Atentamente: "+"Administracion de OnlineHotel\n "
							+"\nE-mail: onlinehotelpremium@gmail.com" 
							+"\nTel: 112345632" 
							+"\nDirección: Calle falsa 1234");	
				}
					
				javaMailSender.send(msg);
	  }
	  	  
	public String encryptID(Long Id) 
		{
			//long n=Long.parseLong(Id);
			String Ids=Long.toString(Id,4);
			long parse= Long.parseLong(Ids, 4)*17*99999*7;
			String storageId= Long.toString(parse,16);
			return storageId;	
		}
	  
	public String encryptPassword(String password) 
		{
			long n=Long.parseLong(password);
			String pass=Long.toString(n,4);
			long parse= Long.parseLong(pass, 4)*17*99999*7;
			String storagePassword= Long.toString(parse,16);
			return storagePassword;
		}

	public String desencryptPassword(String password) 
	{
		long parse=Long.parseLong(password, 16)/7/999999/17;
		String getPass=Long.toString(parse,4);
		long getPassword=Long.parseLong(getPass+"",4);
		return getPassword+"";
	}
}