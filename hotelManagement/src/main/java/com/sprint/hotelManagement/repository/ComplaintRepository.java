package com.sprint.hotelManagement.repository;

import com.sprint.hotelManagement.entity.Complaint;
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
public class ComplaintRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Complaint save(Complaint complaint) {
        if (complaint.getId() == null) {
            String sql = "INSERT INTO complaints (user_id, title, description, category, status, submission_date, resolution_notes) VALUES (?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql, complaint.getUser().getId(), complaint.getTitle(), complaint.getDescription(),
                    complaint.getCategory(), complaint.getStatus(),
                    java.sql.Timestamp.valueOf(complaint.getSubmissionDate()), complaint.getResolutionNotes());
        } else {
            String sql = "UPDATE complaints SET status=?, resolution_notes=? WHERE id=?";
            jdbcTemplate.update(sql, complaint.getStatus(), complaint.getResolutionNotes(), complaint.getId());
        }
        return complaint;
    }

    public List<Complaint> findByUserId(Long userId) {
        return jdbcTemplate.query("SELECT * FROM complaints WHERE user_id=?", new ComplaintRowMapper(), userId);
    }

    public List<Complaint> findAll() {
        return jdbcTemplate.query("SELECT * FROM complaints", new ComplaintRowMapper());
    }

    public Optional<Complaint> findById(Long id) {
        List<Complaint> list = jdbcTemplate.query("SELECT * FROM complaints WHERE id=?", new ComplaintRowMapper(), id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    private static class ComplaintRowMapper implements RowMapper<Complaint> {
        @Override
        public Complaint mapRow(ResultSet rs, int rowNum) throws SQLException {
            Complaint c = new Complaint();
            c.setId(rs.getLong("id"));
            c.setTitle(rs.getString("title"));
            c.setDescription(rs.getString("description"));
            c.setCategory(rs.getString("category"));
            c.setStatus(rs.getString("status"));
            c.setSubmissionDate(rs.getTimestamp("submission_date").toLocalDateTime());
            c.setResolutionNotes(rs.getString("resolution_notes"));

            User u = new User();
            u.setId(rs.getLong("user_id"));
            c.setUser(u);

            return c;
        }
    }
}
