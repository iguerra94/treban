package ar.edu.iua.treban.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskMoveToEqualListException extends RuntimeException {
    public TaskMoveToEqualListException(String message) {
        super(message);
    }
}