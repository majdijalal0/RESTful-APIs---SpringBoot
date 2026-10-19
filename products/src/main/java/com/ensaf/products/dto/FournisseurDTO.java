package com.ensaf.products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FournisseurDTO {

    private Long id;
    private String nom;
    private String contact;
    private String adresse;

    // On retourne seulement les noms des produits fournis
    private List<String> produitNoms;
}