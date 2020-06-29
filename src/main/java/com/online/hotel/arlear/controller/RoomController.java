package com.online.hotel.arlear.controller;
import java.util.ArrayList;
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
import com.online.hotel.arlear.dto.MenuDTOUpdate;
import com.online.hotel.arlear.dto.MenuDTOFindUnity;
import com.online.hotel.arlear.dto.ObjectConverter;
import com.online.hotel.arlear.dto.ProductDTO;
import com.online.hotel.arlear.dto.ResponseCreateReservation;
import com.online.hotel.arlear.dto.ResponseDTO;
import com.online.hotel.arlear.dto.RoomDTO;
import com.online.hotel.arlear.dto.RoomUpdateDTO;
import com.online.hotel.arlear.dto.RoomDTOFind;
import com.online.hotel.arlear.exception.ErrorMessages;
import com.online.hotel.arlear.model.Menu;
import com.online.hotel.arlear.model.Product;
import com.online.hotel.arlear.model.Room;
import com.online.hotel.arlear.service.RoomService;
import com.online.hotel.arlear.util.Validation;

@RestController
@RequestMapping("/")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@Autowired
	private ObjectConverter objectConverter;
	
	@PostMapping(value="room")
	public ResponseEntity<?> createRoom(@RequestBody RoomDTO roomDTO) {
		ResponseDTO response;
		
		List<String> errors = Validation.applyValidationRoom(roomDTO);
		
		if(errors.size()==0) {
			Room room = objectConverter.converter(roomDTO);
			
			if(roomService.create(room)) {
				response = new ResponseCreateReservation(room.getId(),"OK",
						   ErrorMessages.CREATE_OK.getCode(),
						   ErrorMessages.CREATE_OK.getDescription("room"));
			}
			else {
				response = new ResponseDTO("ERROR",
						   ErrorMessages.CREATE_ERROR.getCode(),
						   ErrorMessages.CREATE_ERROR.getDescription("La habitacion:"+" "+roomDTO.getRoomNumber()+" ya se encuentra registrada"));
			}
		}else {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.CREATE_ERROR.getCode(),
						errors.toString());
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		
	}
	
	//Metodo para modificar HAbitaciones
	@PutMapping(value="room")
	public ResponseEntity<?> createRoom(@RequestBody RoomUpdateDTO roomUpdateDto){
		ResponseDTO response = new ResponseDTO();
		//System.out.print(menucreate.toString());
		RoomDTO roomDTO = roomUpdateDto;
		
		List<String> errors = Validation.applyValidationRoom(roomDTO);
		if(errors.size()==0) {
			Room room=objectConverter.converter(roomDTO);
			
			if(roomService.update(roomUpdateDto.getIdRoom(),room)) {
				response= new ResponseDTO("OK", 
						ErrorMessages.UPDATE_OK.getCode(),
						ErrorMessages.UPDATE_OK.getDescription("la habitación:"+" "+room.getRoomNumber()));
			}
			else {
				response= new ResponseDTO("ERROR", 
						ErrorMessages.UPDATE_ERROR.getCode(),
						ErrorMessages.UPDATE_ERROR.getDescription("la habitación No existe"));
			}
		}
		else {
			response=findList(errors);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping(value="rooms")
	public ResponseEntity<?> getRooms() {
		/*List<RoomResponseDTO> responseDTO = new ArrayList<RoomResponseDTO>();
		RoomResponseDTO RoomResponseDTO = new RoomResponseDTO();
		FloorDTO floorDTO = new FloorDTO();
		floorDTO.setFloor(floor);
		List<Room> rooms = roomService.find();
		int floor = 0;
		for(Room room: rooms) {
			if(floor == 0) {
				floor = room.getFloor();
				floorDTO.setFloor(floor);
			}else if(){
				
			}
			floor = room.getFloor();
			floorDTO.setFloor(room.getFloor());

			RoomResponseDTO.setFloors(Arrays.asList(New ));
		}
		*/
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(roomService.find());
	}
	
	@GetMapping(value="findRoom/{numberRoom}")
	public ResponseEntity<?> getRoom(@PathVariable Integer numberRoom) {
		ResponseDTO response = new ResponseDTO();
		Room room=roomService.findRoom(numberRoom);
		
		if(room!=null) {
			RoomDTOFind roomFind=objectConverter.converterRoomUnity(room);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(roomFind);
		}
		else {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.SEARCH_ERROR.getCode(),
					   ErrorMessages.SEARCH_ERROR.getDescription("No existe ninguna habitación número: "+numberRoom));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body((response));
		}
	
	}
	
	@DeleteMapping(value="room/{idRoom}")
	public ResponseEntity<?> deleteRoom(@PathVariable Long idRoom) {
		ResponseDTO response = new ResponseDTO();
		//validacion

		if(!roomService.delete(idRoom)) {
			response = new ResponseDTO("ERROR",
					   ErrorMessages.DELETED_ERROR.getCode(),
					   ErrorMessages.DELETED_ERROR.getDescription("la habitacion. ID incorrecto"));
		}
		
		else	{
			response = new ResponseDTO("OK",
					   ErrorMessages.DELETED_OK.getCode(),
					   ErrorMessages.DELETED_OK.getDescription("la habitacion"));
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		
	}
	
	//Funcion para listar más de un error
	public ResponseDTO findList(List<?> errors){
		ResponseDTO response = new ResponseDTO();
		List<String> code= new ArrayList<>();
		List<String> messages= new ArrayList<>();
		int j=0;
		int i;
		for (i=0; i<errors.size();i=((2*i)/2)+2) {
			response= new ResponseDTO("ERROR",errors.get(j).toString(),errors.get(j+1).toString());
			code.add(response.getCode().toString());
			messages.add(response.getMessage().toString());
			j=((2*j)/2)+2;
		}
		response.setStatus("ERROR");
		response.setCode(code.toString());
		response.setMessage(messages.toString());
		return response;
	}
}
