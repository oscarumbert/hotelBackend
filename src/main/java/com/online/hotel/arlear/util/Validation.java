package com.online.hotel.arlear.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.w3c.dom.events.EventException;

import com.online.hotel.arlear.dto.CardDTO;
import com.online.hotel.arlear.dto.ContactDTO;
import com.online.hotel.arlear.dto.ContactFindDTO;
import com.online.hotel.arlear.dto.ReservationCreateDTO;
import com.online.hotel.arlear.dto.UserDTO;
import com.online.hotel.arlear.enums.CardType;
import com.online.hotel.arlear.enums.DocumentType;
import com.online.hotel.arlear.enums.GenderType;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomAditionals;

import com.online.hotel.arlear.enums.UserType;
import com.online.hotel.arlear.exception.ErrorGeneric;
import com.online.hotel.arlear.exception.ErrorMessages;

public class Validation {
	
	public static List<String> applyValidationUser(UserDTO userDto) {
		
		List<String> errors = new ArrayList<String>();
		//Validaciones de Name
		if(userDto.getName()!=null) {
			if(!userDto.getName().matches("[A-Za-z_]+") && userDto.getName().length()!=0) {
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
					errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Nombre debe ser de tipo String"));
			}
			
			if(userDto.getName().matches("[0-9]*") && userDto.getName().length()!=0) {
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
				errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Nombre debe ser de tipo String"));
			}
					
			if(userDto.getName().length()==0) {
				errors.add(ErrorMessages.EMPTY_FIELD.getCode());
				errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Nombre"));
			}
			
			if(userDto.getName().length()<2 && userDto.getName().length()>0 && !userDto.getName().matches("[0-9]*")) {
			    	errors.add(ErrorMessages.SHORT_WORD.getCode());
					errors.add(ErrorMessages.SHORT_WORD.getDescription("El Nombre"));
			}
			 
			if(userDto.getName().length()>40 && !userDto.getName().matches("[0-9]*")) {
				 	errors.add(ErrorMessages.LONG_WORD.getCode());
					errors.add(ErrorMessages.LONG_WORD.getDescription("El Nombre"));
			}
		}	
		
		if(userDto.getName()==null) {
			errors.add(ErrorMessages.REQUIRED.getCode());
			errors.add(ErrorMessages.REQUIRED.getDescription("Nombre"));
		}
		
		//Validacion Tipo de usario
		boolean existUserType = false;
		for(UserType value: UserType.values()) {
			if(value.name().equals(userDto.getUserType())) {
				existUserType = true;
			}
		}
		
		if(!existUserType) {
			errors.add(ErrorMessages.EMPTY_ENUM.getCode());
			errors.add(ErrorMessages.EMPTY_ENUM.getDescription("El Tipo de Usuario ingresado"));
		}
		
		/*if(userDto.getUserType()==null) {
			errors.add(ErrorMessages.REQUIRED.getCode());
			errors.add(ErrorMessages.REQUIRED.getDescription("Tipo de Usuario"));
		}
		
		if(userDto.getUserType().toString().matches("[0-9]*") && userDto.getUserType().toString().length()!=0) {
			errors.add(ErrorMessages.FORMAT_INVALID.getCode());
				errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Tipo de Usuario debe ser de tipo String"));
		}
		
		if(userDto.getUserType().toString().length()==0) {
			errors.add(ErrorMessages.EMPTY_FIELD.getCode());
			errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Tipo de Usuario"));
		}
		
		if(userDto.getUserType().toString().length()<2 && userDto.getUserType().toString().length()>0 && !userDto.getUserType().toString().matches("[0-9]*")) {
		    	errors.add(ErrorMessages.SHORT_WORD.getCode());
				errors.add(ErrorMessages.SHORT_WORD.getDescription("El Tipo de Usuario"));
		}
		 
		if(userDto.getUserType().toString().length()>20  && !userDto.getUserType().toString().matches("[0-9]*")) {
			 	errors.add(ErrorMessages.LONG_WORD.getCode());
				errors.add(ErrorMessages.LONG_WORD.getDescription("El Tipo de Usuario"));
		}*/
		
	   	return errors;
	}
	
	
	
	
	
