package com.online.hotel.arlear.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.model.StructureItem;
import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.repository.TicketRepository;
import com.online.hotel.arlear.util.TicketStructure;

import net.sf.jasperreports.engine.JasperCompileManager;
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
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<Ticket> find() {
		// TODO Auto-generated method stub
		return ticketRepository.findAll();
	}
	private TicketStructure generateData() {
		Ticket ticket = findByConctact(34567890);
		TicketStructure structure = new  TicketStructure();
		/*List<StructureItem> ItemsHotel = new ArrayList<StructureItem>();
		List<StructureItem> ItemsRestaurant = new ArrayList<StructureItem>();
		List<StructureItem> ItemsSaloon = new ArrayList<StructureItem>();
		Double totalHotel = 0.0;
		Double totalRestaurant = 0.0;
		Double totalSaloon = 0.0;
		for(Transaction transaction: ticket.getTransaction()) {
			
			if(transaction.getSection().equals("HOTEL")) {
				ItemsHotel.add(new StructureItem(transaction.getElement(),transaction.getAmount(),transaction.getDate()));
				totalHotel = totalHotel+transaction.getAmount();
			}else if(transaction.getSection().equals("RESTAURANTE")) {
				ItemsRestaurant.add(new StructureItem(transaction.getElement(),transaction.getAmount(),transaction.getDate()));
				totalRestaurant = totalRestaurant+transaction.getAmount();
			}else if(transaction.getSection().equals("SALON")) {
				ItemsSaloon.add(new StructureItem(transaction.getElement(),transaction.getAmount(),transaction.getDate()));
				totalSaloon = totalSaloon+transaction.getAmount();
			}
		}
		structure.setItemsHotel(ItemsHotel);
		structure.setItemsRestaurant(ItemsRestaurant);
		structure.setItemsSaloon(ItemsSaloon);
		structure.setTotalItemsHotel(totalHotel);
		structure.setTotalItemsRestaurant(totalRestaurant);
		structure.setTotalItemsSaloon(totalSaloon);*/
		List<StructureItem> items = new ArrayList<StructureItem>();
		Double totalHotel = 0.0;
		Double totalRestaurant = 0.0;
		Double totalSaloon = 0.0;
		for(Transaction transaction: ticket.getTransaction()) {
			
			if(transaction.getSection().toString().equals("HOTEL")) {
				items.add(new StructureItem(transaction.getSection().toString(),transaction.getElement(),transaction.getAmount(),transaction.getDate()));
				totalHotel = totalHotel+transaction.getAmount();
			}else if(transaction.getSection().toString().equals("RESTAURANTE")) {
				items.add(new StructureItem(transaction.getSection().toString(),transaction.getElement(),transaction.getAmount(),transaction.getDate()));
				totalRestaurant = totalRestaurant+transaction.getAmount();
			}else if(transaction.getSection().toString().equals("SALON")) {
				items.add(new StructureItem(transaction.getSection().toString(),transaction.getElement(),transaction.getAmount(),transaction.getDate()));
				totalSaloon = totalSaloon+transaction.getAmount();
			}
		}

		structure.setItems(items);
		structure.setSubtotalHotel(totalHotel);
		structure.setSubtotalRestaurant(totalRestaurant);
		structure.setSubtotalSaloon(totalSaloon);
		structure.setTotal(totalHotel+totalRestaurant+totalSaloon);
		return structure;
	}
	public Ticket findByConctact(Integer document) {
		
		
		Optional<Ticket> optional = ticketRepository.findAll().stream().
															   filter(p -> p.getContact().
																	   		 getDocumentNumber().
																	   		 equals(document)).
															   findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	@Override
	public Ticket find(Long id) {
				return null;
	}

	public File  generateReport() {
		try {
		

		// Compile the Jasper report from .jrxml to .japser
		//JasperReport jasperReport = JasperCompileManager.compileReport("factura" + File.separator + "ticket.jrxml");
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile( "factura" + File.separator + "ticket.jasper" );

		// Get your data source
		//JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(generateData());

		// Add parameters
		Map<String, Object> parameters = new HashMap<>();

		
		TicketStructure structure= generateData();
		parameters.put("createdBy", "Websparrow.org");
		parameters.put("total",structure.getTotal() );
		// Fill the report
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
				new JRBeanCollectionDataSource(structure.getItems()));


		
		File pdf = File.createTempFile("output.", ".pdf"); 
		JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf)); 
		// Export the report to a PDF file
			/*
			 * JasperExportManager.exportReportToPdfFile(jasperPrint,"factura" +
			 * File.separator + "ticket.pdf"); File archivo = new File("factura" +
			 * File.separator + "ticket.pdf");
			 */
		System.out.println("Done");
		
		

		return pdf;

	} catch (Exception e) {
		e.printStackTrace();
	}
		return null;
	}

}
