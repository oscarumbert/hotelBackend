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
}
