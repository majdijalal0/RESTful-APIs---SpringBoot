package com.ensaf.products.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data //Permet de créer les getters et setters et constructeur
@Entity
public class Categorie {
    //Définition des attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;

    //On définit la relation avec Produit : one to many
    @OneToMany(
            mappedBy = "categorie",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    //Créer la liste des produits de chaque categorie
    private List<Produit> produits = new ArrayList<>();

}
