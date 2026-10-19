package com.ensaf.ensaf.controller;

import com.ensaf.ensaf.model.Person;
import com.ensaf.ensaf.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//Generer le constructor auto
@RequiredArgsConstructor
public class PersonController {
    //Le service prédifini avec les fonctions utilisables
    private final PersonService personService;

    //La route GET qui permet de recuperer tous les persons
    @GetMapping("/person")
    public List<Person> getPersons() {
        return personService.getAllPersons();
    }

    //La route Post qui permet d'ajouter un nouveau person
    @PostMapping("/person")
    @ResponseBody
    public Person addPerson(@RequestBody Person person) {
        System.out.println(person);
        return personService.createPerson(person);
    }


    //La route GET qui permet de recuperer un Person par son ID
    @GetMapping("/person/{id}")
    @ResponseBody
    public Person findPerson(@PathVariable Long id) {
        return personService.findPersonById(id);
    }

    //La route PUT pour modifier les informations d'un person
    @PutMapping("/person/{id}")
    @ResponseBody
    public Person updatePerson(@PathVariable Long id ,@RequestBody Person person) {
        return personService.updatePerson(id,person);
    }

    //La route delete qui permet de supprimer un person
    @DeleteMapping("/person/{id}")
    @ResponseBody
    public void deletePerson(@PathVariable Long id) {
       personService.deletePerson(id);
    }

    @PostMapping("/person/addWithAdress")
    public Person addPersonWithAdress(@RequestBody Person person) {
        return personService.createPersonWithNewAdresses(person);
    }

}
