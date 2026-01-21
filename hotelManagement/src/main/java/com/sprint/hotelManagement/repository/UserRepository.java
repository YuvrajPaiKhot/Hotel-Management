package com.sprint.hotelManagement.repository;

import com.sprint.hotelManagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User save(User user) {
        if (user.getId() == null) {
            String sql = "INSERT INTO users (username, email, password, mobile, address) VALUES (?, ?, ?, ?, ?)";
            // We need the generated ID
            org.springframework.jdbc.support.KeyHolder keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                java.sql.PreparedStatement ps = connection.prepareStatement(sql,
                        java.sql.Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getMobile());
                ps.setString(5, user.getAddress());
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                user.setId(keyHolder.getKey().longValue());
            }

            saveRoles(user);
            return user;
        } else {
            String sql = "UPDATE users SET mobile = ?, address = ?, email = ? WHERE id = ?";
            jdbcTemplate.update(sql, user.getMobile(), user.getAddress(), user.getEmail(), user.getId());
            saveRoles(user);
            return user;
        }
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), username);
        if (users.isEmpty())
            return Optional.empty();

        User user = users.get(0);
        user.setRoles(getRolesForUser(user.getId()));
        return Optional.of(user);
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), id);
        if (users.isEmpty())
            return Optional.empty();

        User user = users.get(0);
        user.setRoles(getRolesForUser(user.getId()));
        return Optional.of(user);
    }

    public Boolean existsByUsername(String username) {
        String sql = "SELECT count(*) FROM users WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    public Boolean existsByEmail(String email) {
        String sql = "SELECT count(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
        users.forEach(u -> u.setRoles(getRolesForUser(u.getId())));
        return users;
    }

    private Set<String> getRolesForUser(Long userId) {
        // For simplicity storing roles as comma separated in user_roles table or better
        // yet just a simple String in new schema?
        // Schema.sql had user_roles table
        // But for AuthController we save roles... we need to INSERT into user_roles
        // too.

        // Let's implement basics.
        // Reading from user_roles table
        // We simplified schema to `user_roles(user_id, roles)`?
        // Yes: CREATE TABLE user_roles (user_id BIGINT, roles VARCHAR(50));
        String sql = "SELECT roles FROM user_roles WHERE user_id = ?";
        List<String> roles = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("roles"), userId);
        return new HashSet<>(roles);
    }

    // Helper to save roles
    public void saveRoles(User user) {
        // Clear existing
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id = ?", user.getId());
        // Insert new
        String sql = "INSERT INTO user_roles (user_id, roles) VALUES (?, ?)";
        for (String role : user.getRoles()) {
            jdbcTemplate.update(sql, user.getId(), role);
        }
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setMobile(rs.getString("mobile"));
            user.setAddress(rs.getString("address"));
            return user;
        }
    }
}
