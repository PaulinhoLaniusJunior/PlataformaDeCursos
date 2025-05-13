package com.exemplo.jdbc.repository;

import com.exemplo.jdbc.model.Curso;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InstrutorRepository instrutorRepository;

    @Test
    void testSaveAndFindById() {
        Curso curso = new Curso(null, "Java", 40.0, null);
        Curso saved = cursoRepository.save(curso);
        Curso found = cursoRepository.findById(saved.getId());
        assertEquals("Java", found.getTitulo());
    }

    @Test
    void testFindByFilters() {
        Curso curso = new Curso(null, "Spring Boot", 20.0, null);
        cursoRepository.save(curso);
        List<Curso> cursos = cursoRepository.findByFilters("Spring", 10.0, 30.0, null);
        assertFalse(cursos.isEmpty());
    }

    @Test
    void testBatchSave() {
        List<Curso> cursos = Arrays.asList(
                new Curso(null, "Curso 1", 10.0, null),
                new Curso(null, "Curso 2", 15.0, null)
        );
        cursoRepository.saveAll(cursos);
        List<Curso> found = cursoRepository.findAll();
        assertEquals(2, found.size());
    }
}