package com.ensaf.products.controller;

import com.ensaf.products.dto.TagDTO;
import com.ensaf.products.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // Récupérer tous les tags
    @GetMapping
    public ResponseEntity<List<TagDTO>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }

    // Récupérer un tag par son ID
    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getById(id));
    }

    // Créer un nouveau tag
    @PostMapping
    public ResponseEntity<TagDTO> create(@Valid @RequestBody TagDTO dto) {
        TagDTO created = tagService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    // Supprimer un tag
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}