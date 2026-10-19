package com.ensaf.products.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter // Permet d'ajouter les getters
@Setter //Permet d'ajouter les setters
@NoArgsConstructor //Constructeur pour les
@AllArgsConstructor // Constructeur pour
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true) //toString function
@Entity
public class Produit {
    //Définition des attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private BigDecimal price;
    private String codeBar;
    private LocalDate dateExpiration;

    //La relation ManytoOne avec categorie
    @ManyToOne(fetch=FetchType.LAZY)
    //Définir la categorie de ce produit
    private Categorie categorie;

    //La relation ManyToOne avec fournisseur
    @ManyToOne(fetch=FetchType.LAZY)
    //Définir le fournisseur de ce produit
    private Fournisseur fournisseur;

    //La relation ManyToMany avec Tags
    @ManyToMany(
            fetch=FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE}
    )
    //Créer la table representant la relation entre les deux , inclure les Ids de chaque table
    @JoinTable(
            name="product_tags",
            joinColumns = @JoinColumn(name="product_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id")
    )
    //La liste des tags de ce produit , on utilise set pour ne pas avoir des répititions
    private Set<Tag> tags = new HashSet<>();

    //Des methodes d'ajout et suppression des tags de la liste
    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getProduits().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getProduits().remove(this);
    }


    //Relation OneToOne avec detail produit
    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    //Les détails du produit
    private DetailProduit detailProduit;
    //Méthode pour définir l'objet details
    public void setDetail(DetailProduit details) {
        this.detailProduit = details;
        if (details != null) {
            details.setProduit(this);
        }
    }
}
