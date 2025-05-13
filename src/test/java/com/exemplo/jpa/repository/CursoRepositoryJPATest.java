package com.exemplo.jpa.repository;

import com.exemplo.jpa.model.Curso;
import com.exemplo.jpa.model.Instrutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class CursoRepositoryJPATest {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InstrutorRepository instrutorRepository;

    @Test
    void testSaveAndFindByTitulo() {
        Instrutor instrutor = new Instrutor("João", "joao@example.com");
        instrutorRepository.save(instrutor);
        Curso curso = new Curso("Java", 40.0, instrutor);
        cursoRepository.save(curso);
        List<Curso> cursos = cursoRepository.findByTituloContainingIgnoreCase("Java");
        assertFalse(cursos.isEmpty());
    }

    @Test
    void testFindByInstrutorIdAndMinDuracao() {
        Instrutor instrutor = new Instrutor("Maria", "maria@example.com");
        instrutorRepository.save(instrutor);
        Curso curso = new Curso("Spring", 30.0, instrutor);
        cursoRepository.save(curso);
        List<Curso> cursos = cursoRepository.findByInstrutorIdAndMinDuracao(instrutor.getId(), 20.0);
        assertFalse(cursos.isEmpty());
    }
}