package com.online.hotel.arlear.exception;

import java.util.ArrayList;
import java.util.List;
import com.online.hotel.arlear.dto.ResponseDTO;

public class ErrorTools {
	//Funcion para listar más de un error
	public static ResponseDTO listErrors(List<?> errors){
				ResponseDTO response = new ResponseDTO();
				List<String> code= new ArrayList<>();
				List<String> messages= new ArrayList<>();
				int j=0;
				int i;
				for (i=0; i<errors.size();i=((2*i)/2)+2) {
					response= new ResponseDTO("ERROR",errors.get(j).toString(),errors.get(j+1).toString());
					code.add(response.getCode().toString());
					messages.add(response.getMessage().toString());
					j=((2*j)/2)+2;
				}
				response.setStatus("ERROR");
				response.setCode(code.toString());
				response.setMessage(messages.toString());
				return response;
		}
	
	//Metodo Create_OK
	public static ResponseDTO createOk(String okMsg) {
		ResponseDTO response=new ResponseDTO();
		response = new ResponseDTO("OK",
					   ErrorMessages.CREATE_OK.getCode(),
					   ErrorMessages.CREATE_OK.getDescription(okMsg));
		return response;
	}
	
	//Metodo Required
	public static ResponseDTO required(String errorMsg) {
		ResponseDTO response=new ResponseDTO();
		response = new ResponseDTO("ERROR",
					   ErrorMessages.REQUIRED.getCode(),
					   ErrorMessages.REQUIRED.getDescription(errorMsg));
		return response;
	}
		
	//Metodo Create_Error
	public static ResponseDTO createError(String errorMsg) {
		ResponseDTO response=new ResponseDTO();
		response = new ResponseDTO("ERROR",
				   ErrorMessages.CREATE_ERROR.getCode(),
				   ErrorMessages.CREATE_ERROR.getDescription(errorMsg));
		return response;
	}
	
	//Metodo Update_OK
	public static ResponseDTO updateOk(String okMsg) {
		ResponseDTO response=new ResponseDTO();
		response = new ResponseDTO("OK",
						   ErrorMessages.UPDATE_OK.getCode(),
						   ErrorMessages.UPDATE_OK.getDescription(okMsg));
		return response;
	}
		
	//Metodo Update_Error
	public static ResponseDTO updateError(String errorMsg) {
		ResponseDTO response=new ResponseDTO();
		response = new ResponseDTO("ERROR",
						   ErrorMessages.UPDATE_ERROR.getCode(),
						   ErrorMessages.UPDATE_ERROR.getDescription(errorMsg));
		return response;
	}
	
	//Metodo Delete_OK
	public static ResponseDTO deleteOk(String okMsg) {
		ResponseDTO response=new ResponseDTO();
		response = new ResponseDTO("OK",
						   ErrorMessages.DELETED_OK.getCode(),
						   ErrorMessages.DELETED_OK.getDescription(okMsg));
		return response;
	}
			
	//Metodo Delete_Error
	public static ResponseDTO deleteError(String errorMsg) {
		ResponseDTO response=new ResponseDTO();
		response = new ResponseDTO("ERROR",
						   ErrorMessages.DELETED_ERROR.getCode(),
						   ErrorMessages.DELETED_ERROR.getDescription(errorMsg));
		return response;
	}
	
	//Metodo Search_Error
	public static ResponseDTO searchError(String errorMsg) {
		ResponseDTO response=new ResponseDTO();
		response = new ResponseDTO("ERROR",
				   ErrorMessages.SEARCH_ERROR.getCode(),
				   ErrorMessages.SEARCH_ERROR.getDescription(errorMsg));
		return response;
	}
	
	//Metodo User_NoExist
	public static ResponseDTO userNoexist() {
		ResponseDTO response=new ResponseDTO();
		response = new ResponseDTO("ERROR",
				   ErrorMessages.USER_NOEXIST.getCode(),
				   ErrorMessages.USER_NOEXIST.getDescription(""));
		return response;
	}
	
	//Metodo Price_Overange
		public static ResponseDTO priceOverange() {
			ResponseDTO response=new ResponseDTO();
			response= new ResponseDTO("ERROR", 
					ErrorMessages.PRICE_OVERANGE.getCode(),
					ErrorMessages.PRICE_OVERANGE.getDescription(""));
			return response;
		}
	
	
}
