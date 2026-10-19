package com.ensaf.products.dto;

import lombok.*;

//Cette classe permet de determiner le format du reponse de Tag
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TagDTO {
    private Long id;
    private String libelle;
}
