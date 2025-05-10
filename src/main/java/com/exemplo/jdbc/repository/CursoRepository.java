package com.exemplo.jdbc.repository;

import com.exemplo.jdbc.model.Curso;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CursoRepository {
    private final JdbcTemplate jdbcTemplate;

    public CursoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    private RowMapper<Curso> mapRowToCurso() {
        return (rs, rowNum) -> new Curso(
                rs.getLong("id"),
                rs.getString("titulo"),
                rs.getDouble("duracao_horas"),
                rs.getLong("instrutor_id")
        );
    }

    public void save(Curso curso) {
        String sql = "INSERT INTO curso (titulo, duracao_horas, instrutor_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, curso.getTitulo(), curso.getDuracaoHoras(), curso.getInstrutorId());
    }

    public Curso findById(Long id) {
        String sql = "SELECT * FROM curso WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, mapRowToCurso(), id);
    }

    public List<Curso> findAll() {
        String sql = "SELECT * FROM curso";
        return jdbcTemplate.query(sql, mapRowToCurso());
    }

    public void update(Curso curso) {
        String sql = "UPDATE curso SET titulo = ?, duracao_horas = ?, instrutor_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, curso.getTitulo(), curso.getDuracaoHoras(), curso.getInstrutorId(), curso.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM curso WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Curso> findByInstrutorId(Long instrutorId) {
        String sql = "SELECT * FROM curso WHERE instrutor_id = ?";
        return jdbcTemplate.query(sql, mapRowToCurso(), instrutorId);
    }

    public List<Curso> search(String titulo, Double minDuracao, Double maxDuracao, Long instrutorId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM curso WHERE 1=1 ");
        if (titulo != null) sql.append("AND LOWER(titulo) LIKE LOWER(?) ");
        if (minDuracao != null) sql.append("AND duracao_horas >= ? ");
        if (maxDuracao != null) sql.append("AND duracao_horas <= ? ");
        if (instrutorId != null) sql.append("AND instrutor_id = ? ");

        return jdbcTemplate.query(sql.toString(), preparedStatement -> {
            int index = 1;
            if (titulo != null) preparedStatement.setString(index++, "%" + titulo + "%");
            if (minDuracao != null) preparedStatement.setDouble(index++, minDuracao);
            if (maxDuracao != null) preparedStatement.setDouble(index++, maxDuracao);
            if (instrutorId != null) preparedStatement.setLong(index, instrutorId);
        }, mapRowToCurso());
    }

    public void batchInsert(List<Curso> cursos) {
        String sql = "INSERT INTO curso (titulo, duracao_horas, instrutor_id) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, cursos, cursos.size(), (ps, curso) -> {
            ps.setString(1, curso.getTitulo());
            ps.setDouble(2, curso.getDuracaoHoras());
            ps.setLong(3, curso.getInstrutorId());
        });
    }
}
