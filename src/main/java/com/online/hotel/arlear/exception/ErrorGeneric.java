package com.online.hotel.arlear.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorGeneric {

	private String code;
	private String message;
}
