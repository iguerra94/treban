package ar.edu.iua.treban.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BusinessException extends RuntimeException {
    public BusinessException(String message) { super(message); }

    public BusinessException(Throwable cause) { super(cause); }

    public BusinessException(String message, Throwable cause) { super(message, cause); }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) { super(message, cause, enableSuppression, writableStackTrace); }
}