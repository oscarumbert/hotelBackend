package com.online.hotel.arlear.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.dto.MenuDTO;
import com.online.hotel.arlear.enums.ProductType;
import com.online.hotel.arlear.enums.UserType;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.model.UserHotel;
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
	
	
	public Product findID(Long id) {
		Optional<Product> optional = productRepository.findAll().stream().filter(p -> p.getId().equals(id)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	
	public List<Product> FilterProduct(Product product) {
		
		if(!product.getName().equals("") && product.getProductType() == null ) {
			return findName(product.getName());
		}
		else if(product.getProductType() != null && product.getName().equals("")) {
			return findType(product.getProductType());
		}
		else if(product.getProductType() != null && !product.getName().equals("")) {
			return findNameType(product.getName(),product.getProductType());
		}
		return null;
	}
	
	public List<Product> findName(String name) {
		return productRepository.findAll().stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
	}

	public List<Product> findType(ProductType productType) {
		return productRepository.findAll().stream().filter(p -> p.getProductType().equals(productType)).collect(Collectors.toList());
	}

	public List<Product> findNameType(String name, ProductType type) {
		return productRepository.findAll().stream().filter(p -> p.getName().equals(name) && p.getProductType().equals(type)).collect(Collectors.toList());
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

	public List<Product> findIDProducts(List<Integer> list) {
		List<Product> product=new ArrayList<Product>();
		for(int i=0;i<list.size();i++) {
			Optional<Product> optional=productRepository.findById(list.get(i).longValue());
			product.add(optional.get());
		}
		return product;
	}
	


}
