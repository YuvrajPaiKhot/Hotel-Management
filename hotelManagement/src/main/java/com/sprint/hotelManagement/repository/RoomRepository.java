package com.sprint.hotelManagement.repository;

import com.sprint.hotelManagement.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class RoomRepository {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        public List<Room> findAll() {
                return jdbcTemplate.query("SELECT * FROM rooms", new RoomRowMapper());
        }

        public Optional<Room> findById(Long id) {
                String sql = "SELECT * FROM rooms WHERE id = ?";
                List<Room> rooms = jdbcTemplate.query(sql, new RoomRowMapper(), id);
                return rooms.isEmpty() ? Optional.empty() : Optional.of(rooms.get(0));
        }

        public Room save(Room room) {
                if (room.getId() == null) {
                        String sql = "INSERT INTO rooms (room_number, type, price, is_available, max_occupancy, description, amenities) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        jdbcTemplate.update(sql, room.getRoomNumber(), room.getType(), room.getPrice(),
                                        room.isAvailable(), room.getMaxOccupancy(), room.getDescription(),
                                        room.getAmenities());
                        // In a real app we'd get the generated ID back
                } else {
                        String sql = "UPDATE rooms SET type=?, price=?, is_available=?, max_occupancy=?, description=?, amenities=? WHERE id=?";
                        jdbcTemplate.update(sql, room.getType(), room.getPrice(), room.isAvailable(),
                                        room.getMaxOccupancy(), room.getDescription(), room.getAmenities(),
                                        room.getId());
                }
                return room;
        }

        public void deleteById(Long id) {
                jdbcTemplate.update("DELETE FROM rooms WHERE id = ?", id);
        }

        public List<Room> findAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
                // Same logic as before but converted to Derby SQL
                // "SELECT r FROM Room r ..." becomes standard SQL
                // "SELECT * FROM rooms r WHERE r.id NOT IN (SELECT b.room_id FROM bookings b
                // WHERE ...)"

                String sql = "SELECT * FROM rooms r WHERE r.id NOT IN (" +
                                "SELECT b.room_id FROM bookings b WHERE " +
                                "(b.check_in_date <= ? AND b.check_out_date >= ?) " +
                                "AND b.status IN ('CONFIRMED', 'CHECKED_IN'))";

                return jdbcTemplate.query(sql, new RoomRowMapper(), java.sql.Date.valueOf(checkOut),
                                java.sql.Date.valueOf(checkIn));
        }

        public List<Room> findByRoomNumberContainingOrTypeContaining(String roomNumber, String type) {
                String sql = "SELECT * FROM rooms WHERE room_number LIKE ? OR type LIKE ?";
                return jdbcTemplate.query(sql, new RoomRowMapper(), "%" + roomNumber + "%", "%" + type + "%");
        }

        private static class RoomRowMapper implements RowMapper<Room> {
                @Override
                public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Room room = new Room();
                        room.setId(rs.getLong("id"));
                        room.setRoomNumber(rs.getString("room_number"));
                        room.setType(rs.getString("type"));
                        room.setPrice(rs.getDouble("price"));
                        room.setAvailable(rs.getBoolean("is_available"));
                        room.setMaxOccupancy(rs.getInt("max_occupancy"));
                        room.setDescription(rs.getString("description"));
                        room.setAmenities(rs.getString("amenities"));
                        return room;
                }
        }
}
