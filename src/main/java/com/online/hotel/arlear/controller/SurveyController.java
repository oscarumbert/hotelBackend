package com.online.hotel.arlear.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.dto.SurveyDTO;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Survey;
import com.online.hotel.arlear.service.SurveyService;
import com.online.hotel.arlear.util.Validation;

@RestController
@RequestMapping("/survey")
public class SurveyController {

	@Autowired
	private SurveyService surveyService;

	@Autowired
	private ObjectConverter objectConverter;

	
	@PostMapping
	public ResponseEntity<?> createSurvey(@RequestBody SurveyDTO surveyDTO) {
		ResponseDTO response = new ResponseDTO();	
		List<String> errors = Validation.applyValidationSurvey(surveyDTO);
		if(errors.size()==0) {
			
			Survey survey = objectConverter.converter(surveyDTO);
			
			if(surveyService.create(survey)) {
				response= new ResponseDTO("OK", 
										ErrorMessages.CREATE_OK.getCode(),
										ErrorMessages.CREATE_OK.getDescription("el producto: "+ surveyDTO.getQuestion()));
			}
		}
		else{
			response.setStatus("ERROR");
			response.setCode(errors.get(0).toString());
			response.setMessage(errors.get(1).toString());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
