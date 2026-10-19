package com.ensaf.products.controller;

import com.ensaf.products.dto.FournisseurDTO;
import com.ensaf.products.service.FournisseurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
@RequiredArgsConstructor
public class FournisseurController {

    private final FournisseurService fournisseurService;

    // Récupérer tous les fournisseurs
    @GetMapping
    public ResponseEntity<List<FournisseurDTO>> getAll() {
        return ResponseEntity.ok(fournisseurService.getAll());
    }

    // Récupérer un fournisseur par son ID
    @GetMapping("/{id}")
    public ResponseEntity<FournisseurDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(fournisseurService.getById(id));
    }

    // Créer un nouveau fournisseur
    @PostMapping
    public ResponseEntity<FournisseurDTO> create(@Valid @RequestBody FournisseurDTO dto) {
        FournisseurDTO created = fournisseurService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Modifier un fournisseur existant
    @PutMapping("/{id}")
    public ResponseEntity<FournisseurDTO> update(@PathVariable Long id,
                                                 @Valid @RequestBody FournisseurDTO dto) {
        return ResponseEntity.ok(fournisseurService.update(id, dto));
    }

    // Supprimer un fournisseur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fournisseurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}