package ar.edu.iua.treban.web.services;

import ar.edu.iua.treban.business.exception.*;
import ar.edu.iua.treban.model.ErrorDetails;
import ar.edu.iua.treban.model.exception.*;
import ar.edu.iua.treban.web.services.exception.GetTaskListInvalidNameParamException;
import ar.edu.iua.treban.web.services.exception.GetTaskListInvalidOrderByParamException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler {

    /* General Exceptions */

    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity handleAllExceptions(Exception ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /* Task List Exceptions */

    @ExceptionHandler(TaskListNameInvalidException.class)
    public final ResponseEntity handleTaskListNameInvalidException(TaskListNameInvalidException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskListEmptyFieldsException.class)
    public final ResponseEntity handleTaskListEmptyFieldsException(TaskListEmptyFieldsException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskListNameExistsException.class)
    public final ResponseEntity handleTaskListNameExistsException(TaskListNameExistsException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Task Exceptions */

    @ExceptionHandler(TaskEmptyFieldsException.class)
    public final ResponseEntity handleTaskEmptyFieldsException(TaskEmptyFieldsException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNameExistsException.class)
    public ResponseEntity handleTaskNameExistsException(TaskNameExistsException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TaskEstimationInvalidException.class)
    public final ResponseEntity handleTaskEstimationInvalidException(TaskEstimationInvalidException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskPriorityInvalidException.class)
    public final ResponseEntity handleTaskPriorityInvalidException(TaskPriorityInvalidException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskListNameNotExistsException.class)
    public ResponseEntity handleTaskListNameNotExistsException(TaskListNameNotExistsException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TaskMoveToEqualListException.class)
    public ResponseEntity handleTaskMoveToEqualListException(TaskMoveToEqualListException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskMoveFromDoneListException.class)
    public ResponseEntity handleTaskMoveFromDoneListException(TaskMoveFromDoneListException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskMoveFromBacklogListException.class)
    public ResponseEntity handleTaskMoveFromBacklogListException(TaskMoveFromBacklogListException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GetTaskListInvalidNameParamException.class)
    public final ResponseEntity handleGetTaskListInvalidNameParamException(GetTaskListInvalidNameParamException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GetTaskListInvalidOrderByParamException.class)
    public final ResponseEntity handleGetTaskListInvalidOrderByParamException(GetTaskListInvalidOrderByParamException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNameOrEmailExistsException.class)
    public ResponseEntity handleTaskNameExistsException(UserNameOrEmailExistsException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

}