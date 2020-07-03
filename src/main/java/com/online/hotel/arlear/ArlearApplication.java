package com.online.hotel.arlear;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.online.hotel.arlear.security.JWTAuthorizationFilter;
import com.online.hotel.arlear.service.MenuService;
import com.online.hotel.arlear.service.ProductService;
import com.online.hotel.arlear.service.ReservationService;
import com.online.hotel.arlear.service.RoomService;
import com.online.hotel.arlear.service.TicketService;
import com.online.hotel.arlear.service.UserService;
import com.online.hotel.arlear.util.LoadInitial;


@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})

public class ArlearApplication {
	@Value("${database.url}")
	private String url;
	public static void main(String[] args) {
		SpringApplication.run(ArlearApplication.class, args);
	}
	@Bean
	public CommandLineRunner init(final RoomService roomService,
								  final ReservationService reservationService,
								  final TicketService ticketService,
								  final MenuService menuService,
								  final ProductService productService,
								  final UserService userService) {
		return new CommandLineRunner() {
			public void run(String... strings) {
				System.out.println("Url *****"+url);
				if(roomService.find().size() == 0) {
					LoadInitial.createRoom().stream().forEach(p-> roomService.create(p));
					LoadInitial.createReservation(roomService.find()).stream().forEach(p-> reservationService.create(p));
					ticketService.create(LoadInitial.createTicket());
					
				}
				System.out.println("Url *****"+url);
				if(productService.find().size() == 0) {
					
					LoadInitial.createProduct().stream().forEach(p-> productService.create(p));
				//	LoadInitial.createMenu(productService.find()).stream().forEach(p->menuService.create(p));
				}
				
				System.out.println("Url *****"+url);
				if(userService.find().size() == 0) {
					LoadInitial.createUser().stream().forEach(p-> userService.create(p));
				}
			
				
				
			}
		};
	}
/*

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/userTest").permitAll()
				.antMatchers(HttpMethod.POST, "/user").permitAll()
				
				.antMatchers(HttpMethod.POST, "/product/getAll").permitAll()	
				.antMatchers(HttpMethod.POST, "/product").permitAll()
				.antMatchers(HttpMethod.PUT, "/product").permitAll()
				.antMatchers(HttpMethod.DELETE, "/product").permitAll()
				.antMatchers(HttpMethod.GET, "/product/{idProduct}").permitAll()
				
				.antMatchers(HttpMethod.POST, "/menu/getAll").permitAll()	
				.antMatchers(HttpMethod.POST, "/menu").permitAll()
				.antMatchers(HttpMethod.PUT, "/menu").permitAll()
				.antMatchers(HttpMethod.DELETE, "/menu").permitAll()
				.antMatchers(HttpMethod.GET, "/menu/{idMenu}").permitAll()
				
				.antMatchers(HttpMethod.POST, "/guest").permitAll()	
				.antMatchers(HttpMethod.POST, "/guests").permitAll()
				.antMatchers(HttpMethod.GET, "/guests").permitAll()
				
				.antMatchers(HttpMethod.DELETE, "/room/{idRoom}").permitAll()
				.antMatchers(HttpMethod.GET, "/rooms").permitAll()
				.antMatchers(HttpMethod.POST, "/room").permitAll()
				
				.antMatchers(HttpMethod.DELETE, "/question/{id}").permitAll()
				.antMatchers(HttpMethod.POST, "/question").permitAll()
				.antMatchers(HttpMethod.POST, "/question/getAll").permitAll()
				
				.antMatchers(HttpMethod.POST, "/orderrestaurant").permitAll()
				.antMatchers(HttpMethod.PUT, "/orderrestaurant").permitAll()
				.antMatchers(HttpMethod.DELETE, "/orderrestaurant/{idOrder}").permitAll()
				.antMatchers(HttpMethod.GET, "/orderrestaurant").permitAll()
				
				.antMatchers(HttpMethod.POST, "/reservation").permitAll()
				.antMatchers(HttpMethod.PUT, "/reservation").permitAll()
				.antMatchers(HttpMethod.DELETE, "/reservation").permitAll()
				.antMatchers(HttpMethod.GET, "/reservation").permitAll()
				
				.antMatchers(HttpMethod.GET, "/ticket").permitAll()
				.antMatchers(HttpMethod.GET, "/ticket/{document}").permitAll()
				.antMatchers(HttpMethod.GET, "/ticket/transaction").permitAll()
				.antMatchers(HttpMethod.GET, "/ticket/transaction/{idTransaction}").permitAll()
				.antMatchers(HttpMethod.GET, "/ticket/exportTicketClient/{client}").permitAll()
				.antMatchers(HttpMethod.GET, "/ticket/exportTicket/{sectionNumber}").permitAll()
				.antMatchers(HttpMethod.POST, "/ticket").permitAll()
				.antMatchers(HttpMethod.PUT, "/ticket").permitAll()
				.antMatchers(HttpMethod.POST, "/ticket/transaction").permitAll()
				.antMatchers(HttpMethod.PUT, "/ticket/Transaction").permitAll()
				.antMatchers(HttpMethod.DELETE, "/ticket/{idTicket}").permitAll()
				
				.antMatchers(HttpMethod.POST, "/survey/get").permitAll()
				.antMatchers(HttpMethod.POST, "/survey/getAll").permitAll()
				.antMatchers(HttpMethod.GET, "/survey/{idSurvey}").permitAll()
				.antMatchers(HttpMethod.POST, "/survey").permitAll()
				.antMatchers(HttpMethod.DELETE, "/survey/{id}").permitAll()
				
				.antMatchers(HttpMethod.POST, "/event").permitAll()
				.antMatchers(HttpMethod.POST, "/event/getAll").permitAll()
				
				.anyRequest().authenticated();
		}
	}*/

	
}
