package ar.edu.iua.treban.web.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GetTaskListInvalidNameParamException extends RuntimeException {
    public GetTaskListInvalidNameParamException(String message) {
        super(message);
    }
}