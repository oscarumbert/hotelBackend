package com.online.hotel.arlear.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.online.hotel.arlear.enums.CardType;
import com.online.hotel.arlear.enums.DocumentType;
import com.online.hotel.arlear.enums.GenderType;
import com.online.hotel.arlear.enums.MenuState;
import com.online.hotel.arlear.enums.MenuType;
import com.online.hotel.arlear.enums.ProductAvailability;
import com.online.hotel.arlear.enums.ProductType;
import com.online.hotel.arlear.enums.ReservationStatus;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomAditionals;
import com.online.hotel.arlear.enums.RoomCategory;
import com.online.hotel.arlear.enums.RoomStatus;
import com.online.hotel.arlear.enums.RoomType;
import com.online.hotel.arlear.enums.Section;
import com.online.hotel.arlear.enums.TicketStatus;
import com.online.hotel.arlear.enums.UserType;
import com.online.hotel.arlear.model.Address;
import com.online.hotel.arlear.model.Card;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.Reservation;
import com.online.hotel.arlear.model.Room;
import com.online.hotel.arlear.model.Subsidiary;
import com.online.hotel.arlear.model.Ticket;
import com.online.hotel.arlear.model.Transaction;
import com.online.hotel.arlear.model.UserHotel;

public class LoadInitial {
	
	public static List<Room> createRoom(){
		Room room = new Room();
		Room room1 = new Room();
		Room room2= new Room();
		Room room3 = new Room();
		Room room4= new Room();
		Room room5 = new Room();
		Room room6= new Room();
		Room room7 = new Room();
		Room room8= new Room();
		Room room9 = new Room();
		
		room.setCapacity(2);
		room.setId(1L);
		room.setCategory(RoomCategory.BRONCE);
		room.setFloor(1);
		room.setPrice(500);
		room.setRoomNumber(101);
		room.setRoomStatus(RoomStatus.DISPONIBLE);
		room.setType(RoomType.DOBLE);
		
		room1.setCapacity(3);
		room1.setId(2L);
		room1.setCategory(RoomCategory.ORO);
		room1.setFloor(1);
		room1.setPrice(800);
		room1.setRoomNumber(102);
		room1.setRoomStatus(RoomStatus.DISPONIBLE);
		room1.setType(RoomType.TRIPLE);
		
		room2.setCapacity(1);
		room2.setId(3L);
		room2.setCategory(RoomCategory.BRONCE);
		room2.setFloor(1);
		room2.setPrice(500);
		room2.setRoomNumber(230);
		room2.setRoomStatus(RoomStatus.DISPONIBLE);
		room2.setType(RoomType.SIMPLE);
		
		room3.setCapacity(1);
		room3.setId(4L);
		room3.setCategory(RoomCategory.PLATA);
		room3.setFloor(2);
		room3.setPrice(500);
		room3.setRoomNumber(201);
		room3.setRoomStatus(RoomStatus.DISPONIBLE);
		room3.setType(RoomType.SIMPLE);
		
		room4.setCapacity(2);
		room4.setId(5L);
		room4.setCategory(RoomCategory.PLATA);
		room4.setFloor(2);
		room4.setPrice(500);
		room4.setRoomNumber(202);
		room4.setRoomStatus(RoomStatus.DISPONIBLE);
		room4.setType(RoomType.DOBLE);

		room5.setCapacity(3);
		room5.setId(6L);
		room5.setCategory(RoomCategory.PLATA);
		room5.setFloor(2);
		room5.setPrice(500);
		room5.setRoomNumber(203);
		room5.setRoomStatus(RoomStatus.DISPONIBLE);
		room5.setType(RoomType.TRIPLE);
		
		room6.setCapacity(1);
		room6.setId(7L);
		room6.setCategory(RoomCategory.ORO);
		room6.setFloor(3);
		room6.setPrice(500);
		room6.setRoomNumber(301);
		room6.setRoomStatus(RoomStatus.DISPONIBLE);
		room6.setType(RoomType.SIMPLE);
		
		room7.setCapacity(2);
		room7.setId(8L);
		room7.setCategory(RoomCategory.ORO);
		room7.setFloor(3);
		room7.setPrice(500);
		room7.setRoomNumber(302);
		room7.setRoomStatus(RoomStatus.DISPONIBLE);
		room7.setType(RoomType.DOBLE);
		
		room8.setCapacity(2);
		room8.setId(9L);
		room8.setCategory(RoomCategory.ORO);
		room8.setFloor(3);
		room8.setPrice(500);
		room8.setRoomNumber(303);
		room8.setRoomStatus(RoomStatus.DISPONIBLE);
		room8.setType(RoomType.DOBLE);
		
		room9.setCapacity(3);
		room9.setId(10L);
		room9.setCategory(RoomCategory.ORO);
		room9.setFloor(3);
		room9.setPrice(500);
		room9.setRoomNumber(304);
		room9.setRoomStatus(RoomStatus.DISPONIBLE);
		room9.setType(RoomType.TRIPLE);
		
		return Arrays.asList(room,room1,room2,room3,room4,room5,room6,room7,room8,room9);
	}
	
