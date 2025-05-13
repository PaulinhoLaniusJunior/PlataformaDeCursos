package com.exemplo.jdbc.controller;

import com.exemplo.jdbc.model.Instrutor;
import com.exemplo.jdbc.repository.InstrutorRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instrutores")
public class InstrutorController {

    private final InstrutorRepository instrutorRepository;

    public InstrutorController(InstrutorRepository instrutorRepository) {
        this.instrutorRepository = instrutorRepository;
    }

    @PostMapping
    public ResponseEntity<Instrutor> criar(@Valid @RequestBody Instrutor instrutor) {
        return ResponseEntity.ok(instrutorRepository.save(instrutor));
    }

    @GetMapping
    public ResponseEntity<List<Instrutor>> listar() {
        return ResponseEntity.ok(instrutorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrutor> buscarPorId(@PathVariable Long id) {
        Instrutor instrutor = instrutorRepository.findById(id);
        return instrutor != null ? ResponseEntity.ok(instrutor) : ResponseEntity.notFound().build();
    }
}