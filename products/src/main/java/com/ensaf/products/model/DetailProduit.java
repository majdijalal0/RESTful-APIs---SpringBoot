package com.ensaf.products.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DetailProduit {
    //Définition des attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fabricant;
    private String paysOrigine;
    private String composition;
    private String dosage;
    private String indications;

    //Définir la relation OneToOne avec Produit
    @OneToOne(mappedBy = "detailProduit")
    //L'objet produit concerné par ces détails
    private Produit produit;
}