	private static List<Contact> createContact() {
		
		Card card = new Card();
		
		card.setCardNumber("1234599876543678");
		card.setCardType(CardType.CREDITO);
		card.setCodeSecurity(1224);
		card.setExpirationDate(LocalDate.of(2020, 12, 12));
		
		Contact contact = new Contact();
		contact.setCard(card);
		contact.setDocumentNumber(32234454);
		contact.setDocumentType(DocumentType.DNI);
		contact.setGender(GenderType.MASCULINO);
		contact.setMail("carlosperez@gmail.com");
		contact.setName("CARLOS");
		contact.setPhone(1167863667);
		contact.setSurname("PEREZ");
		
		Card card2 = new Card();
		
		card2.setCardNumber("5757583721908898");
		card2.setCardType(CardType.DEBITO);
		card2.setCodeSecurity(6753);
		card2.setExpirationDate(LocalDate.of(2021,03,15));

		
		Contact contact2 = new Contact();
		
		contact2.setCard(card2);
		contact2.setDocumentNumber(36876784);
		contact2.setDocumentType(DocumentType.DNI);
		contact2.setGender(GenderType.MASCULINO);
		contact2.setMail("julio13321@gmail.com");
		contact2.setName("JULIO");
		contact2.setPhone(1183987423);
		contact2.setSurname("VILLAGRA");
		
		Card card3 = new Card();
		
		card3.setCardNumber("7767576728909080");
		card3.setCardType(CardType.DEBITO);
		card3.setCodeSecurity(6532);
		card3.setExpirationDate(LocalDate.of(2022,04,05));
		
		Contact contact3 = new Contact();
		
		contact3.setCard(card3);
		contact3.setDocumentNumber(17803805);
		contact3.setDocumentType(DocumentType.DNI);
		contact3.setGender(GenderType.MASCULINO);
		contact3.setMail("juan22@gmail.com");
		contact3.setName("JUAN");
		contact3.setPhone(1197376307);
		contact3.setSurname("MARTINEZ");
		
		Card card4 = new Card();
		
		card4.setCardNumber("2345678868776999");
		card4.setCardType(CardType.DEBITO);
		card4.setCodeSecurity(7864);
		card4.setExpirationDate(LocalDate.of(2021,11,11));
		
		Contact contact4 = new Contact();
		
		contact4.setCard(card4);
		contact4.setDocumentNumber(12487984);
		contact4.setDocumentType(DocumentType.DNI);
		contact4.setGender(GenderType.FEMENINO);
		contact4.setMail("lachiqui@gmail.com");
		contact4.setName("MIRTA");
		contact4.setPhone(1132554634);
		contact4.setSurname("MORENO");
		
		Card card5 = new Card();
		
		card5.setCardNumber("1873687608098031");
		card5.setCardType(CardType.DEBITO);
		card5.setCodeSecurity(6342);
		card5.setExpirationDate(LocalDate.of(2020,02,19));
		
		Contact contact5 = new Contact();
		
		contact5.setCard(card5);
		contact5.setDocumentNumber(18043857);
		contact5.setDocumentType(DocumentType.DNI);
		contact5.setGender(GenderType.FEMENINO);
		contact5.setMail("laura77@gmail.com");
		contact5.setName("LAURA");
		contact5.setPhone(1187876638);
		contact5.setSurname("DOMINGUEZ");
		
		
		Card card6 = new Card();
		
		card6.setCardNumber("1873687608098031");
		card6.setCardType(CardType.DEBITO);
		card6.setCodeSecurity(9877);
		card6.setExpirationDate(LocalDate.of(2021,02,10));	
		
		Contact contact6 = new Contact();
		
		contact6.setCard(card6);
		contact6.setDocumentNumber(21008379);
		contact6.setDocumentType(DocumentType.DNI);
		contact6.setGender(GenderType.FEMENINO);
		contact6.setMail("moni44@gmail.com");
		contact6.setName("MONICA");
		contact6.setPhone(1163725567);
		contact6.setSurname("JUAREZ");
		
		Card card7 = new Card();
		
		card7.setCardNumber("1873687608098031");
		card7.setCardType(CardType.DEBITO);
		card7.setCodeSecurity(7654);
		card7.setExpirationDate(LocalDate.of(2022,05,03));	
		
		Contact contact7 = new Contact();
		
		contact7.setCard(card7);
		contact7.setDocumentNumber(27378678);
		contact7.setDocumentType(DocumentType.DNI);
		contact7.setGender(GenderType.FEMENINO);
		contact7.setMail("luciPerez22@gmail.com");
		contact7.setName("LUCIA");
		contact7.setPhone(1179837266);
		contact7.setSurname("PEREZ");
		
		Card card8 = new Card();
		
		card8.setCardNumber("1873687608098031");
		card8.setCardType(CardType.DEBITO);
		card8.setCodeSecurity(1213);
		card8.setExpirationDate(LocalDate.of(2020,10,30));	
		
		Contact contact8 = new Contact();
		
		contact8.setCard(card8);
		contact8.setDocumentNumber(28786785);
		contact8.setDocumentType(DocumentType.DNI);
		contact8.setGender(GenderType.MASCULINO);
		contact8.setMail("marcos23@gmail.com");
		contact8.setName("MARCOS");
		contact8.setPhone(1144354323);
		contact8.setSurname("GUZMAN");
	
		Card card9 = new Card();
		
		card9.setCardNumber("1873687608098031");
		card9.setCardType(CardType.DEBITO);
		card9.setCodeSecurity(8592);
		card9.setExpirationDate(LocalDate.of(2023,01,01));	
	
		Contact contact9 = new Contact();
		
		contact9.setCard(card9);
		contact9.setDocumentNumber(36893874);
		contact9.setDocumentType(DocumentType.DNI);
		contact9.setGender(GenderType.MASCULINO);
		contact9.setMail("pepito45@gmail.com");
		contact9.setName("PEDRO");
		contact9.setPhone(1182838662);
		contact9.setSurname("MAYORGA");
		
		Card card10 = new Card();
		
		card10.setCardNumber("1873687608098031");
		card10.setCardType(CardType.DEBITO);
		card10.setCodeSecurity(1375);
		card10.setExpirationDate(LocalDate.of(2021,02,04));	
		
		Contact contact10 = new Contact();
		
		contact10.setCard(card10);
		contact10.setDocumentNumber(15787323);
		contact10.setDocumentType(DocumentType.DNI);
		contact10.setGender(GenderType.MASCULINO);
		contact10.setMail("rojito@gmail.com");
		contact10.setName("MARTIN");
		contact10.setPhone(1133454334);
		contact10.setSurname("ROJO");

		return Arrays.asList(contact,contact2);
	}
	public static List<Reservation> createReservation(List<Room> rooms){
	
		List<Contact> contacts = createContact();
		
		Reservation reservation = new Reservation();
		
		reservation.setAditionals(RoomAditionals.A);
		reservation.setAdultsCuantity(2);
		reservation.setBeginDate(LocalDate.now());
		reservation.setChildsCuantity(1);
		reservation.setContact(contacts.get(0));
		reservation.setEndDate(LocalDate.of(2020, 05, 20));
		reservation.setPrice(1093.0);
		reservation.setReservationType(ReservationType.DESAYUNO_INCLUIDO);
		reservation.setRoom(rooms.get(0));
		reservation.setReservationStatus(ReservationStatus.RESERVADA_SEÑADA);
		reservation.setSign(200.0);
		
		Reservation reservation1 = new Reservation();
		
		reservation1.setAditionals(RoomAditionals.A);
		reservation1.setAdultsCuantity(1);
		reservation1.setBeginDate(LocalDate.of(2020,06,10));
		reservation1.setChildsCuantity(1);
		reservation1.setContact(contacts.get(1));
		reservation1.setEndDate(LocalDate.of(2020, 06, 19));
		reservation1.setPrice(1093.0);
		reservation1.setReservationType(ReservationType.DESAYUNO_INCLUIDO);
		reservation1.setRoom(rooms.get(1));
		reservation1.setReservationStatus(ReservationStatus.RESERVADA_SEÑADA);
		reservation1.setSign(200.0);
			
	/*	Reservation reservation2 = new Reservation();
		
		reservation2.setAditionals(RoomAditionals.A);
		reservation2.setAdultsCuantity(3);
		reservation2.setBeginDate(LocalDate.of(2020,06,20));
		reservation2.setChildsCuantity(1);
		reservation2.setContact(contacts.get(0));//cambiar
		reservation2.setEndDate(LocalDate.of(2020, 06, 27));
		reservation2.setPrice(17000.0);
		reservation2.setReservationType(ReservationType.MEDIA_PENSION);
		reservation2.setRoom(rooms.get(0));//cambiar
		reservation2.setSign(2000.0);
			
		Reservation reservation3 = new Reservation();
		
		reservation3.setAditionals(RoomAditionals.A);
		reservation3.setAdultsCuantity(2);
		reservation3.setBeginDate(LocalDate.of(2020,06,25));
		reservation3.setChildsCuantity(1);
		reservation3.setContact(contacts.get(0));//cambiar
		reservation3.setEndDate(LocalDate.of(2020, 06, 30));
		reservation3.setPrice(10000.0);
		reservation3.setReservationType(ReservationType.DESAYUNO_INCLUIDO);
		reservation3.setRoom(rooms.get(0));//cambiar
		reservation3.setSign(2000.0);
		
		Reservation reservation4 = new Reservation();
		
		reservation4.setAditionals(RoomAditionals.A);
		reservation4.setAdultsCuantity(2);
		reservation4.setBeginDate(LocalDate.of(2020, 07, 01));
		reservation4.setChildsCuantity(0);
		reservation4.setContact(contacts.get(0));//cambiar
		reservation4.setEndDate(LocalDate.of(2020, 07, 10));
		reservation4.setPrice(24000.0);
		reservation4.setReservationType(ReservationType.PENSION_COMPLETA);
		reservation4.setRoom(rooms.get(0));//cambiar
		reservation4.setSign(2000.0);
		
		Reservation reservation5 = new Reservation();
		
		reservation5.setAditionals(RoomAditionals.A);
		reservation5.setAdultsCuantity(2);
		reservation5.setBeginDate(LocalDate.of(2020, 07, 20));
		reservation5.setChildsCuantity(2);
		reservation5.setContact(contacts.get(0));//cambiar
		reservation5.setEndDate(LocalDate.of(2020, 07, 25));
		reservation5.setPrice(25000.0);
		reservation5.setReservationType(ReservationType.DESAYUNO_INCLUIDO);
		reservation5.setRoom(rooms.get(0));//cambiar
		reservation5.setSign(2000.0);
		
		Reservation reservation6 = new Reservation();
		
		reservation6.setAditionals(RoomAditionals.A);
		reservation6.setAdultsCuantity(2);
		reservation6.setBeginDate(LocalDate.of(2020, 07, 20));
		reservation6.setChildsCuantity(2);
		reservation6.setContact(contacts.get(0));//cambiar
		reservation6.setEndDate(LocalDate.of(2020, 07, 25));
		reservation6.setPrice(27000.0);
		reservation6.setReservationType(ReservationType.MEDIA_PENSION);
		reservation6.setRoom(rooms.get(0));//cambiar
		reservation6.setSign(2000.0);
		
		Reservation reservation7 = new Reservation();
		
		reservation7.setAditionals(RoomAditionals.A);
		reservation7.setAdultsCuantity(2);
		reservation7.setBeginDate(LocalDate.of(2020, 07, 20));
		reservation7.setChildsCuantity(2);
		reservation7.setContact(contacts.get(0));//cambiar
		reservation7.setEndDate(LocalDate.of(2020, 07, 23));
		reservation7.setPrice(20000.0);
		reservation7.setReservationType(ReservationType.DESAYUNO_INCLUIDO);
		reservation7.setRoom(rooms.get(0));//cambiar
		reservation7.setSign(2000.0);
		
		Reservation reservation8 = new Reservation();
		
		reservation8.setAditionals(RoomAditionals.A);
		reservation8.setAdultsCuantity(3);
		reservation8.setBeginDate(LocalDate.of(2020, 07, 20));
		reservation8.setChildsCuantity(0);
		reservation8.setContact(contacts.get(0));//cambiar
		reservation8.setEndDate(LocalDate.of(2020, 07, 28));
		reservation8.setPrice(23000.0);
		reservation8.setReservationType(ReservationType.DESAYUNO_INCLUIDO);
		reservation8.setRoom(rooms.get(0));//cambiar
		reservation8.setSign(2000.0);
		
		Reservation reservation9 = new Reservation();
		
		reservation9.setAditionals(RoomAditionals.A);
		reservation9.setAdultsCuantity(2);
		reservation9.setBeginDate(LocalDate.of(2020, 07, 20));
		reservation9.setChildsCuantity(1);
		reservation9.setContact(contacts.get(0));//cambiar
		reservation9.setEndDate(LocalDate.of(2020, 07, 24));
		reservation9.setPrice(25000.0);
		reservation9.setReservationType(ReservationType.PENSION_COMPLETA);
		reservation9.setRoom(rooms.get(0));//cambiar
		reservation9.setSign(2000.0);
		
		Reservation reservation10 = new Reservation();
		
		reservation10.setAditionals(RoomAditionals.A);
		reservation10.setAdultsCuantity(2);
		reservation10.setBeginDate(LocalDate.of(2020, 07, 10));
		reservation10.setChildsCuantity(0);
		reservation10.setContact(contacts.get(0));//cambiar
		reservation10.setEndDate(LocalDate.of(2020, 07, 13));
		reservation10.setPrice(2000.0);
		reservation10.setReservationType(ReservationType.MEDIA_PENSION);
		reservation10.setRoom(rooms.get(0));//cambiar
		reservation10.setSign(2000.0);*/
		
		return Arrays.asList(reservation,reservation1);
	}
	
