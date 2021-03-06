package ar.edu.iua.treban.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskListEmptyFieldsException extends RuntimeException {
    public TaskListEmptyFieldsException(String message) {
        super(message);
    }
}