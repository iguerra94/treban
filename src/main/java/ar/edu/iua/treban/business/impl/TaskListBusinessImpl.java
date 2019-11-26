package ar.edu.iua.treban.business.impl;

import ar.edu.iua.treban.business.ITaskListBusiness;
import ar.edu.iua.treban.business.exception.AlreadyExistsException;
import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.dao.FactoryDAO;
import ar.edu.iua.treban.model.TaskList;
import ar.edu.iua.treban.model.exception.EmptyFieldsException;
import ar.edu.iua.treban.model.exception.TaskListNameInvalidException;
import ar.edu.iua.treban.utils.TaskListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListBusinessImpl implements ITaskListBusiness {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<TaskList> getLists() {
        log.info("Info when getting the list of Tasks Lists: Starting method logs.");

        List<TaskList> lists = (List<TaskList>) FactoryDAO.getInstance().getTaskListDAO().getAll();

        log.info("Info when getting the list of Tasks Lists: The list of Tasks Lists was returned successfully.");
        log.info("Info when getting the list of Tasks Lists: Finished method logs.");

        return lists;
    }

    @Override
    public TaskList addTaskList(TaskList taskList) throws BusinessException, EmptyFieldsException, TaskListNameInvalidException, AlreadyExistsException {
        log.info("Info when adding the Task List: Starting method logs.");
        if (taskList.getName() == null || taskList.getSprintName() == null) {
            log.error("Error when adding the Task List: name and sprintName fields must be entered.");
            log.info("Info when adding the Task List: Finished method logs.");
            throw new BusinessException("name and sprintName fields must be entered.");
        }

        if (taskList.getName().trim().length() == 0 || taskList.getSprintName().trim().length() == 0) {
            log.error("Error when adding the Task List: None of the fields can be empty.");
            log.info("Info when adding the Task List: Finished method logs.");
            throw new EmptyFieldsException("None of the fields can be empty.");
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
            throw new AlreadyExistsException("The list name entered already exists.");
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