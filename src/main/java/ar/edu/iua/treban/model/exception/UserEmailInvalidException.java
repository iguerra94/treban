package ar.edu.iua.treban.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserEmailInvalidException extends RuntimeException {
    public UserEmailInvalidException(String message) {
        super(message);
    }
}