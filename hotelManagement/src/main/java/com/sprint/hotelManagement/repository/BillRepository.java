package com.sprint.hotelManagement.repository;

import com.sprint.hotelManagement.entity.Bill;
import com.sprint.hotelManagement.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class BillRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Bill save(Bill bill) {
        if (bill.getId() == null) {
            String sql = "INSERT INTO bills (booking_id, issue_date, total_amount, payment_status, details) VALUES (?,?,?,?,?)";
            jdbcTemplate.update(sql, bill.getBooking().getId(), java.sql.Timestamp.valueOf(bill.getIssueDate()),
                    bill.getTotalAmount(), bill.getPaymentStatus(), bill.getDetails());
        } // Update logic omitted
        return bill;
    }

    public List<Bill> findByBookingUserId(Long userId) {
        // Requires JOIN with booking
        String sql = "SELECT b.* FROM bills b JOIN bookings bk ON b.booking_id = bk.id WHERE bk.user_id = ?";
        return jdbcTemplate.query(sql, new BillRowMapper(), userId);
    }

    public List<Bill> findAll() {
        return jdbcTemplate.query("SELECT * FROM bills", new BillRowMapper());
    }

    public Optional<Bill> findById(Long id) {
        List<Bill> list = jdbcTemplate.query("SELECT * FROM bills WHERE id=?", new BillRowMapper(), id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    private static class BillRowMapper implements RowMapper<Bill> {
        @Override
        public Bill mapRow(ResultSet rs, int rowNum) throws SQLException {
            Bill b = new Bill();
            b.setId(rs.getLong("id"));
            b.setIssueDate(rs.getTimestamp("issue_date").toLocalDateTime());
            b.setTotalAmount(rs.getDouble("total_amount"));
            b.setPaymentStatus(rs.getString("payment_status"));
            b.setDetails(rs.getString("details")); // Derby CLOB usage might vary but string usually works for limited
                                                   // size

            Booking bk = new Booking();
            bk.setId(rs.getLong("booking_id"));
            b.setBooking(bk);
            return b;
        }
    }
}
