package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.repository.ProductRepository;

@Service
public class ProductService implements ServiceGeneric<Product>{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public boolean create(Product entity) {
		return productRepository.save(entity) != null;
	}
	
	@Override
	public boolean delete(Long id) {
		if(findID(id).equals(null)) {
			return false;
		}
		else {
			 productRepository.deleteById(id);
			return true;
		}
	}
	
	
	public boolean update(Long id, Product entity) {
		if(find(id).equals(null)){
			return false;
		}
		else {
			Product product= find(id);
			product.setName(entity.getName());
			product.setCode(entity.getCode());
			product.setProductType(entity.getProductType());
			product.setPrice(entity.getPrice());
			productRepository.save(product);
			return true;
		}
	}
	
	@Override
	public List<Product> find() {
		return productRepository.findAll();
	}
	
	@Override
	public Product find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Product entity) {
		// TODO Auto-generated method stub
		return false;
	}
	

	public Product findID(Long id) {
		Optional<Product> optional = productRepository.findAll().stream().filter(p -> p.getId().equals(id)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}

}
