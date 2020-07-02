package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.online.hotel.arlear.model.Survey;
import com.online.hotel.arlear.repository.SurveyRepository;

@Service
public class SurveyService implements ServiceGeneric<Survey>{
	@Autowired
	private SurveyRepository surveyRepository;
	
	@Override
	public boolean create(Survey entity) {
		if(surveyDuplicate(entity.getClient(),entity.getIdReservation())) {
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
			survey.setAnswer(entity.getAnswer());
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
		
		if(!survey.getIdReservation().equals("") && survey.getIdReservation() == null ) {
			return findidReservation(survey.getIdReservation());
		}
		else if(survey.getClient() != null && survey.getIdReservation().equals("")) {
			return findClient(survey.getClient());
		}
		else if(survey.getIdReservation() != null && !survey.getClient().equals("")) {
			return findReservationClient(survey.getIdReservation(),survey.getClient());
		}
		return null;
	}
	
	public List<Survey> findidReservation(Long idReservation) {
		return surveyRepository.findAll().stream().filter(p -> p.getIdReservation().equals(idReservation)).collect(Collectors.toList());
	}

	public List<Survey> findClient(String client) {
		return surveyRepository.findAll().stream().filter(p -> p.getClient().equals(client)).collect(Collectors.toList());
	}

	public List<Survey> findReservationClient(Long idReservation, String client) {
		return surveyRepository.findAll().stream().filter(p -> p.getIdReservation().equals(idReservation) && p.getClient().equals(client)).collect(Collectors.toList());
	}
	
	public boolean surveyDuplicate(String client, Long idReservation) {
		if(findSurveyIdReservation(idReservation)!=null && findSurveyClient(client)!=null) {
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
	
	/*public Survey findSurveyQuestion(Question question) {
		Optional<Survey> optional = surveyRepository.findAll().stream().filter(p -> p.getQuestion().equals(question)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}*/
	
	/*public Survey findSurveyStars(Integer stars) {
		Optional<Survey> optional = surveyRepository.findAll().stream().filter(p -> p.getStars().equals(stars)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}*/
	
	/*public Survey findSurveyAnswerDetails(String answerDetails) {
		Optional<Survey> optional = surveyRepository.findAll().stream().filter(p -> p.getAnswerDetails().equals(answerDetails)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}*/
	
	public Survey findSurveyClient(String client) {
		Optional<Survey> optional = surveyRepository.findAll().stream().filter(p -> p.getClient().equals(client)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Survey findSurveyIdReservation(Long idReservation) {
		Optional<Survey> optional = surveyRepository.findAll().stream().filter(p -> p.getIdReservation().equals(idReservation)).findAny();
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
