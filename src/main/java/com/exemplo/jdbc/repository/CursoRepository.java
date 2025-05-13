package com.exemplo.jdbc.repository;

import com.exemplo.jdbc.model.Curso;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CursoRepository {

    private final JdbcTemplate jdbcTemplate;

    public CursoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Curso save(Curso curso) {
        String sql = "INSERT INTO curso (titulo, duracao_horas, instrutor_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, curso.getTitulo(), curso.getDuracaoHoras(), curso.getInstrutorId());
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        curso.setId(id);
        return curso;
    }

    public Curso findById(Long id) {
        String sql = "SELECT * FROM curso WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToCurso, id);
    }

    public List<Curso> findAll() {
        String sql = "SELECT * FROM curso";
        return jdbcTemplate.query(sql, this::mapRowToCurso);
    }

    public void update(Curso curso) {
        String sql = "UPDATE curso SET titulo = ?, duracao_horas = ?, instrutor_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, curso.getTitulo(), curso.getDuracaoHoras(), curso.getInstrutorId(), curso.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM curso WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Curso> findByFilters(String titulo, Double minDuracao, Double maxDuracao, Long instrutorId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM curso WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (titulo != null && !titulo.isEmpty()) {
            sql.append(" AND titulo LIKE ?");
            params.add("%" + titulo + "%");
        }
        if (minDuracao != null) {
            sql.append(" AND duracao_horas >= ?");
            params.add(minDuracao);
        }
        if (maxDuracao != null) {
            sql.append(" AND duracao_horas <= ?");
            params.add(maxDuracao);
        }
        if (instrutorId != null) {
            sql.append(" AND instrutor_id = ?");
            params.add(instrutorId);
        }

        return jdbcTemplate.query(sql.toString(), ps -> {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
        }, this::mapRowToCurso);
    }



    public List<Curso> findByInstrutorId(Long instrutorId) {
        String sql = "SELECT c.* FROM curso c JOIN instrutor i ON c.instrutor_id = i.id WHERE i.id = ?";
        return jdbcTemplate.query(sql, this::mapRowToCurso, instrutorId);
    }

    public void saveAll(List<Curso> cursos) {
        String sql = "INSERT INTO curso (titulo, duracao_horas, instrutor_id) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, cursos, cursos.size(), (ps, curso) -> {
            ps.setString(1, curso.getTitulo());
            ps.setDouble(2, curso.getDuracaoHoras());
            ps.setObject(3, curso.getInstrutorId());
        });
    }

    private Curso mapRowToCurso(ResultSet rs, int rowNum) throws SQLException {
        return new Curso(
                rs.getLong("id"),
                rs.getString("titulo"),
                rs.getDouble("duracao_horas"),
                rs.getObject("instrutor_id") != null ? rs.getLong("instrutor_id") : null
        );
    }
}