package com.sprint.hotelManagement.service;

import com.sprint.hotelManagement.entity.Room;
import com.sprint.hotelManagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room roomDetails) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room != null) {
            room.setType(roomDetails.getType());
            room.setPrice(roomDetails.getPrice());
            room.setAmenities(roomDetails.getAmenities());
            room.setMaxOccupancy(roomDetails.getMaxOccupancy());
            room.setDescription(roomDetails.getDescription());
            room.setAvailable(roomDetails.isAvailable());
            return roomRepository.save(room);
        }
        return null;
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public List<Room> searchRooms(LocalDate checkIn, LocalDate checkOut) {
        return roomRepository.findAvailableRooms(checkIn, checkOut);
    }

    public List<Room> searchRoomsByCriteria(String criteria) {
        return roomRepository.findByRoomNumberContainingOrTypeContaining(criteria, criteria);
    }
}
