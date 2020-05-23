package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.dto.ContactFindDTO;
import com.online.hotel.arlear.enums.DocumentType;
import com.online.hotel.arlear.enums.GenderType;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.exception.ExceptionUnique;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.repository.ContactRepository;

@Service
public class ContactService implements ServiceGeneric<Contact>{

	@Autowired
	private ContactRepository contactRepository;
	
	public boolean createContact(Contact entity) throws ExceptionUnique {
		// TODO Auto-generated method stub
		if(findUnique(new ContactFindDTO(entity.getDocumentNumber().toString(),
										 entity.getDocumentType().toString(),
										 entity.getGender().toString())) != null) {
			throw new ExceptionUnique(ErrorMessages.CREATE_ERROR_UNIQUE.getDescription("Contact"));
		}else {
			return create(entity);
		}
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Contact> find() {
		return contactRepository.findAll();
	}
	
	public Contact findByDocument(Integer document) {
		// TODO Auto-generated method stub
		Optional<Contact> optional = find().stream().filter(p -> p.getDocumentNumber().equals(document)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}


	}
	
	public Contact findUnique(ContactFindDTO contact) {
		Optional<Contact> optional =find().stream().filter(p -> p.getDocumentNumber().toString().equals(contact.getDocumentNumber()) ).findAny();
		//Optional<Contact> optional =null ;
		return optional.isPresent() ? optional.get():null;
		
	}

	@Override
	public Contact find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean update(Long id, Contact entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Contact entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(Contact entity) {
		// TODO Auto-generated method stub
		return contactRepository.save(entity)!=null;
	}

}
