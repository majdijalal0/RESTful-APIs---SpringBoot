package com.ensaf.products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProduitRequestDTO {

    private String nom;
    private String description;
    private BigDecimal prix;
    private String codeBar;
    private LocalDate dateExpiration;

    //Les requetes de l'utilisateur vont seulement inclure les IDs et pas tous les objets
    private Long categorieId;
    private Long fournisseurId;
    private DetailProduitRequestDTO detailProduit;
    private Set<Long> tagIds;
}
