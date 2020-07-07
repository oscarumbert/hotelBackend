package com.online.hotel.arlear.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.enums.TicketStatus;

import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.repository.TicketRepository;
import com.online.hotel.arlear.util.StructureItem;
import com.online.hotel.arlear.util.TicketStructure;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class TicketService implements ServiceGeneric<Ticket>{
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Override
	public boolean create(Ticket entity) {
		return ticketRepository.save(entity) != null;
	}
	@Override
	public boolean update(Ticket entity) {
		return ticketRepository.save(entity) != null;
	}
	@Override
	public boolean delete(Long id) {
		if(find(id)==null) {
			return false;
		}
		else {
			ticketRepository.findById(id).get().getTransaction().clear();
			ticketRepository.deleteById(id);//
			return true;
		}
	}
	
	@Override
	public List<Ticket> find() {
		// TODO Auto-generated method stub
		return ticketRepository.findAll();
	}
	private TicketStructure generateDataClient(Integer idContact) {
		Ticket ticket = findByConctact(idContact);
		if(ticket!=null) {
			TicketStructure structure = new  TicketStructure();
			List<StructureItem> items = new ArrayList<StructureItem>();
			Double totalHotel = 0.0;
			Double totalRestaurant = 0.0;
			Double totalSaloon = 0.0;
			structure.setNumber(ticket.getIdTicket().toString());
			
			for(Transaction transaction: ticket.getTransaction()) {
				DateTimeFormatter format =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				if(transaction.getSection().toString().equals("HOTEL")) {
					items.add(new StructureItem(transaction.getSection().toString(),transaction.getElement(),transaction.getAmount(),transaction.getDate().format(format)));
					totalHotel = totalHotel+transaction.getAmount();
				}else if(transaction.getSection().toString().equals("RESTAURANTE")) {
					items.add(new StructureItem(transaction.getSection().toString(),transaction.getElement(),transaction.getAmount(),transaction.getDate().format(format)));
					totalRestaurant = totalRestaurant+transaction.getAmount();
				}else if(transaction.getSection().toString().equals("SALON")) {
					items.add(new StructureItem(transaction.getSection().toString(),transaction.getElement(),transaction.getAmount(),transaction.getDate().format(format)));
					totalSaloon = totalSaloon+transaction.getAmount();
				}
			}

			structure.setItems(items);
			structure.setSubtotalHotel(totalHotel);
			structure.setSubtotalRestaurant(totalRestaurant);
			structure.setSubtotalSaloon(totalSaloon);
			structure.setTotal(totalHotel+totalRestaurant+totalSaloon);
			structure.setSubsidiary(ticket.getSubsidiary());
			structure.setClientName(ticket.getContact().getSurname()+" "+ticket.getContact().getName());
			structure.setDocument(ticket.getContact().getDocumentNumber().toString());
			return structure;
		}
		else {
			return null;
		}
		
	}
	
	private TicketStructure generateData(Integer accountNumber) {
		List<Ticket> tickets = find();
		TicketStructure structure = new  TicketStructure();
		List<StructureItem> items = new ArrayList<StructureItem>();
		Double total = 0.0;
		structure.setNumber(accountNumber.toString());
		String section = "";
		
		if(accountNumber == 1) {
			section = "HOTEL";
		}else if(accountNumber == 2){
			section = "RESTAURANTE";
		}else {
			section = "SALON";
		}
		for(Ticket ticket: tickets ) {
			
			for(Transaction transaction: ticket.getTransaction()) {
				DateTimeFormatter format =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				if(transaction.getSection().toString().equals(section)) {
					items.add(new StructureItem(transaction.getSection().toString(),transaction.getElement(),transaction.getAmount(),transaction.getDate().format(format)));
					total = total+transaction.getAmount();
				}
			}
		}

		structure.setItems(items);
		structure.setTotal(total);
		structure.setSubsidiary(tickets.get(0).getSubsidiary());
		return structure;
	}
	
	public Ticket findByConctact(Integer idContact) {
		Optional<Ticket> optional = ticketRepository.findAll().stream().
											filter(p -> p.getContact().getId().toString().
														equals(idContact.toString()) && p.getStatus().equals(TicketStatus.ABIERTO)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Ticket findByConctactDocument(Integer Document) {
		Optional<Ticket> optional = ticketRepository.findAll().stream().filter(p -> p.getContact().getDocumentNumber().
									equals(Document)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Ticket findByTransaction(Transaction transaction) {
		Optional<Ticket> optional = ticketRepository.findAll().stream().filter(p -> p.getTransaction().contains(transaction)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public Ticket findByTicketOpen(Integer Document) {
		Optional<Ticket> optional = ticketRepository.findAll().stream().filter(p -> p.getContact().getDocumentNumber().
									equals(Document) && p.getStatus().equals(TicketStatus.ABIERTO)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	@Override
	public Ticket find(Long id) {
		Optional<Ticket> optional = ticketRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	public byte[]  generateReport(Integer contact,Integer accountNumber) throws IOException, JRException {
		
		JasperReport jasperReport = null;
		Map<String, Object> parameters = new HashMap<>();
		TicketStructure structure = null;
		if(contact != null) {
			structure = generateDataClient(contact);
			if(structure!=null) {
				System.out.println("****antes de poner ruta");
				jasperReport = (JasperReport) JRLoader.loadObjectFromFile( "factura" + File.separator + "ticket.jasper" );
				System.out.println("*****despues de obtener ruta");	
			}
			else {
				return null;
			}
			
		}
		else {
			structure = generateData(accountNumber);
			jasperReport = (JasperReport) JRLoader.loadObjectFromFile( "factura" + File.separator + "ticketContador.jasper" );

		}
		
		List<StructureItem> list = structure.getItems();
		
		for(StructureItem item : list)
			System.out.println(item.getSection());
    		list.sort(new Comparator<StructureItem>() {

			@Override
			public int compare(StructureItem item1, StructureItem item2) {
				return item1.getSection().compareTo(item2.getSection());
			}
			
		});

    	for(StructureItem item : list)
			System.out.println(item.getSection());
		
		list.add(new StructureItem("TOTAL","",structure.getTotal(),""));
		parameters.put("createdBy", "Websparrow.org");
		parameters.put("total",structure.getTotal() );
		parameters.put("ticket",structure.getNumber());
		parameters.put("subsidiary",structure.getSubsidiary());
		
		if(contact != null) {
			parameters.put("name",structure.getClientName().toUpperCase());
			parameters.put("document",structure.getDocument());
		}

		JasperPrint jasperPrint;
		byte[] report =null;
		
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					new JRBeanCollectionDataSource(structure.getItems()));
			report = JasperExportManager.exportReportToPdf(jasperPrint);
		
		return report;
	}
}
