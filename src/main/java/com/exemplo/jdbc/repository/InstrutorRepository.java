package com.exemplo.jdbc.repository;

import com.exemplo.jdbc.model.Instrutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstrutorRepository {
    private final JdbcTemplate jdbcTemplate;

    public InstrutorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Instrutor> mapRowToInstrutor() {
        return (rs, rowNum) -> new Instrutor(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("email")
        );
    }

    public void save(Instrutor instrutor) {
        String sql = "INSERT INTO instrutor (nome, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, instrutor.getNome(), instrutor.getEmail());
    }

    public Instrutor findById(Long id) {
        String sql = "SELECT * FROM instrutor WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, mapRowToInstrutor(), id);
    }

    public List<Instrutor> findAll() {
        String sql = "SELECT * FROM instrutor";
        return jdbcTemplate.query(sql, mapRowToInstrutor());
    }

    public void update(Instrutor instrutor) {
        String sql = "UPDATE instrutor SET nome = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, instrutor.getNome(), instrutor.getEmail(), instrutor.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM instrutor WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
