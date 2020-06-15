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

import com.online.hotel.arlear.dto.MenuDTO;
import com.online.hotel.arlear.dto.MenuDTOUpdate;
import com.online.hotel.arlear.dto.MenuDTOfind;
import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ResponseDTO;

import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.service.MenuService;
import com.online.hotel.arlear.service.ProductService;
import com.online.hotel.arlear.util.Validation;


@RestController
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ObjectConverter objectConverter;
	
	@PostMapping(value="/get")
	public ResponseEntity<?> getMenus(@RequestBody MenuDTOfind menufind) {
		ResponseDTO response=new ResponseDTO();
		//validacion
			//Integer min=menufind.getMinPrice();
			//Integer max=menufind.getMaxPrice();
			Menu menu = objectConverter.converter(menufind);
			//List<Menu> menuList= menuService.FilterMenuPrice(menu, min, max);
			List<Menu> menuList= menuService.FilterMenu(menu);
			
			if(menuList!=null) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(menuList);
			}
			else{ 
					response = new ResponseDTO("ERROR",
							   ErrorMessages.SEARCH_ERROR.getCode(),
							   ErrorMessages.SEARCH_ERROR.getDescription(""));
					return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
			}		
	}
	
	@PostMapping(value="/getAll")
	public ResponseEntity<?> getMenuAll() {		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(menuService.find());	
	}
	
	@GetMapping(value="{idMenu}")
	public MenuDTO getMenu(@PathVariable Long idmenu) {
		MenuDTO menu=objectConverter.converter(menuService.find(idmenu));
		return menu;
	}
	
	@PostMapping
	public ResponseEntity<?> createMenu(@RequestBody MenuDTO menucreate){
		ResponseDTO response = new ResponseDTO();
		//System.out.print(menucreate.toString());
		
		
		List<String> errors = Validation.applyValidationMenu(menucreate);
		if(errors.size()==0) {
			Menu menu=objectConverter.converter(menucreate);
			List<Product> product=productService.findIDProducts(menucreate.getProducto());
			menu.setProduct(product);
			
			if(menuService.create(menu)) {
				response= new ResponseDTO("OK", 
						ErrorMessages.CREATE_OK.getCode(),
						ErrorMessages.CREATE_OK.getDescription("el producto:"+" "+menucreate.getNameMenu()));
			}
			else {
				response= new ResponseDTO("ERROR", 
						ErrorMessages.CREATE_ERROR.getCode(),
						ErrorMessages.CREATE_ERROR.getDescription("El menu:"+" "+menu.getNameMenu()+" ya se encuentra registrado"));
			}
		}
		else {
			response=findList(errors);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Metodo para modificar menu
	@PutMapping
	public ResponseEntity<?> createMenu(@RequestBody MenuDTOUpdate menuDtoUP){
		ResponseDTO response = new ResponseDTO();
		//System.out.print(menucreate.toString());
		MenuDTO menudto= menuDtoUP;
		
		List<String> errors = Validation.applyValidationMenu(menudto);
		if(errors.size()==0) {
			Menu menu=objectConverter.converter(menudto);
			List<Product> product=productService.findIDProducts(menudto.getProducto());
			menu.setProduct(product);
			
			if(menuService.update(menuDtoUP.getIdMenu(),menu)) {
				response= new ResponseDTO("OK", 
						ErrorMessages.UPDATE_OK.getCode(),
						ErrorMessages.UPDATE_OK.getDescription("el producto:"+" "+menu.getNameMenu()));
			}
			else {
				response= new ResponseDTO("ERROR", 
						ErrorMessages.UPDATE_ERROR.getCode(),
						ErrorMessages.UPDATE_ERROR.getDescription("el menu. ID no existe"));
			}
		}
		else {
			response=findList(errors);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	public ResponseDTO findList(List<?> errors){
		ResponseDTO response = new ResponseDTO();
		List<String> code= new ArrayList<>();
		List<String> messages= new ArrayList<>();
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
		return response;
	}
	
	@DeleteMapping(value="{idMenu}")
	public ResponseEntity<?> deleteMenu(@PathVariable Long idMenu) {
		ResponseDTO response = new ResponseDTO();
		//validacion
		if(!menuService.delete(idMenu)) {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.DELETED_ERROR.getCode(),
					   ErrorMessages.DELETED_ERROR.getDescription("el menu. ID incorrecto"));
		}
		
		else	{
			response = new ResponseDTO("OK",
					   ErrorMessages.DELETED_OK.getCode(),
					   ErrorMessages.DELETED_OK.getDescription("el menu"));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
}
