package ar.edu.iua.treban.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyFieldsException extends RuntimeException {
    public EmptyFieldsException(String message) {
        super(message);
    }
}