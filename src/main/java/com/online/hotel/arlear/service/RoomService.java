package com.online.hotel.arlear.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.hotel.arlear.model.Room;
import com.online.hotel.arlear.repository.RoomRepository;
@Service
public class RoomService implements ServiceGeneric<Room>{

	@Autowired
	private RoomRepository roomRepository;
	
	@Override
	public boolean create(Room entity) {
		// TODO Auto-generated method stub
		if(findByRoomNumber(entity.getRoomNumber())==null) {
			return roomRepository.save(entity) != null;
		}else {
			return false;
		}

	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Room> find() {
		return roomRepository.findAll().stream().sorted(Comparator.comparingInt(Room::getRoomNumber))
				.collect(Collectors.toList());
	}
	
	public Room findByRoomNumber(Integer roomNUmber) {
		// TODO Auto-generated method stub
		Optional<Room> optional = roomRepository.findAll().stream().filter(p->p.getRoomNumber().equals(roomNUmber)).findAny();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}


	@Override
	public Room find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean update(Long id, Room entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Room entity) {
		// TODO Auto-generated method stub
		return false;
	}

}
