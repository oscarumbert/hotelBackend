package com.online.hotel.arlear.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.model.StructureItem;
import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.repository.TicketRepository;
import com.online.hotel.arlear.util.TicketStructure;

import net.sf.jasperreports.engine.JRException;
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
	private TicketStructure generateDataClient(Integer contact) {
		Ticket ticket = findByConctact(contact);
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
		return structure;
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

	public byte[]  generateReport(Integer contact,Integer accountNumber) throws IOException, JRException {
		
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile( "factura" + File.separator + "ticket.jasper" );

		Map<String, Object> parameters = new HashMap<>();
		TicketStructure structure = null;
		if(contact != null) {
			structure = generateDataClient(34567890);
		}else {
			structure = generateData(accountNumber);
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

		JasperPrint jasperPrint;
		File pdf = null;
		try {
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					new JRBeanCollectionDataSource(structure.getItems()));
			pdf = File.createTempFile("output.", ".pdf",new File("factura/")); 
			JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf)); 
			System.out.println("Done");
			
		} catch (JRException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return generatBytes(pdf);
	}
	
	private byte[] generatBytes(File file) throws IOException {

		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		byte[] buffer = null;
		try {
			buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
			}

			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}
		}
		return ous.toByteArray();

	}

}
