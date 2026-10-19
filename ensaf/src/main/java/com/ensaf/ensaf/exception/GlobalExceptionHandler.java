package com.ensaf.ensaf.exception;

import com.ensaf.ensaf.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice //Cette annotation permet de supervisé tous les controlleurs en cas de detection d'exception
public class GlobalExceptionHandler {

    //Cette methode est exectuée seuelement si une exception PersonNotFoundException est detecté
    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // HTTP status =404 (Not found)
    public ErrorMessage handlePersonNotFoundException(PersonNotFoundException ex) {

        //Retourne la format d'erreur définit dans le package dto
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
    }
}
