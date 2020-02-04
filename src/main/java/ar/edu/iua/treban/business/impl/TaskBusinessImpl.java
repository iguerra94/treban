package ar.edu.iua.treban.business.impl;

import ar.edu.iua.treban.business.ITaskBusiness;
import ar.edu.iua.treban.business.exception.*;
import ar.edu.iua.treban.dao.FactoryDAO;
import ar.edu.iua.treban.model.Task;
import ar.edu.iua.treban.model.TaskList;
import ar.edu.iua.treban.model.exception.*;
import ar.edu.iua.treban.model.persistence.TaskRepository;
import ar.edu.iua.treban.utils.TaskListUtils;
import ar.edu.iua.treban.utils.TaskUtils;
import ar.edu.iua.treban.web.services.exception.GetTaskListInvalidNameParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskBusinessImpl implements ITaskBusiness {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskRepository taskDAO;

    @Override
    public List<Task> getTaskList() throws BusinessException {
        log.info("Info when getting the list of Tasks without params: Starting method logs.");
        try {
            List<Task> taskList = taskDAO.findAll()
                    .stream()
                    .peek(task -> {
                        String priority = TaskUtils.deMapPriority(task.getPriorityValue());
                        task.setPriority(priority);
                    })
                    .collect(Collectors.toList());
            log.info("Info when getting the list of Tasks without params: The list of Tasks was returned successfully.");
            return taskList;
        } catch (Exception e) {
            log.error("Error when getting the list of Tasks without params: {}.", e.getMessage());
            throw new BusinessException(e);
        } finally {
            log.info("Info when getting the list of Tasks without params: Finished method logs.");
        }
    }

    @Override
    public List<Task> getTaskListByName(String name) throws BusinessException, GetTaskListInvalidNameParamException {
        log.info("Info when getting the list of Tasks by name: Starting method logs.");
        try {
            TaskListUtils.isValid(name);
            log.info("Info when getting the list of Tasks by name: The name param entered is valid.");
        } catch (Exception e) {
            log.error("Error when getting the list of Tasks by name: The name param entered is not valid.");
            log.info("Info when getting the list of Tasks by name: Finished method logs.");
            throw new GetTaskListInvalidNameParamException("The name param entered is not valid.");
        }

        try {
            List<Task> taskList = taskDAO.findAllByListName(name)
                    .stream()
                    .peek(task -> {
                        String priority = TaskUtils.deMapPriority(task.getPriorityValue());
                        task.setPriority(priority);
                    })
                    .collect(Collectors.toList());
            log.info("Info when getting the list of Tasks by name: The list of Tasks was returned successfully.");
            return taskList;
        } catch (Exception e) {
            log.error("Error when getting the list of Tasks by name: {}.", e.getMessage());
            throw new BusinessException(e);
        } finally {
            log.info("Info when getting the list of Tasks by name: Finished method logs.");
        }
    }

    @Override
    public List<Task> getTaskListOrderByPriorityDesc() throws BusinessException {
        log.info("Info when getting the list of Tasks by priority in descendant order: Starting method logs.");
        try {
            List<Task> taskList = taskDAO.findAll(new Sort(Sort.Direction.DESC, "priorityValue"))
                    .stream()
                    .peek(task -> {
                        String priority = TaskUtils.deMapPriority(task.getPriorityValue());
                        task.setPriority(priority);
                    })
                    .collect(Collectors.toList());
            log.info("Info when getting the list of Tasks by priority in descendant order: The list of Tasks was returned successfully.");
            return taskList;
        } catch (Exception e) {
            log.error("Error when getting the list of Tasks by priority in descendant order: {}.", e.getMessage());
            throw new BusinessException(e);
        } finally {
            log.info("Info when getting the list of Tasks by priority in descendant order: Finished method logs.");
        }
    }

    @Override
    public List<Task> getTaskListOrderByPriorityAsc() throws BusinessException {
        log.info("Info when getting the list of Tasks by priority in ascendant order: Starting method logs.");
        try {
            List<Task> taskList = taskDAO.findAll(new Sort(Sort.Direction.ASC, "priorityValue"))
                    .stream()
                    .peek(task -> {
                        String priority = TaskUtils.deMapPriority(task.getPriorityValue());
                        task.setPriority(priority);
                    })
                    .collect(Collectors.toList());
            log.info("Info when getting the list of Tasks by priority in ascendant order: The list of Tasks was returned successfully.");
            return taskList;
        } catch (Exception e) {
            log.error("Error when getting the list of Tasks by priority in ascendant order: {}.", e.getMessage());
            throw new BusinessException(e);
        } finally {
            log.info("Info when getting the list of Tasks by priority in ascendant order: Finished method logs.");
        }
    }

    @Override
    public List<Task> getTaskListOrderByCreatedAtDesc() throws BusinessException {
        log.info("Info when getting the list of Tasks by creation date in descendant order: Starting method logs.");
        try {
            List<Task> taskList = taskDAO.findAll(new Sort(Sort.Direction.DESC, "createdAt"))
                    .stream()
                    .peek(task -> {
                        String priority = TaskUtils.deMapPriority(task.getPriorityValue());
                        task.setPriority(priority);
                    })
                    .collect(Collectors.toList());
            log.info("Info when getting the list of Tasks by creation date in descendant order: The list of Tasks was returned successfully.");
            return taskList;
        } catch (Exception e) {
            log.error("Error when getting the list of Tasks by creation date in descendant order: {}.", e.getMessage());
            throw new BusinessException(e);
        } finally {
            log.info("Info when getting the list of Tasks by creation date in descendant order: Finished method logs.");
        }
    }

    @Override
    public List<Task> getTaskListOrderByCreatedAtAsc() throws BusinessException {
        log.info("Info when getting the list of Tasks by creation date in ascendant order: Starting method logs.");
        try {
            List<Task> taskList = taskDAO.findAll(new Sort(Sort.Direction.ASC, "createdAt"))
                    .stream()
                    .peek(task -> {
                        String priority = TaskUtils.deMapPriority(task.getPriorityValue());
                        task.setPriority(priority);
                    })
                    .collect(Collectors.toList());
            log.info("Info when getting the list of Tasks by creation date in ascendant order: The list of Tasks was returned successfully.");
            return taskList;
        } catch (Exception e) {
            log.error("Error when getting the list of Tasks by creation date in ascendant order: {}.", e.getMessage());
            throw new BusinessException(e);
        } finally {
            log.info("Info when getting the list of Tasks by creation date in ascendant order: Finished method logs.");
        }
    }

    @Override
    public List<Task> getTaskListByNameOrderByPriorityDesc(String name) throws BusinessException, GetTaskListInvalidNameParamException {
        log.info("Info when getting the list of Tasks by name and priority in descendant order: Starting method logs.");
        try {
            TaskListUtils.isValid(name);
            log.info("Info when getting the list of Tasks by name and priority in descendant order: The name param entered is valid.");
        } catch (Exception e) {
            log.error("Error when getting the list of Tasks by name and priority in descendant order: The name param entered is not valid.");
            log.info("Info when getting the list of Tasks by name and priority in descendant order: Finished method logs.");
            throw new GetTaskListInvalidNameParamException("The name param entered is not valid.");
        }

        try {
            List<Task> taskList = taskDAO.findAllByListNameOrderByPriorityDesc(name)
                    .stream()
                    .peek(task -> {
                        String priority = TaskUtils.deMapPriority(task.getPriorityValue());
                        task.setPriority(priority);
                    })
                    .collect(Collectors.toList());
            log.info("Info when getting the list of Tasks by name and priority in descendant order: The list of Tasks was returned successfully.");
            return taskList;
        } catch (Exception e) {
            log.error("Error when getting the list of Tasks by name and priority in descendant order: {}.", e.getMessage());
            throw new BusinessException(e);
        } finally {
            log.info("Info when getting the list of Tasks by name and priority in descendant order: Finished method logs.");
        }
    }

    @Override
    public List<Task> getTaskListByNameOrderByCreatedAtDesc(String name) throws BusinessException, GetTaskListInvalidNameParamException {
        log.info("Info when getting the list of Tasks by name and creation date in descendant order: Starting method logs.");
        try {
            TaskListUtils.isValid(name);
            log.info("Info when getting the list of Tasks by name and creation date in descendant order: The name param entered is valid.");
        } catch (Exception e) {
            log.error("Error when getting the list of Tasks by name and creation date in descendant order: The name param entered is not valid.");
            log.info("Info when getting the list of Tasks by name and creation date in descendant order: Finished method logs.");
            throw new GetTaskListInvalidNameParamException("The name param entered is not valid.");
        }

        try {
            List<Task> taskList = taskDAO.findAllByListNameOrderByCreatedAtDesc(name)
                    .stream()
                    .peek(task -> {
                        String priority = TaskUtils.deMapPriority(task.getPriorityValue());
                        task.setPriority(priority);
                    })
                    .collect(Collectors.toList());
            log.info("Info when getting the list of Tasks by name and creation date in descendant order: The list of Tasks was returned successfully.");
            return taskList;
        } catch (Exception e) {
            log.error("Error when getting the list of Tasks by name and creation date in descendant order: {}.", e.getMessage());
            throw new BusinessException(e);
        } finally {
            log.info("Info when getting the list of Tasks by name and creation date in descendant order: Finished method logs.");
        }
    }

    @Override
    public Task getOne(int id) throws BusinessException, NotFoundException {
        log.info("Info when getting one Task by id: Starting method logs.");
        Optional<Task> taskFound = null;

        try {
            taskFound = taskDAO.findById(id);
            log.info("Info when getting one Task by id: The Task with id = {} was found.", id);
        } catch (Exception e) {
            log.error("Error when getting one Task by id: {}.", e.getMessage());
            log.info("Info when getting one Task by id: Finished method logs.");
            throw new BusinessException(e);
        }

        if (!taskFound.isPresent()) {
            log.error("Error when getting one Task by id: The Task with id = {} was not found.", id);
            log.info("Info when getting one Task by id: Finished method logs.");
            throw new NotFoundException("The task with id = " + id + " was not found.");
        }

        String priority = TaskUtils.deMapPriority(taskFound.get().getPriorityValue());
        taskFound.get().setPriority(priority);

        log.info("Info when getting one Task by id: The Task with id = {} was returned succesfully.", id);
        log.info("Info when getting one Task by id: Finished method logs.");
        return taskFound.get();
    }

    @Override
    public Task addTask(Task task) throws BusinessException, EmptyFieldsException, TaskNameExistsException, TaskEstimationInvalidException, TaskPriorityInvalidException, TaskListNameNotExistsException, TaskListNameInvalidException {
        log.info("Info when adding one Task: Starting method logs.");
        if (task.getName() == null ||
                task.getEstimation() == null ||
                task.getPriority() == null ||
                task.getStatus() == null ||
                task.getStatus().getName() == null) {
            log.error("Error when adding one Task: name, estimation, priority and status fields must be entered.");
            log.info("Info when adding one Task: Finished method logs.");
            throw new BusinessException("name, estimation, priority and status fields must be entered.");
        }

        if (task.getName().trim().length() == 0 ||
                task.getPriority().trim().length() == 0 ||
                task.getStatus().getName().trim().length() == 0 ||
                task.getEstimation().toString().length() == 0) {
            log.error("Error when adding one Task: Neither the name, estimation, priority and status might be empty");
            log.info("Info when adding one Task: Finished method logs.");
            throw new EmptyFieldsException("Neither the name, estimation, priority and status might be empty.");
        }
/*
        Task taskNameExists = taskDAO.findByName(task.getName());

        if (taskNameExists != null) {
            log.error("Error when adding one Task: The task name entered already exists.");
            log.info("Info when adding one Task: Finished method logs.");
            throw new TaskNameExistsException("The task name entered already exists.");
        }

        log.info("Info when adding one Task: The task name entered does not exists already in the database.");
*/
        if (task.getEstimation() < 1 || task.getEstimation() > 10) {
            log.error("Error when adding one Task: The estimation must be a number between 1 (one) and 10 (ten).");
            log.info("Info when adding one Task: Finished method logs.");
            throw new TaskEstimationInvalidException("The estimation must be a number between 1 (one) and 10 (ten).");
        }

        try {
            TaskUtils.isPriorityValid(task.getPriority());
            log.info("Info when adding one Task: The priority entered is valid.");
        } catch (Exception e) {
            log.error("Error when adding one Task: The priority entered is not valid.");
            log.info("Info when adding one Task: Finished method logs.");
            throw new TaskPriorityInvalidException("The priority entered is not valid.");
        }

        TaskList taskListExists = (TaskList) FactoryDAO.getInstance().getTaskListDAO().findByName(task.getStatus().getName());

        if (taskListExists == null) {
            log.error("Error when adding one Task: The list name entered not exists.");
            log.info("Info when adding one Task: Finished method logs.");
            throw new TaskListNameNotExistsException("The list name entered not exists.");
        }

        log.info("Info when adding one Task: The list name entered exists already in the database.");

        if (!TaskUtils.isTaskListNameValid(task.getStatus().getName())) {
            log.error("Error when adding one Task: The task was assigned to the wrong list.");
            log.info("Info when adding one Task: Finished method logs.");
            throw new TaskListNameInvalidException("The task was assigned to the wrong list.");
        }

        try {
            int priorityValue = TaskUtils.mapPriority(task.getPriority());
            task.setPriorityValue(priorityValue);

            task.setCreatedAt(new Date());
            task.setLastModified(new Date());

            TaskList taskList = (TaskList) FactoryDAO.getInstance().getTaskListDAO().findByName(task.getStatus().getName());
            task.setStatus(taskList);

            Task taskSaved = taskDAO.save(task);
            log.info("Info when adding one Task: The Task with id = {} was saved succesfully.", taskSaved.getId());
            return taskSaved;
        } catch (Exception e) {
            log.error("Error when adding one Task: {}.", e.getMessage());
            throw new BusinessException(e);
        } finally {
            log.info("Info when adding one Task: Finished method logs.");
        }
    }

    @Override
    public Task moveTask(int id, Task task) throws BusinessException, EmptyFieldsException, TaskListNameInvalidException, TaskListNameNotExistsException, NotFoundException, TaskMoveToEqualListException, TaskMoveFromDoneListException, TaskMoveFromBacklogListException {
        log.info("Info when moving one Task: Starting method logs.");
        Task taskFound = getOne(id);

        if (task.getStatus().getName() == null) {
            log.error("Error when moving one Task: The status name field must be entered.");
            log.info("Info when moving one Task: Finished method logs.");
            throw new BusinessException("The status name field must be entered.");
        }

        if (task.getStatus().getName().trim().length() == 0) {
            log.error("Error when moving one Task: The status name cannot be empty.");
            log.info("Info when moving one Task: Finished method logs.");
            throw new EmptyFieldsException("The status name cannot be empty.");
        }

        try {
            TaskListUtils.isValid(task.getStatus().getName());
            log.info("Info when moving one Task: The list name entered is valid.");
        } catch (Exception e) {
            log.error("Error when moving one Task: The list name entered is not valid.");
            log.info("Info when moving one Task: Finished method logs.");
            throw new TaskListNameInvalidException("The list name entered is not valid.");
        }

        TaskList taskListExists = (TaskList) FactoryDAO.getInstance().getTaskListDAO().findByName(task.getStatus().getName());

        if (taskListExists == null) {
            log.error("Error when moving one Task: The list name entered not exists.");
            log.info("Info when moving one Task: Finished method logs.");
            throw new TaskListNameNotExistsException("The list name entered not exists.");
        }

        log.info("Info when moving one Task: The Task List name entered does exists already in the database..");

        if (taskFound.getStatus().getName().equalsIgnoreCase(task.getStatus().getName())) {
            log.error("Error when moving one Task: The task cannot be moved to the same list that belongs.");
            log.info("Info when moving one Task: Finished method logs.");
            throw new TaskMoveToEqualListException("The task cannot be moved to the same list that belongs.");
        }

        if (taskFound.getStatus().getName().equalsIgnoreCase(TaskListUtils.TaskListName.DONE.getValue())) {
            log.error("Error when moving one Task: The task cannot be moved from the DONE list.");
            log.info("Info when moving one Task: Finished method logs.");
            throw new TaskMoveFromDoneListException("The task cannot be moved from the DONE list.");
        }

        if (taskFound.getStatus().getName().equalsIgnoreCase(TaskListUtils.TaskListName.BACKLOG.getValue()) &&
                !task.getStatus().getName().equalsIgnoreCase(TaskListUtils.TaskListName.TODO.getValue())) {
            log.error("Error when moving one Task: La tarea en la lista BACKLOG solo se puede mover a la lista TODO.");
            log.info("Info when moving one Task: Finished method logs.");
            throw new TaskMoveFromBacklogListException("La tarea en la lista BACKLOG solo se puede mover a la lista TODO.");
        }

        try {
            taskFound.setLastModified(new Date());

            TaskList listToBeMoved = (TaskList) FactoryDAO.getInstance().getTaskListDAO().findByName(task.getStatus().getName());
            taskFound.setStatus(listToBeMoved);

            log.info("Info when moving one Task: The Task was moved from {} list to the {} list successfully.", taskFound.getStatus().getName(), task.getStatus().getName());
            return taskDAO.save(taskFound);
        } catch (Exception e) {
            log.error("Error when moving one Task: {}", e.getMessage());
            throw new BusinessException(e);
        } finally {
            log.info("Info when moving one Task: Finished method logs.");
        }
    }

    @Override
    public Task deleteById(int id) throws BusinessException, NotFoundException {
        log.info("Info when deleting one Task by id: Starting method logs.");
        Task task = getOne(id);

        try {
            taskDAO.delete(task);
            log.info("Info when deleting one Task by id: The Task with id = {} was deleted successfully.", id);
            return task;
        } catch (Exception e) {
            log.error("Error when deleting one Task by id: {}.", e.getMessage());
            throw new BusinessException(e);
        } finally {
            log.info("Info when deleting one Task by id: Finished method logs.");
        }
    }

}