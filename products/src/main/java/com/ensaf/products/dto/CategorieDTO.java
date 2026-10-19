package com.ensaf.products.dto;

import lombok.*;
import java.util.List;

//Cette class permet de determiner le format du reponse/requete pour Categorie
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CategorieDTO {
    private Long id;
    private String nom;
    private String description;
    private List<String> produitNoms; // On veut seulement retourner les noms des produits et pas tous l'objet produit
}
