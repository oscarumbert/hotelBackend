package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.online.hotel.arlear.enums.TicketStatus;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.OrderRestaurant;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.repository.MenuRepository;
import com.online.hotel.arlear.repository.ProductRepository;
import com.online.hotel.arlear.repository.TransactionRepository;

@Service
public class TransactionService implements ServiceGeneric<Transaction>{

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Override
	public boolean create(Transaction entity) {
		return transactionRepository.save(entity) != null;
	}

	@Override
	public boolean update(Transaction entity) {
		Transaction transaction = findID(entity.getId());
		transaction.setAmount(entity.getAmount());
		transaction.setDate(entity.getDate());
		transaction.setDescription(entity.getDescription());
		transaction.setElement(entity.getElement());
		transaction.setSection(entity.getSection());
		return transactionRepository.save(transaction) != null;

	}
	
	public boolean updateTransaccionTicket(String orderDescription, OrderRestaurant entity, Long idReservation) {
		
		Ticket ticket=ticketService.findByTicketOpen(reservationService.findID(idReservation).getContact().getDocumentNumber());
		if(ticket!=null) {
			Ticket ticketNew=findDescription(ticket,orderDescription,entity);
			if(ticketNew!=null) {
				ticketService.update(ticketNew);
				return true;
			}
			else {
				return false;
			}
			
		}
		else {
			return false;
		}	
	}
	
	public boolean deleteTransactionTicket(OrderRestaurant order) {
		String orderDescription="Pedido Restaurant N°: "+order.getIdOrder();
		Transaction transaction=findByDescription(orderDescription);
		if(transaction!=null) {
			Ticket ticket=ticketService.findByTransaction(transaction);
			if(ticket!=null && ticket.getStatus().equals(TicketStatus.ABIERTO)) {
				Ticket ticketFinal=deleteTransaction(ticket,transaction);
				ticketService.update(ticketFinal);
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	private Ticket deleteTransaction(Ticket ticket, Transaction transaction) {
		if(ticket!=null) {
			for (int y = 0; y < ticket.getTransaction().size(); y++) {
				if(ticket.getTransaction().get(y).equals(transaction)) {
					ticket.getTransaction().remove(y);
				}
			}
			transactionRepository.deleteById(transaction.getId());
			return ticket;
		}
		else {
			return null;
		}
	}
	
	private Transaction findByDescription(String orderDescription) {
		Optional<Transaction> optional=transactionRepository.findAll().stream().filter(p ->
								p.getDescription().equals(orderDescription)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	private Ticket findDescription(Ticket ticket, String orderDescription, OrderRestaurant entity) {
		Transaction transaction=null;
		
		for (int y = 0; y < ticket.getTransaction().size(); y++) {
			if(ticket.getTransaction().get(y).getDescription().equals(orderDescription)) {
				transaction=ticket.getTransaction().get(y);
				ticket.getTransaction().get(y).setAmount(entity.getPriceTotal());
				ticket.getTransaction().get(y).setElement(listElements(entity.getProduct(),entity.getMenu()));
				ticket.getTransaction().get(y).setDate(java.time.LocalDateTime.now());
			}
		}
		
		if(transaction!=null) {
			return ticket;
		}else {
			return null;
		}
	}

	private String listElements(List<Product> product, List<Menu> menu) {
		String elements="";
		
		for (int x = 0; x < product.size(); x++) {
			Optional<Product> optionalProduct = productRepository.findById(product.get(x).getId());
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
	
	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		transactionRepository.deleteById(id);
		return false;
	}

	@Override
	public List<Transaction> find() {
		// TODO Auto-generated method stub
		return transactionRepository.findAll();
	}

	public Transaction findID(Long id) {
		// TODO Auto-generated method stub
		Optional<Transaction> optional = transactionRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
		
	}
	
	public Transaction findByNumberSection(Integer numberSection) {
		// TODO Auto-generated method stub
		Long parseo = Long.parseLong(numberSection.toString());
		Optional<Transaction> optional = transactionRepository.findAll().stream()
																		.filter(p -> p.getNumberSection().equals(parseo)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
		
	}
	
	@Override
	public Transaction find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
