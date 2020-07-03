package com.online.hotel.arlear.exception;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.*;

public class FieldValidation {
	private Object fieldValue;
	private String fieldName;
	private List<String> messages = new ArrayList<String>();
	private Integer sizeMax;
	private Integer sizeMin;
	private String expressionReg;
	private String expressionExample;
	private Double valueCompare;
	private boolean required = true;
	private String fieldCompareDate;
	private LocalDate valueCompareDate;
	private static String expresionNumber = ExpressionRegular.NUMBER.getValue();
	/**
	 * Cada predicate ejecuta una la validacion deiferente 
	 */
	
	private Predicate<Object> predicateNull = p -> p == null || p.equals("") ;
	private Predicate<Object> predicateMax = p -> p.toString().length() > this.sizeMax;
	private Predicate<Object> predicateMin = p -> p.toString().length() < this.sizeMin;
	private Predicate<Object> predicateFormat = p -> Pattern.compile(this.expressionReg).matcher(p.toString()).find();
	private Predicate<Object> predicateNumber = p -> Pattern.compile(this.expresionNumber).matcher(p.toString()).find();
	private Predicate<Double> predicateCompareMax = p -> p > this.valueCompare;
	private Predicate<Double> predicateCompareMin = p -> p < this.valueCompare;
	private Predicate<LocalDate> predicateDateMax = p -> p.isAfter(valueCompareDate);
	private Predicate<LocalDate> predicateDateMin = p -> p.isBefore(valueCompareDate);
	
	/**
	 * Este metodo se encarga de validar el campo recibdo con sus validaciones
	 * @param value Es el valor del campo
	 * @param validationConfiguration Este parametro contiene todas las propiedades necesarias para poder validar
	 * el valor del campo como por ejemplo: nombre del mismo ,longitud maxima, minima, si es obligatorio etc.
	 */
	public void validate( Object value, ValidationConfiguration validationConfiguration) {
		
			this.fieldValue = value;
			this.fieldName = validationConfiguration.getFieldName();
			this.sizeMax = validationConfiguration.getSizeMax();
			this.sizeMin = validationConfiguration.getSizeMin();
			this.expressionExample = validationConfiguration.getExpressionExample();
			this.expressionReg = validationConfiguration.getExpressionReg();
			this.valueCompare = validationConfiguration.getValueCompare();
			this.fieldCompareDate = validationConfiguration.getValueCompareDate();
			this.required = validationConfiguration.isRequired();
			
			if(Arrays.asList(validationConfiguration.NAME_MENU.getFieldName(),
							 validationConfiguration.GENDER.getFieldName(),
							 validationConfiguration.NAME.getFieldName(),
							 validationConfiguration.SURNAME.getFieldName(),
							 validationConfiguration.PROVINCE.getFieldName(),
							 validationConfiguration.LOCALITY.getFieldName(),
							 validationConfiguration.STREET.getFieldName(),
							 validationConfiguration.APARTMENT.getFieldName(),
							 validationConfiguration.OPERATION_TYPE.getFieldName(),
							 validationConfiguration.OPERATION_NUMBER.getFieldName(),
							 validationConfiguration.QUALITY.getFieldName(),
							 validationConfiguration.BARCODE_NUMBER.getFieldName()).contains(validationConfiguration.getFieldName())){
				
				if(!isNull()) {
					validateSizemax();
					validateSizeMin();
				}
				
			}else if(Arrays.asList(validationConfiguration.AMOUNT.getFieldName(),
								   validationConfiguration.AMOUNT_DEBT.getFieldName()).contains(validationConfiguration.getFieldName())) {
				
				if(!isNull()) {
					validateSizemax();
					validateSizeMin();
					if(isDouble()) {
						validateCompare(validationConfiguration.getTypeCompare());
					}
				}
			}else if(Arrays.asList(validationConfiguration.CELL_PHONE.getFieldName(),
								   validationConfiguration.POSTAL_CODE.getFieldName()).contains(validationConfiguration.getFieldName())) {
				if(!isNull()) {
					validateSizemax();
					validateSizeMin();
					validateFormat();
					validateNumber();
				}
			}else if(Arrays.asList(validationConfiguration.PHONE.getFieldName(),
								   validationConfiguration.STREET_NUMBER.getFieldName()).contains(validationConfiguration.getFieldName())) {
				if(!isNull()) {
					validateSizemax();
					validateSizeMin();
					validateNumber();
				}
			}else if(Arrays.asList(validationConfiguration.EMAIL.getFieldName()).contains(validationConfiguration.getFieldName())) {
				if(!isNull()) {
					validateSizemax();
					validateSizeMin();
					validateFormat();
				}
			}else if(Arrays.asList(validationConfiguration.OPERATION_DATE.getFieldName(),
								   validationConfiguration.DATE_MORA.getFieldName()).contains(validationConfiguration.getFieldName())) {
				
				if(this.valueCompareDate == null) {
					this.valueCompareDate = LocalDate.now();

				}
				if(!isNull()) {
					validateSizemax();
					validateSizeMin();
					validateCompareDate(validationConfiguration.getTypeCompare());
				}
				this.valueCompareDate = null;
			}
			
			
			
	}
	/**
	 * La funcion principal de este metodo es recibir el valor de un objeto de tipo LocalDate para comparar con el valor
	 * del que campo que se esta validando, luego de guardar dicho campo se llama a validate para que aplica todas las validaciones. 
	 * @param value
	 * @param validationConfiguration
	 * @param dateCompare
	 */
	public void validate( Object value, ValidationConfiguration validationConfiguration,LocalDate dateCompare) {
		this.valueCompareDate = dateCompare;
		validate(value,validationConfiguration);
		
	}
	private void validateSizemax() {
		
		if(this.predicateMax.test(this.fieldValue)) {
			this.messages.add(ErrorMessagesVal.SIZE_MAX.getMessage(this.fieldName, this.sizeMax.toString()));
		}
	}
	
