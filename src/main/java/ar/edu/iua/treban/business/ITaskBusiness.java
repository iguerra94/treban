package ar.edu.iua.treban.business;

import ar.edu.iua.treban.business.exception.*;
import ar.edu.iua.treban.model.Task;
import ar.edu.iua.treban.model.exception.*;
import ar.edu.iua.treban.web.services.exception.GetTaskListInvalidNameParamException;

import java.util.List;

public interface ITaskBusiness {
    List<Task> getTaskList() throws BusinessException;
    List<Task> getTaskListByName(String name) throws BusinessException, GetTaskListInvalidNameParamException;
    List<Task> getTaskListOrderByPriorityDesc() throws BusinessException;
    List<Task> getTaskListOrderByCreatedAtDesc() throws BusinessException;
    List<Task> getTaskListByNameOrderByPriorityDesc(String name) throws BusinessException, GetTaskListInvalidNameParamException;
    List<Task> getTaskListByNameOrderByCreatedAtDesc(String name) throws BusinessException, GetTaskListInvalidNameParamException;
    Task getOne(int id) throws BusinessException, NotFoundException;
    Task addTask(Task task) throws BusinessException, TaskEmptyFieldsException, TaskNameExistsException, TaskEstimationInvalidException, TaskPriorityInvalidException, TaskListNameNotExistsException, TaskListNameInvalidException;
    Task moveTask(int id, Task task) throws BusinessException, TaskEmptyFieldsException, TaskListNameInvalidException, TaskListNameNotExistsException, NotFoundException, TaskMoveToEqualListException, TaskMoveFromDoneListException, TaskMoveFromBacklogListException;
    Task deleteById(int id) throws BusinessException, NotFoundException;
}