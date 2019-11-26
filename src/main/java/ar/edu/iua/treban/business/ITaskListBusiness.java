package ar.edu.iua.treban.business;

import ar.edu.iua.treban.business.exception.AlreadyExistsException;
import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.model.TaskList;
import ar.edu.iua.treban.model.exception.EmptyFieldsException;
import ar.edu.iua.treban.model.exception.TaskListNameInvalidException;

import java.util.List;

public interface ITaskListBusiness {
    List<TaskList> getLists();
    TaskList addTaskList(TaskList taskList) throws BusinessException, EmptyFieldsException, TaskListNameInvalidException, AlreadyExistsException;
}