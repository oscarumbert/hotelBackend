package com.online.hotel.arlear.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.enums.ProductType;
import com.online.hotel.arlear.enums.Question;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.Survey;
import com.online.hotel.arlear.repository.SurveyRepository;

@Service
public class SurveyService implements ServiceGeneric<Survey>{
	@Autowired
	private SurveyRepository surveyRepository;
	
	@Override
	public boolean create(Survey entity) {
		if(surveyDuplicate(entity.getQuestion(),entity.getStars(),entity.getAnswerDetails(),entity.getClient())) {
			return false;
		}
		else {
			surveyRepository.save(entity);
			return true;
		}
	}
	
	public boolean update(Long id, Survey entity) {
		
		if(findID(id)==null) {
			return false;
		}
		else {
			Survey survey= findID(id);
			survey.setQuestion(entity.getQuestion());
			survey.setStars(entity.getStars());
			survey.setAnswerDetails(entity.getAnswerDetails());
			survey.setClient(entity.getClient());
			survey.setDate(entity.getDate());
			surveyRepository.save(survey);
			return true;
		} 		
	}
	
	@Override
	public boolean delete(Long id) {
		if(findID(id)==null) {
			return false;
		}
		else {
			 surveyRepository.deleteById(id);
			return true;
		}
	}
	
	@Override
	public List<Survey> find() {
		return surveyRepository.findAll();
	}
	
	public List<Survey> FilterSurvey(Survey survey) {
		
		if(!survey.getQuestion().equals("") && survey.getQuestion() == null ) {
			return findQuestion(survey.getQuestion());
		}
		else if(survey.getClient() != null && survey.getQuestion().equals("")) {
			return findClient(survey.getClient());
		}
		else if(survey.getQuestion() != null && !survey.getClient().equals("")) {
			return findQuestionClient(survey.getQuestion(),survey.getClient());
		}
		return null;
	}
	
	public List<Survey> findQuestion(Question question) {
		return surveyRepository.findAll().stream().filter(p -> p.getQuestion().equals(question)).collect(Collectors.toList());
	}

	public List<Survey> findClient(String client) {
		return surveyRepository.findAll().stream().filter(p -> p.getClient().equals(client)).collect(Collectors.toList());
	}

	public List<Survey> findQuestionClient(Question question, String client) {
		return surveyRepository.findAll().stream().filter(p -> p.getQuestion().equals(question) && p.getClient().equals(client)).collect(Collectors.toList());
	}
	
	public boolean surveyDuplicate(Question question, Integer stars, String answerDetails, String client) {
		if(findSurveyQuestion(question)!=null && findSurveyStars(stars)!=null && findSurveyAnswerDetails(answerDetails)!=null && findSurveyClient(client)!=null) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public Survey findID(Long id) {
		Optional<Survey> optional = surveyRepository.findAll().stream().filter(p -> p.getIdSurvey().equals(id)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Survey findSurveyQuestion(Question question) {
		Optional<Survey> optional = surveyRepository.findAll().stream().filter(p -> p.getQuestion().equals(question)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Survey findSurveyStars(Integer stars) {
		Optional<Survey> optional = surveyRepository.findAll().stream().filter(p -> p.getStars().equals(stars)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Survey findSurveyAnswerDetails(String answerDetails) {
		Optional<Survey> optional = surveyRepository.findAll().stream().filter(p -> p.getAnswerDetails().equals(answerDetails)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Survey findSurveyClient(String client) {
		Optional<Survey> optional = surveyRepository.findAll().stream().filter(p -> p.getClient().equals(client)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	
	@Override
	public boolean update(Survey entity) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Survey find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
