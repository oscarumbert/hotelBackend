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
import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.OrderRestaurantDTO;
import com.online.hotel.arlear.dto.OrderRestaurantDTOUpdate;
import com.online.hotel.arlear.dto.ReservationOpenDTO;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.enums.OrderType;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.OrderRestaurant;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.Reservation;
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
		ResponseDTO response=new ResponseDTO();
		List<OrderRestaurant> orders= orderService.find();
		if(orders.isEmpty()) {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.SEARCH_ERROR.getCode(),
					   ErrorMessages.SEARCH_ERROR.getDescription(""));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		}
		else {
		
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(orders);
		}
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
		
	//Metodo para modificar menu
	@PutMapping
	public ResponseEntity<?> updateOrder(@RequestBody OrderRestaurantDTOUpdate orderUp){
		ResponseDTO response = new ResponseDTO();
		//System.out.print(menucreate.toString());
		OrderRestaurantDTO orderdto= orderUp;
		
		List<String> errors = Validation.applyValidationOrderRestaurant(orderdto);
		if(errors.size()==0) {
			OrderRestaurant order=objectConverter.converter(orderdto);
			
			List<Product> product=productService.findIDProducts(orderUp.getProduct());
			List<Menu> menu=menuService.findIDMenues(orderUp.getMenu());
			
			if(product!=null || menu!=null) {
				order.setProduct(product);
				order.setMenu(menu);		
				
				if(orderdto.getIdReservation()==null) {
					if(orderService.updateOrderClient(orderUp.getIdOrder(),order)) {
						response= new ResponseDTO("OK", 
								ErrorMessages.UPDATE_OK.getCode(),
								ErrorMessages.UPDATE_OK.getDescription("El pedido : "+orderUp.getIdOrder()));
					}
					else {
						response= new ResponseDTO("ERROR", 
								ErrorMessages.UPDATE_ERROR.getCode(),
								ErrorMessages.UPDATE_ERROR.getDescription("el pedido. ID no existe"));
					}
				}
				else {
					Long idReservation=orderdto.getIdReservation();
					if(orderService.updateOrderGuest(orderUp.getIdOrder(),order,idReservation)) {
						response= new ResponseDTO("OK", 
								ErrorMessages.UPDATE_OK.getCode(),
								ErrorMessages.UPDATE_OK.getDescription("El pedido : "+orderUp.getIdOrder()));
					}
					else {
						response= new ResponseDTO("ERROR", 
								ErrorMessages.UPDATE_ERROR.getCode(),
								ErrorMessages.UPDATE_ERROR.getDescription("el pedido. ID no existe"));
					}
				}
			}
			else {
				response= new ResponseDTO("ERROR", 
						ErrorMessages.CREATE_ERROR.getCode(),
						ErrorMessages.CREATE_ERROR.getDescription("Algunos productos o menues ingresados no existen"));
			}
			
		}
		else {
			response=findList(errors);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@DeleteMapping(value="{idOrder}")
	public ResponseEntity<?> deleteOrder(@PathVariable Long idOrder) {
		ResponseDTO response = new ResponseDTO();
		//validacion
		if(!orderService.delete(idOrder)) {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.DELETED_ERROR.getCode(),
					   ErrorMessages.DELETED_ERROR.getDescription("el pedido. ID incorrecto"));
		}
		
		else	{
			response = new ResponseDTO("OK",
					   ErrorMessages.DELETED_OK.getCode(),
					   ErrorMessages.DELETED_OK.getDescription("el pedido"));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
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
