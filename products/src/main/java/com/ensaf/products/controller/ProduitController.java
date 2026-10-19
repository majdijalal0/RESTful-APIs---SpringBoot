package com.ensaf.products.controller;

import com.ensaf.products.dto.ProduitRequestDTO;
import com.ensaf.products.dto.ProduitResponseDTO;
import com.ensaf.products.service.ProduitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    //Récuperer tous les produits
    @GetMapping
    public ResponseEntity<List<ProduitResponseDTO>> getAll() {
        return ResponseEntity.ok(produitService.getAll());
    }


    //Récuperer un produit par son ID
    @GetMapping("/{id}")
    public ResponseEntity<ProduitResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.getById(id));
    }

    //Ajouter un nouveau produit
    @PostMapping
    public ResponseEntity<ProduitResponseDTO> create(@Valid @RequestBody ProduitRequestDTO request) {
        ProduitResponseDTO created = produitService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //Modifier un produit
    @PutMapping("/{id}")
    public ResponseEntity<ProduitResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody ProduitRequestDTO request) {
        return ResponseEntity.ok(produitService.update(id, request));
    }

    //Supprimer un produit
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}