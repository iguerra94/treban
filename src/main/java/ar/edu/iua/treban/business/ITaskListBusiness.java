package ar.edu.iua.treban.business;

import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.business.exception.TaskListNameExistsException;
import ar.edu.iua.treban.model.TaskList;
import ar.edu.iua.treban.model.exception.TaskListEmptyFieldsException;
import ar.edu.iua.treban.model.exception.TaskListNameInvalidException;

public interface ITaskListBusiness {
    TaskList addTaskList(TaskList taskList) throws BusinessException, TaskListEmptyFieldsException, TaskListNameInvalidException, TaskListNameExistsException;
}