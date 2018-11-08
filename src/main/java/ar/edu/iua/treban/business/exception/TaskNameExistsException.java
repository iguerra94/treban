package ar.edu.iua.treban.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TaskNameExistsException extends RuntimeException {
    public TaskNameExistsException(String message) {
        super(message);
    }
}