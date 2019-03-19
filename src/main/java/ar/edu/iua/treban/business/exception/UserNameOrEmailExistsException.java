package ar.edu.iua.treban.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserNameOrEmailExistsException extends RuntimeException {

    public UserNameOrEmailExistsException(String message) {
        super(message);
    }
}