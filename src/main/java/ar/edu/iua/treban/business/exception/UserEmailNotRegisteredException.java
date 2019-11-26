package ar.edu.iua.treban.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserEmailNotRegisteredException extends RuntimeException {
    public UserEmailNotRegisteredException(String message) {
        super(message);
    }
}