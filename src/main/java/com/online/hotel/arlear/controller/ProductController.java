package com.online.hotel.arlear.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ProductDTO;
import com.online.hotel.arlear.dto.ProductDTOfind;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.dto.UserDTO;
import com.online.hotel.arlear.enums.ProductType;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.UserHotel;
import com.online.hotel.arlear.service.ProductService;
import com.online.hotel.arlear.util.Validation;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ObjectConverter objectConverter;


	@PostMapping(value="/get")
	public ResponseEntity<?> getProducts(@RequestBody ProductDTOfind productDTO) {
		ResponseDTO response=new ResponseDTO();
		Product product = new Product();
		product.setName(productDTO.getName());
		if(!product.getProductType().equals("")) {
			product.setProductType(ProductType.valueOf(productDTO.getProductType()));
		}
	
		if(product.getName()==null && product.getProductType()==null) {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.NULL.getCode(),
					   ErrorMessages.NULL.getDescription("Campos nulos"));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	
		if(product.getName().equals("") && product.getProductType().equals("")) {
		response = new ResponseDTO("ERROR",
				   ErrorMessages.EMPTY_ENUM.getCode(),
				   ErrorMessages.EMPTY_SEARCH.getDescription("el producto"));
		return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	
		if(productService.FilterProduct(product)!=null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.FilterProduct(product));
		}
		else { 
			if(productService.FilterProduct(product)==null){
				response = new ResponseDTO("ERROR",
						   ErrorMessages.CREATE_ERROR.getCode(),
						   ErrorMessages.CREATE_ERROR.getDescription("busqueda del usuario"));
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	}	

	@PostMapping(value="/getAll")
	public ResponseEntity<?> getProductsAll() {
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.find());
		
	}
	
	@GetMapping(value="{idProduct}")
	public ProductDTO getUser(@PathVariable Long idProduct) {
		ProductDTO productDTO=objectConverter.converter(productService.findID(idProduct));
		return productDTO;
	}
	
	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
		ResponseDTO response = new ResponseDTO();	
		List<String> errors = Validation.applyValidationProduct(productDTO);
		if(errors.size()==0) {
			
			Product product = objectConverter.converter(productDTO);
			
			if(productService.create(product)) {
				response= new ResponseDTO("OK", 
										ErrorMessages.CREATE_OK.getCode(),
										ErrorMessages.CREATE_OK.getDescription("El producto:"+" "+productDTO.getName()+", Codigo: "+productDTO.getCode()));
			}
		}
		else{
			response.setStatus("ERROR");
			response.setCode(errors.get(0).toString());
			response.setMessage(errors.get(1).toString());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}


}