package com.ensaf.ensaf.service;

import com.ensaf.ensaf.exception.PersonNotFoundException;
import com.ensaf.ensaf.model.Adress;
import com.ensaf.ensaf.model.Person;
import com.ensaf.ensaf.repository.AdressRepository;
import com.ensaf.ensaf.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//Le service permet de definir des fonctions pour les operations CRUD à l'aide de Repository
@Service
//Cette annotation permet de créer le constructor
@RequiredArgsConstructor
public class PersonService {
    //Repository permet d'effectuer les operations CRUD tq lire, sauvegarder ... des informations de / dans la DB
    private final PersonRepository personRepository;
    private final AdressRepository adressRepository;

    //Fonction qui permet de retourner tous les Persons dans la DB
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    //Fonction qui permet de créer un nouveau Person
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    //Fonction qui permet de trouver un Person d'après son ID
    public Person findPersonById(Long id) {
        return personRepository.findById(id).orElseThrow(()->new PersonNotFoundException("Person not found"));
    }

    //Fonction qui permet de modifier les informations d'un Person existant
    public Person updatePerson(Long id,Person PersonDetails) {
        Person existingPerson = personRepository.findById(id).orElseThrow(()->new PersonNotFoundException("Person not found"));
        existingPerson.setFirstName(PersonDetails.getFirstName());
        existingPerson.setLastName(PersonDetails.getLastName());
        return personRepository.save(existingPerson);
    }

    //Fonction qui permet de supprimer un Person
    public void deletePerson(Long id){
        Person existingPerson = personRepository.findById(id).orElseThrow(()->new PersonNotFoundException("Person not found"));
        personRepository.delete(existingPerson);
    }
    //Fonction qui permet d'ajouter person avec adress
    public Person createPersonWithNewAdresses(Person person) {
        //Pour chaque adress qu'on a, on sauvegarde dans la table des adresses
        adressRepository.saveAll(person.getAdresses());

        //On ajoute aussi la Person to the list of persons qu'on a dans la class Adress
        for (Adress a : person.getAdresses()) {
            a.getPersons().add(person);
        }

        // Sauvegarder Person
        return personRepository.save(person);
    }
}
