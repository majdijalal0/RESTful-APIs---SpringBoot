package com.ensaf.ensaf.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

//Les annotations @Getter @Setter @NoArgsConstructor permet de créer automatiquement le constructor , les getters and setters.
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    //La relation many to many entre la classe Person and Adress
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    //Permet de créer la table de liaison entre les deux tables , avec leurs Ids
    @JoinTable(
            name = "person_adresse",
            joinColumns        = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "adresse_id")
    )
    //La liste des adresses pour Person
    private Set<Adress> adresses = new HashSet<>();

}
