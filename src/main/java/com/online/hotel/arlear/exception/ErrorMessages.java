package com.online.hotel.arlear.exception;

public enum ErrorMessages {

	CREATE_OK("200","Se ha creado $ correctamente"),
	REQUIRED("303","El campo $ es obligatorio."),
	CREATE_ERROR("302","La creación $ ha fallado."),
	INVALID("304"," El dato ingresado $ no es válido."),
	FORMAT_INVALID("305","Formato incorrecto: $."),
	EMPTY_FIELD("306","El campo $ está vacio."),
	SHORT_WORD("307","$ es muy corto."),
	LONG_WORD("308","$ es muy largo."),
	EMPTY_ENUM("309","$ no existe."),
	NULL("310","$."),
	EMPTY_SEARCH("311","No ha ingreado ningun dato para la búsqueda de $."),
	OPTION("312","Opcion no Disponible para $"),
	OUTDATE("313","La $ está desactualizada"),
	PREVIUS_DATE("314","La fecha de salida es anterior a la fecha de entrada");
	
	private String code;
	private String description;

	private ErrorMessages(String code,String description) {
		this.description = description;
		this.code = code;
	}

	public String getDescription(String entityName) {
		return description.replace("$", entityName);
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}



	
}