package com.ensaf.ensaf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

//Définir la structure de la reponse qu'on receoit en cas d'erreur
@Data
@AllArgsConstructor
public class ErrorMessage {
    //Le code status (404 par exemple)
    private int status;
    //Le message d'erreur qu'on definit
    private String message;
    //Le temps exact de la reponse pour les logs
    private LocalDateTime timestamp;
}
