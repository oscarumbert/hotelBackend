package com.online.hotel.arlear.service;

import java.io.File;
import java.time.LocalDate;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.dto.ReservationOpenDTO;
import com.online.hotel.arlear.enums.ReservationStatus;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomCategory;
import com.online.hotel.arlear.enums.RoomStatus;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Guest;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.model.Room;
import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.repository.ReservationRepository;
import com.online.hotel.arlear.repository.RoomRepository;
import com.online.hotel.arlear.util.ReservationStructure;
import com.online.hotel.arlear.util.StructureItem;
import com.online.hotel.arlear.util.StructureReport;
import com.online.hotel.arlear.util.TicketStructure;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class ReservationService implements ServiceGeneric<Reservation> {
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private TicketService ticketService;
	
	
	@Override
	public boolean create(Reservation entity) {
		return reservationRepository.save(entity) != null;

	}

	public Long createReservation(Reservation entity) {
		entity.setReservationStatus(ReservationStatus.EN_ESPERA);
		return reservationRepository.save(entity).getId();

	}
	
	public void setRoomReservation(Reservation reserv) {
		reservationRepository.save(reserv);		
	}

	public boolean update(Long id, Reservation entity) {
		if(find(id).equals(null)){
			return false;
		}
		else {
			
			Reservation reservation= find(id);
			//reservation.setRoom(entity.getRoom());
			reservation.setBeginDate(entity.getBeginDate());
			reservation.setEndDate(entity.getEndDate());
			reservation.setAdultsCuantity(entity.getAdultsCuantity());
			reservation.setChildsCuantity(entity.getChildsCuantity());
			reservation.setReservationType(entity.getReservationType());
			reservation.setAditionals(entity.getAditionals());
			//reservation.setContact(entity.getContact());
			reservation.setPrice(entity.getPrice());
			reservation.setSign(entity.getSign());
			//reservation.setPromotion(entity.getPromotion());
			reservationRepository.save(reservation);
			return true;
		}
	}
	

	@Override
	public boolean delete(Long id) {
		if(findID(id)==null) {
			return false;
		}
		else {
			reservationRepository.deleteById(id);
			return true;
		}
	}

	@Override
	public List<Reservation> find() {
		// TODO Auto-generated method stub
		System.out.println("entro");
		return reservationRepository.findAll();
	}

	/*public List<Reservation> FilterReservationIdDate(Reservation reserv) {
		if(reserv.getBeginDate()==null && reserv.getId()!=null ) {
			return findIDElements(reserv.getId());
			
		}
		else if(reserv.getBeginDate()!=null && reserv.getId()==null) {
			return findBeingDates(reserv.getBeginDate().toString());
		}
		else if(reserv.getBeginDate()!= null && reserv.getId()!=null) {
			return findReservation(reserv.getId(),reserv.getBeginDate());
		}
		return null;
	}*/
	
	public List<Reservation> FilterReservationDates(Reservation reserv) {
		if(reserv.getBeginDate()!=null && reserv.getEndDate()!=null ) {
			return findDataRange(reserv.getBeginDate(),reserv.getEndDate());
			
		}		
		return null;
	}

	private List<Reservation> findDataRange(LocalDate beginDate, LocalDate endDate) {
		return reservationRepository.findAll().stream().filter(
				p -> p.getBeginDate().isAfter(beginDate) && p.getEndDate().isBefore(endDate)).collect(Collectors.toList());		
	}

	/*private List<Reservation> findReservation(Long id, LocalDate beginDate) {
		return reservationRepository.findAll().stream().filter(p -> (p.getId().equals(id) && p.getBeginDate().equals(beginDate))).collect(Collectors.toList());
	}
	private List<Reservation> findBeingDates(String beginDate) {
		return reservationRepository.findAll().stream().filter(
				p -> p.getBeginDate().equals(beginDate)).collect(Collectors.toList());
	}
	private List<Reservation> findIDElements(Long id) {
		return reservationRepository.findAll().stream().filter(
				p -> p.getId().equals(id)).collect(Collectors.toList());
	}*/

	public Reservation findID(Long id) {
		Optional<Reservation> optional = reservationRepository.findAll().stream().filter(p -> p.getId().equals(id)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	@Override
	public boolean update(Reservation entity) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean update(Contact entity,Long id) {
		// TODO Auto-generated method stub
		Contact ContactExist=contactService.findDuplicate(entity.getDocumentNumber(), entity.getGender(), entity.getDocumentType());
		Contact ContactActual=entity;
		
		Reservation reservation = findID(id);
		
		if(ContactExist!=null) {
			if(!ContactExist.getName().equals(ContactActual.getName()) || !ContactExist.getSurname().equals(ContactActual.getSurname())) {
				return false;
			}
			else {
				if(ContactActual.getCard().getCardNumber().equals(ContactExist.getCard().getCardNumber()) &&
					!ContactActual.getCard().getCodeSecurity().equals(ContactExist.getCard().getCodeSecurity())) {
					return false;
				}
				else {
					
						/*Ticket ticketOne=ticketService.findByConctactDocument(ContactExist.getDocumentNumber());
						if(ticketOne!=null) {
							ticketService.delete(ticketOne.getIdTicket());
						}*/
						ContactExist.getCard().setCardNumber(ContactActual.getCard().getCardNumber());
						ContactExist.getCard().setCardType(ContactActual.getCard().getCardType());
						ContactExist.getCard().setCodeSecurity(ContactActual.getCard().getCodeSecurity());
						ContactExist.getCard().setExpirationDate(ContactActual.getCard().getExpirationDate());
						ContactExist.getCard().setNameOwner(ContactActual.getCard().getNameOwner());
						contactService.update(ContactExist);
						reservation.setContact(ContactExist);
						reservationRepository.save(reservation);
						return true;
					}
			}
		}
		else {
			reservation.setContact(ContactActual);
			 reservationRepository.save(reservation);
			 return true;
		}
	}
	
	public boolean update(List<Guest> entities,Long id) {
		// TODO Auto-generated method stub
		Reservation reservation = find(id);
		reservation.setGuests(entities);
		return reservationRepository.save(reservation)!=null;
	}
	
	

	@Override
	public Reservation find(Long id) {
		// TODO Auto-generated method stub
		Optional<Reservation> optional = reservationRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	/*public boolean CheckIn(Reservation reserva, Double debt) {
		if(verificateCheckIn(reserva.getId(),debt)) {
			return true;
		}
		return false;
	}*/
	
	public boolean verificateCheckIn(Long id, Double debt) {
		Optional<Reservation> optional = reservationRepository.findById(id);
		if(optional.isPresent()) {
			Double sign=optional.get().getSign();
			//Double priceTotal= optional.get().getPrice();
			Reservation reserva=optional.get();
			Room room=roomRepository.findById(reserva.getRoom().getId()).get();
			reserva.setSign(debt+sign);
			reserva.setReservationStatus(ReservationStatus.EN_CURSO);
			room.setRoomStatus(RoomStatus.OCUPADA);
			reservationRepository.save(reserva);
			roomRepository.save(room);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean verificateCheckOut(Long id) {
		Optional<Reservation> optional = reservationRepository.findById(id);
		if(optional.isPresent()) {
			Reservation reserva=optional.get();
			Room room=roomRepository.findById(reserva.getRoom().getId()).get();
			reserva.setReservationStatus(ReservationStatus.CERRADA);
			room.setRoomStatus(RoomStatus.DISPONIBLE);
			reservationRepository.save(reserva);
			roomRepository.save(room);
			return true;
		}
		else {
			return false;
		}
	}

	public List<Reservation> FindReservationOpen(){
		
		List<Reservation> listOne= ReservationOpen(reservationRepository.findAll());
		
		if(!listOne.isEmpty()) {
			return listOne;
		}
		else {
			return null;
		}		
	}
	
	private List<Reservation> ReservationOpen(List<Reservation> reservation) {
		List<Reservation> reservationOpen=new ArrayList<Reservation>();
		
		for(int i=0;i<reservation.size();i++) {
			if(reservation.get(i).getReservationStatus().equals(ReservationStatus.EN_CURSO) && reservation.get(i).getRoom().getRoomStatus().equals(RoomStatus.OCUPADA)) {
				reservationOpen.add(reservation.get(i));
			}
		}

		if(!reservationOpen.isEmpty()) {
			return reservationOpen;
		}
		else {
			return reservationOpen;
		}
	}
	public byte[] creatReport(String type, LocalDate beginDate, LocalDate endDate) throws JRException {
		JasperReport jasperReport = null;
		//Map<String, Object> parameters = new HashMap<>();
		
		jasperReport = (JasperReport) JRLoader.loadObjectFromFile("factura" + File.separator + "reporte.jasper");

		ReservationStructure reservationStructure = null;
		
		if (type.equals("DAY")) {
			reservationStructure = generateData(beginDate, endDate);

		} else if (type.equals("WEEK")) {
			reservationStructure = generateDataWeek(beginDate, endDate);

		} else {
			reservationStructure = generateDataMonth(beginDate, endDate);
			jasperReport = (JasperReport) JRLoader
					.loadObjectFromFile("factura" + File.separator + "reporteMonth.jasper");

		}
		JasperPrint jasperPrint;
		byte[] report = null;

		jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(),
				new JRBeanCollectionDataSource(reservationStructure.getStructureReports()));
		
		report = JasperExportManager.exportReportToPdf(jasperPrint);

		return report;
	}

	private ReservationStructure generateData(LocalDate beginDate, LocalDate endDate) {

		ReservationStructure reservationStructure = new ReservationStructure();
		List<StructureReport> structureReports = new ArrayList<StructureReport>();
		StructureReport structureReport = null;
		List<Reservation> reservations = findDataRange(beginDate, endDate);

		
		reservations.sort(new Comparator<Reservation>() {

			@Override
			public int compare(Reservation item1, Reservation item2) {
				int resultado = item1.getBeginDate().compareTo(item2.getBeginDate());
				if (resultado != 0) {
					return resultado;
				}

				resultado = item1.getRoom().getCategory().compareTo(item2.getRoom().getCategory());
				if (resultado != 0) {
					return resultado;
				}

				return resultado;
			}

		});
		
		structureReports = loadStructure(reservations,true,structureReports);
		structureReports = loadStructure(reservations,false,structureReports);

		structureReports.sort(new Comparator<StructureReport>() {

			@Override
			public int compare(StructureReport item1, StructureReport item2) {
				return item1.getDate().compareTo(item2.getDate());
			}

		});
		//structureReports = structureReports.stream().distinct().collect(Collectors.toList());

		structureReports.stream().filter(p -> p.isDuplicate()).forEach(p -> {
			p.setEntryCount(0);
			p.setValueTotal(0);
		});

		List<String> categories = Arrays.asList("BRONCE","PLATA","ORO");
		for(String categoryTotal: categories) {
			structureReport = new StructureReport();
			structureReport.setEgressCount(structureReports.stream()
					.filter(p -> p.getCategory().equals(categoryTotal))
					.mapToInt(StructureReport::getEgressCount)
					.sum());
			
			structureReport.setEntryCount(structureReports.stream()
					  .filter(p -> p.getCategory().equals(categoryTotal))
					  .mapToInt(StructureReport::getEntryCount)
					  .sum());
			structureReport.setDate("");
			structureReport.setCategory(categoryTotal);
			structureReport.setValueTotal(structureReports.stream()
								  .filter(p -> p.getCategory().equals(categoryTotal))
								  .mapToInt(StructureReport::getValueTotal)
								  .sum());
			structureReport.setAgrupation("TOTAL");
			structureReport.setDuplicate(false);;
			structureReports.add(structureReport);
		}
	
		reservationStructure.setStructureReports(structureReports);
		return reservationStructure;
	}
	
	private List<StructureReport> loadStructure(List<Reservation> reservations,boolean entries,
			List<StructureReport> structureReports){
		
		LocalDate dateActualy = null;
		String categoryActualy = null;
		LocalDate dateReservationcompare= null;
		
		int egressCategory = 0;
		int entryCategory = 0;
		Integer total = 0;
		int index = 1;
		
		StructureReport structureReport = null;
		
		for (Reservation reservation : reservations) {
			
			dateReservationcompare = entries?reservation.getBeginDate():reservation.getEndDate();
			
			if (dateActualy == null) {
				dateActualy = dateReservationcompare;
				categoryActualy = reservation.getRoom().getCategory().toString();
			}

			if (!dateActualy.equals(dateReservationcompare) || 
				!categoryActualy.equals(reservation.getRoom().getCategory().toString())) {

				structureReport = new StructureReport();
				structureReport.setEgressCount(egressCategory);
				structureReport.setEntryCount(entryCategory);
				structureReport.setDate(dateActualy.toString());
				structureReport.setCategory(categoryActualy);
				structureReport.setValueTotal(total);
				structureReport.setAgrupation("DIA");
				structureReport.setDuplicate(entries?false:true);
				
				if(entries) {
					structureReports.add(structureReport);
				}else {
					boolean exist = false;
					for (StructureReport structureReportLocal : structureReports) {
						if (structureReportLocal.getDate().equals(dateActualy) && 
							structureReportLocal.getCategory().equals(categoryActualy)) {
							
							structureReportLocal.setEgressCount(structureReportLocal.getEgressCount() + egressCategory);
							exist=true;
						}
					}
					if(!exist) {
						structureReports.add(structureReport);
					}

				}

				
				entryCategory = 1;
				
				egressCategory = entries ? 0 : 1;
				total = reservation.getRoom().getPrice();
				categoryActualy = reservation.getRoom().getCategory().toString();
				dateActualy = dateReservationcompare;

			} else {
				if (reservation.getBeginDate().equals(dateActualy)) {
					entryCategory++;
					total = total + reservation.getRoom().getPrice();
				}
				if (reservation.getEndDate().equals(dateActualy)) {
					egressCategory++;

				}

			}

			if (index == reservations.size()) {
				structureReport = new StructureReport();
				structureReport.setEgressCount(egressCategory);
				structureReport.setEntryCount(entryCategory);
				structureReport.setDate(dateActualy.toString());
				structureReport.setCategory(categoryActualy);
				structureReport.setValueTotal(total);
				structureReport.setAgrupation("DIA");
				structureReport.setDuplicate(entries?false:true);
				if(entries) {
					structureReports.add(structureReport);
				}else {
					boolean exist = false;
					for (StructureReport structureReportLocal : structureReports) {
						if (structureReportLocal.getDate().equals(dateActualy) && 
							structureReportLocal.getCategory().equals(categoryActualy)) {
							
							structureReportLocal.setEgressCount(structureReportLocal.getEgressCount() + egressCategory);
							exist=true;
						}
					}
					if(!exist) {
						structureReports.add(structureReport);
					}

				}

			}
			index++;

		}
		return structureReports;
	}
	private List<StructureReport> loadStructureWeek(List<Reservation> reservations,boolean entries,LocalDate beginDate,
													List<StructureReport> structureReports){
		
		LocalDate dateBeginWeek = beginDate;
		LocalDate dateEndWeek = beginDate.plusDays(7);
		String categoryActualy = null;
		LocalDate dateReservationcompare= null;
		
		int egressCategory = 0;
		int entryCategory = 0;
		Integer total = 0;
		int index = 1;
		
		StructureReport structureReport = null;
		
		for (Reservation reservation : reservations) {
			
			dateReservationcompare = entries?reservation.getBeginDate():reservation.getEndDate();

			if (categoryActualy == null) {
				categoryActualy = reservation.getRoom().getCategory().toString();
			}

			if (dateEndWeek.isBefore(dateReservationcompare)// || dateEndWeek.isEqual(dateReservationcompare)
					|| !categoryActualy.equals(reservation.getRoom().getCategory().toString())) {

				structureReport = new StructureReport();
				structureReport.setEgressCount(egressCategory);
				structureReport.setEntryCount(entryCategory);
				structureReport.setDate(dateBeginWeek.toString());
				structureReport.setCategory(categoryActualy);
				structureReport.setValueTotal(total);
				
				structureReport.setAgrupation("DIA");
				structureReport.setDuplicate(entries?false:true);
				if(entries) {
					structureReports.add(structureReport);
				}else {
					boolean exist = false;
					if(entries) {
						//solucionar fix
						for (StructureReport structureReportLocal : structureReports) {
							if (structureReportLocal.getDate().equals(dateBeginWeek) && 
								structureReportLocal.getCategory().equals(categoryActualy)) {
								structureReportLocal.setEgressCount(structureReportLocal.getEgressCount() + egressCategory);
								exist=true;
							}
						}
					}else {
						for (StructureReport structureReportLocal : structureReports) {
							if (structureReportLocal.getDate().equals(dateEndWeek) && 
								structureReportLocal.getCategory().equals(categoryActualy)) {
								structureReportLocal.setEgressCount(structureReportLocal.getEgressCount() + egressCategory);
								exist=true;
							}
						}
					}
					
					if(!exist) {
						structureReports.add(structureReport);
					}

				}
				
				entryCategory = 1;
				egressCategory = entries ? 0 : 1;
				total = reservation.getRoom().getPrice();
				categoryActualy = reservation.getRoom().getCategory().toString();
				
				if(entries) {
					while(dateEndWeek.isBefore(reservation.getBeginDate()) || dateEndWeek.equals(reservation.getBeginDate())) {
						dateBeginWeek = dateBeginWeek.plusDays(7);
						dateEndWeek = dateEndWeek.plusDays(7);
					}
				}else {
					while (dateEndWeek.isBefore(reservation.getEndDate()) || dateEndWeek.equals(reservation.getEndDate())) {
						dateBeginWeek = dateBeginWeek.plusDays(7);
						dateEndWeek = dateEndWeek.plusDays(7);
					}
				}
				

			} else {
				if (reservation.getBeginDate().isBefore(dateEndWeek)) {
					entryCategory++;
					total = total + reservation.getRoom().getPrice();
				}
				if (reservation.getEndDate().isBefore(dateEndWeek)) {
					egressCategory++;

				}

			}

			if (index == reservations.size()) {
				structureReport = new StructureReport();
				structureReport.setEgressCount(egressCategory);
				structureReport.setEntryCount(entryCategory);
				structureReport.setDate(dateBeginWeek.toString());
				structureReport.setCategory(categoryActualy);
				structureReport.setValueTotal(total);
				structureReport.setAgrupation("DIA");
				structureReport.setDuplicate(entries?false:true);
				if(entries) {
					structureReports.add(structureReport);
				}else {
					boolean exist = false;
					for (StructureReport structureReportLocal : structureReports) {
						if (structureReportLocal.getDate().equals(dateBeginWeek) && 
							structureReportLocal.getCategory().equals(categoryActualy)) {
							structureReportLocal.setEgressCount(structureReportLocal.getEgressCount() + egressCategory);
							exist=true;
						}
					}
					if(!exist) {
						structureReports.add(structureReport);
					}

				}
			}
			index++;

		}
		return structureReports;
	}
	

	private ReservationStructure generateDataWeek(LocalDate beginDate, LocalDate endDate) {
		List<Reservation> reservations = findDataRange(beginDate, endDate);
		ReservationStructure reservationStructure = new ReservationStructure();
		List<StructureReport> structureReports = new ArrayList<StructureReport>();
		LocalDate dateBeginWeek = beginDate;
		LocalDate dateEndWeek = beginDate.plusDays(7);
		StructureReport structureReport = null;
		
		while(dateEndWeek.isBefore(endDate)) {
			
			for (Reservation reservation : reservations) {
				if(dateBeginWeek.isBefore(reservation.getBeginDate()) && dateEndWeek.isAfter(reservation.getBeginDate())) {
					reservation.setBeginDate(dateBeginWeek.plusDays(1));
				}
				if(dateBeginWeek.isBefore(reservation.getEndDate()) && dateEndWeek.isAfter(reservation.getEndDate())) {
					reservation.setEndDate(dateBeginWeek.plusDays(1));
				}
			}
			dateBeginWeek = dateBeginWeek.plusDays(7);
			dateEndWeek = dateEndWeek.plusDays(7);
		}
	
		dateBeginWeek = beginDate;
		dateEndWeek = beginDate.plusDays(7);
		
		reservations.sort(new Comparator<Reservation>() {

			@Override
			public int compare(Reservation item1, Reservation item2) {
				int resultado = item1.getBeginDate().compareTo(item2.getBeginDate());
				if (resultado != 0) {
					return resultado;
				}

				resultado = item1.getRoom().getCategory().compareTo(item2.getRoom().getCategory());
				if (resultado != 0) {
					return resultado;
				}

				return resultado;
			}

		});
		
		structureReports = loadStructureWeek(reservations,true,beginDate,structureReports);
		structureReports = loadStructureWeek(reservations,false,beginDate,structureReports);

		structureReports.sort(new Comparator<StructureReport>() {

			@Override
			public int compare(StructureReport item1, StructureReport item2) {
				return item1.getDate().compareTo(item2.getDate());
			}

		});
		structureReports.stream().filter(p -> p.isDuplicate()).forEach(p -> {
			p.setEntryCount(0);
			p.setValueTotal(0);
		});

		List<String> categories = Arrays.asList("BRONCE","PLATA","ORO");
		for(String categoryTotal: categories) {
			structureReport = new StructureReport();
			structureReport.setEgressCount(structureReports.stream()
					.filter(p -> p.getCategory().equals(categoryTotal))
					.mapToInt(StructureReport::getEgressCount)
					.sum());
			
			structureReport.setEntryCount(structureReports.stream()
					  .filter(p -> p.getCategory().equals(categoryTotal))
					  .mapToInt(StructureReport::getEntryCount)
					  .sum());
			structureReport.setDate("");
			structureReport.setCategory(categoryTotal);
			structureReport.setValueTotal(structureReports.stream()
								  .filter(p -> p.getCategory().equals(categoryTotal))
								  .mapToInt(StructureReport::getValueTotal)
								  .sum());
			structureReport.setAgrupation("TOTAL");
			structureReport.setDuplicate(false);;
			structureReports.add(structureReport);
		}

		reservationStructure.setStructureReports(structureReports);
		return reservationStructure;
	}

	private ReservationStructure generateDataMonth(LocalDate beginDate, LocalDate endDate) {

		ReservationStructure reservationStructure = new ReservationStructure();
		List<StructureReport> structureReports = new ArrayList<StructureReport>();
		StructureReport structureReport = null;
		List<Reservation> reservations = findDataRange(beginDate, endDate);
		
		LocalDate dateBeginMonth = beginDate;
		LocalDate dateEndMonth = beginDate.plusMonths(1);
		String categoryActualy = null;
		
		int egressCategory = 0;
		int entryCategory = 0;
		int index = 1;
		Integer total = 0;

		for (Reservation reservation : reservations) {
			reservation.setBeginDate(
					LocalDate.of(reservation.getBeginDate().getYear(), reservation.getBeginDate().getMonth(), 1));
			reservation.setEndDate(
					LocalDate.of(reservation.getBeginDate().getYear(), reservation.getBeginDate().getMonth(), 1));

		}

		reservations.sort(new Comparator<Reservation>() {
			@Override
			public int compare(Reservation item1, Reservation item2) {
				int resultado = item1.getBeginDate().compareTo(item2.getBeginDate());
				if (resultado != 0) {
					return resultado;
				}

				resultado = item1.getRoom().getCategory().compareTo(item2.getRoom().getCategory());
				if (resultado != 0) {
					return resultado;
				}

				return resultado;
			}
		});

		for (Reservation reservation : reservations) {

			if (categoryActualy == null) {
				categoryActualy = reservation.getRoom().getCategory().toString();
			}

			if (dateEndMonth.isBefore(reservation.getBeginDate())
					|| !categoryActualy.equals(reservation.getRoom().getCategory().toString())) {

				structureReport = new StructureReport();
				structureReport.setEgressCount(egressCategory);
				structureReport.setEntryCount(entryCategory);
				structureReport.setDate(dateBeginMonth.toString());
				structureReport.setCategory(categoryActualy);
				structureReport.setValueTotal(total);
				structureReport.setAgrupation("MES");
				structureReport.setDuplicate(false);
				
				structureReports.add(structureReport);

				entryCategory = 1;
				egressCategory = 1;

				total = reservation.getRoom().getPrice();
				categoryActualy = reservation.getRoom().getCategory().toString();
				if (dateEndMonth.isBefore(reservation.getBeginDate())) {
					dateBeginMonth = dateBeginMonth.plusMonths(1);
					dateEndMonth = dateEndMonth.plusMonths(1);
				}

			} else {
				if (reservation.getBeginDate().isBefore(dateEndMonth)) {
					entryCategory++;
					egressCategory++;
					total = total + reservation.getRoom().getPrice();
				}
				if (reservation.getEndDate().isBefore(dateEndMonth)) {

				}

			}

			if (index == reservations.size()) {
				structureReport = new StructureReport();
				structureReport.setEgressCount(egressCategory);
				structureReport.setEntryCount(entryCategory);
				structureReport.setDate(dateBeginMonth.toString());
				structureReport.setCategory(categoryActualy);
				structureReport.setValueTotal(total);
				structureReports.add(structureReport);
				structureReport.setAgrupation("MES");
				structureReport.setDuplicate(false);

			}
			
			index++;

		}
		structureReports.sort(new Comparator<StructureReport>() {

			@Override
			public int compare(StructureReport item1, StructureReport item2) {
				return item1.getDate().compareTo(item2.getDate());
			}

		});
		structureReports.stream().filter(p -> p.isDuplicate()).forEach(p -> p.setEntryCount(0));

		
		List<String> categories = Arrays.asList("BRONCE","PLATA","ORO");
		for(String categoryTotal: categories) {
			structureReport = new StructureReport();
			structureReport.setEgressCount(structureReports.stream()
															.filter(p -> p.getCategory().equals(categoryTotal))
															.mapToInt(StructureReport::getEgressCount)
															.sum());
			
			structureReport.setEntryCount(structureReports.stream()
														  .filter(p -> p.getCategory().equals(categoryTotal))
														  .mapToInt(StructureReport::getEntryCount)
														  .sum());
			structureReport.setDate("");
			structureReport.setCategory(categoryTotal);
			structureReport.setValueTotal(structureReports.stream()
														  .filter(p -> p.getCategory().equals(categoryTotal))
														  .mapToInt(StructureReport::getValueTotal)
														  .sum());
			structureReport.setAgrupation("TOTAL");
			structureReport.setDuplicate(false);;
			structureReports.add(structureReport);
		}

		reservationStructure.setStructureReports(structureReports);
		return reservationStructure;
	}

	private boolean verificateTotalPrice(Double price, Double sign, Double debt) {
		if (debt == 0.0 && price == sign) {
			return true;
		} else if (price == (sign + debt)) {
			return true;
		} else {
			return false;
		}
	}

}
