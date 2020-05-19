package com.online.hotel.arlear.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.repository.TicketRepository;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
		JasperReport jasperReport = JasperCompileManager.compileReport("factura" + File.separator + "ticket.jrxml");

		// Get your data source
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(find());

		// Add parameters
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("createdBy", "Websparrow.org");

		// Fill the report
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
				jrBeanCollectionDataSource);

		
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
