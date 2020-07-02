package com.online.hotel.arlear.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.dto.SurveyDTO;
import com.online.hotel.arlear.dto.SurveyDTOfind;
import com.online.hotel.arlear.exception.ErrorTools;
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

	@PostMapping(value="/get")
	public ResponseEntity<?> getSurveys(@RequestBody SurveyDTOfind surveyDTO) {
		ResponseDTO response=new ResponseDTO();
		Survey survey = objectConverter.converter(surveyDTO);
		List<Survey> surveyList= surveyService.FilterSurvey(survey);
		
		if(surveyList!=null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(surveyList);
		}
		else{ 
				response = ErrorTools.searchError("");
				return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	}	

	@PostMapping(value="/getAll")
	public ResponseEntity<?> getSurveyAll() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(surveyService.find());
	}
	
	@GetMapping(value="{idSurvey}")
	public SurveyDTO getSurvey(@PathVariable Long idSurvey) {
		SurveyDTO surveyDTO=objectConverter.converter(surveyService.findID(idSurvey));
		return surveyDTO;
	}
	
	//Metodo para crear una encuesta
	@PostMapping
	public ResponseEntity<?> createSurvey(@RequestBody SurveyDTO surveyDTO) {
		ResponseDTO response = new ResponseDTO();	
		List<String> errors = Validation.applyValidationSurvey(surveyDTO);
		if(errors.size()==0) {
			Survey survey = objectConverter.converter(surveyDTO);
			
			if(surveyService.create(survey)) {
				response=ErrorTools.createOk("la encuesta: "+ surveyDTO.getAnswer());
			}
		}
		else{
			response.setStatus("ERROR");
			response.setCode(errors.get(0).toString());
			response.setMessage(errors.get(1).toString());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Metodo para eliminar una encuesta, con el ID
	@DeleteMapping(value="{id}")
	public ResponseEntity<?> deleteSurvey(@PathVariable Long id) {
		ResponseDTO response = new ResponseDTO();

		if(!surveyService.delete(id)) {
			response=ErrorTools.deleteError("la encuesta. ID incorrecto");
		}
		else{
			response=ErrorTools.deleteOk("la encuesta");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
	
}
