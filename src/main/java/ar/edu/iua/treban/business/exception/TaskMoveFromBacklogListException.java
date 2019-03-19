package ar.edu.iua.treban.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskMoveFromBacklogListException extends RuntimeException {
    public TaskMoveFromBacklogListException(String message) {
        super(message);
    }
}