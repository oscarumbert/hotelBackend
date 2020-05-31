package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.enums.ProductAvailability;
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
		if(productDuplicate(entity.getName(), entity.getPrice(), entity.getProductType(), entity.getProductAvailability())) {
			return false;
		}
		else {
			productRepository.save(entity);
			return true;
		}
	}
	
	@Override
	public boolean delete(Long id) {
		if(findID(id)==null) {
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
			product.setProductAvailability(entity.getProductAvailability());
			product.setPrice(entity.getPrice());
			productRepository.save(product);
			return true;
		}
	}
	
	@Override
	public List<Product> find() {
		return productRepository.findAll();
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
	
	public boolean productDuplicate(String name, Double price, ProductType type, ProductAvailability availability) {
		if(findNameProduct(name)!=null && findPriceProduct(price)!=null && findProductType(type)!=null && findProductAvailability(availability)!=null) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public Product findID(Long id) {
		Optional<Product> optional = productRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Product findNameProduct(String nameProduct) {
		Optional<Product> optional = productRepository.findAll().stream().filter(p -> p.getName().equals(nameProduct)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Product findPriceProduct(Double priceProduct) {
		Optional<Product> optional = productRepository.findAll().stream().filter(p -> p.getPrice().equals(priceProduct)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Product findProductType(ProductType productType) {
		Optional<Product> optional = productRepository.findAll().stream().filter(p -> p.getProductType().equals(productType)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Product findProductAvailability(ProductAvailability productAvailability) {
		Optional<Product> optional = productRepository.findAll().stream().filter(p -> p.getProductAvailability().equals(productAvailability)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
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
	


}