	private void validateSizeMin() {
		
		if(this.predicateMin.test(this.fieldValue)) {
			this.messages.add(ErrorMessagesVal.SIZE_MIN.getMessage(this.fieldName, this.sizeMin.toString()));
		}
	}
	private boolean isNull() {
		
		if(this.predicateNull.test(this.fieldValue)) {
			
			if(this.required) {
				this.messages.add(ErrorMessagesVal.REQUIRED.getMessage(this.fieldName));
			}
			
			return true;
		}
		return false;
	}
	private void validateFormat() {
		
		if(!this.predicateFormat.test(this.fieldValue)) {
			this.messages.add(ErrorMessagesVal.FORMAT_INCORRECT.getMessage(this.fieldName, this.expressionExample));
		}
	}
	private void validateNumber() {
		
		if(!this.predicateNumber.test(this.fieldValue)) {
			this.messages.add(ErrorMessagesVal.NUMBER.getMessage(this.fieldName));
		}
	}
	private boolean isDouble() {
		
		try {
			Double.parseDouble(this.fieldValue.toString());
			return true;
		}catch(NumberFormatException e) {
			this.messages.add(ErrorMessagesVal.COMPARE_MAX.getMessage(this.fieldName,this.valueCompare.toString()));
			return false;

		}
	}
	private void validateCompare(String typeCompare) {
		
		if(typeCompare.equals("max")) {
			if(!this.predicateCompareMax.test(Double.parseDouble(this.fieldValue.toString()))) {
				this.messages.add(ErrorMessagesVal.COMPARE_MAX.getMessage(this.fieldName,this.valueCompare.toString()));
			}
		}else {
			if(!this.predicateCompareMin.test(Double.parseDouble(this.fieldValue.toString()))) {
				this.messages.add(ErrorMessagesVal.COMPARE_MIN.getMessage(this.fieldName,this.valueCompare.toString()));
			}
		}
		
	}
	private void validateCompareDate(String typeCompare) {
		
		if(typeCompare.equals("max")) {
			if(!this.predicateDateMax.test(LocalDate.parse(this.fieldValue.toString()))) {
				this.messages.add(ErrorMessagesVal.COMPARE_MAX.getMessage(this.fieldName,this.fieldCompareDate));
			}
		}else {
			if(!this.predicateDateMin.test(LocalDate.parse(this.fieldValue.toString()))) {
				this.messages.add(ErrorMessagesVal.COMPARE_MIN.getMessage(this.fieldName,this.fieldCompareDate.toString()));
			}
		}
		
	}

	public List<String> getMessage() {
		return this.messages;
	}

}
