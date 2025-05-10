package com.exemplo.jdbc.service;

import com.exemplo.jdbc.model.Curso;
import com.exemplo.jdbc.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CursoService {
    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveCursoWithLog(Curso curso) throws Exception {
        if (curso.getDuracaoHoras() <= 0) {
            throw new Exception("A duração do curso deve ser positiva.");
        }

        cursoRepository.save(curso);

        String logSql = "INSERT INTO log_curso (curso_id, titulo, data_criacao) VALUES (?, ?, ?)";
        cursoRepository.getJdbcTemplate().update(logSql, curso.getId(), curso.getTitulo(), LocalDateTime.now());
    }
}
