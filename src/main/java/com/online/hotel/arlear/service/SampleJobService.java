package com.online.hotel.arlear.service;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.model.Contact;

@Service
public class SampleJobService {
	
	@Autowired
	private JavaMailSender javaMailSender; 
	
	@Autowired
	private ContactService contactService;
	
	//@Scheduled(cron="0 0 12 1/1 * *") //todos los dias a las 12:00hs
	//@Scheduled(cron="0 0/1 * 1/1 * *")//cada minuto
  public void sendNotification() {
    	
    	System.out.println("Prueba");
    	
		SimpleMailMessage msg = new SimpleMailMessage();
		Contact contact = contactService.findByDocument(34534534);

		msg.setTo(contact.getMail());

		msg.setSubject("Notificacion de reserva OnlineHotel");
		
		msg.setText("Estimado/a cliente; usted tiene una reserva para xxx personas el xxxx hasta xxxx ");
		
		javaMailSender.send(msg);
		
		System.out.println("Enviado");
    }
  //@Scheduled(cron="0 0 12 1/1 * *") //todos los dias a las 12:00hs
  //@Scheduled(cron="0 0/1 * 1/1 * *")//cada minuto
  public void sendPoll() throws MessagingException, IOException {
	  
		System.out.println("Prueba");

        MimeMessage msg = javaMailSender.createMimeMessage();       
        
    	Contact contact = contactService.findByDocument(34534534);
        
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(contact.getMail());

        helper.setSubject("Testing from Spring Boot");

        helper.setText("\"Estimado/a cliente;<br>Queriamos saber acerca de su experiencia durante su estadia,"
        		+ " para ello le pedimos que conteste una breve encuesta haciendo click en "
        		+ "link: https://online-hotel-frontend.herokuapp.com/reservations. <br><br>Administracion de Online Hotel", true);

        javaMailSender.send(msg);
        
		System.out.println("Enviado");
	}

}