package com.sprint.hotelManagement.repository;

import com.sprint.hotelManagement.entity.Booking;
import com.sprint.hotelManagement.entity.Room;
import com.sprint.hotelManagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class BookingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Booking save(Booking booking) {
        if (booking.getId() == null) {
            String sql = "INSERT INTO bookings (user_id, room_id, check_in_date, check_out_date, num_adults, num_children, status, total_amount, booking_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    booking.getUser().getId(),
                    booking.getRoom().getId(),
                    java.sql.Date.valueOf(booking.getCheckInDate()),
                    java.sql.Date.valueOf(booking.getCheckOutDate()),
                    booking.getNumAdults(),
                    booking.getNumChildren(),
                    booking.getStatus(),
                    booking.getTotalAmount(),
                    java.sql.Timestamp.valueOf(booking.getBookingDate()));
        } else {
            String sql = "UPDATE bookings SET status=?, total_amount=?, check_in_date=?, check_out_date=? WHERE id=?";
            jdbcTemplate.update(sql, booking.getStatus(), booking.getTotalAmount(),
                    java.sql.Date.valueOf(booking.getCheckInDate()),
                    java.sql.Date.valueOf(booking.getCheckOutDate()),
                    booking.getId());
        }
        return booking;
    }

    public List<Booking> findByUserId(Long userId) {
        List<Booking> bookings = jdbcTemplate.query("SELECT * FROM bookings WHERE user_id = ?", new BookingRowMapper(),
                userId);
        bookings.forEach(this::populateDetails);
        return bookings;
    }

    public List<Booking> findAll() {
        List<Booking> bookings = jdbcTemplate.query("SELECT * FROM bookings", new BookingRowMapper());
        bookings.forEach(this::populateDetails);
        return bookings;
    }

    public Optional<Booking> findById(Long id) {
        List<Booking> bookings = jdbcTemplate.query("SELECT * FROM bookings WHERE id=?", new BookingRowMapper(), id);
        if (bookings.isEmpty())
            return Optional.empty();
        Booking booking = bookings.get(0);
        populateDetails(booking);
        return Optional.of(booking);
    }

    private void populateDetails(Booking booking) {
        if (booking.getUser() != null && booking.getUser().getId() != null) {
            userRepository.findById(booking.getUser().getId()).ifPresent(booking::setUser);
        }
        if (booking.getRoom() != null && booking.getRoom().getId() != null) {
            roomRepository.findById(booking.getRoom().getId()).ifPresent(booking::setRoom);
        }
    }

    private static class BookingRowMapper implements RowMapper<Booking> {
        @Override
        public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
            Booking b = new Booking();
            b.setId(rs.getLong("id"));
            b.setCheckInDate(rs.getDate("check_in_date").toLocalDate());
            b.setCheckOutDate(rs.getDate("check_out_date").toLocalDate());
            b.setNumAdults(rs.getInt("num_adults"));
            b.setNumChildren(rs.getInt("num_children"));
            b.setStatus(rs.getString("status"));
            b.setTotalAmount(rs.getDouble("total_amount"));
            b.setBookingDate(rs.getTimestamp("booking_date").toLocalDateTime());

            User u = new User();
            u.setId(rs.getLong("user_id"));
            b.setUser(u);

            Room r = new Room();
            r.setId(rs.getLong("room_id"));
            b.setRoom(r);

            return b;
        }
    }
}
