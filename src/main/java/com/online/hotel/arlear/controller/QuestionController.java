package com.online.hotel.arlear.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.hotel.arlear.dto.MenuDTO;
import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.QuestionDTO;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.Question;
import com.online.hotel.arlear.service.QuestionService;
import com.online.hotel.arlear.util.Validation;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ObjectConverter objectConverter;

	
	@PostMapping
	public ResponseEntity<?> createQuestion(@RequestBody QuestionDTO questioncreate){
		ResponseDTO response = new ResponseDTO();
		
		
		List<String> errors = Validation.applyValidationQuestion(questioncreate);
		if(errors.size()==0) {
			
			Question question = objectConverter.converter(questioncreate);
			
			if(questionService.create(question)) {
				response= new ResponseDTO("OK", 
										ErrorMessages.CREATE_OK.getCode(),
										ErrorMessages.CREATE_OK.getDescription("la pregunta: "+ questioncreate.getQuestion()));
			}
		}
		else{
			response.setStatus("ERROR");
			response.setCode(errors.get(0).toString());
			response.setMessage(errors.get(1).toString());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}


	@DeleteMapping(value="{id}")
	public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
		ResponseDTO response = new ResponseDTO();

		if(!questionService.delete(id)) {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.DELETED_ERROR.getCode(),
					   ErrorMessages.DELETED_ERROR.getDescription("la pregunta. ID incorrecto"));
		}
		
		else	{
			response = new ResponseDTO("OK",
					   ErrorMessages.DELETED_OK.getCode(),
					   ErrorMessages.DELETED_OK.getDescription("la pregunta"));
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
	
}
