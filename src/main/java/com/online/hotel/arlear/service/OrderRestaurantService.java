package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.TransactiontDTO;
import com.online.hotel.arlear.enums.ReservationStatus;
import com.online.hotel.arlear.enums.Section;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.OrderRestaurant;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.repository.MenuRepository;
import com.online.hotel.arlear.repository.OrderRestaurantRepository;
import com.online.hotel.arlear.repository.ProductRepository;

@Service
public class OrderRestaurantService implements ServiceGeneric<OrderRestaurant> {

	@Autowired
	private OrderRestaurantRepository orderRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private ObjectConverter objectConverter;
	
	@Override
	public boolean create(OrderRestaurant entity) {
		
		entity.setPriceTotal(precio(entity.getProduct(),entity.getMenu()));
		orderRepository.save(entity);
		return true;
	}
	
	public boolean createOrderGuest(OrderRestaurant entity, Long id) {
		Reservation reservation=reservationService.findID(id);
		if(reservation!=null && reservation.getReservationStatus().equals(ReservationStatus.EN_CURSO)) {
			Ticket ticket=ticketService.findByTicketOpen(reservation.getContact().getDocumentNumber());
			if(ticket==null) {
				return false;
			}
			else {
				entity.setPriceTotal(precio(entity.getProduct(),entity.getMenu()));
				long idOrder= orderRepository.save(entity).getIdOrder();
				
				TransactiontDTO transaction= new TransactiontDTO();
				transaction.setDocument(reservation.getContact().getDocumentNumber());
				transaction.setAmount(entity.getPriceTotal());
				transaction.setElement(listElements(entity.getProduct(),entity.getMenu()));
				transaction.setDescription("Pedido Restaurant N°: "+idOrder);
				transaction.setSection(Section.RESTAURANTE);
				transaction.setDate(java.time.LocalDateTime.now());
				Transaction transactionModel = objectConverter.converter(transaction);
				ticket.getTransaction().add(transactionModel);
				ticketService.update(ticket);
				
				return true;
			}
		}
		else {
			return false;
		}
	}
	
	private String listElements(List<Product> product, List<Menu> menu) {
		String elements="";
		
		for (int x = 0; x < product.size(); x++) {
			Optional<Product> optionalProduct = productRepository.findById(product.get(x).getId());
			//Optional<Menu> optionalMenu = productRepository.findById(list.get(x).getId());
				 if(optionalProduct != null) {
					  elements=elements+" "+optionalProduct.get().getName()+": "+optionalProduct.get().getPrice().toString()+".";
				 }		 
		}
		 
		for (int y = 0; y < menu.size(); y++) {
				Optional<Menu> optionalMenu = menuRepository.findById(menu.get(y).getIdMenu());
					 if(optionalMenu != null) {
						  elements=elements+" "+optionalMenu.get().getNameMenu()+": "+optionalMenu.get().getPriceTotal().toString()+".";
					 }		 
		}
		
		return elements;
	}

	private Double precio(List<Product> product, List<Menu> menu) {
		Double finalPriceProduct = 0.0;
		Double finalPriceMenu = 0.0;
		Double finalPrice = 0.0;
		  
		for (int x = 0; x < product.size(); x++) {
			Optional<Product> optionalProduct = productRepository.findById(product.get(x).getId());
			//Optional<Menu> optionalMenu = productRepository.findById(list.get(x).getId());
				 if(optionalProduct != null) {
					  finalPriceProduct=optionalProduct.get().getPrice()+finalPriceProduct;
				 }		 
		}
		 
		for (int y = 0; y < menu.size(); y++) {
				Optional<Menu> optionalMenu = menuRepository.findById(menu.get(y).getIdMenu());
					 if(optionalMenu != null) {
						  finalPriceMenu=optionalMenu.get().getPriceTotal()+finalPriceMenu;
					 }		 
		}
		
		finalPrice=finalPriceProduct+finalPriceMenu;
		return finalPrice;
	}


	@Override
	public List<OrderRestaurant> find() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}
	
	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(OrderRestaurant entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public OrderRestaurant find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
