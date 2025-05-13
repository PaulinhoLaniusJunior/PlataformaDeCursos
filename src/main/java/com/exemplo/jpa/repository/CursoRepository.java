package com.exemplo.jpa.repository;

import com.exemplo.jpa.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByTituloContainingIgnoreCase(String titulo);

    List<Curso> findByDuracaoHorasBetween(Double min, Double max);

    List<Curso> findByInstrutorId(Long instrutorId);

    @Query("SELECT c FROM Curso c JOIN FETCH c.instrutor WHERE c.instrutor.id = :instrutorId AND c.duracaoHoras >= :minDuracao")
    List<Curso> findByInstrutorIdAndMinDuracao(Long instrutorId, Double minDuracao);
}