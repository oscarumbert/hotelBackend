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
				.antMatchers(HttpMethod.POST, "/userTest").permitAll().
				antMatchers(HttpMethod.POST, "/user").permitAll()
				.anyRequest().authenticated();
		}
	}
	*/
	
}
