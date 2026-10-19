package com.ensaf.products.controller;

import com.ensaf.products.dto.CategorieDTO;
import com.ensaf.products.service.CategorieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieService categorieService;

    // Récupérer toutes les catégories
    @GetMapping
    public ResponseEntity<List<CategorieDTO>> getAll() {
        return ResponseEntity.ok(categorieService.getAll());
    }

    // Récupérer une catégorie par son ID
    @GetMapping("/{id}")
    public ResponseEntity<CategorieDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categorieService.getById(id));
    }

    // Créer une nouvelle catégorie
    @PostMapping
    public ResponseEntity<CategorieDTO> create(@Valid @RequestBody CategorieDTO dto) {
        CategorieDTO created = categorieService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Modifier une catégorie existante
    @PutMapping("/{id}")
    public ResponseEntity<CategorieDTO> update(@PathVariable Long id,
                                               @Valid @RequestBody CategorieDTO dto) {
        return ResponseEntity.ok(categorieService.update(id, dto));
    }

    // Supprimer une catégorie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categorieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}