	public static List<ErrorGeneric> applyValidationContact(ContactDTO contact){
		List<ErrorGeneric> errors = new ArrayList<ErrorGeneric>();

		if(contact.getName().equals("") || contact.getName() == null) {
			errors.add(new ErrorGeneric(ErrorMessages.REQUIRED.getCode(),
										ErrorMessages.REQUIRED.getDescription("Nombre")));
		}else {
			if(contact.getName().matches("[0-9]*")) {
				errors.add(new ErrorGeneric(ErrorMessages.FORMAT_INVALID.getCode(),
											ErrorMessages.FORMAT_INVALID.getDescription("El nombre debe tener solo letras")));
			}
		}
		if(contact.getSurname().equals("") || contact.getSurname() == null) {
			errors.add(new ErrorGeneric(ErrorMessages.REQUIRED.getCode(),
										ErrorMessages.REQUIRED.getDescription("Apellido")));
		}else {
			if(contact.getSurname().matches("[0-9]*")) {
				errors.add(new ErrorGeneric(ErrorMessages.FORMAT_INVALID.getCode(),
											ErrorMessages.FORMAT_INVALID.getDescription("El apellido debe tener solo letras")));
			}
		}
		if(contact.getMail().equals("") || contact.getMail() == null) {
			errors.add(new ErrorGeneric(ErrorMessages.REQUIRED.getCode(),
										ErrorMessages.REQUIRED.getDescription("Mail")));
		}
		if(contact.getPhone().equals("") || contact.getPhone() == null) {
			errors.add(new ErrorGeneric(ErrorMessages.REQUIRED.getCode(),
										ErrorMessages.REQUIRED.getDescription("Telefono")));
		}else {
			if(!contact.getPhone().matches("[0-9]*")) {
				errors.add(new ErrorGeneric(ErrorMessages.FORMAT_INVALID.getCode(),
											ErrorMessages.FORMAT_INVALID.getDescription("El telefono debe ser de tipo numerico")));
			}
		}
		List<ErrorGeneric> errorsFind = applyValidationContactFind(new ContactFindDTO(contact.getDocumentType(),contact.getDocumentNumber(),contact.getGender()));
		
		if(errorsFind.size()>0) {
			errors.addAll(errorsFind);
		}
		List<ErrorGeneric> errorsCard = appliValidationCard(contact.getCard());

		if(errorsCard.size()>0) {
			errors.addAll(errorsCard);
		}
		return errors;
		
	}
	
