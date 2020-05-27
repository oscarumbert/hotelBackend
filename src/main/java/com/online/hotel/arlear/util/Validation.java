package com.online.hotel.arlear.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.w3c.dom.events.EventException;

import com.online.hotel.arlear.dto.ProductDTO;
import com.online.hotel.arlear.dto.ProductDTOUpdate;
import com.online.hotel.arlear.dto.ReservationCreateDTO;
import com.online.hotel.arlear.dto.ReservationFind;
import com.online.hotel.arlear.dto.UserDTO;
import com.online.hotel.arlear.dto.UserDTOUpdate;
import com.online.hotel.arlear.enums.ProductType;
import com.online.hotel.arlear.dto.CardDTO;
import com.online.hotel.arlear.dto.ContactDTO;
import com.online.hotel.arlear.dto.ContactFindDTO;
import com.online.hotel.arlear.enums.CardType;
import com.online.hotel.arlear.enums.DocumentType;
import com.online.hotel.arlear.enums.GenderType;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomAditionals;

import com.online.hotel.arlear.enums.UserType;
import com.online.hotel.arlear.exception.ErrorGeneric;
import com.online.hotel.arlear.exception.ErrorMessages;

public class Validation {
	
	//Validaciones de Usuario
	public static List<String> applyValidationUser(UserDTO userDto) {
		
		List<String> errors = new ArrayList<String>();
		
		//Validaciones de Name
		if(userDto.getName()!=null) {
			if(!userDto.getName().matches("^[a-zA-Z\\s]*$") && userDto.getName().length()!=0) {
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
		
		//Validaciones de Apellido
		if(userDto.getSurname()!=null) {
					if(!userDto.getSurname().matches("^[a-zA-Z\\s]*$") && userDto.getSurname().length()!=0) {
						errors.add(ErrorMessages.FORMAT_INVALID.getCode());
							errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Apellido debe ser de tipo String"));
					}
							
					if(userDto.getSurname().length()==0) {
						errors.add(ErrorMessages.EMPTY_FIELD.getCode());
						errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Apellido"));
					}
					
					if(userDto.getSurname().length()<2 && userDto.getSurname().length()>0 && !userDto.getSurname().matches("[0-9]*")) {
					    	errors.add(ErrorMessages.SHORT_WORD.getCode());
							errors.add(ErrorMessages.SHORT_WORD.getDescription("El Apellido"));
					}
					 
					if(userDto.getSurname().length()>40 && !userDto.getSurname().matches("[0-9]*")) {
						 	errors.add(ErrorMessages.LONG_WORD.getCode());
							errors.add(ErrorMessages.LONG_WORD.getDescription("El Apellido"));
					}
				}	
				
		if(userDto.getSurname()==null) {
			errors.add(ErrorMessages.REQUIRED.getCode());
			errors.add(ErrorMessages.REQUIRED.getDescription("Apellido"));
		}
		

		//Validaciones de UserName
		if(userDto.getUserName()!=null) {
					//"^[a-zA-Z]*$"
					if(userDto.getUserName().matches("[a-zA-Z]") && userDto.getUserName().length()!=0) {
						errors.add(ErrorMessages.FORMAT_INVALID.getCode());
							errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Nombre de Usuario debe ser de tipo Alfanumerico"));
					}
					
					if(userDto.getUserName().matches("[0-9]*") && userDto.getUserName().length()!=0) {
						errors.add(ErrorMessages.FORMAT_INVALID.getCode());
							errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Nombre de Usuario debe ser de tipo Alfanumerico"));
					}
					
					if(userDto.getUserName().length()==0) {
						errors.add(ErrorMessages.EMPTY_FIELD.getCode());
						errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Nombre de Usuario"));
					}
					
					if(userDto.getUserName().length()<2 && userDto.getUserName().length()>0 && !userDto.getUserName().matches("[0-9]*")) {
					    	errors.add(ErrorMessages.SHORT_WORD.getCode());
							errors.add(ErrorMessages.SHORT_WORD.getDescription("Nombre de Usuario"));
					}
					 
					if(userDto.getUserName().length()>40 && !userDto.getUserName().matches("[0-9]*")) {
						 	errors.add(ErrorMessages.LONG_WORD.getCode());
							errors.add(ErrorMessages.LONG_WORD.getDescription("El Nombre de Usuario"));
					}
		}	
				
		if(userDto.getUserName()==null) {
			errors.add(ErrorMessages.REQUIRED.getCode());
			errors.add(ErrorMessages.REQUIRED.getDescription("User Name"));
		}
		

		//Validaciones de Password
		if(userDto.getPassword()!=null) {
					if(!userDto.getPassword().matches("[0-9]*") && userDto.getPassword().length()!=0) {
						errors.add(ErrorMessages.FORMAT_INVALID.getCode());
						errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Password debe ser de tipo numerico"));
					}
					
					if( userDto.getPassword().length()==0) {
						errors.add(ErrorMessages.EMPTY_FIELD.getCode());
						errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Password"));
					}
					
					if(userDto.getPassword().length()<4 && userDto.getPassword().length()>0) {
					    	errors.add(ErrorMessages.SHORT_WORD.getCode());
							errors.add(ErrorMessages.SHORT_WORD.getDescription("La Password"));
					}
					 
					if(userDto.getPassword().length()>=5) {
						 	errors.add(ErrorMessages.LONG_WORD.getCode());
							errors.add(ErrorMessages.LONG_WORD.getDescription("La Password"));
					}
				}	
				
		if(userDto.getPassword()==null) {
			errors.add(ErrorMessages.REQUIRED.getCode());
			errors.add(ErrorMessages.REQUIRED.getDescription("Password"));
		}
		
		//Validacion Tipo de usario
		if(userDto.getUserType()!=null) {
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
		}
		
		if(userDto.getUserType()==null) {
			errors.add(ErrorMessages.REQUIRED.getCode());
			errors.add(ErrorMessages.REQUIRED.getDescription("Tipo de Usuario"));
		}
		
	   	return errors;
	}
	
	
	//Validaciones de Busqueda de reserva por fechas
		public static List<String> applyValidationReservaDates(ReservationFind reserv) {
			
			List<String> errors = new ArrayList<String>();
			//Validaciones de fecha inicial
			/*if(reserv.getBeginDate()!=null) {
				if(reserv.getBeginDate().toString().matches("[A-Za-z_]+")) {
					errors.add(ErrorMessages.FORMAT_INVALID.getCode());
						errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Fecha Inicial debe ser de tipo dd/MM/aaaa"));
				}
						
				if(reserv.getBeginDate().toString().length()==0) {
					errors.add(ErrorMessages.EMPTY_FIELD.getCode());
					errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Fecha Inicial"));
				}
			}*/
			
			if(reserv.getBeginDate()==null) {
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("Fecha Inicial"));
			}
			
			//Validaciones de fecha final
			/*if(reserv.getBeginDate()!=null) {
				if(reserv.getEndDate().toString().matches("[A-Za-z_]+")) {
					errors.add(ErrorMessages.FORMAT_INVALID.getCode());
						errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Fecha Final debe ser de tipo dd/MM/aaaa"));
				}
						
				if(reserv.getEndDate().toString().length()==0) {
					errors.add(ErrorMessages.EMPTY_FIELD.getCode());
					errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Fecha Final"));
				}
			}*/
			
			if(reserv.getEndDate()==null) {
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("Fecha Final"));
			}
			
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
		
			
			if((reservationDto.getAdultsCuantity()==(null))||(reservationDto.getAdultsCuantity()==(""))) {
					errors.add(ErrorMessages.REQUIRED.getCode());
					errors.add(ErrorMessages.REQUIRED.getDescription("cantidad de adultos"));
			}else if(!reservationDto.getAdultsCuantity().toString().matches("[0-9]*")){
					errors.add(ErrorMessages.FORMAT_INVALID.getCode());
					errors.add(ErrorMessages.FORMAT_INVALID.getDescription("cantidad de adultos"));
			}else if((Integer.parseInt(reservationDto.getAdultsCuantity()) < 1)) {
						errors.add(ErrorMessages.INVALID.getCode());
						errors.add(ErrorMessages.INVALID.getDescription("cantidad de adultos"));
			}
				
			if((reservationDto.getChildsCuantity()==(null))||(reservationDto.getChildsCuantity()==(null))) {
					errors.add(ErrorMessages.REQUIRED.getCode());
					errors.add(ErrorMessages.REQUIRED.getDescription("cantidad de niños"));
			}else if(!reservationDto.getChildsCuantity().toString().matches("[0-9]*")){
					errors.add(ErrorMessages.FORMAT_INVALID.getCode());
					errors.add(ErrorMessages.FORMAT_INVALID.getDescription("cantidad de niños"));
			}else if((Integer.parseInt(reservationDto.getChildsCuantity()) < 0)) {
					errors.add(ErrorMessages.INVALID.getCode());
					errors.add(ErrorMessages.INVALID.getDescription("cantidad de niños"));
			}
									
			
			/*if (reservationDto.getBeginDate()==(null)||reservationDto.getBeginDate()==("")) {
				
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("fecha de inicio"));
			}else if (!(isValidFecha(reservationDto.getBeginDate()))){
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
				errors.add(ErrorMessages.FORMAT_INVALID.getDescription("fecha de inicio"));
			}else {
				LocalDate date=StringAFecha(reservationDto.getBeginDate());
				if ((date.isBefore(LocalDate.now()))) {
					errors.add(ErrorMessages.OUTDATE.getCode());
					errors.add(ErrorMessages.OUTDATE.getDescription("fecha de inicio"));
				};
				
			}*/
			
			if (reservationDto.getBeginDate()==(null)) {
				
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("fecha de inicio"));
			/*}else if (!(isValidFecha(reservationDto.getBeginDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))){
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
				errors.add(ErrorMessages.FORMAT_INVALID.getDescription("fecha de inicio"));*/
			}else {
				if ((reservationDto.getBeginDate().isBefore(LocalDate.now()))) {
					errors.add(ErrorMessages.OUTDATE.getCode());
					errors.add(ErrorMessages.OUTDATE.getDescription("fecha de inicio"));
				};
				
			}
			
