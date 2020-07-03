package com.online.hotel.arlear.exception;


public enum ExpressionRegular {

		EMAIL("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"),
		POSTAL_CODE("2$"),
		PHONE("45"),
		CELL_PHONE("^09"),
		NUMBER("^([0-9])*$");
	
	
	private String value;
	
	private ExpressionRegular(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
