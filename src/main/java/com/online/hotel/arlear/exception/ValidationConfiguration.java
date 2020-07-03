package com.online.hotel.arlear.exception;

public enum ValidationConfiguration {

	NAME_MENU("nameMenu",60,2,true,"STRING","","",0.0,"",null),
	GENDER("gender",1,1,true,"STRING","","",0.0,"",null),
	NAME("name",100,2,true,"STRING","","",0.0,"",null),
	SURNAME("surname",200,2,true,"STRING","","",0.0,"",null),
	PHONE("phone",20,7,false,"NUMBER","","",0.0,"",null),
	CELL_PHONE("cellPhone",20,8,false,"NUMBER",ExpressionRegular.CELL_PHONE.getValue(),"0934552812",0.0,"",null),
	EMAIL("email",100,5,false,"STRING",ExpressionRegular.EMAIL.getValue(),"oscar@gmail.com",0.0,"",null),
	PROVINCE("province",3,1,false,"STRING","","",0.0,"",null),
	LOCALITY("locality",3,1,false,"STRING","","",0.0,"",null),
	STREET("street",200,2,false,"STRING","","",0.0,"",null),
	STREET_NUMBER("numberStreet",20,2,false,"NUMBER","","",0.0,"",null),
	APARTMENT("apartment",6,1,false,"STRING","","",0.0,"",null),
	POSTAL_CODE("postalCode",4,3,false,"NUMBER",ExpressionRegular.POSTAL_CODE.getValue(),"3422",0.0,"",null),
	OPERATION_TYPE("operationType",2,1,true,"STRING","","",0.0,"",null),
	OPERATION_NUMBER("operationNumber",72,4,true,"STRING","","",0.0,"",null),
	QUALITY("quality",3,1,false,"STRING","","",0.0,"",null),
	OPERATION_DATE("operationDate",19,6,true,"DATE","","",0.0,"min","fecha actual"),
	DATE_MORA("dateMora",19,6,true,"DATE","","",0.0,"max","operationDate"),
	OPERATION_AMOUNT("operationAmount",10,2,true,"STRING","","",0.0,"",null),
	LAST_PAY_DATE("lastPayDate",19,6,true,"DATE","","",0.0,"",null),
	AMOUNT_DEBT("amountDebt",10,4,true,"NUMBER","","",300.0,"max",null),
	BARCODE_NUMBER("barcodeNumber",200,10,false,"NUMBER","","",0.0,"",null),
	AMOUNT("amount",10,4,true,"NUMBER","","",300.0,"max",null),
	CELL_PHONE_SENDING("cellPhone",20,8,true,"NUMBER",ExpressionRegular.CELL_PHONE.getValue(),"0934552812",0.0,"",null),
	EMAIL_SENDING("email",100,5,true,"STRING",ExpressionRegular.EMAIL.getValue(),"oscar@gmail.com",0.0,"",null),
	DATE_MORA_EXPIRATED("dateMora",19,6,true,"DATE","","",0.0,"min","fecha actual");

	
	private String fieldName;
	//private StringBuilder messages = new StringBuilder();
	private Integer sizeMax;
	private Integer sizeMin;
	private boolean required;
	private String type;
	private String expressionReg;
	private String expressionExample;
	private Double valueCompare;
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
