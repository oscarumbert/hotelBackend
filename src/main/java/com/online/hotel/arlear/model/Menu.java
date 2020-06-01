package com.online.hotel.arlear.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.online.hotel.arlear.enums.MenuState;
import com.online.hotel.arlear.enums.MenuType;

import lombok.Data;

@Entity
@Data
public class Menu {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long idMenu;
	
	private String nameMenu;
	private Double priceTotal;
	private Integer discount;
	
	@Enumerated(EnumType.STRING)
	private MenuType menutype;
	
	@Enumerated(EnumType.STRING)
	private MenuState menustate;
	
	@ManyToMany (cascade= CascadeType.ALL)
	private List<Product> product;
}
