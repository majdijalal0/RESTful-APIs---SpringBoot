package com.ensaf.products.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Fournisseur {
    //Définition des attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String contact;
    private String adresse;

    //Définir la relation oneToMany avec Produit
    @OneToMany(
            mappedBy = "fournisseur",
            cascade = CascadeType.ALL, //Les opérations appliquées au parent sont aussi appliquées aux fils
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    //La liste des produits provided par ce fournisseur
    private List<Produit> produits = new ArrayList<>();

}
