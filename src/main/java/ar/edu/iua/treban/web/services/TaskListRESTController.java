package ar.edu.iua.treban.web.services;

import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.business.ITaskListBusiness;
import ar.edu.iua.treban.business.exception.TaskListNameExistsException;
import ar.edu.iua.treban.model.TaskList;
import ar.edu.iua.treban.model.exception.TaskListEmptyFieldsException;
import ar.edu.iua.treban.model.exception.TaskListNameInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping(Constantes.URL_LISTS)
public class TaskListRESTController {

    @Autowired
    private ITaskListBusiness taskListBusiness;

    @PostMapping(value = {"", "/"})
    public ResponseEntity<TaskList> addTaskList(@RequestBody TaskList taskList, UriComponentsBuilder uriComponentsBuilder, HttpServletRequest request) {
        try {
            TaskList taskListCreated = taskListBusiness.addTaskList(taskList);

            URI locationURI = uriComponentsBuilder
                            .path(Constantes.URL_LISTS + "/{id}")
                            .buildAndExpand(taskListCreated.getId())
                            .toUri();

            return ResponseEntity.created(locationURI).body(taskListCreated);
        } catch (TaskListEmptyFieldsException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskListEmptyFieldsException(e, request);
        } catch (TaskListNameInvalidException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskListNameInvalidException(e, request);
        } catch (TaskListNameExistsException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskListNameExistsException(e, request);
        } catch (BusinessException e) {
            return new CustomizedResponseEntityExceptionHandler().handleAllExceptions(e, request);
        }

    }

}