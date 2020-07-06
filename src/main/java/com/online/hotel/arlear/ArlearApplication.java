package com.online.hotel.arlear;


import java.awt.GraphicsEnvironment;
import java.util.Arrays;

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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
				
				
				String[] fontNames=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
				System.out.println(Arrays.toString(fontNames));
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

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			//http.csrf().requireCsrfProtectionMatcher(requireCsrfProtectionMatcher)
			
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/userExternal/**").permitAll()
				.antMatchers("/user/**").permitAll()
				
				.antMatchers("/product/**").permitAll()	
				
				.antMatchers("/menu/**").permitAll()	
				.antMatchers("/guest/**").permitAll()	
				.antMatchers("/guests/**").permitAll()
				
				.antMatchers("/room/**").permitAll()
				.antMatchers("/rooms/**").permitAll()
				
				.antMatchers("/question/**").permitAll()

				.antMatchers("/orderrestaurant/**").permitAll()
				
				.antMatchers("/reservation/**").permitAll()
				
				.antMatchers("/ticket/**").permitAll()
				
				.antMatchers("/survey/**").permitAll()
				.antMatchers("/event").permitAll()
				
				.anyRequest().authenticated();
			

		}
		 @Override
	      public void configure(WebSecurity web) throws Exception {
	          web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources","/swagger-resources/**", "/swagger-ui.html","/configuration/security", "/webjars/**");
	      }
	}
	



	
}
