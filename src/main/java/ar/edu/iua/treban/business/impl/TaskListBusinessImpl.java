package ar.edu.iua.treban.business.impl;

import ar.edu.iua.treban.business.ITaskListBusiness;
import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.business.exception.TaskListNameExistsException;
import ar.edu.iua.treban.dao.FactoryDAO;
import ar.edu.iua.treban.model.TaskList;
import ar.edu.iua.treban.model.exception.TaskListEmptyFieldsException;
import ar.edu.iua.treban.model.exception.TaskListNameInvalidException;
import ar.edu.iua.treban.utils.TaskListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaskListBusinessImpl implements ITaskListBusiness {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public TaskList addTaskList(TaskList taskList) throws BusinessException, TaskListEmptyFieldsException, TaskListNameInvalidException, TaskListNameExistsException {
        log.info("Info when adding the Task List: Starting method logs.");
        if (taskList.getName() == null || taskList.getSprintName() == null) {
            log.error("Error when adding the Task List: name and sprintName fields must be entered.");
            log.info("Info when adding the Task List: Finished method logs.");
            throw new BusinessException("name and sprintName fields must be entered.");
        }

        if (taskList.getName().trim().length() == 0 || taskList.getSprintName().trim().length() == 0) {
            log.error("Error when adding the Task List: Neither the name nor the sprintName might be empty.");
            log.info("Info when adding the Task List: Finished method logs.");
            throw new TaskListEmptyFieldsException("Neither the name nor the sprintName might be empty.");
        }

        try {
            TaskListUtils.isValid(taskList.getName());
            log.info("Info when adding the Task List: The Task List name entered is valid.");
        } catch (Exception e) {
            log.error("Error when adding the Task List: The list name entered is not valid.");
            log.info("Info when adding the Task List: Finished method logs.");
            throw new TaskListNameInvalidException("The list name entered is not valid.");
        }

        TaskList taskListExists = (TaskList) FactoryDAO.getInstance().getTaskListDAO().findByName(taskList.getName());

        if (taskListExists != null) {
            log.error("Error when adding the Task List: The list name entered already exists.");
            log.info("Info when adding the Task List: Finished method logs.");
            throw new TaskListNameExistsException("The list name entered already exists.");
        }

        log.info("Info when adding the Task List: The Task List name entered does not exists already in the database.");

        TaskList taskListCreated;
        try {
            taskListCreated = (TaskList) FactoryDAO.getInstance().getTaskListDAO().add(taskList);
            log.info("Info when adding the Task List: The Task List with id = {} was created succesfully.", taskListCreated.getId());
            log.info("Info when adding the Task List: Finished method logs.");
        } catch (Exception e) {
            log.error("Error when adding the Task List: {}", e.getMessage());
            log.info("Info when adding the Task List: Finished method logs.");
            throw new BusinessException(e);
        }
        return taskListCreated;
    }
}