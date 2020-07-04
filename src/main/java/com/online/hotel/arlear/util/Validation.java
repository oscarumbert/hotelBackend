package com.online.hotel.arlear.util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.online.hotel.arlear.dto.ProductDTO;
import com.online.hotel.arlear.dto.QuestionDTO;
import com.online.hotel.arlear.dto.ReservationCheckIn;
import com.online.hotel.arlear.dto.ReservationCreateDTO;
import com.online.hotel.arlear.dto.ReservationEx2DTO;
import com.online.hotel.arlear.dto.ReservationFind;
import com.online.hotel.arlear.dto.RoomDTO;
import com.online.hotel.arlear.dto.SurveyDTO;
import com.online.hotel.arlear.dto.UserDTO;
import com.online.hotel.arlear.enums.ProductType;
import com.online.hotel.arlear.dto.CardDTO;
import com.online.hotel.arlear.dto.ContactDTO;
import com.online.hotel.arlear.dto.ContactDTOEvent;
import com.online.hotel.arlear.dto.ContactFindDTO;
import com.online.hotel.arlear.dto.EventDTO;
import com.online.hotel.arlear.dto.EventFindDTO;
import com.online.hotel.arlear.dto.GuestDTO;
import com.online.hotel.arlear.dto.MenuDTO;
import com.online.hotel.arlear.dto.OrderRestaurantDTO;
import com.online.hotel.arlear.enums.CardType;
import com.online.hotel.arlear.enums.DocumentType;
import com.online.hotel.arlear.enums.GenderType;
import com.online.hotel.arlear.enums.MenuState;
import com.online.hotel.arlear.enums.MenuType;
import com.online.hotel.arlear.enums.OrderType;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomAditionals;
import com.online.hotel.arlear.enums.RoomCategory;
import com.online.hotel.arlear.enums.RoomStatus;
import com.online.hotel.arlear.enums.RoomType;
import com.online.hotel.arlear.enums.UserType;
import com.online.hotel.arlear.exception.ErrorGeneric;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.exception.ErrorTools;
import com.online.hotel.arlear.exception.FieldValidation;
import com.online.hotel.arlear.exception.ValidationConfiguration;

public class Validation {
	
	private static FieldValidation fieldValidation;
	
	//Validaciones de Menu
	public static List<String> applyValidationMenu(MenuDTO menudto){
		
		fieldValidation = new FieldValidation();
		
		
		fieldValidation.validate(menudto.getNameMenu(), ValidationConfiguration.NAME_MENU);
		List<String> errors = new ArrayList<String>();
		if(menudto.getDiscount()!=null) {
			
			if(menudto.getDiscount()<0) {
			    	errors.add(ErrorMessages.NEGATIVE_NUMBER.getCode());
					errors.add(ErrorMessages.NEGATIVE_NUMBER.getDescription("El Descuento ingresado es negativo"));
			}
			 
			if(menudto.getDiscount()>=100) {
				 	errors.add(ErrorMessages.BIG_NUMBER.getCode());
					errors.add(ErrorMessages.BIG_NUMBER.getDescription("El Descuento ingresado es del más del 100%"));
			}
		}
		
		fieldValidation.validate(menudto.getDiscount(), ValidationConfiguration.DISCOUNT);
		fieldValidation.validate(menudto.getProducto(), ValidationConfiguration.PRODUCT);
		fieldValidation.validate(menudto.getMenustate(), ValidationConfiguration.MENU_STATE);
		fieldValidation.validate(menudto.getMenutype(), ValidationConfiguration.MENU_TYPE);

		errors.addAll(fieldValidation.getMessage());
		
		//Validacion Estado de Menu
		if(menudto.getMenustate()!=null && !menudto.getMenustate().equals("") ) {
					boolean existMenuState = false;
					for(MenuState value: MenuState.values()) {
						if(value.name().equals(menudto.getMenustate())) {
							existMenuState= true;
						}
					}
			
					if(!existMenuState) {
						errors.add(ErrorMessages.EMPTY_ENUM.getCode());
						errors.add(ErrorMessages.EMPTY_ENUM.getDescription("El estado menu ingresado"));
					}
				}
		
		//Validacion Tipo de Menu
		if(menudto.getMenutype()!=null && !menudto.getMenutype().equals("")) {
					boolean existMenuType = false;
					for(MenuType value: MenuType.values()) {
						if(value.name().equals(menudto.getMenutype())) {
								existMenuType= true;
						}
					}
					
					if(!existMenuType) {
						errors.add(ErrorMessages.EMPTY_ENUM.getCode());
						errors.add(ErrorMessages.EMPTY_ENUM.getDescription("El tipo menu ingresado"));
					}
		}
		
		return errors;
	}
	