	public static Ticket createTicket(){
		
		Address address = new Address();
		address.setName("Peralta Ramos");
		address.setNumber(1234);
		
		Subsidiary subsidiary = new Subsidiary();
		subsidiary.setAddress(address);
		subsidiary.setCuit(2323424);
		subsidiary.setMail("onlinehotelpremium@gmail.com");
		subsidiary.setName("Polvorines");
		subsidiary.setPhone("023204858");
		
		Transaction transaction = new Transaction();
		transaction.setAmount(43534.0);
		transaction.setDate(LocalDateTime.now());
		transaction.setDescription("Reserva de salon");
		transaction.setElement("Evento");
		transaction.setSection(Section.SALON);
		
		Transaction transaction2 = new Transaction();
		transaction2.setAmount(600.0);
		transaction2.setDate(LocalDateTime.now());
		transaction2.setDescription("Reserva de Hotel");
		transaction2.setElement("Habitacion");
		transaction2.setSection(Section.HOTEL);
		
		Transaction transaction3 = new Transaction();
		transaction3.setAmount(600.0);
		transaction3.setDate(LocalDateTime.now());
		transaction3.setDescription("Almuerzo");
		transaction3.setElement("Menu");
		transaction3.setSection(Section.RESTAURANTE);
		
		Contact contact = new Contact();
		contact.setDocumentNumber(34567890);
		contact.setDocumentType(DocumentType.DNI);
		contact.setGender(GenderType.MASCULINO);
		contact.setMail("oscar@gmail.com");
		contact.setName("oscar");
		contact.setPhone(53434543);
		contact.setSurname("umbert");
		
		Ticket ticket = new Ticket();
		ticket.setDate(LocalDateTime.now());
		ticket.setSubsidiary(subsidiary);
		ticket.setTransaction(Arrays.asList(transaction,transaction2,transaction3));
		ticket.setContact(contact);
		ticket.setStatus(TicketStatus.ABIERTO);
		
		return ticket;
	
	}
	
