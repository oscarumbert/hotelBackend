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

import com.online.hotel.arlear.dto.ReservationCreateDTO;
import com.online.hotel.arlear.dto.UserDTO;
import com.online.hotel.arlear.dto.UserDTOUpdate;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomAditionals;

import com.online.hotel.arlear.enums.UserType;

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
					if(!userDto.getSurname().matches("[A-Za-z_]+") && userDto.getSurname().length()!=0) {
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
					if(!userDto.getUserName().matches("[A-Za-z_]+") && userDto.getUserName().length()!=0) {
						errors.add(ErrorMessages.FORMAT_INVALID.getCode());
							errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Nombre de Usuario debe ser de tipo String"));
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
					 
					if(userDto.getPassword().length()>5) {
						 	errors.add(ErrorMessages.LONG_WORD.getCode());
							errors.add(ErrorMessages.LONG_WORD.getDescription("La Password"));
					}
				}	
				
		if(userDto.getPassword()==null) {
			errors.add(ErrorMessages.REQUIRED.getCode());
			errors.add(ErrorMessages.REQUIRED.getDescription("Password"));
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
			
	   	return errors;
	}
	
	public static List<String> applyValidationUserUpdate(UserDTOUpdate userDtoUp) {
		
		List<String> errors = new ArrayList<String>();
		//validacion en conjunto
		/*if(userDtoUp.getIdUser()==null && userDtoUp.getName()==null && userDtoUp.getSurname()==null && userDtoUp.getPassword()==null
				&& userDtoUp.getUserName()==null && userDtoUp.getUserType()==null) {
			errors.add(ErrorMessages.GROUP_NULL.getCode());
			errors.add(ErrorMessages.GROUP_NULL.getDescription(""));
		}
		
		if(userDtoUp.getIdUser().toString().equals("") && userDtoUp.getName().equals("") && userDtoUp.getSurname().equals("") 
				&& userDtoUp.getPassword().equals("") && userDtoUp.getUserName().equals("") && userDtoUp.getUserType().equals("")) {
			errors.add(ErrorMessages.GROUP_EMPTY.getCode());
			errors.add(ErrorMessages.GROUP_EMPTY.getDescription(""));
		}*/
		
		//Validaciones de ID
		if(userDtoUp.getIdUser()!=null) {
				if(!userDtoUp.getIdUser().toString().matches("[0-9]*") && userDtoUp.getName().length()!=0) {
						errors.add(ErrorMessages.FORMAT_INVALID.getCode());
							errors.add(ErrorMessages.FORMAT_INVALID.getDescription("ID debe ser de tipo Integer"));
				}
							
				if(userDtoUp.getIdUser().toString().length()==0) {
						errors.add(ErrorMessages.EMPTY_FIELD.getCode());
						errors.add(ErrorMessages.EMPTY_FIELD.getDescription("ID"));
				}
		}	
				
		if(userDtoUp.getIdUser()==null) {
				errors.add(ErrorMessages.REQUIRED.getCode());
				errors.add(ErrorMessages.REQUIRED.getDescription("ID"));
		}
				
		//Validaciones de Name
		if(userDtoUp.getName()!=null) {
			if(!userDtoUp.getName().matches("[A-Za-z_]+") && userDtoUp.getName().length()!=0) {
				errors.add(ErrorMessages.FORMAT_INVALID.getCode());
					errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Nombre debe ser de tipo String"));
			}
					
			if(userDtoUp.getName().length()==0) {
				errors.add(ErrorMessages.EMPTY_FIELD.getCode());
				errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Nombre"));
			}
			
			if(userDtoUp.getName().length()<2 && userDtoUp.getName().length()>0 && !userDtoUp.getName().matches("[0-9]*")) {
			    	errors.add(ErrorMessages.SHORT_WORD.getCode());
					errors.add(ErrorMessages.SHORT_WORD.getDescription("El Nombre"));
			}
			 
			if(userDtoUp.getName().length()>40 && !userDtoUp.getName().matches("[0-9]*")) {
				 	errors.add(ErrorMessages.LONG_WORD.getCode());
					errors.add(ErrorMessages.LONG_WORD.getDescription("El Nombre"));
			}
		}	
		
		if(userDtoUp.getName()==null) {
			errors.add(ErrorMessages.REQUIRED.getCode());
			errors.add(ErrorMessages.REQUIRED.getDescription("Nombre"));
		}
		
		//Validaciones de Apellido
		if(userDtoUp.getSurname()!=null) {
					if(!userDtoUp.getSurname().matches("[A-Za-z_]+") && userDtoUp.getSurname().length()!=0) {
						errors.add(ErrorMessages.FORMAT_INVALID.getCode());
							errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Apellido debe ser de tipo String"));
					}
							
					if(userDtoUp.getSurname().length()==0) {
						errors.add(ErrorMessages.EMPTY_FIELD.getCode());
						errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Apellido"));
					}
					
					if(userDtoUp.getSurname().length()<2 && userDtoUp.getSurname().length()>0 && !userDtoUp.getSurname().matches("[0-9]*")) {
					    	errors.add(ErrorMessages.SHORT_WORD.getCode());
							errors.add(ErrorMessages.SHORT_WORD.getDescription("El Apellido"));
					}
					 
					if(userDtoUp.getSurname().length()>40 && !userDtoUp.getSurname().matches("[0-9]*")) {
						 	errors.add(ErrorMessages.LONG_WORD.getCode());
							errors.add(ErrorMessages.LONG_WORD.getDescription("El Apellido"));
					}
				}	
				
		if(userDtoUp.getSurname()==null) {
			errors.add(ErrorMessages.REQUIRED.getCode());
			errors.add(ErrorMessages.REQUIRED.getDescription("Apellido"));
		}
		

		//Validaciones de UserName
		if(userDtoUp.getUserName()!=null) {
					if(!userDtoUp.getUserName().matches("[A-Za-z_]+") && userDtoUp.getUserName().length()!=0) {
						errors.add(ErrorMessages.FORMAT_INVALID.getCode());
							errors.add(ErrorMessages.FORMAT_INVALID.getDescription("Nombre de Usuario debe ser de tipo String"));
					}
							
					if(userDtoUp.getUserName().length()==0) {
						errors.add(ErrorMessages.EMPTY_FIELD.getCode());
						errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Nombre de Usuario"));
					}
					
					if(userDtoUp.getUserName().length()<2 && userDtoUp.getUserName().length()>0 && !userDtoUp.getUserName().matches("[0-9]*")) {
					    	errors.add(ErrorMessages.SHORT_WORD.getCode());
							errors.add(ErrorMessages.SHORT_WORD.getDescription("Nombre de Usuario"));
					}
					 
					if(userDtoUp.getUserName().length()>40 && !userDtoUp.getUserName().matches("[0-9]*")) {
						 	errors.add(ErrorMessages.LONG_WORD.getCode());
							errors.add(ErrorMessages.LONG_WORD.getDescription("El Nombre de Usuario"));
					}
		}	
				
		if(userDtoUp.getUserName()==null) {
			errors.add(ErrorMessages.REQUIRED.getCode());
		}
		

		//Validaciones de Password
		if(userDtoUp.getPassword()!=null) {
							
					if(userDtoUp.getPassword().length()==0) {
						errors.add(ErrorMessages.EMPTY_FIELD.getCode());
						errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Password"));
					}
					
					if(userDtoUp.getPassword().length()<4 && userDtoUp.getPassword().length()>0) {
					    	errors.add(ErrorMessages.SHORT_WORD.getCode());
							errors.add(ErrorMessages.SHORT_WORD.getDescription("La Password"));
					}
					 
					if(userDtoUp.getPassword().length()>5) {
						 	errors.add(ErrorMessages.LONG_WORD.getCode());
							errors.add(ErrorMessages.LONG_WORD.getDescription("La Password"));
					}
				}	
				
		if(userDtoUp.getPassword()==null) {
			errors.add(ErrorMessages.REQUIRED.getCode());
			errors.add(ErrorMessages.REQUIRED.getDescription("Password"));
		}
		
		//Validacion Tipo de usario
		boolean existUserType = false;
		for(UserType value: UserType.values()) {
			if(value.name().equals(userDtoUp.getUserType())) {
				existUserType = true;
			}
		}
		
		if(!existUserType) {
			errors.add(ErrorMessages.EMPTY_ENUM.getCode());
			errors.add(ErrorMessages.EMPTY_ENUM.getDescription("El Tipo de Usuario ingresado"));
		}
			
	   	return errors;
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
