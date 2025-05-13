package com.exemplo.jdbc.service;

import com.exemplo.jdbc.model.Curso;
import com.exemplo.jdbc.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final JdbcTemplate jdbcTemplate;

    public CursoService(CursoRepository cursoRepository, JdbcTemplate jdbcTemplate) {
        this.cursoRepository = cursoRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(rollbackFor = Exception.class)
    public Curso criarCurso(Curso curso) {
        if (curso.getDuracaoHoras() < 0) {
            throw new IllegalArgumentException("Duração não pode ser negativa");
        }

        Curso savedCurso = cursoRepository.save(curso);

        String sql = "INSERT INTO log_curso (curso_id, titulo, data_criacao) VALUES (?, ?, NOW())";
        jdbcTemplate.update(sql, savedCurso.getId(), savedCurso.getTitulo());

        return savedCurso;
    }
}
