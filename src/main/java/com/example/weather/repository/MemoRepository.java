package com.example.weather.repository;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.weather.domain.Memo;

@Repository
public class MemoRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Memo> MEMO_ROW_MAPPER = (rs, i) -> {
        Memo memo = new Memo();
        memo.setId(rs.getInt("id"));
        memo.setDates(rs.getDate("dates"));
        memo.setTexts(rs.getString("texts"));
        memo.setWeatherSnapshot(rs.getString("weather_snapshot"));
        memo.setTemperatureSnapshot(rs.getDouble("temperature_snapshot"));
        memo.setUserId(rs.getInt("user_id"));
        memo.setCreatedAt(rs.getTimestamp("created_at"));
        memo.setUpdatedAt(rs.getTimestamp("updated_at"));
        memo.setCity(rs.getString("city"));
        return memo;
    };

    public List<Memo> getMemoList() {
        String sql = "SELECT id, dates, texts, weather_snapshot, temperature_snapshot, user_id, created_at, updated_at, city FROM memos ORDER BY id;";
        List<Memo> memoList = jdbcTemplate.query(sql, MEMO_ROW_MAPPER);
        return memoList;
    }

    public List<Memo> getMemoListByUserId(int id){
        String sql = "SELECT * FROM memos WHERE user_id = ?;";
        List<Memo> memoList = jdbcTemplate.query(sql, MEMO_ROW_MAPPER, id);
        return memoList;
    }

    public void create(Memo memo) {
        jdbcTemplate.update(
                "INSERT INTO memos(temperature_snapshot, weather_snapshot, texts, city, dates, created_at, updated_at, user_id) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                memo.getTemperatureSnapshot(),
                memo.getWeatherSnapshot(),
                memo.getTexts(),
                memo.getCity(),
                memo.getDates(),
                memo.getCreatedAt(),
                memo.getUpdatedAt(),
                memo.getUserId());
    }

    public Memo findById(int id){
        String sql = "SELECT * FROM memos WHERE id = ?;";
        Memo memo = jdbcTemplate.queryForObject(sql, MEMO_ROW_MAPPER, id);
        return memo;                
    }

    public void update(Memo memo){
        jdbcTemplate.update(
            "UPDATE memos SET temperature_snapshot = ?, weather_snapshot = ?, texts = ?, city = ?, dates = ?, updated_at = ? WHERE id = ?;",
            memo.getTemperatureSnapshot(),
            memo.getWeatherSnapshot(),
            memo.getTexts(),
            memo.getCity(),
            memo.getDates(),
            memo.getUpdatedAt(),
            memo.getId());
        }

    public void deleteById(int id){
        jdbcTemplate.update("DELETE FROM memos WHERE id = ?", id);
    }
}
