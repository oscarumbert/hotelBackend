package com.online.hotel.arlear.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.model.Question;
import com.online.hotel.arlear.model.Room;
import com.online.hotel.arlear.repository.QuestionRepository;

@Service
public class QuestionService implements ServiceGeneric<Question>{

	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public boolean create(Question entity) {
		if(questionDuplicate(entity.getQuestion())) {
			return false;
		}
		else {
			questionRepository.save(entity);
			return true;
		}
	}

	@Override
	public boolean delete(Long id) {
		Optional<Question> question = questionRepository.findById(id);
		if(question.isPresent()) {
			questionRepository.deleteById(id);
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public List<Question> find() {
		return questionRepository.findAll().stream().sorted(Comparator.comparing(Question::getQuestion))
				.collect(Collectors.toList());
	}
	
	
	private boolean questionDuplicate(String question) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Question entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Question find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
