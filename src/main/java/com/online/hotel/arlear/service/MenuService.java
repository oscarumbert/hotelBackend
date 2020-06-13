package com.online.hotel.arlear.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.enums.MenuType;
import com.online.hotel.arlear.enums.UserType;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.UserHotel;
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
		if(find(id)==null) {
			return false;
		}
		else {
			menuRepository.findById(id).get().getProduct().clear();
			menuRepository.deleteById(id);//
			return true;
		}
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
	
	//Filtro que busca menu por tipo y nombre de menu
	public List<Menu> FilterMenu(Menu menu) {
	
		if(menu.getNameMenu()==null) {
			if(menu.getMenutype()!=null) {
				return findType(menu.getMenutype());
			}
			else {
				return null;
			}
		}
		else {
			//Todo equals
			if(!menu.getNameMenu().equals("") && menu.getMenutype()==null) {
				return findName(menu.getNameMenu());
			}
			
			else if(menu.getMenutype()!=null && menu.getNameMenu().equals("") ) {
				return findType(menu.getMenutype());
			}
			
			else if(menu.getMenutype()!= null && !menu.getNameMenu().equals("")) {
				return findNameType(menu.getNameMenu(),menu.getMenutype());
			}
			return null;
		}
	}
	
	public List<Menu> findName(String name) {
		List<Menu> menu=menuRepository.findAll().stream().filter(p -> p.getNameMenu().equals(name)).collect(Collectors.toList());
		
		if(!menu.isEmpty()) {
			return menu;
		}
		else {
		return null;
		}
	}

	public List<Menu> findType(MenuType type) {
	List<Menu> menu=menuRepository.findAll().stream().filter(p -> p.getMenutype().equals(type)).collect(Collectors.toList());
		
		if(!menu.isEmpty()) {
			return menu;
		}
		else {
			return null;
		}
	}

	public List<Menu> findNameType(String name, MenuType type) {
		List<Menu> menu=menuRepository.findAll().stream().filter(p -> p.getNameMenu().equals(name) && p.getMenutype().equals(type)).collect(Collectors.toList());
		
		if(!menu.isEmpty()) {
			return menu;
		}
		else {
			return null;
		}
	}
	
	/*public List<Menu> FilterPrices(Integer minPrice, Integer maxPrice) {
		List<Menu> menues=findPriceRange(minPrice,maxPrice);
		if(!menues.isEmpty()) {
				return menues;
		}
		return null;
	}
	
	//Filtro que busca menu por tipo, nombre de menu y rango de precio
	 public List<Menu> FilterMenuPrice(Menu menu, Integer min, Integer max) {
	
		if((menu.getNameMenu()==null || menu.getNameMenu().equals(""))  && min==null && max==null) {
			if(menu.getMenutype()!=null) {
				return findType(menu.getMenutype());
			}
		}
		
		else if( menu.getMenutype()==null  && min==null && max==null) {
			if(menu.getNameMenu()!=null || !menu.getNameMenu().equals("")){
				return findName(menu.getNameMenu());
			}
			else {
				return null;
			}
		}
		
		else if((menu.getNameMenu()==null || menu.getNameMenu().equals("")) && menu.getMenutype()==null && min==null){
			List<Menu> menues=findPriceRangeMax(max);
			if(!menues.isEmpty()) {
					return menues;
			}
		}
		
		else if((menu.getNameMenu()==null || menu.getNameMenu().equals("")) && menu.getMenutype()==null && max==null){
			List<Menu> menues=findPriceRangeMin(min);
			if(!menues.isEmpty()) {
					return menues;
			}
		}
		
		else if((menu.getNameMenu()==null || menu.getNameMenu().equals("")) && menu.getMenutype()==null){
			List<Menu> menues=findPriceRange(min,max);
			if(!menues.isEmpty()) {
					return menues;
			}
		}
		
		else if(max==null && min==null){
			return findNameType(menu.getNameMenu(),menu.getMenutype());
		}
		
		else if(menu.getMenutype()==null && min==null) {
			return findNameMax(menu.getNameMenu(),max);
		}
		
		else if(menu.getMenutype()==null && max==null ) {
			return findNameMin(menu.getNameMenu(),min);
		}
		
		else if((menu.getNameMenu()==null || menu.getNameMenu().equals(""))  && min==null) {
			return findTypeMax(menu.getMenutype(),max);
		}
		
		else if((menu.getNameMenu()==null || menu.getNameMenu().equals(""))  && max==null ) {
			return findTypeMin(menu.getMenutype(),max);
		}
		
		else if((menu.getNameMenu()!=null || !menu.getNameMenu().equals("")) && menu.getMenutype()!=null && min!=null && max!=null){
			return findNameTypeMinMax(menu.getNameMenu(),menu.getMenutype(),min,max);
		}
		
		else if(max==null){
			return findNameTypeMin(menu.getNameMenu(),menu.getMenutype(),min);
		}
		
		else if(min==null){
			return findNameTypeMax(menu.getNameMenu(),menu.getMenutype(),max);
		}
		
		else if(menu.getMenutype()==null){
			return findNameMinMax(menu.getNameMenu(),min,max);
		}
		
		else if((menu.getNameMenu()==null || menu.getNameMenu().equals(""))){
			return findTypeMinMax(menu.getMenutype(),min,max);
		}
		
		return null;
	}
		private List<Menu> findTypeMin(MenuType menutype, Integer max) {
		return menuRepository.findAll().stream().filter(
				p -> p.getPriceTotal()<=max
				&&  p.getMenutype().equals(menutype)).collect(Collectors.toList());
		}
	
	private List<Menu> findTypeMax(MenuType menutype, Integer max) {
		return menuRepository.findAll().stream().filter(
				p ->  p.getPriceTotal()<=max
				&&  p.getMenutype().equals(menutype)).collect(Collectors.toList());
		}
	
	private List<Menu> findNameMin(String nameMenu, Integer min) {
		return menuRepository.findAll().stream().filter(
				p -> p.getPriceTotal()>=min &&  p.getNameMenu().equals(nameMenu)).collect(Collectors.toList());
		}
	
	private List<Menu> findNameMax(String nameMenu, Integer max) {
		return menuRepository.findAll().stream().filter(
				p -> p.getPriceTotal()<=max
				&&  p.getNameMenu().equals(nameMenu)).collect(Collectors.toList());
	}


	private List<Menu> findTypeMinMax(MenuType menutype, Integer min, Integer max) {
		return menuRepository.findAll().stream().filter(
				p -> p.getPriceTotal()>=min && p.getPriceTotal()<=max
				&&  p.getMenutype().equals(menutype)).collect(Collectors.toList());
	}

	private List<Menu> findNameMinMax(String nameMenu, Integer min, Integer max) {
		return menuRepository.findAll().stream().filter(
				p -> p.getPriceTotal()>=min && p.getPriceTotal()<=max
				&&  p.getNameMenu().equals(nameMenu)).collect(Collectors.toList());
	}

	private List<Menu> findNameTypeMax(String nameMenu, MenuType menutype, Integer max) {
		return menuRepository.findAll().stream().filter(
				p -> p.getPriceTotal()<=max &&  p.getNameMenu().equals(nameMenu) 
				&&  p.getMenutype().equals(menutype)).collect(Collectors.toList());
	}

	private List<Menu> findNameTypeMin(String nameMenu, MenuType menutype, Integer min) {
		return menuRepository.findAll().stream().filter(
				p -> p.getPriceTotal()>=min &&  p.getNameMenu().equals(nameMenu) 
				&&  p.getMenutype().equals(menutype)).collect(Collectors.toList());
	}

	private List<Menu> findNameTypeMinMax(String nameMenu, MenuType menutype, Integer min, Integer max) {
		return menuRepository.findAll().stream().filter(
				p -> p.getPriceTotal()>=min && p.getPriceTotal()<=max
				&&  p.getNameMenu().equals(nameMenu) &&  p.getMenutype().equals(menutype)).collect(Collectors.toList());
	}

	private List<Menu> findPriceRangeMax(Integer max) {
		return menuRepository.findAll().stream().filter(
				p -> p.getPriceTotal()>=0 && p.getPriceTotal()<=max).collect(Collectors.toList());
	}

	private List<Menu> findPriceRangeMin(Integer min) {
		return menuRepository.findAll().stream().filter(
				p -> p.getPriceTotal()>=min).collect(Collectors.toList());
	}

	private List<Menu> findPriceRange(Integer minPrice, Integer maxPrice) {
		return menuRepository.findAll().stream().filter(
				p -> p.getPriceTotal()>=minPrice && p.getPriceTotal()<=maxPrice).collect(Collectors.toList());
	}*/
	
}
