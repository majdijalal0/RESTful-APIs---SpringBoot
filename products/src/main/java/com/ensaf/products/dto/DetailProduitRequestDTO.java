package com.ensaf.products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailProduitRequestDTO {
    private String fabricant;
    private String paysOrigine;
    private String composition;
    private String dosage;
    private String indications;
}