	public static List<ErrorGeneric> appliValidationCard(CardDTO card){
		List<ErrorGeneric> errors = new ArrayList<ErrorGeneric>();
		boolean existType = false;
	
		if(card.getCardNumber().equals("") || card.getCardNumber() == null) {
			errors.add(new ErrorGeneric(ErrorMessages.REQUIRED.getCode(),
										ErrorMessages.REQUIRED.getDescription("Numero de tarjeta")));
		}else {
			if(!card.getCardNumber().matches("[0-9]*")) {
				errors.add(new ErrorGeneric(ErrorMessages.FORMAT_INVALID.getCode(),
											ErrorMessages.FORMAT_INVALID.getDescription("Numero de tarjeta debe ser de tipo numerico")));
			}
		}
		if(card.getCardType().equals("") || card.getCardType() == null) {
			errors.add(new ErrorGeneric(ErrorMessages.REQUIRED.getCode(),
										ErrorMessages.REQUIRED.getDescription("Tipo de tarjeta")));
		}else {
			existType = false;
			for(CardType value: CardType.values()) {
				if(value.name().equals(card.getCardType())) {
					existType = true;
				}
			}
			
			if(!existType) {
				errors.add(new ErrorGeneric(ErrorMessages.EMPTY_ENUM.getCode(),
						ErrorMessages.EMPTY_ENUM.getDescription("El tipo de tarjeta ingresado")));
			}
		}
		if(card.getCodeSecurity().equals("") || card.getCodeSecurity() == null) {
			errors.add(new ErrorGeneric(ErrorMessages.REQUIRED.getCode(),
										ErrorMessages.REQUIRED.getDescription("Codigo de tarjeta")));
		}else {
			if(!card.getCodeSecurity().matches("[0-9]*")) {
				errors.add(new ErrorGeneric(ErrorMessages.FORMAT_INVALID.getCode(),
											ErrorMessages.FORMAT_INVALID.getDescription("Codigo de tarjeta debe ser de tipo numerico")));
			}
		}
		if(card.getNameOwner().equals("") || card.getNameOwner() == null) {
			errors.add(new ErrorGeneric(ErrorMessages.REQUIRED.getCode(),
										ErrorMessages.REQUIRED.getDescription("Nombre del dueño de la tarjeta")));
		}
		
		return errors;
	}
	public static List<ErrorGeneric> applyValidationContactFind(ContactFindDTO contact){
		List<ErrorGeneric> errors = new ArrayList<ErrorGeneric>();
		boolean existType = false;
	
		if(contact.getDocumentNumber().equals("") || contact.getDocumentNumber() == null) {
			errors.add(new ErrorGeneric(ErrorMessages.REQUIRED.getCode(),
										ErrorMessages.REQUIRED.getDescription("Numero de documento")));
		}else {
			if(contact.getDocumentNumber() == null && !contact.getDocumentNumber().matches("[0-9]*")) {
				errors.add(new ErrorGeneric(ErrorMessages.FORMAT_INVALID.getCode(),
											ErrorMessages.FORMAT_INVALID.getDescription("Numero de documento debe ser de tipo numerico")));
			}
		}
		if(contact.getDocumentType().equals("") || contact.getDocumentType() == null) {
			errors.add(new ErrorGeneric(ErrorMessages.REQUIRED.getCode(),
										ErrorMessages.REQUIRED.getDescription("Tipo de documento")));
		}else {
			existType = false;
			for(DocumentType value: DocumentType.values()) {
				if(value.name().equals(contact.getDocumentType())) {
					existType = true;
				}
			}
			
			if(!existType) {
				errors.add(new ErrorGeneric(ErrorMessages.EMPTY_ENUM.getCode(),
						ErrorMessages.EMPTY_ENUM.getDescription("El tipo de documento ingresado")));
			}
		}
		if(contact.getGender().equals("") || contact.getGender() == null) {
			errors.add(new ErrorGeneric(ErrorMessages.REQUIRED.getCode(),
										ErrorMessages.REQUIRED.getDescription("Genero")));
		}else {
			existType = false;
			for(GenderType value: GenderType.values()) {
				if(value.name().equals(contact.getGender())) {
					existType = true;
				}
			}
			
			if(!existType) {
				errors.add(new ErrorGeneric(ErrorMessages.EMPTY_ENUM.getCode(),
						ErrorMessages.EMPTY_ENUM.getDescription("El genero ingresado")));
			}
			
		}
		
		
	
		return errors;
		
	}
	public static boolean isLoad(String value) {
		
		if(value.equals("") || value == null) {
			return true;
		}
		return false;
		
	}
	
	
	public static List<String> applyValidationReservation(ReservationCreateDTO reservationDto) {
		
		List<String> errors = new ArrayList<String>(); 
		
			
			if((reservationDto.getAdultsCuantity()==(null))) {
					errors.add(ErrorMessages.REQUIRED.getCode());
					errors.add(ErrorMessages.REQUIRED.getDescription("cantidad de adultos"));
			}else if(!reservationDto.getAdultsCuantity().toString().matches("[0-9]*")){
					errors.add(ErrorMessages.FORMAT_INVALID.getCode());
					errors.add(ErrorMessages.FORMAT_INVALID.getDescription("cantidad de adultos"));
			}else if((Integer.parseInt(reservationDto.getAdultsCuantity()) <= 1)) {
						errors.add(ErrorMessages.INVALID.getCode());
						errors.add(ErrorMessages.INVALID.getDescription("cantidad de adultos"));
			}
				
			if((reservationDto.getChildsCuantity()==(null))) {
					errors.add(ErrorMessages.REQUIRED.getCode());
					errors.add(ErrorMessages.REQUIRED.getDescription("cantidad de niños"));
			}else if(!reservationDto.getChildsCuantity().toString().matches("[0-9]*")){
					errors.add(ErrorMessages.FORMAT_INVALID.getCode());
					errors.add(ErrorMessages.FORMAT_INVALID.getDescription("cantidad de niños"));
			}else if((Integer.parseInt(reservationDto.getChildsCuantity()) <= 0)) {
					errors.add(ErrorMessages.INVALID.getCode());
					errors.add(ErrorMessages.INVALID.getDescription("cantidad de niños"));
			}
									
			if (reservationDto.getBeginDate()==(null)) {
				
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("fecha de inicio"));
			}else{
				if ((reservationDto.getBeginDate().isBefore(LocalDate.now()))) {
					errors.add(ErrorMessages.INVALID.getCode());
					errors.add(ErrorMessages.INVALID.getDescription("fecha de inicio"));
				};
			}
			
