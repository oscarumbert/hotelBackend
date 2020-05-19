package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.repository.TransactionRepository;

@Service
public class TransactionService implements ServiceGeneric<Transaction>{

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Override
	public boolean create(Transaction entity) {
		// TODO Auto-generated method stub
		return transactionRepository.save(entity) != null;
	}

	@Override
	public boolean update(Transaction entity) {
		// TODO Auto-generated method stub
		Transaction transaction = findID(entity.getId());
		transaction.setAmount(entity.getAmount());
		transaction.setDate(entity.getDate());
		transaction.setDescription(entity.getDescription());
		transaction.setElement(entity.getElement());
		transaction.setSection(entity.getSection());
		return transactionRepository.save(transaction) != null;

	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		transactionRepository.deleteById(id);
		return false;
	}

	@Override
	public List<Transaction> find() {
		// TODO Auto-generated method stub
		return transactionRepository.findAll();
	}

	public Transaction findID(Long id) {
		// TODO Auto-generated method stub
		Optional<Transaction> optional = transactionRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
		
	}
	@Override
	public Transaction find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
