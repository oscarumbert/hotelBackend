package com.online.hotel.arlear.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.online.hotel.arlear.dto.MenuDTO;
import com.online.hotel.arlear.dto.MenuDTOFindUnity;
import com.online.hotel.arlear.dto.MenuDTOUpdate;
import com.online.hotel.arlear.dto.MenuDTOfind;
import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ProductDTO;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.exception.ErrorTools;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.UserHotel;
import com.online.hotel.arlear.service.MenuService;
import com.online.hotel.arlear.service.ProductService;
import com.online.hotel.arlear.util.Validation;


@RestController
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ObjectConverter objectConverter;
	
	//Metodo para buscar menues con nombre y tipo de menu
	@PostMapping(value="/get")
	public ResponseEntity<?> getMenus(@RequestBody MenuDTOfind menufind) {
		ResponseDTO response=new ResponseDTO();
		String name=menufind.getNameMenu();
		String menuType=menufind.getMenutype();
		if(name==null && menuType==null) {
			response=ErrorTools.searchError("Tiene que ingresar algunos de los campos para realizar la busqueda.");
		}
		else if(menuType==null && name!=null) {
				Menu menu = objectConverter.converter(menufind);
				List<Menu> menuList= menuService.FilterMenu(menu);
				if(menuList!=null) {
						return ResponseEntity.status(HttpStatus.ACCEPTED).body(menuList);
					}
				else{ 
						response=ErrorTools.searchError("");
						return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
					}	
			}
		else {
			boolean existMenuType = menuService.searchEnum(menuType);
			if(existMenuType) {
				Menu menu = objectConverter.converter(menufind);
				List<Menu> menuList= menuService.FilterMenu(menu);
				
				if(menuList!=null) {
						return ResponseEntity.status(HttpStatus.ACCEPTED).body(menuList);
				}
				else{ 
						response=ErrorTools.searchError("");
						return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
				}	
			}
			else {
					response=ErrorTools.searchError("Tipo de Menu no existe.");
			}
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
	}

	//Metodo para obtener todos los menus
	@PostMapping(value="/getAll")
	public ResponseEntity<?> getMenuAll() {	
		ResponseDTO response=new ResponseDTO();
		List<Menu> menus= menuService.find();
		if(menus.isEmpty()) {
			response=ErrorTools.searchError("");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		}
		else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(menus);
		}
	}

	//Metodo para obtener con el ID, un menu
	@GetMapping(value="{idMenu}")
	public ResponseEntity<?> getMenu(@PathVariable Long idMenu) {
		ResponseDTO response = new ResponseDTO();
		Menu menuModel=menuService.find(idMenu);
		if(menuModel!=null) {
			List<Product> producModel=menuModel.getProduct();
			List<ProductDTO> produc= productService.converterProduct(producModel);
			MenuDTOFindUnity menu=objectConverter.converterMenuUnity(menuModel);
			menu.setProducto(produc);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(menu);
		}
		else {
			response=ErrorTools.searchError("No existe ningun menu con el id: "+idMenu);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	}

	//Metodo para crear un menu
	@PostMapping
	public ResponseEntity<?> createMenu(@RequestBody MenuDTO menucreate){
		ResponseDTO response = new ResponseDTO();
		List<String> errors = Validation.applyValidationMenu(menucreate);
		
		if(errors.size()==0) {
			Menu menu=objectConverter.converter(menucreate);
			List<Product> product=productService.findIDProducts(menucreate.getProducto());
			
			if(product!=null) {
				menu.setProduct(product);
				
				if(menuService.create(menu)) {
					response= new ResponseDTO("OK", 
							ErrorMessages.CREATE_OK.getCode(),
							ErrorMessages.CREATE_OK.getDescription("El menu:"+" "+menucreate.getNameMenu()));
				}
				else {
					response= new ResponseDTO("ERROR", 
							ErrorMessages.CREATE_ERROR.getCode(),
							ErrorMessages.CREATE_ERROR.getDescription("El menu:"+" "+menu.getNameMenu()+" ya se encuentra registrado"));
				}
			}
			else {
				response= new ResponseDTO("ERROR", 
						ErrorMessages.CREATE_ERROR.getCode(),
						ErrorMessages.CREATE_ERROR.getDescription("Algun o algunos productos ingresados no existen"));
			}
		}
		else {
			response=ErrorTools.listErrors(errors);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Metodo para modificar un menu
	@PutMapping
	public ResponseEntity<?> createMenu(@RequestBody MenuDTOUpdate menuDtoUP){
		ResponseDTO response = new ResponseDTO();
		MenuDTO menudto= menuDtoUP;
		List<String> errors = Validation.applyValidationMenu(menudto);
		
		if(errors.size()==0) {
			Menu menu=objectConverter.converter(menudto);
			List<Product> product=productService.findIDProducts(menudto.getProducto());
			menu.setProduct(product);
			
			if(menuService.update(menuDtoUP.getIdMenu(),menu)) {
				response= new ResponseDTO("OK", 
						ErrorMessages.UPDATE_OK.getCode(),
						ErrorMessages.UPDATE_OK.getDescription("el producto:"+" "+menu.getNameMenu()));
			}
			else {
				response= new ResponseDTO("ERROR", 
						ErrorMessages.UPDATE_ERROR.getCode(),
						ErrorMessages.UPDATE_ERROR.getDescription("el menu. ID no existe"));
			}
		}
		else {
			response=ErrorTools.listErrors(errors);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Metodo para eliminar un menu, con el ID
	@DeleteMapping(value="{idMenu}")
	public ResponseEntity<?> deleteMenu(@PathVariable Long idMenu) {
		ResponseDTO response = new ResponseDTO();
		
		if(!menuService.delete(idMenu)) {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.DELETED_ERROR.getCode(),
					   ErrorMessages.DELETED_ERROR.getDescription("el menu. ID incorrecto"));
		}
		else	{
			response = new ResponseDTO("OK",
					   ErrorMessages.DELETED_OK.getCode(),
					   ErrorMessages.DELETED_OK.getDescription("el menu"));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
}
