package com.exemplo.jdbc.repository;

import com.exemplo.jdbc.model.Instrutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InstrutorRepository {

    private final JdbcTemplate jdbcTemplate;

    public InstrutorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Instrutor save(Instrutor instrutor) {
        String sql = "INSERT INTO instrutor (nome, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, instrutor.getNome(), instrutor.getEmail());
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        instrutor.setId(id);
        return instrutor;
    }

    public Instrutor findById(Long id) {
        String sql = "SELECT * FROM instrutor WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToInstrutor, id);
    }

    public List<Instrutor> findAll() {
        String sql = "SELECT * FROM instrutor";
        return jdbcTemplate.query(sql, this::mapRowToInstrutor);
    }

    public void update(Instrutor instrutor) {
        String sql = "UPDATE instrutor SET nome = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, instrutor.getNome(), instrutor.getEmail(), instrutor.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM instrutor WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Instrutor mapRowToInstrutor(ResultSet rs, int rowNum) throws SQLException {
        return new Instrutor(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("email")
        );
    }
}