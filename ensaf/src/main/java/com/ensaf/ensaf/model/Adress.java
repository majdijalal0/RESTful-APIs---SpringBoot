package com.ensaf.ensaf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Adress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adressId;
    private String street;
    private String city;
    private String codePostal;

    @ManyToMany(mappedBy = "adresses") // mapped By permet de lier avec la class Person en se basant sur "adresses" définit dans la class Person
    @JsonIgnore               //Pour éviter la loop infinie

    //La liste des persons pour l'adresse
    private Set<Person> persons = new HashSet<>();


}
