package com.ensaf.products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProduitResponseDTO {

    private Long id;
    private String nom;
    private String description;
    private BigDecimal prix;
    private String codeBarre;
    private LocalDate dateExpiration;
    //On retourne seulement les noms de categorie et de fournisseur
    private String categorieNom;
    private String fournisseurNom;

    //On inclut les attributs de la classe DetailProduit
    private String fabricant;
    private String paysOrigine;
    private String composition;
    private String dosage;
    private String indications;

    //On retour les noms des tags
    private Set<String> tags;
}
