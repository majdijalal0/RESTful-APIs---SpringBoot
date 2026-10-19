package com.ensaf.products.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;

    //Relation ManyToMany avec Produit
    @ManyToMany(
            mappedBy = "tags",
            fetch = FetchType.LAZY
    )
    //Set des produits de ce tag, pour un tag on ne peut pas avoir des repitions pur le meme produit
    private Set<Produit> produits = new HashSet<>();
}