			/*if (reservationDto.getBeginDate()==(null)) {
				
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("fecha de inicio"));
			}else if (!(isValidFecha(reservationDto.getBeginDate()))){
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
				errors.add(ErrorMessages.FORMAT_INVALID.getDescription("fecha de inicio"));
			}else {
				LocalDate date=StringAFecha(reservationDto.getBeginDate());
				if ((date.isBefore(LocalDate.now()))) {
					errors.add(ErrorMessages.INVALID.getCode());
					errors.add(ErrorMessages.INVALID.getDescription("fecha de inicio"));
				};
				
			}*/
			if (reservationDto.getEndDate()==(null)) {
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("fecha de fin"));
			}else{
				if ((reservationDto.getEndDate().isBefore(LocalDate.now())) || (reservationDto.getEndDate().isBefore(reservationDto.getBeginDate()))) {
					errors.add(ErrorMessages.INVALID.getCode());
					errors.add(ErrorMessages.INVALID.getDescription("fecha de fin"));
				};
			}
			
			//AGREGAR SI SON REQUERIDOS
			
			if((reservationDto.getAditionals()==(null))) {
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("adicionales"));
			}else if(!isValidAditionals(reservationDto.getAditionals())) {
				errors.add(ErrorMessages.OPTION.getCode());
				errors.add(ErrorMessages.OPTION.getDescription("adicionales"));
			}
			
			if((reservationDto.getReservationType()==(null))) {
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("tipo de reservacion"));
			}else if(!isValidReservationType(reservationDto.getReservationType())) {
				errors.add(ErrorMessages.OPTION.getCode());
				errors.add(ErrorMessages.OPTION.getDescription("Tipo de Reservacion"));
			}
			
			if((reservationDto.getPrice()==(null))) {
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("precio"));
			}else if(!reservationDto.getPrice().toString().matches("\\d+(\\.\\d{1,2})?")){
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
				errors.add(ErrorMessages.FORMAT_INVALID.getDescription("preccio"));
			}else if((Float.parseFloat(reservationDto.getPrice()) < 0)) {
					errors.add(ErrorMessages.INVALID.getCode());
					errors.add(ErrorMessages.INVALID.getDescription("precio"));
			}
			
			if((reservationDto.getSign()==(null))) {
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("seña"));
			}else if(!reservationDto.getSign().toString().matches("\\d+(\\.\\d{1,2})?")){
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
				errors.add(ErrorMessages.FORMAT_INVALID.getDescription("seña"));
			}else if((Float.parseFloat(reservationDto.getSign()) < 0)) {
					errors.add(ErrorMessages.INVALID.getCode());
					errors.add(ErrorMessages.INVALID.getDescription("seña"));
			}
			
		return errors;
	}

	private static boolean isValidReservationType(String type) {
		// TODO Auto-generated method stub
		 return Arrays.stream(ReservationType.values())
			        .map(ReservationType::name)
			        .collect(Collectors.toSet())
			        .contains(type);
	}

	public static boolean esNumero(String num) {
		try {
			Integer.parseInt(num);
		} catch (NumberFormatException ne) {
			return false;
		}
		return true;
	}
	
	public static boolean isValidAditionals( String aditionals) {
	    return Arrays.stream(RoomAditionals.values())
	        .map(RoomAditionals::name)
	        .collect(Collectors.toSet())
	        .contains(aditionals);
	    
	}
	
	private static boolean isValidFecha(String fecha) {
		boolean valid=false;
		String regexp = "\\d{1,2}/\\d{1,2}/\\d{4}";

		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(fecha);
		valid = (matcher.matches());
		try {
			StringAFecha(fecha);
			valid=true;
		}catch (EventException e) {
			valid=false;
		}
		return valid;
		
	}
	
	private static LocalDate StringAFecha (String fecha) {
		CharSequence c=fecha;
		DateTimeFormatter f=DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(c, f);
		
	}

}
