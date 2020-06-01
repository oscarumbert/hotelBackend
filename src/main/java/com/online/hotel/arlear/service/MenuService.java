package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.enums.MenuType;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.repository.MenuRepository;
import com.online.hotel.arlear.repository.ProductRepository;

@Service
public class MenuService implements ServiceGeneric<Menu> {
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Override
	public boolean create(Menu entity) {
		//verifica si hay duplicado por nombre de menu, descuento y tipo de menu
		if(menuDuplicate(entity.getNameMenu(),entity.getDiscount(),entity.getMenutype())) {
			return false;
		}
		else {
			entity.setPriceTotal(precio(entity.getProduct(),entity.getDiscount()));
			menuRepository.save(entity);
			return true;
		}
	}

	//Verifica si existe duplicados
	private boolean menuDuplicate(String nameMenu, Integer discount, MenuType menutype) {
		if(findNameMenu(nameMenu)!=null && findDiscount(discount)!=null && findMenuType(menutype)!=null) {
			return true;
		}
		else {
			return false;
		}
	}

	private Object findMenuType(MenuType menutype) {
		Optional<Menu> optional = menuRepository.findAll().stream().filter(p -> p.getMenutype().equals(menutype)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}


	private Object findDiscount(Integer discount) {
		Optional<Menu> optional = menuRepository.findAll().stream().filter(p -> p.getDiscount().equals(discount)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}


	private Object findNameMenu(String nameMenu) {
		Optional<Menu> optional = menuRepository.findAll().stream().filter(p -> p.getNameMenu().equals(nameMenu)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}


	private Double precio(List<Product> list, int discount) {
		Double finalPrice = 0.0;
		Double desc=Double.valueOf(discount)/100.0;
		Double porcentaje= 1.0-desc;
		if(list!=null) {
			for (int x = 0; x < list.size(); x++) {
				Optional<Product> optional = productRepository.findById(list.get(x).getId());
				  if(optional != null) {
					  finalPrice=optional.get().getPrice()+finalPrice;
				  }
			}
		}
		return finalPrice*porcentaje;
	}
	

	public boolean update(Long id, Menu entity) {
		if(find(id)==null) {
			return false;
		}
		else {
			Menu menu=find(id);
			menu.setNameMenu(entity.getNameMenu());
			menu.setPriceTotal(precio(entity.getProduct(),entity.getDiscount()));
			menu.setDiscount(entity.getDiscount());
			menu.setMenutype(entity.getMenutype());
			menu.setMenustate(entity.getMenustate());
			menu.setProduct(entity.getProduct());
			menuRepository.save(menu);
			return true;
		}
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Menu> find() {
		return menuRepository.findAll();
	}

	@Override
	public boolean update(Menu entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Menu find(Long id) {
		Optional<Menu> optional = menuRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}



}
