package ar.edu.iua.treban.business.impl;

import ar.edu.iua.treban.business.ITaskListBusiness;
import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.business.exception.TaskListNameExistsException;
import ar.edu.iua.treban.model.TaskList;
import ar.edu.iua.treban.model.exception.TaskListEmptyFieldsException;
import ar.edu.iua.treban.model.exception.TaskListNameInvalidException;
import ar.edu.iua.treban.model.persistence.TaskListRepository;
import ar.edu.iua.treban.utils.TaskListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskListBusinessImpl implements ITaskListBusiness {

    @Autowired
    private TaskListRepository taskListDAO;

    @Override
    public TaskList addTaskList(TaskList taskList) throws BusinessException, TaskListEmptyFieldsException, TaskListNameInvalidException, TaskListNameExistsException {
        if (taskList.getName() == null || taskList.getSprintName() == null) {
            throw new BusinessException("name and sprintName fields must be entered.");
        }

        if (taskList.getName().trim().length() == 0 || taskList.getSprintName().trim().length() == 0) {
            throw new TaskListEmptyFieldsException("Neither the name nor the sprintName might be empty.");
        }

        try {
            TaskListUtils.isValid(taskList.getName());
        } catch (Exception e) {
            throw new TaskListNameInvalidException("The list name entered is not valid.");
        }

        TaskList taskListExists = taskListDAO.findByName(taskList.getName());

        if (taskListExists != null) {
            throw new TaskListNameExistsException("The list name entered already exists.");
        }

        try {
            return taskListDAO.save(taskList);
        } catch (Exception e) {
            throw new BusinessException(e);
        }

    }
}