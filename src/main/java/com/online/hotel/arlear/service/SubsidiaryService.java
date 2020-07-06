package com.online.hotel.arlear.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.online.hotel.arlear.model.Subsidiary;
import com.online.hotel.arlear.repository.SubsidiaryRepository;

public class SubsidiaryService implements ServiceGeneric<Subsidiary>{

	@Autowired
	private SubsidiaryRepository subsidiaryRepository;
	@Override
	public boolean create(Subsidiary entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Subsidiary> find() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public boolean update(Subsidiary entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Subsidiary find(Long id) {
		// TODO Auto-generated method stub
		return subsidiaryRepository.findAll().stream().findAny().get();
		
	}

}
