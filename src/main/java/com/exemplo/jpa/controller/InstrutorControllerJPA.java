package com.exemplo.jpa.controller;

import com.exemplo.jpa.model.Instrutor;
import com.exemplo.jpa.repository.InstrutorRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jpa/instrutores")
public class InstrutorControllerJPA {

    private final InstrutorRepository instrutorRepository;

    public InstrutorControllerJPA(InstrutorRepository instrutorRepository) {
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
        return instrutorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}