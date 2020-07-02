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
import com.online.hotel.arlear.dto.ContactDTOOrder;
import com.online.hotel.arlear.dto.MenuDTOOrder;
import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.OrderOpensDTO;
import com.online.hotel.arlear.dto.OrderRestaurantDTO;
import com.online.hotel.arlear.dto.OrderRestaurantDTOUpdate;
import com.online.hotel.arlear.dto.ProductDTO;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.dto.RoomDTOOrder;
import com.online.hotel.arlear.enums.OrderType;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.OrderRestaurant;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.service.MenuService;
import com.online.hotel.arlear.service.OrderRestaurantService;
import com.online.hotel.arlear.service.ProductService;
import com.online.hotel.arlear.service.ReservationService;
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
	private ReservationService reservationService;
	
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
	
	//Obtiene los pedidos abiertos.
	@GetMapping(value="/OpenOrders")
	public ResponseEntity<?> getOpenOrders() {
		ResponseDTO response=new ResponseDTO();
		List<OrderRestaurant> orders= orderService.findOpenOrders();
		if(orders==null) {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.SEARCH_ERROR.getCode(),
					   ErrorMessages.SEARCH_ERROR.getDescription(""));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		}
		else {
			
			List<OrderOpensDTO> orderOpen=converterOrders(orders);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderOpen);
		}
	}	 
	
	//Obtiene un pedido.
	@GetMapping(value="{idOrder}")
	public ResponseEntity<?> getOrder(@PathVariable Long idOrder) {
		ResponseDTO response = new ResponseDTO();
		OrderRestaurant order=orderService.find(idOrder);
		if(order!=null) {
			OrderOpensDTO orderOpen=objectConverter.converter(order);
			List<Product> product=order.getProduct();
			List<ProductDTO> productDTO= converterProduct(product);
			List<Menu> menu=order.getMenu();
			List<MenuDTOOrder> menuDTO=converterMenu(menu);
			orderOpen.setProduct(productDTO);
			orderOpen.setMenu(menuDTO);			
			if(order.getOrderType().equals(OrderType.CONSUMICION_HABITACION)) {
				Reservation reservation=reservationService.find(order.getNumberReservation());
				ContactDTOOrder contact=objectConverter.converterContactOrder(reservation.getContact());
				RoomDTOOrder room=objectConverter.converter(reservation.getRoom());
				orderOpen.setClient(contact);
				orderOpen.setRoom(room);
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderOpen);
			}			
			else {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderOpen);
			}	
		}
		else {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.SEARCH_ERROR.getCode(),
					   ErrorMessages.SEARCH_ERROR.getDescription("No existe ningun pedido con el id: "+idOrder));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}		
	}
	
	private List<MenuDTOOrder> converterMenu(List<Menu> menuModel) {
		List<MenuDTOOrder> menu=new ArrayList<MenuDTOOrder>();
		for(int i=0;i<menuModel.size();i++) {
			Menu m=menuModel.get(i);
			MenuDTOOrder mDTO=objectConverter.converterMenu(m);
			menu.add(mDTO);
		}
		return menu;
	}

	private List<ProductDTO> converterProduct(List<Product> producModel) {
		List<ProductDTO> product=new ArrayList<ProductDTO>();
		for(int i=0;i<producModel.size();i++) {
			Product prod=producModel.get(i);
			ProductDTO pro=objectConverter.converter(prod);
			product.add(pro);
		}
		return product;
	}
	
	private List<OrderOpensDTO> converterOrders(List<OrderRestaurant> orders) {
		List<OrderOpensDTO> orderOpen=new ArrayList<OrderOpensDTO>();		
		for(int i=0;i<orders.size();i++) {
			List<ProductDTO> productDTO=new ArrayList<ProductDTO>();;	
			List<MenuDTOOrder> menuDTO=new ArrayList<MenuDTOOrder>();;
			List<Product> product=orders.get(i).getProduct();
			List<Menu> menus=orders.get(i).getMenu();
			OrderOpensDTO order=objectConverter.converter(orders.get(i));			
			if(orders.get(i).getOrderType().equals(OrderType.CONSUMICION_HABITACION)) {
				Reservation reservation=reservationService.findID(orders.get(i).getNumberReservation());
				if(!product.isEmpty()) {
					for(int j=0;j<product.size();j++) {
						ProductDTO productUnity=objectConverter.converter(product.get(j));
						productDTO.add(productUnity);
					}
				}
				if(!menus.isEmpty()) {
					for(int k=0;k<menus.size();k++) {
						MenuDTOOrder menuUnity=objectConverter.converterMenu(menus.get(k));
						menuDTO.add(menuUnity);
					}
				}
				ContactDTOOrder contact=objectConverter.converterContactOrder(reservation.getContact());
				RoomDTOOrder room=objectConverter.converter(reservation.getRoom());				
				order.setClient(contact);
				order.setRoom(room);
				order.setMenu(menuDTO);
				order.setProduct(productDTO);
				orderOpen.add(order);
			}			
			if(orders.get(i).getOrderType().equals(OrderType.CONSUMICION_RESTAURANT)) {
				if(!product.isEmpty()) {
					for(int j=0;j<product.size();j++) {
						ProductDTO productUnity=objectConverter.converter(product.get(j));
						productDTO.add(productUnity);
					}
				}
				if(!menus.isEmpty()) {
					for(int k=0;k<menus.size();k++) {
						MenuDTOOrder menuUnity=objectConverter.converterMenu(menus.get(k));
						menuDTO.add(menuUnity);
					}
				}
				order.setMenu(menuDTO);
				order.setProduct(productDTO);
				order.setRoom(null);
				order.setClient(null);
				orderOpen.add(order);
			}			
		}		
		return orderOpen;
	}

	@PostMapping
	public ResponseEntity<?> createOrder(@RequestBody OrderRestaurantDTO ordercreate){
		ResponseDTO response = new ResponseDTO();
		List<String> errors = Validation.applyValidationOrderRestaurant(ordercreate);
		if(errors.size()==0) {
			OrderRestaurant order=objectConverter.converter(ordercreate);			
			List<Product> product=productService.findIDProducts(ordercreate.getProduct());
			List<Menu> menu=menuService.findIDMenues(ordercreate.getMenu());			
			if(product!=null || menu!=null) {
				order.setProduct(product);
				order.setMenu(menu);				
				if(ordercreate.getNumberReservation()==null) {
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
				if(ordercreate.getNumberReservation()!=null)  {
					Long id=ordercreate.getNumberReservation();
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
	
	//Metodparacerrarpedidos
	@PutMapping(value="/closeOrder/{idOrder}")
	public ResponseEntity<?> closeOrder(@PathVariable Long idOrder) {
		ResponseDTO response=new ResponseDTO();
		if(!orderService.close(idOrder)) {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.UPDATE_ERROR.getCode(),
					   ErrorMessages.UPDATE_ERROR.getDescription("el pedido. ID incorrecto"));
		}		
		else	{
			response = new ResponseDTO("OK",
					   ErrorMessages.UPDATE_OK.getCode(),
					   ErrorMessages.UPDATE_OK.getDescription("el pedido. Estado: Cerrado"));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
	
	//Metodo para modificar menu
	@PutMapping
	public ResponseEntity<?> updateOrder(@RequestBody OrderRestaurantDTOUpdate orderUp){
		ResponseDTO response = new ResponseDTO();
		OrderRestaurantDTO orderdto= orderUp;		
		List<String> errors = Validation.applyValidationOrderRestaurant(orderdto);
		if(errors.size()==0) {
			OrderRestaurant order=objectConverter.converter(orderdto);			
			List<Product> product=productService.findIDProducts(orderUp.getProduct());
			List<Menu> menu=menuService.findIDMenues(orderUp.getMenu());			
			if(product!=null || menu!=null) {
				order.setProduct(product);
				order.setMenu(menu);					
				if(orderdto.getNumberReservation()==null) {
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
				if(orderdto.getNumberReservation()!=null)  {
					Long idReservation=orderdto.getNumberReservation();
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
