package com.ensaf.ensaf.exception;

//L'exception pour le cas ou Person n'est pas trouvé dans la DB
public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String message) {
        super(message);
    }
}
