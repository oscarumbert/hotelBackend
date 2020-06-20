package com.online.hotel.arlear.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.enums.GenderType;
import com.online.hotel.arlear.model.Contact;
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
							+"https://online-hotel-frontend.herokuapp.com/survey?id="+idForSend
							+"\n\n¡Muchas gracias por confiar en nosotros!\n "
							+"Atentamente: "+"Administracion de OnlineHotel\n "
							+"\nE-mail: onlinehotelpremium@gmail.com" 
							+"\nTel: xxxxxxxxx" 
							+"\nDirección: xxxxxx");	
				}
				if(contact.getGender().equals(GenderType.MASCULINO)) {
					msg.setText("Estimado "+contact.getName()+";\n\t\t"
							+"Estamos encantados de hayas sido nuestro cliente. Esperamos volverte a ver pronto."
							+"\n\t\tQueríamos saber cómo fue tu experiencia durante tu estadia en nuestro Hotel, "
							+"para ello te pedimos que contestes una breve encuesta. Tu respuesta es muy valiosa para nosotros, ya que nos permitirá seguir mejorando. \n "
							+"\t\tPara acceder a la encuesta haga click en el siguiente link: \n"
							+"https://online-hotel-frontend.herokuapp.com/survey?id="+idForSend
							+"\n\n¡Muchas gracias por confiar en nosotros!\n "
							+"Atentamente: "+"Administracion de OnlineHotel\n "
							+"\nE-mail: onlinehotelpremium@gmail.com" 
							+"\nTel: xxxxxxxxx" 
							+"\nDirección: xxxxxx");	
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
}