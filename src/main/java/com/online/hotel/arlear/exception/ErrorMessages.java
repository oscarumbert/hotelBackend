package com.online.hotel.arlear.exception;

public enum ErrorMessages {

	CREATE_OK("200","Se ha creado $ correctamente"),
	REQUIRED("303","El campo $ es obligatorio."),
	CREATE_ERROR("302","La creación no se pudo hacer. $."),
	FIND_ERROR("313","La busqueda ingresada de $ no ha encontrado ningun resultado."),
	CREATE_ERROR_UNIQUE("555","El $ que se quiere dar de alta ya existe"),
	INVALID("304"," El dato ingresado $ no es válido."),
	FORMAT_INVALID("305","Formato incorrecto: $."),
	EMPTY_FIELD("306","El campo $ está vacio."),
	SHORT_WORD("307","$ es muy corto."),
	LONG_WORD("308","$ es muy largo."),
	EMPTY_ENUM("309","$ no existe."),
	NULL("310","$."),
	EMPTY_SEARCH("311","No ha ingreado ningun dato para la búsqueda de $."),
	OPTION("312","Opcion no Disponible para $"),
	UPDATE_OK("313","Se ha modificacado correctamente $."),
	UPDATE_ERROR("314","No se ha modificacado correctamente $."),
	GROUP_EMPTY("315","Todos los campos están vacíos."),
	GROUP_NULL("316","Todos los campos están nulos."),
	DELETED_OK("317","Se eliminó correctamente $."),
	DELETED_ERROR("318","No se pudo eliminar $."),
	OUTDATE("319","La $ está desactualizada"),
	PREVIUS_DATE("320","La fecha de salida es anterior a la fecha de entrada"),
	SEARCH_ERROR("321","No se ha encontrado lo que desea buscar."),
	NEGATIVE_NUMBER("322","$."),
	BIG_NUMBER("323","$."),
	USER_NOEXIST("324","No existe el usuario. Ingrese nuevamente el nombre de usuario y/o la contraseña.");

	
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