package com.online.hotel.arlear.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.OrderRestaurantDTO;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.OrderRestaurant;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.service.MenuService;
import com.online.hotel.arlear.service.OrderRestaurantService;
import com.online.hotel.arlear.service.ProductService;
import com.online.hotel.arlear.util.Validation;

@RestController
@RequestMapping("/orderrestaurant")
public class OrderRestaurantController {
	@Autowired
	private OrderRestaurantService orderService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ObjectConverter objectConverter;
	
	//obtiene todas los pedidos
	@GetMapping
	public ResponseEntity<?> getReservations() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.find());
	}
	
	@PostMapping
	public ResponseEntity<?> createOrder(@RequestBody OrderRestaurantDTO ordercreate){
		ResponseDTO response = new ResponseDTO();
		List<String> errors = Validation.applyValidationOrderRestaurant(ordercreate);
		//System.out.print(menucreate.toString());
		if(errors.size()==0) {
			OrderRestaurant order=objectConverter.converter(ordercreate);
			
			List<Product> product=productService.findIDProducts(ordercreate.getProduct());
			List<Menu> menu=menuService.findIDMenues(ordercreate.getMenu());
			
			if(product!=null || menu!=null) {
				order.setProduct(product);
				order.setMenu(menu);
				
				if(ordercreate.getIdReservation()==null) {
					if(orderService.create(order)) {
						response= new ResponseDTO("OK", 
								ErrorMessages.CREATE_OK.getCode(),
								ErrorMessages.CREATE_OK.getDescription(""));
					}
				else {
						response= new ResponseDTO("ERROR", 
								ErrorMessages.CREATE_ERROR.getCode(),
								ErrorMessages.CREATE_ERROR.getDescription(""));
					}
				}
				else {
					Long id=ordercreate.getIdReservation();
					if(orderService.createOrderGuest(order,id)) {
						response= new ResponseDTO("OK", 
								ErrorMessages.CREATE_OK.getCode(),
								ErrorMessages.CREATE_OK.getDescription(""));
					}
					else {
						response= new ResponseDTO("ERROR", 
								ErrorMessages.CREATE_ERROR.getCode(),
								ErrorMessages.CREATE_ERROR.getDescription("La reserva no existe o no esta en curso."));
					}
				}
			}
			else {
				response= new ResponseDTO("ERROR", 
						ErrorMessages.CREATE_ERROR.getCode(),
						ErrorMessages.CREATE_ERROR.getDescription("Algunos productos o menues ingresados no existen"));
			}
		}
		else
		{
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

}
