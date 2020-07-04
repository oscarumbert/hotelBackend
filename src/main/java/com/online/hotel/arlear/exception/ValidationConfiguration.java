package com.online.hotel.arlear.exception;

public enum ValidationConfiguration {

	NAME_MENU("Nombre de menu",60,2,true,"STRING","","",0.0,"",null),
	DISCOUNT("Descuento",100,0,true,"NUMBER","","",0.0,"",null),
	PRODUCT("Producto",100,2,true,"PRODUCT","","",0.0,"",null),
	MENU_STATE("Estado de menu",200,1,true,"STRING","","",0.0,"",null),
	MENU_TYPE("Tipo de menu",200,1,true,"NUMBER","","",0.0,"",null),
	NAME("Nombre",40,2,true,"STRING",ExpressionRegular.NAME.getValue(),"de tipo String",0.0,"",null),
	SURNAME("Apellido",40,2,true,"STRING",ExpressionRegular.NAME.getValue(),"de tipo String",0.0,"",null),
	USER_NAME("Nombre de usuario",40,2,true,"STRING",ExpressionRegular.NAME.getValue(),"de tipo String",0.0,"",null),
	PASSWORD("Password",5,3,true,"NUMBER",ExpressionRegular.NUMBER.getValue(),"de tipo Numerico",0.0,"",null),
	USER_TYPE("Tipo de Usuario",100,1,true,"STRING","","",0.0,"",null),
	BEGIN_DATE("Fecha inicial",100,1,true,"DATE","","",0.0,"",null),
	END_DATE("Fecha final",100,1,true,"DATE","","",0.0,"",null),
	EMAIL("email",100,5,true,"STRING",ExpressionRegular.EMAIL.getValue(),"del siguiente tipo de formato oscar@gmail.com",0.0,"",null),
	PHONE("Telefono",100,5,true,"NUMBER","","53453454",0.0,"",null),
	CARD_NUMBER("Numero de tarjeta",100,1,true,"NUMBER","","53453454",0.0,"",null),
	CARD_TYPE("Tipo de tarjeta",100,1,true,"NUMBER","","53453454",0.0,"",null),
	CODE_SECURITY("Codigo de seguridad",100,1,true,"NUMBER","","53453454",0.0,"",null),
	NAME_OWNER("Nombre del dueño de la tarjeta",100,1,true,"STRING","","",0.0,"",null),
	DOCUMENT_NUMBER("Numero de documento",10,7,true,"STRING","","de tipo Numerico",0.0,"",null),
	DOCUMENT_TYPE("Tipo de documento",100,1,true,"STRING","","",0.0,"",null),
	GENDER("Genero",100,1,true,"STRING","","",0.0,"",null),
	ADULTS_CUANTITY("Cantidad de adultos",10,1,true,"NUMBER","","",0.0,"max",null),
	CHILDS_CUANTITY("Cantidad de niños",10,1,true,"NUMBER","","",-1.0,"max",null),
	BEGIN_DATE2("Fecha de inicio",19,6,true,"DATE","","",0.0,"max","fecha actual"),
	END_DATE2("Fecha de fin",19,6,true,"DATE","","",0.0,"max","fecha actual"),
	ADITIONALS("Adicionales",100,1,true,"STRING","","",0.0,"",null),
	RESERVATION_TYPE("Tipo de reserva",100,1,true,"STRING","","",0.0,"",null),
	PRICE("precio",10,1,true,"NUMBER","","",0.0,"max",null),
	PRODUCT_TYPE("Tipo de producto",100,1,true,"STRING","","",0.0,"",null),
	CAPACITY("Capacidad",10,1,true,"NUMBER","","",0.0,"max",null),
	FLOOR("Numero de piso",10,1,true,"NUMBER","","",0.0,"max",null),
	ROOM_NUMBER("Numero de habitacion",10,1,true,"NUMBER","","",0.0,"max",null),
	ROOM_STATUS("Estado de habitacion",100,1,true,"STRING","","",0.0,"",null),
	ROOM_CATEGORY("Estado de category",100,1,true,"STRING","","",0.0,"",null),
	ROOM_TYPE("Tipo de habitacion",100,1,true,"STRING","","",0.0,"",null),
	DEBT("Deuda",10,1,true,"NUMBER","","",0.0,"max",null),
	NUMBER_RESERVATION("Numero de reservacion",10,1,false,"NUMBER","","",0.0,"max",null),
	QUESTION("PREGUNTA",100,2,true,"STRING","","",0.0,"",null);
	private String fieldName;
	//private StringBuilder messages = new StringBuilder();
	private Integer sizeMax;
	private Integer sizeMin;
	private boolean required;
	private String type;
	private String expressionReg;
	private String expressionExample;
	private Double valueCompare;
	private Integer valueCompareInteger;
	private String typeCompare;
	private String valueCompareDate;	


	private ValidationConfiguration(String fieldName, Integer sizeMax, Integer sizeMin, boolean required, String type,
			String expressionReg, String expressionExample, Double valueCompare,String typeCompare, String valueCompareDate) {
		this.fieldName = fieldName;
		this.sizeMax = sizeMax;
		this.sizeMin = sizeMin;
		this.required = required;
		this.type = type;
		this.expressionReg = expressionReg;
		this.expressionExample = expressionExample;
		this.valueCompare = valueCompare;
		this.valueCompareDate = valueCompareDate;
		this.typeCompare = typeCompare;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Integer getSizeMax() {
		return sizeMax;
	}

	public Integer getSizeMin() {
		return sizeMin;
	}

	public boolean isRequired() {
		return required;
	}

	public String getType() {
		return type;
	}

	public String getExpressionReg() {
		return expressionReg;
	}

	public String getExpressionExample() {
		return expressionExample;
	}

	public Double getValueCompare() {
		return valueCompare;
	}

	public String getValueCompareDate() {
		return valueCompareDate;
	}

	public String getTypeCompare() {
		return typeCompare;
	}
	
}
