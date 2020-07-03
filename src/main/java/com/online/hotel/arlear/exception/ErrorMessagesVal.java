package com.online.hotel.arlear.exception;

public enum ErrorMessagesVal {
	REQUIRED("El campo # es obligatorio"),
	NULL_OBJECT("El objeto # no puede estar nulo o vacio"),
	FORMAT_INCORRECT("El campo # tiene el formato incorrecto, este debe tener el siguiente formato $1"),
	EMPTY("El campo # no puede estar vacio"),
	NUMBER("El campo # solo puede contener numeros"),
	COMPARE_MAX("El campo # debe ser mayor a $1"),
	COMPARE_MIN("El campo # debe ser menor a $1"),
	SIZE_MAX("El campo # debe tener una longitud menor a $1"),
	SIZE_EQUALS("El campo # debe tener una longitud de $1"),
	SIZE_MIN("El campo # debe tener una longitud mayor a $1"),
	SIZE_BETWEEN("El campo # debe estar entre $1 y $2"),
	DEPENDENCY("El campo # depende de"),
	EXTERNAL_SERVICES("El servicio # no esta disponible"),
	NOT_APPLICATE_ACTION("No se puede aplicar la accion a la mora seleccionada");
	
	private String message;
	
	private ErrorMessagesVal(String message) {
		this.message = message;
	}

	
	public String getMessage(String field) {
		return this.message.replace("#", field);
	}
	public String getMessage(String field, String value1) {
		return this.message.replace("#", field).replace("$1", value1);
	}
	public String getMessage(String field, String value1,String value2) {
		return this.message.replace("#", field).replace("$1", value1).replace("$2", value2);
	}
}
