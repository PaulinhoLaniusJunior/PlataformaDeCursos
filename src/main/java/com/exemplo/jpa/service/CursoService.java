package com.exemplo.jpa.service;

import com.exemplo.jpa.model.Curso;
import com.exemplo.jpa.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Curso criarCurso(Curso curso) {
        if (curso.getDuracaoHoras() < 0) {
            throw new IllegalArgumentException("Duração não pode ser negativa");
        }

        Curso savedCurso = cursoRepository.save(curso);

        entityManager.createNativeQuery("INSERT INTO log_curso (curso_id, titulo, data_criacao) VALUES (?, ?, NOW())")
                .setParameter(1, savedCurso.getId())
                .setParameter(2, savedCurso.getTitulo())
                .executeUpdate();

        return savedCurso;
    }
}