	//Validaciones de Usuario
	public static List<String> applyValidationUser(UserDTO userDto) {
		
		List<String> errors = new ArrayList<String>();
		fieldValidation = new FieldValidation();
		fieldValidation.validate(userDto.getName().replace(" ",""), ValidationConfiguration.NAME);
		fieldValidation.validate(userDto.getSurname().replace(" ",""), ValidationConfiguration.SURNAME);
		fieldValidation.validate(userDto.getUserName(), ValidationConfiguration.USER_NAME);
		fieldValidation.validate(userDto.getPassword(), ValidationConfiguration.PASSWORD);
		fieldValidation.validate(userDto.getUserType(), ValidationConfiguration.USER_TYPE);

		errors.addAll(fieldValidation.getMessage());

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
		}	
		
		//Validacion Tipo de usario
		if(userDto.getUserType()!=null && !userDto.getUserType().equals("")) {
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
	   	return errors;
	}
	
	
	//Validaciones de Busqueda de reserva por fechas
		public static List<String> applyValidationReservaDates(ReservationFind reserv) {
			
			List<String> errors = new ArrayList<String>();
			fieldValidation = new FieldValidation();
			fieldValidation.validate(reserv.getBeginDate(), ValidationConfiguration.BEGIN_DATE);
			fieldValidation.validate(reserv.getBeginDate(), ValidationConfiguration.END_DATE);
			errors.addAll(fieldValidation.getMessage());
			
		   	return errors;
	}
			
			
			
		
			
