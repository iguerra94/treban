package ar.edu.iua.treban.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskMoveFromDoneListException extends RuntimeException {
    public TaskMoveFromDoneListException(String message) {
        super(message);
    }
}