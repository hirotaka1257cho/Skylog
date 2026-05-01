package com.example.weather.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.weather.domain.User;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        return user;
    };

    public User findByEmail(String email){
        String sql = "SELECT * FROM users WHERE email = ?;";
        User user = jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, email);
        return user;
    }

    public void insert(User user){
        jdbcTemplate.update(
            "INSERT INTO users(name, email, password, created_at, updated_at) "
            +"VALUES(?, ?, ?, ?, ?)",
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}
