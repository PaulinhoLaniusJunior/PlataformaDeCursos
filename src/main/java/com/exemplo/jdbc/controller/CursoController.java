package com.exemplo.jdbc.controller;

import com.exemplo.jdbc.model.Curso;
import com.exemplo.jdbc.service.CursoService;
import com.exemplo.jdbc.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;
    private final CursoRepository cursoRepository;

    public CursoController(CursoService cursoService, CursoRepository cursoRepository) {
        this.cursoService = cursoService;
        this.cursoRepository = cursoRepository;
    }

    @PostMapping
    public ResponseEntity<Curso> criar(@Valid @RequestBody Curso curso) {
        return ResponseEntity.ok(cursoService.criarCurso(curso));
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(cursoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {
        Curso curso = cursoRepository.findById(id);
        return curso != null ? ResponseEntity.ok(curso) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizar(@PathVariable Long id, @Valid @RequestBody Curso curso) {
        curso.setId(id);
        cursoRepository.update(curso);
        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cursoRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<Curso>> filtrar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Double minDuracao,
            @RequestParam(required = false) Double maxDuracao,
            @RequestParam(required = false) Long instrutorId) {
        return ResponseEntity.ok(cursoRepository.findByFilters(titulo, minDuracao, maxDuracao, instrutorId));
    }
}