	public static List<UserHotel> createUser(){
		
		UserHotel user1= new UserHotel();
		user1.setName("Maria");
		user1.setSurname("Laprida");
		user1.setUserName("mlaprida");
		user1.setPassword("4537");
		user1.setUserType(UserType.PERSONAL_RECEPCION);
		
		UserHotel user2= new UserHotel();
		user2.setName("Federico");
		user2.setSurname("Estrada");
		user2.setUserName("festrada");
		user2.setPassword("7889");
		user2.setUserType(UserType.PERSONAL_RESTAURANT);
		
		UserHotel user3= new UserHotel();
		user3.setName("Julio");
		user3.setSurname("Moroni");
		user3.setUserName("jmoroni");
		user3.setPassword("6578");
		user3.setUserType(UserType.GERENTE);
		
		UserHotel user4= new UserHotel();
		user4.setName("Ricardo");
		user4.setSurname("Romero");
		user4.setUserName("rromero");
		user4.setPassword("4279");
		user4.setUserType(UserType.SUPERVISOR_HOTEL);
		
		UserHotel user5= new UserHotel();
		user5.setName("Melisa");
		user5.setSurname("Villegas");
		user5.setUserName("mvillegas");
		user5.setPassword("7832");
		user5.setUserType(UserType.SUPERVISOR_SALON);
		
		UserHotel user6= new UserHotel();
		user6.setName("Romina");
		user6.setSurname("Moreno");
		user6.setUserName("rmoreno");
		user6.setPassword("4582");
		user6.setUserType(UserType.PERSONAL_RECEPCION);
		
		UserHotel user7= new UserHotel();
		user7.setName("Matias");
		user7.setSurname("Benitez");
		user7.setUserName("mbenitez");
		user7.setPassword("3889");
		user7.setUserType(UserType.PERSONAL_RESTAURANT);
		
		UserHotel user8= new UserHotel();
		user8.setName("Gabriel");
		user8.setSurname("Perez");
		user8.setUserName("gperez");
		user8.setPassword("2438");
		user8.setUserType(UserType.PERSONAL_RESTAURANT);
		
		UserHotel user9= new UserHotel();
		user9.setName("Patricia");
		user9.setSurname("GOnzalez");
		user9.setUserName("pgonzalez");
		user9.setPassword("1479");
		user9.setUserType(UserType.PERSONAL_RECEPCION);
		
		UserHotel user10= new UserHotel();
		user10.setName("Camila");
		user10.setSurname("Speletta");
		user10.setUserName("cspeletta");
		user10.setPassword("5453");
		user10.setUserType(UserType.PERSONAL_RESTAURANT);
		
		return Arrays.asList(user1,user2,user3,user4,user5,user6,user7,user8,user9,user10);
	}
	
