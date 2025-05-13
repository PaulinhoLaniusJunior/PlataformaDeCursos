package com.exemplo.jpa.controller;

import com.exemplo.jpa.model.Curso;
import com.exemplo.jpa.service.CursoService;
import com.exemplo.jpa.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jpa/cursos")
public class CursoControllerJPA {

    private final CursoService cursoService;
    private final CursoRepository cursoRepository;

    public CursoControllerJPA(CursoService cursoService, CursoRepository cursoRepository) {
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
        return cursoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizar(@PathVariable Long id, @Valid @RequestBody Curso curso) {
        curso.setId(id);
        return ResponseEntity.ok(cursoRepository.save(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<Curso>> filtrar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Double minDuracao,
            @RequestParam(required = false) Double maxDuracao,
            @RequestParam(required = false) Long instrutorId) {
        if (titulo != null) {
            return ResponseEntity.ok(cursoRepository.findByTituloContainingIgnoreCase(titulo));
        }
        if (minDuracao != null && maxDuracao != null) {
            return ResponseEntity.ok(cursoRepository.findByDuracaoHorasBetween(minDuracao, maxDuracao));
        }
        if (instrutorId != null) {
            return ResponseEntity.ok(cursoRepository.findByInstrutorId(instrutorId));
        }
        return ResponseEntity.ok(cursoRepository.findAll());
    }
}