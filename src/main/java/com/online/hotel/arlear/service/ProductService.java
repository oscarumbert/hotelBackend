package com.online.hotel.arlear.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ProductDTO;
import com.online.hotel.arlear.enums.ProductAvailability;
import com.online.hotel.arlear.enums.ProductType;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.repository.ProductRepository;

@Service
public class ProductService implements ServiceGeneric<Product>{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ObjectConverter objectConverter;
	
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
		if(findID(id)==(null)){
			return false;
		}
		else {
			Product product= findID(id);
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

	public List<Product> findIDProducts(List<Integer> list) {
		List<Product> product=new ArrayList<Product>();
		for(int i=0;i<list.size();i++) {
			Optional<Product> optional=productRepository.findById(list.get(i).longValue());
			if(!optional.isPresent()) {
				return null;
			}
			else {
				product.add(optional.get());
			}
		}
		return product;
	}
	
	public List<ProductDTO> converterProduct(List<Product> producModel) {
		List<ProductDTO> produc=new ArrayList<ProductDTO>();
		for(int i=0;i<producModel.size();i++) {
			Product prod=producModel.get(i);
			ProductDTO pro=objectConverter.converter(prod);
			produc.add(pro);
		}
		return produc;
	}


}
