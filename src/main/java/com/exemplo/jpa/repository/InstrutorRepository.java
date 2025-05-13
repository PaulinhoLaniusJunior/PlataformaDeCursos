package com.exemplo.jpa.repository;

import com.exemplo.jpa.model.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
}