	public static List<Product> createProduct(){
	
		Product product1 = new Product();
		product1.setName("Hambuguesa completa");
		product1.setPrice(160.00);
		product1.setProductType(ProductType.PLATO_PRINCIPAL);
		product1.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product2 = new Product();
		product2.setName("Papas Fritas");
		product2.setPrice(90.00);
		product2.setProductType(ProductType.GUARNICION);
		product2.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product3 = new Product();
		product3.setName("Gaseosa");
		product3.setPrice(90.00);
		product3.setProductType(ProductType.BEBIDA);
		product3.setProductAvailability(ProductAvailability.DISPONIBLE);

		Product product4 = new Product();
		product4.setName("Empanadas");
		product4.setPrice(50.00);
		product4.setProductType(ProductType.ENTRADA);
		product4.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product5 = new Product();
		product5.setName("Salsa Fileto");
		product5.setPrice(60.00);
		product5.setProductType(ProductType.ADICIONAL);
		product5.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product6 = new Product();
		product6.setName("Salsa 4 quesos");
		product6.setPrice(60.00);
		product6.setProductType(ProductType.ADICIONAL);
		product6.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product7 = new Product();
		product7.setName("Salsa Bolonieza");
		product7.setPrice(60.00);
		product7.setProductType(ProductType.ADICIONAL);
		product7.setProductAvailability(ProductAvailability.NO_DISPONIBLE);
		
		Product product8 = new Product();
		product8.setName("Salsa Bolonieza");
		product8.setPrice(60.00);
		product8.setProductType(ProductType.ADICIONAL);
		product8.setProductAvailability(ProductAvailability.NO_DISPONIBLE);
		
		Product product9 = new Product();
		product9.setName("Sorrentinos");
		product9.setPrice(250.00);
		product9.setProductType(ProductType.PLATO_PRINCIPAL);
		product9.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product10 = new Product();
		product10.setName("nioquis");
		product10.setPrice(230.00);
		product10.setProductType(ProductType.ENTRADA);
		product10.setProductAvailability(ProductAvailability.NO_DISPONIBLE);
		
		Product product11= new Product();
		product11.setName("Porcion de rabas");
		product11.setPrice(300.00);
		product11.setProductType(ProductType.ENTRADA);
		product11.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product12 = new Product();
		product12.setName("Milanesa a caballo");
		product12.setPrice(200.00);
		product12.setProductType(ProductType.PLATO_PRINCIPAL);
		product12.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product13 = new Product();
		product13.setName("Milanesa a la napolitana");
		product13.setPrice(200.00);
		product13.setProductType(ProductType.PLATO_PRINCIPAL);
		product13.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product14 = new Product();
		product14.setName("Milanesa con salsa cheddar");
		product14.setPrice(200.00);
		product14.setProductType(ProductType.PLATO_PRINCIPAL);
		product14.setProductAvailability(ProductAvailability.NO_DISPONIBLE);
		
		Product product15 = new Product();
		product15.setName("Pure de papas");
		product15.setPrice(90.00);
		product15.setProductType(ProductType.GUARNICION);
		product15.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product16 = new Product();
		product16.setName("Ensalada Mixta");
		product16.setPrice(90.00);
		product16.setProductType(ProductType.GUARNICION);
		product16.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product17 = new Product();
		product17.setName("Vino Don Valentin Lacrado Malbec");
		product17.setPrice(168.00);
		product17.setProductType(ProductType.BEBIDA);
		product17.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product18 = new Product();
		product18.setName("Vino Don Valentin Lacrado Blanco");
		product18.setPrice(168.00);
		product18.setProductType(ProductType.BEBIDA);
		product18.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product19 = new Product();
		product19.setName("Vino Tinto Rutini Cabernet Malbec");
		product19.setPrice(586.85);
		product19.setProductType(ProductType.BEBIDA);
		product19.setProductAvailability(ProductAvailability.NO_DISPONIBLE);
		
		Product product20 = new Product();
		product20.setName("Helado");
		product20.setPrice(100.00);
		product20.setProductType(ProductType.POSTRE);
		product20.setProductAvailability(ProductAvailability.NO_DISPONIBLE);
		
		Product product21 = new Product();
		product21.setName("Panqueques con dulce de leche");
		product21.setPrice(110.00);
		product21.setProductType(ProductType.POSTRE);
		product21.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product22 = new Product();
		product22.setName("Ensalada de frutas");
		product22.setPrice(110.00);
		product22.setProductType(ProductType.POSTRE);
		product22.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product23 = new Product();
		product23.setName("Cafe Americano");
		product23.setPrice(100.00);
		product23.setProductType(ProductType.ADICIONAL);
		product23.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product24 = new Product();
		product24.setName("Tostadas");
		product24.setPrice(120.00);
		product24.setProductType(ProductType.ADICIONAL);
		product24.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product25 = new Product();
		product25.setName("Jugo exprimido");
		product25.setPrice(120.00);
		product25.setProductType(ProductType.ADICIONAL);
		product25.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product26 = new Product();
		product26.setName("Medialuna");
		product26.setPrice(50.00);
		product26.setProductType(ProductType.ADICIONAL);
		product26.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product27 = new Product();
		product27.setName("Submarino");
		product27.setPrice(110.00);
		product27.setProductType(ProductType.ADICIONAL);
		product27.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product28 = new Product();
		product28.setName("Porcion de torta");
		product28.setPrice(150.00);
		product28.setProductType(ProductType.ADICIONAL);
		product28.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product29 = new Product();
		product29.setName("Capuccino");
		product29.setPrice(125.00);
		product29.setProductType(ProductType.ADICIONAL);
		product29.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product30 = new Product();
		product30.setName("Tostado");
		product30.setPrice(120.00);
		product30.setProductType(ProductType.ADICIONAL);
		product30.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		Product product31 = new Product();
		product31.setName("Te");
		product31.setPrice(90.00);
		product31.setProductType(ProductType.ADICIONAL);
		product31.setProductAvailability(ProductAvailability.DISPONIBLE);
		
		
		return Arrays.asList(product1,product2,product3,product4,product5,product6,product7,product8,product9,product10
				,product11,product12,product13,product14,product15,product16,product17,product18,product19,product20,
				product21,product22,product23,product24,product25,product26,product27,product28,product29,product30,product31);
	}
	