			/*if (reservationDto.getEndDate()==(null)||reservationDto.getEndDate()==("")) {
				
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("fecha de salida"));
			}else if (!(isValidFecha(reservationDto.getEndDate()))){
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
				errors.add(ErrorMessages.FORMAT_INVALID.getDescription("fecha de salida"));
			}else {
				LocalDate date=StringAFecha(reservationDto.getEndDate());
				if ((date.isBefore(LocalDate.now()))) {
					errors.add(ErrorMessages.OUTDATE.getCode());
					errors.add(ErrorMessages.OUTDATE.getDescription("fecha de salida"));
				}else if (((isValidFecha(reservationDto.getBeginDate())))&&(StringAFecha(reservationDto.getEndDate()).isBefore(StringAFecha(reservationDto.getBeginDate())))) {
					errors.add(ErrorMessages.PREVIUS_DATE.getCode());
					errors.add(ErrorMessages.PREVIUS_DATE.getDescription(""));
				};
				
			}*/
			
			if (reservationDto.getEndDate()==(null)) {
				
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("fecha de salida"));
			/*}else if (!(isValidFecha(reservationDto.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))){
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
				errors.add(ErrorMessages.FORMAT_INVALID.getDescription("fecha de salida"));*/
			}else {
				if ((reservationDto.getEndDate().isBefore(LocalDate.now()))) {
					errors.add(ErrorMessages.OUTDATE.getCode());
					errors.add(ErrorMessages.OUTDATE.getDescription("fecha de salida"));
				}else if ((!(reservationDto.getBeginDate()==(null))&&(reservationDto.getEndDate().isBefore(reservationDto.getBeginDate())))) {
					errors.add(ErrorMessages.PREVIUS_DATE.getCode());
					errors.add(ErrorMessages.PREVIUS_DATE.getDescription(""));
				};
				
			}
		
			//AGREGAR SI SON REQUERIDOS
			
			if((reservationDto.getAditionals()==(null))||(reservationDto.getAditionals()==(""))) {
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("adicionales"));
			}else if(!isValidAditionals(reservationDto.getAditionals())) {
				errors.add(ErrorMessages.OPTION.getCode());
				errors.add(ErrorMessages.OPTION.getDescription("adicionales"));
			}
			
			if((reservationDto.getReservationType()==(null))||(reservationDto.getReservationType()==(""))) {
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("tipo de reservacion"));
			}else if(!isValidReservationType(reservationDto.getReservationType())) {
				errors.add(ErrorMessages.OPTION.getCode());
				errors.add(ErrorMessages.OPTION.getDescription("Tipo de Reservacion"));
			}
			
			if((reservationDto.getPrice()==(null))||(reservationDto.getPrice()==(""))) {
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("precio"));
			}else if(!reservationDto.getPrice().toString().matches("\\d+(\\.\\d{1,2})?")){
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
				errors.add(ErrorMessages.FORMAT_INVALID.getDescription("preccio"));
			}else if((Float.parseFloat(reservationDto.getPrice()) < 0)) {
					errors.add(ErrorMessages.INVALID.getCode());
					errors.add(ErrorMessages.INVALID.getDescription("precio"));
			}
			
			if((reservationDto.getSign()==(null))||(reservationDto.getSign()==(""))) {
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

	public static List<String> applyValidationProductUpdate(ProductDTOUpdate ProductDtoUp) {
		//TODO
		return null;
		
		
	}
	public static List<String> applyValidationProduct(ProductDTO productDto) {
		
		List<String> errors = new ArrayList<String>();
		
		//Validaciones de Nombre
		if(productDto.getName()!=null) {
			if(!productDto.getName().matches("^[a-zA-Z\\s]*$") && productDto.getName().length()!=0) {
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
					errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Nombre del product debe ser de tipo String"));
			}
					
			if(productDto.getName().length()==0) {
				errors.add(ErrorMessages.EMPTY_FIELD.getCode());
				errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Nombre"));
			}
			
			if(productDto.getName().length()<1 && productDto.getName().length()>0 && !productDto.getName().matches("[0-9]*")) {
			    	errors.add(ErrorMessages.SHORT_WORD.getCode());
					errors.add(ErrorMessages.SHORT_WORD.getDescription("El Nombre"));
			}
			 
			if(productDto.getName().length()>40 && !productDto.getName().matches("[0-9]*")) {
				 	errors.add(ErrorMessages.LONG_WORD.getCode());
					errors.add(ErrorMessages.LONG_WORD.getDescription("El Nombre"));
			}
		}	
		
		if(productDto.getName()==null) {
			errors.add(ErrorMessages.REQUIRED.getCode());
			errors.add(ErrorMessages.REQUIRED.getDescription("Nombre"));
		}
		
		//Validaciones precio TODO
		if(productDto.getPrice()!=null) {
			if(!productDto.getPrice().matches("[0-9]*") && productDto.getPrice().length()!=0) {
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
				errors.add(ErrorMessages.FORMAT_INVALID.getDescription("El precio debe ser de tipo numerico"));
			}
		}
		
		//Validaciones codigo
		if(productDto.getCode()!=null) {
			if(!productDto.getCode().matches("[0-9]*") && productDto.getCode().length()!=0) {
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
				errors.add(ErrorMessages.FORMAT_INVALID.getDescription("El codigo debe ser de tipo numerico"));
			}
			
			if( productDto.getCode().length()==0) {
				errors.add(ErrorMessages.EMPTY_FIELD.getCode());
				errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Codigo de 0 numeros"));
			}
			
			if(productDto.getCode().length()<4 && productDto.getCode().length()>0) {
			    	errors.add(ErrorMessages.SHORT_WORD.getCode());
					errors.add(ErrorMessages.SHORT_WORD.getDescription("El codigo"));
			}
			 
			if(productDto.getCode().length()>5) {
				 	errors.add(ErrorMessages.LONG_WORD.getCode());
					errors.add(ErrorMessages.LONG_WORD.getDescription("Codigo Largo"));
			}
		}	
		//Validaciones tipo de producto 
		boolean existProductType = false;
		for(ProductType value: ProductType.values()) {
			if(value.name().equals(productDto.getProductType())) {
				existProductType = true;
			}
		}
		
		if(!existProductType) {
			errors.add(ErrorMessages.EMPTY_ENUM.getCode());
			errors.add(ErrorMessages.EMPTY_ENUM.getDescription("El Tipo de product ingresado"));
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
		if  (matcher.matches()) {
			try {
				StringAFecha(fecha);
				valid=true;
			}catch (DateTimeException dte) {
				valid=false;
			}
		}
		return valid;
		
	}
	
	private static LocalDate StringAFecha (String fecha) {
		CharSequence c=fecha;
		DateTimeFormatter f=DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(c, f);
		
	}

}