	public static List<ErrorGeneric> applyValidationContact(ContactDTO contact){
		List<ErrorGeneric> errors = new ArrayList<ErrorGeneric>();

		
		fieldValidation = new FieldValidation();
		fieldValidation.validate(contact.getName().replace(" ",""), ValidationConfiguration.NAME);
		fieldValidation.validate(contact.getSurname().replace(" ",""), ValidationConfiguration.SURNAME);
		fieldValidation.validate(contact.getPhone(), ValidationConfiguration.PHONE);
		fieldValidation.validate(contact.getMail(), ValidationConfiguration.EMAIL);
		errors.addAll(ErrorTools.parseToErrorsGeneric(fieldValidation.getMessage()));
	
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
	
	public static List<ErrorGeneric> applyValidationContactEvent(ContactDTOEvent contact){
		List<ErrorGeneric> errors = new ArrayList<ErrorGeneric>();

		fieldValidation = new FieldValidation();
		fieldValidation.validate(contact.getName(), ValidationConfiguration.NAME);
		fieldValidation.validate(contact.getSurname().replace(" ",""), ValidationConfiguration.SURNAME);
		fieldValidation.validate(contact.getPhone(), ValidationConfiguration.PHONE);
		fieldValidation.validate(contact.getMail(), ValidationConfiguration.EMAIL);
		errors.addAll(ErrorTools.parseToErrorsGeneric(fieldValidation.getMessage()));
	
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
	
		fieldValidation = new FieldValidation();
		fieldValidation.validate(card.getCardNumber(), ValidationConfiguration.CARD_NUMBER);
		fieldValidation.validate(card.getCardType(), ValidationConfiguration.CARD_TYPE);
		fieldValidation.validate(card.getCodeSecurity(), ValidationConfiguration.CODE_SECURITY);
		fieldValidation.validate(card.getNameOwner().replace(" ",""), ValidationConfiguration.NAME_OWNER);

		errors.addAll(ErrorTools.parseToErrorsGeneric(fieldValidation.getMessage()));

		if(card.getCardType() != null && !card.getCardType().equals("")){
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
		
		return errors;
	}
	
	public static List<ErrorGeneric> applyValidationContactFind(ContactFindDTO contact){
		List<ErrorGeneric> errors = new ArrayList<ErrorGeneric>();
		boolean existType = false;
		fieldValidation = new FieldValidation();
		fieldValidation.validate(contact.getDocumentNumber(), ValidationConfiguration.DOCUMENT_NUMBER);
		fieldValidation.validate(contact.getDocumentType(), ValidationConfiguration.DOCUMENT_TYPE);
		fieldValidation.validate(contact.getGender(), ValidationConfiguration.GENDER);
		
		errors.addAll(ErrorTools.parseToErrorsGeneric(fieldValidation.getMessage()));
	
		if(contact.getDocumentType() != null && !contact.getDocumentType().equals("") ) {
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
		if(contact.getGender() != null && !contact.getGender().equals("")) {
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
		fieldValidation = new FieldValidation();
		fieldValidation.validate(reservationDto.getAdultsCuantity(), ValidationConfiguration.ADULTS_CUANTITY);
		fieldValidation.validate(reservationDto.getChildsCuantity(), ValidationConfiguration.CHILDS_CUANTITY);
		fieldValidation.validate(reservationDto.getBeginDate(), ValidationConfiguration.BEGIN_DATE2);
		fieldValidation.validate(reservationDto.getEndDate(), ValidationConfiguration.END_DATE2);
		fieldValidation.validate(reservationDto.getAditionals(), ValidationConfiguration.ADITIONALS);
		fieldValidation.validate(reservationDto.getReservationType(), ValidationConfiguration.RESERVATION_TYPE);

		errors.addAll(fieldValidation.getMessage());

		if (reservationDto.getBeginDate() !=null && reservationDto.getEndDate() !=null
				&& reservationDto.getEndDate().isBefore(reservationDto.getBeginDate())) {
			errors.add(ErrorMessages.PREVIUS_DATE.getCode());
			errors.add(ErrorMessages.PREVIUS_DATE.getDescription(""));
		}
		;
		if (reservationDto.getAditionals() != null && !reservationDto.getAditionals().equals("")) {

			if (!isValidAditionals(reservationDto.getAditionals())) {
				errors.add(ErrorMessages.OPTION.getCode());
				errors.add(ErrorMessages.OPTION.getDescription("adicionales"));

			}
		}

		if (reservationDto.getReservationType() != null && reservationDto.getReservationType().equals("")) {
			if (!isValidReservationType(reservationDto.getReservationType())) {
				errors.add(ErrorMessages.OPTION.getCode());
				errors.add(ErrorMessages.OPTION.getDescription("Tipo de Reservacion"));
			}
		}

		return errors;
	}

	public static List<String> applyValidationReservationEx2(ReservationEx2DTO reservationDto) {
		
		List<String> errors = new ArrayList<String>();
		fieldValidation = new FieldValidation();
		fieldValidation.validate(reservationDto.getAdultsCuantity(), ValidationConfiguration.ADULTS_CUANTITY);
		fieldValidation.validate(reservationDto.getChildsCuantity(), ValidationConfiguration.CHILDS_CUANTITY);
		fieldValidation.validate(reservationDto.getBeginDate(), ValidationConfiguration.BEGIN_DATE2);
		fieldValidation.validate(reservationDto.getEndDate(), ValidationConfiguration.END_DATE2);
		fieldValidation.validate(reservationDto.getAditionals(), ValidationConfiguration.ADITIONALS);
		fieldValidation.validate(reservationDto.getReservationType(), ValidationConfiguration.RESERVATION_TYPE);

		errors.addAll(fieldValidation.getMessage());

		if (reservationDto.getBeginDate() !=null && reservationDto.getEndDate() !=null
				&& reservationDto.getEndDate().isBefore(reservationDto.getBeginDate())) {
			errors.add(ErrorMessages.PREVIUS_DATE.getCode());
			errors.add(ErrorMessages.PREVIUS_DATE.getDescription(""));
		}
		;
		if (reservationDto.getAditionals() != null && !reservationDto.getAditionals().equals("")) {

			if (!isValidAditionals(reservationDto.getAditionals())) {
				errors.add(ErrorMessages.OPTION.getCode());
				errors.add(ErrorMessages.OPTION.getDescription("adicionales"));

			}
		}

		if (reservationDto.getReservationType() != null && reservationDto.getReservationType().equals("")) {
			if (!isValidReservationType(reservationDto.getReservationType())) {
				errors.add(ErrorMessages.OPTION.getCode());
				errors.add(ErrorMessages.OPTION.getDescription("Tipo de Reservacion"));
			}
		}

		return errors;
	}

	public static List<String> applyValidationProduct(ProductDTO productDto) {
		
		List<String> errors = new ArrayList<String>();
		
		fieldValidation = new FieldValidation();
		fieldValidation.validate(productDto.getName(), ValidationConfiguration.NAME);
		fieldValidation.validate(productDto.getPrice(), ValidationConfiguration.PRICE);
		fieldValidation.validate(productDto.getProductType(), ValidationConfiguration.PRODUCT_TYPE);
		errors.addAll(fieldValidation.getMessage());
		
		boolean existProductType = false;
		if(productDto.getProductType() != null && !productDto.getProductType().equals("")) {
			for(ProductType value: ProductType.values()) {
				if(value.name().equals(productDto.getProductType())) {
					existProductType = true;
				}
			}
			
			if(!existProductType) {
				errors.add(ErrorMessages.EMPTY_ENUM.getCode());
				errors.add(ErrorMessages.EMPTY_ENUM.getDescription("El Tipo de product ingresado"));
			}
		}
		
		return errors;
	}
		
	public static List<String> applyValidationSurvey(SurveyDTO surveyDto) {
		
		List<String> errors = new ArrayList<String>();
		
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
	
	public static List<String> applyValidationGuest(GuestDTO guest){
	
		List<String> errors = new ArrayList<String>();
		
		fieldValidation = new FieldValidation();
		fieldValidation.validate(guest.getName().replace(" ",""), ValidationConfiguration.NAME);
		fieldValidation.validate(guest.getSurname().replace(" ",""), ValidationConfiguration.SURNAME);
		fieldValidation.validate(guest.getDocumentNumber(), ValidationConfiguration.DOCUMENT_NUMBER);
		errors.addAll(fieldValidation.getMessage());

		return errors;
	}
	public static List<String> applyValidationRoom(RoomDTO roomDTO){
		List<String> errors = new ArrayList<String>();
		boolean exist = false;
			
		fieldValidation = new FieldValidation();
		fieldValidation.validate(roomDTO.getCapacity(), ValidationConfiguration.CAPACITY);
		fieldValidation.validate(roomDTO.getFloor(), ValidationConfiguration.FLOOR);
		fieldValidation.validate(roomDTO.getPrice(), ValidationConfiguration.PRICE);
		fieldValidation.validate(roomDTO.getRoomNumber(), ValidationConfiguration.ROOM_NUMBER);
		fieldValidation.validate(roomDTO.getType(), ValidationConfiguration.ROOM_TYPE);
		fieldValidation.validate(roomDTO.getCategory(), ValidationConfiguration.ROOM_CATEGORY);
		fieldValidation.validate(roomDTO.getRoomStatus(), ValidationConfiguration.ROOM_STATUS);

		errors.addAll(fieldValidation.getMessage());
	
		
		if(roomDTO.getCategory()!=null) {
			
			for(RoomCategory value: RoomCategory.values()) {
				if(value.name().equals(roomDTO.getCategory())) {
					exist = true;
				}
			}
			
			if(!exist) {
				errors.add(ErrorMessages.EMPTY_ENUM.getCode());
				errors.add(ErrorMessages.EMPTY_ENUM.getDescription("El Tipo de Categoria ingresado"));
			}
		}
		
		if(roomDTO.getType()!=null) {
			exist = false;
			for(RoomType value: RoomType.values()) {
				if(value.name().equals(roomDTO.getType())) {
					exist = true;
				}
			}
			
			if(!exist) {
				errors.add(ErrorMessages.EMPTY_ENUM.getCode());
				errors.add(ErrorMessages.EMPTY_ENUM.getDescription("El Tipo de habitacion ingresado"));
			}
		}
		if(roomDTO.getRoomStatus()!=null) {
			exist = false;
			for(RoomStatus value: RoomStatus.values()) {
				if(value.name().equals(roomDTO.getRoomStatus())) {
					exist = true;
				}
			}
			
			if(!exist) {
				errors.add(ErrorMessages.EMPTY_ENUM.getCode());
				errors.add(ErrorMessages.EMPTY_ENUM.getDescription("El Estado de la habitacion ingresado"));
			}
		}
		

		return errors;
	}

	public static List<String> applyValidationCheckIn(ReservationCheckIn checkIn) {
		List<String> errors = new ArrayList<String>();
		
		fieldValidation = new FieldValidation();
		fieldValidation.validate(checkIn.getDebt(), ValidationConfiguration.DEBT);

		errors.addAll(fieldValidation.getMessage());
		
		return errors;
	}

	public static List<String> applyValidationOrderRestaurant(OrderRestaurantDTO orderDTO) {
		List<String> errors = new ArrayList<String>();
		
		fieldValidation = new FieldValidation();
		fieldValidation.validate(orderDTO.getNumberReservation(), ValidationConfiguration.NUMBER_RESERVATION);

		errors.addAll(fieldValidation.getMessage());

		if(orderDTO.getOrderType().equals(OrderType.CONSUMICION_RESTAURANT.toString())) {
			if(orderDTO.getNumberReservation()!=null) {
				errors.add(ErrorMessages.OPTION.getCode());
				errors.add(ErrorMessages.OPTION.getDescription("pedido de tipo consumicion restaurant. Para el cliente del restaurant no es necesaria la reserva."));
			}
		}
		
		if(orderDTO.getOrderType().equals(OrderType.CONSUMICION_HABITACION.toString() )) {
			
			if(orderDTO.getNumberReservation()==null  ) {
				errors.add(ErrorMessages.NULL.getCode());
				errors.add(ErrorMessages.NULL.getDescription("Si el pedido es una consumición para habitacion, la id de reserva es obligatoria"));
		
			}
		}
		
		//Validacion Estado de Pedido
		if(orderDTO.getOrderType()!=null) {
					boolean existOrderType = false;
					for(OrderType value: OrderType.values()) {
						if(value.name().equals(orderDTO.getOrderType())) {
							existOrderType= true;
						}
					}
			
					if(!existOrderType) {
						errors.add(ErrorMessages.EMPTY_ENUM.getCode());
						errors.add(ErrorMessages.EMPTY_ENUM.getDescription("El tipo del pedido ingresado"));
					}
			}
				
		if(orderDTO.getOrderType()==null) {
					errors.add(ErrorMessages.REQUIRED.getCode());
					errors.add(ErrorMessages.REQUIRED.getDescription("Tipo de Pedido"));
		}
		
		return errors;
	}
	

	public static List<String> applyValidationQuestion(QuestionDTO questionDTO) {
		List<String> errors = new ArrayList<String>();
		
		fieldValidation = new FieldValidation();
		fieldValidation.validate(questionDTO.getQuestion(), ValidationConfiguration.QUESTION);

		errors.addAll(fieldValidation.getMessage());
		
		return errors;
	}
	
	public static List<String> applyValidationEvent(EventDTO eventDTO) {
		List<String> errors = new ArrayList<String>();
		//validaciones de nameMenu
		if(eventDTO.getStartDateHour()!=null || eventDTO.getEndDateHour()!=null) {
			if(eventDTO.getStartDateHour()==null) {
				errors.add(ErrorMessages.EMPTY_FIELD.getCode());
				errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Fechas"));
			}
		}
		return errors;
		
	}
	public static List<String> applyValidationEvent(EventFindDTO eventDTO) {
		List<String> errors = new ArrayList<String>();
		//validaciones de nameMenu
		if(eventDTO.getStartDateHour()!=null || eventDTO.getEndDateHour()!=null) {
			if(eventDTO.getStartDateHour()==null) {
				errors.add(ErrorMessages.EMPTY_FIELD.getCode());
				errors.add(ErrorMessages.EMPTY_FIELD.getDescription("Fechas"));
			}
		}
		return errors;
		
	}
}
