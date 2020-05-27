package com.online.hotel.arlear.controller;

import java.util.ArrayList;
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
import com.online.hotel.arlear.dto.ProductDTOUpdate;
import com.online.hotel.arlear.dto.ProductDTOfind;
import com.online.hotel.arlear.dto.ResponseDTO;
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
		Product product = objectConverter.converter(productDTO);
		List<Product> productList= productService.FilterProduct(product);
		
		if(productList!=null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(productList);
		}
		else{ 
				response = new ResponseDTO("ERROR",
						   ErrorMessages.SEARCH_ERROR.getCode(),
						   ErrorMessages.SEARCH_ERROR.getDescription(""));
				return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	}	

	@PostMapping(value="/getAll")
	public ResponseEntity<?> getProductsAll() {
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.find());
		
	}
	
	@GetMapping(value="{idProduct}")
	public ProductDTO getProduct(@PathVariable Long idProduct) {
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
										ErrorMessages.CREATE_OK.getDescription("el producto: "+ productDTO.getName()));
			}
		}
		else{
			response.setStatus("ERROR");
			response.setCode(errors.get(0).toString());
			response.setMessage(errors.get(1).toString());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping
	public ResponseEntity<?> updateProduct (@RequestBody ProductDTOUpdate productDtoUP) {
		
		ResponseDTO response = new ResponseDTO();
		ProductDTO productdto = productDtoUP;
		List<String> errors = Validation.applyValidationProduct(productdto);
		List<String> code= new ArrayList<>();
		List<String> messages= new ArrayList<>();
		
		
		if(errors.size()==0) {
			Product product = objectConverter.converter(productDtoUP);
				if(productService.update(productDtoUP.getId(),product)) {
					response= new ResponseDTO("OK", 
							ErrorMessages.UPDATE_OK.getCode(),
							ErrorMessages.UPDATE_OK.getDescription("el product "+ productDtoUP.getName()));
				}
				else{
					response= new ResponseDTO("ERROR", 
					ErrorMessages.UPDATE_ERROR.getCode(),
					ErrorMessages.UPDATE_ERROR.getDescription("el product. ID No existe"));
			}		
		}	
		else{
			int j=0;
			int i;
			for (i=0; i<errors.size();i=((2*i)/2)+2) {
				response= new ResponseDTO("ERROR",errors.get(j).toString(),errors.get(j+1).toString());
				code.add(response.getCode().toString());
				messages.add(response.getMessage().toString());
				j=((2*j)/2)+2;
			}
			response.setStatus("ERROR");
			response.setCode(code.toString());
			response.setMessage(messages.toString());
			
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@DeleteMapping(value="{idProduct}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		ResponseDTO response = new ResponseDTO();

		if(!productService.delete(id)) {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.DELETED_ERROR.getCode(),
					   ErrorMessages.DELETED_ERROR.getDescription("el producto. ID incorrecto"));
		}
		
		else	{
			response = new ResponseDTO("OK",
					   ErrorMessages.DELETED_OK.getCode(),
					   ErrorMessages.DELETED_OK.getDescription("el producto"));
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
	
	
	
}