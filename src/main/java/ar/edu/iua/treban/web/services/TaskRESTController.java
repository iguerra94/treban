package ar.edu.iua.treban.web.services;

import ar.edu.iua.treban.business.ITaskBusiness;
import ar.edu.iua.treban.business.exception.*;
import ar.edu.iua.treban.model.Task;
import ar.edu.iua.treban.model.exception.*;
import ar.edu.iua.treban.utils.TaskUtils;
import ar.edu.iua.treban.web.services.exception.GetTaskListInvalidNameParamException;
import ar.edu.iua.treban.web.services.exception.GetTaskListInvalidOrderByParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(Constantes.URL_TASKS)
public class TaskRESTController {

    @Autowired
    private ITaskBusiness taskBusiness;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Task>> getTaskList(
            @RequestParam(required=false, value="name", defaultValue="*") String name,
            @RequestParam(required=false, value="order_by_field", defaultValue="*") String orderByField,
            @RequestParam(required=false, value="order_by_criteria", defaultValue="*") String orderByCriteria,
            HttpServletRequest request) {

        try {
            try {
                if (!orderByField.equals("*") && orderByField.trim().length() > 0) {
                    TaskUtils.isOrderByParamValid(orderByField);
                }
            } catch (Exception e) {
                throw new GetTaskListInvalidOrderByParamException("The order_by_field param entered is not valid. The only values allowed are 'created_at' or 'priority'.");
            }

            if (!orderByField.equals("*") && orderByField.trim().length() > 0 && orderByField.equals("priority")) {
                if (!name.equals("*") && name.trim().length() > 0) {
//                    /api/v1/tasks?name="..."&order_by="priority"
                    return ResponseEntity.ok(taskBusiness.getTaskListByNameOrderByPriorityDesc(name));
                } else {
                    if (!orderByCriteria.equals("*") && orderByCriteria.trim().length() > 0 && orderByCriteria.equals("asc")) {
//                      /api/v1/tasks?order_by_field="priority"&order_by_criteria="asc"
                        return ResponseEntity.ok(taskBusiness.getTaskListOrderByPriorityAsc());
                    }
                    if (!orderByCriteria.equals("*") && orderByCriteria.trim().length() > 0 && orderByCriteria.equals("desc")) {
//                      /api/v1/tasks?order_by_field="priority"&order_by_criteria="desc"
                        return ResponseEntity.ok(taskBusiness.getTaskListOrderByPriorityDesc());
                    }
                }
            } else if (!orderByField.equals("*") && orderByField.trim().length() > 0 && orderByField.equals("created_at")) {
                if (!name.equals("*") && name.trim().length() > 0) {
//                    /api/v1/tasks?name="..."&order_by="created_at"
                    return ResponseEntity.ok(taskBusiness.getTaskListByNameOrderByCreatedAtDesc(name));
                } else {
                    if (!orderByCriteria.equals("*") && orderByCriteria.trim().length() > 0 && orderByCriteria.equals("asc")) {
//                      /api/v1/tasks?order_by_field="created_at"&order_by_criteria="asc"
                        return ResponseEntity.ok(taskBusiness.getTaskListOrderByCreatedAtAsc());
                    }
                    if (!orderByCriteria.equals("*") && orderByCriteria.trim().length() > 0 && orderByCriteria.equals("desc")) {
//                      /api/v1/tasks?order_by_field="created_at"&order_by_criteria="desc"
                        return ResponseEntity.ok(taskBusiness.getTaskListOrderByCreatedAtDesc());
                    }
                }
            } else {
                if (!name.equals("*") && name.trim().length() > 0) {
//                    /api/v1/tasks?name="..."
                    return ResponseEntity.ok(taskBusiness.getTaskListByName(name));
                } else {
//                    /api/v1/tasks
                    return ResponseEntity.ok(taskBusiness.getTaskList());
                }
            }

            return null;
        } catch (GetTaskListInvalidNameParamException e) {
            return new CustomizedResponseEntityExceptionHandler().handleGetTaskListInvalidNameParamException(e, request);
        } catch (GetTaskListInvalidOrderByParamException e) {
            return new CustomizedResponseEntityExceptionHandler().handleGetTaskListInvalidOrderByParamException(e, request);
        } catch (BusinessException e) {
            return new CustomizedResponseEntityExceptionHandler().handleAllExceptions(e, request);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Task> getOneTask(@PathVariable("id") int id, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(taskBusiness.getOne(id));
        } catch (NotFoundException e) {
            return new CustomizedResponseEntityExceptionHandler().handleNotFoundException(e, request);
        } catch (BusinessException e) {
            return new CustomizedResponseEntityExceptionHandler().handleAllExceptions(e, request);
        }
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<Task> addTask(@RequestBody Task task, UriComponentsBuilder uriComponentsBuilder, HttpServletRequest request) {
        try {
            Task taskCreated = taskBusiness.addTask(task);

            URI locationURI = uriComponentsBuilder
                            .path(Constantes.URL_TASKS + "/{id}")
                            .buildAndExpand(taskCreated.getId())
                            .toUri();

            return ResponseEntity.created(locationURI).body(taskCreated);
        } catch (EmptyFieldsException e) {
            return new CustomizedResponseEntityExceptionHandler().handleEmptyFieldsException(e, request);
        } catch (TaskNameExistsException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskNameExistsException(e, request);
        } catch (TaskEstimationInvalidException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskEstimationInvalidException(e, request);
        } catch (TaskPriorityInvalidException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskPriorityInvalidException(e, request);
        } catch (TaskListNameNotExistsException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskListNameNotExistsException(e, request);
        } catch (TaskListNameInvalidException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskListNameInvalidException(e, request);
        } catch (BusinessException e) {
            return new CustomizedResponseEntityExceptionHandler().handleAllExceptions(e, request);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Task> moveTask(@PathVariable("id") int id, @RequestBody Task task, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(taskBusiness.moveTask(id, task));
        } catch (EmptyFieldsException e) {
            return new CustomizedResponseEntityExceptionHandler().handleEmptyFieldsException(e, request);
        } catch (TaskListNameInvalidException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskListNameInvalidException(e, request);
        } catch (TaskListNameNotExistsException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskListNameNotExistsException(e, request);
        } catch (NotFoundException e) {
            return new CustomizedResponseEntityExceptionHandler().handleNotFoundException(e, request);
        } catch (TaskMoveToEqualListException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskMoveToEqualListException(e, request);
        } catch (TaskMoveFromDoneListException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskMoveFromDoneListException(e, request);
        } catch (TaskMoveFromBacklogListException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskMoveFromBacklogListException(e, request);
        } catch (BusinessException e) {
            return new CustomizedResponseEntityExceptionHandler().handleAllExceptions(e, request);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") int id, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(taskBusiness.deleteById(id));
        } catch (NotFoundException e) {
            return new CustomizedResponseEntityExceptionHandler().handleNotFoundException(e, request);
        } catch (BusinessException e) {
            return new CustomizedResponseEntityExceptionHandler().handleAllExceptions(e, request);
        }
    }

}