	public static List<Menu> createMenu(List<Product> products){
		
		Menu menu1 = new Menu();
		products= createProduct();
		
		List<Product> productMenu1= new ArrayList<Product>();
		productMenu1.add(0,products.get(0));
		productMenu1.add(1,products.get(1));
		productMenu1.add(2,products.get(2));
		
		menu1.setNameMenu("Menu1");
		menu1.setPriceTotal(240.00);
		menu1.setDiscount(30);
		menu1.setMenutype(MenuType.ALMUERZO_CENA);
		menu1.setMenustate(MenuState.DISPONIBLE);
		menu1.setProduct(productMenu1);
/*		
		
		List<Product> productMenu2= new ArrayList<Product>();
		productMenu2.add(0,products.get(3));
		productMenu2.add(1,products.get(8));
		productMenu2.add(2,products.get(4));
		productMenu2.add(3,products.get(2));
		
		Menu menu2 = new Menu();
		menu2.setNameMenu("Menu2");
		menu2.setPriceTotal(382.00);
		menu2.setDiscount(15);
		menu2.setMenutype(MenuType.ALMUERZO_CENA);
		menu2.setMenustate(MenuState.DISPONIBLE);
		menu2.setProduct(productMenu2);
		

		List<Product> productMenu3= new ArrayList<Product>();
		productMenu3.add(0,products.get(10));
		productMenu3.add(1,products.get(11));
		productMenu3.add(2,products.get(1));
		productMenu3.add(3,products.get(2));
		
		Menu menu3 = new Menu();
		menu3.setNameMenu("Menu3");
		menu3.setPriceTotal(480.00);
		menu3.setDiscount(20);
		menu3.setMenutype(MenuType.ALMUERZO_CENA);
		menu3.setMenustate(MenuState.DISPONIBLE);
		menu3.setProduct(productMenu3);
		
	
		List<Product> productMenu4 =new ArrayList<Product>();
		productMenu4.add(0,products.get(3));
		productMenu4.add(1,products.get(12));
		productMenu4.add(2,products.get(14));
		productMenu4.add(3,products.get(2));
		
		Menu menu4 = new Menu();
		menu4.setNameMenu("Menu4");
		menu4.setPriceTotal(350.00);
		menu4.setDiscount(0);
		menu4.setMenutype(MenuType.ALMUERZO_CENA);
		menu4.setMenustate(MenuState.DISPONIBLE);
		menu4.setProduct(productMenu4);
		
	
		List<Product> productMenu5= new ArrayList<Product>();
		productMenu5.add(0,products.get(10));
		productMenu5.add(1,products.get(13));
		productMenu5.add(2,products.get(1));
		productMenu5.add(3,products.get(2));
		
		Menu menu5 = new Menu();
		menu5.setNameMenu("Menu5");
		menu5.setPriceTotal(680.00);
		menu5.setDiscount(0);
		menu5.setMenutype(MenuType.ALMUERZO_CENA);
		menu5.setMenustate(MenuState.NO_DISPONIBLE);
		menu5.setProduct(productMenu5);
	
	
		List<Product> productMenu6= new ArrayList<Product>();
		productMenu6.add(0,products.get(22));
		productMenu6.add(1,products.get(23));

		Menu menu6 = new Menu();
		menu6.setNameMenu("Menu6");
		menu6.setPriceTotal(220.00);
		menu6.setDiscount(20);
		menu6.setMenutype(MenuType.DESAYUNO);
		menu6.setMenustate(MenuState.DISPONIBLE);
		menu6.setProduct(productMenu6);
	
	
		List<Product> productMenu7= new ArrayList<Product>();
		productMenu7.add(0,products.get(24));
		productMenu7.add(1,products.get(25));
		
		Menu menu7 = new Menu();
		menu7.setNameMenu("Menu7");
		menu7.setPriceTotal(170.00);
		menu7.setDiscount(0);
		menu7.setMenutype(MenuType.DESAYUNO);
		menu7.setMenustate(MenuState.DISPONIBLE);
		menu7.setProduct(productMenu7);
		
	
		List<Product> productMenu8= new ArrayList<Product>();
		productMenu8.add(0,products.get(26));
		productMenu8.add(1,products.get(27));
	
		Menu menu8 = new Menu();
		menu8.setNameMenu("Menu8");
		menu8.setPriceTotal(260.00);
		menu8.setDiscount(0);
		menu8.setMenutype(MenuType.MERIENDA);
		menu8.setMenustate(MenuState.DISPONIBLE);
		menu8.setProduct(productMenu8);
		
		
		List<Product> productMenu9= new ArrayList<Product>();
		productMenu9.add(0,products.get(28));
		productMenu9.add(1,products.get(29));
	
		Menu menu9 = new Menu();
		menu9.setNameMenu("Menu9");
		menu9.setPriceTotal(245.00);
		menu9.setDiscount(0);
		menu9.setMenutype(MenuType.MERIENDA);
		menu9.setMenustate(MenuState.DISPONIBLE);
		menu9.setProduct(productMenu9);
		

		List<Product> productMenu10= new ArrayList<Product>();
		productMenu10.add(0,products.get(30));
		productMenu10.add(1,products.get(23));
		
		Menu menu10 = new Menu();
		menu10.setNameMenu("Menu10");
		menu10.setPriceTotal(210.0);
		menu10.setDiscount(0);
		menu10.setMenutype(MenuType.MERIENDA);
		menu10.setMenustate(MenuState.DISPONIBLE);
		menu10.setProduct(productMenu10);
		
		return Arrays.asList(menu1,menu2,menu3,menu4,menu5,menu6,menu7,menu8,menu9,menu10);
*/
		return Arrays.asList(menu1);
	}
}
