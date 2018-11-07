package ar.edu.iua.treban.business.impl;

import ar.edu.iua.treban.business.ITaskBusiness;
import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.dao.IGenericDAO;
import ar.edu.iua.treban.model.Task;
import ar.edu.iua.treban.model.TaskList;
import ar.edu.iua.treban.model.exception.*;
import ar.edu.iua.treban.model.persistence.TaskListRepository;
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
import java.util.stream.Collectors;

@Service
public class TaskBusinessImpl implements ITaskBusiness {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskRepository taskDAO;

    @Autowired
    private TaskListRepository taskListDAO;

    @Override
    public List<Task> getTaskList() throws BusinessException {
        try {
            List<Task> taskList = taskDAO.findAll()
                                    .stream()
                                    .peek(task -> {
                                        String priority = TaskUtils.deMapPriority(task.getPriorityValue());
                                        task.setPriority(priority);
                                    })
                                    .collect(Collectors.toList());
            return taskList;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public List<Task> getTaskListByName(String name) throws BusinessException, GetTaskListInvalidNameParamException {
        try {
            TaskListUtils.isValid(name);
        } catch (Exception e) {
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
            return taskList;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public List<Task> getTaskListOrderByPriorityDesc() throws BusinessException {
        try {
            List<Task> taskList = taskDAO.findAll(new Sort(Sort.Direction.DESC, "priorityValue"))
                                    .stream()
                                    .peek(task -> {
                                        String priority = TaskUtils.deMapPriority(task.getPriorityValue());
                                        task.setPriority(priority);
                                    })
                                    .collect(Collectors.toList());
            return taskList;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public List<Task> getTaskListOrderByCreatedAtDesc() throws BusinessException {
        try {
            List<Task> taskList = taskDAO.findAll(new Sort(Sort.Direction.DESC, "createdAt"))
                                    .stream()
                                    .peek(task -> {
                                        String priority = TaskUtils.deMapPriority(task.getPriorityValue());
                                        task.setPriority(priority);
                                    })
                                    .collect(Collectors.toList());
            return taskList;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public List<Task> getTaskListByNameOrderByPriorityDesc(String name) throws BusinessException, GetTaskListInvalidNameParamException {
        try {
            TaskListUtils.isValid(name);
        } catch (Exception e) {
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
            return taskList;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public List<Task> getTaskListByNameOrderByCreatedAtDesc(String name) throws BusinessException, GetTaskListInvalidNameParamException {
        try {
            TaskListUtils.isValid(name);
        } catch (Exception e) {
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
            return taskList;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Task getOneTask(String name) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public Task addTask(Task task) throws BusinessException, TaskEmptyFieldsException, TaskEstimationInvalidException, TaskPriorityInvalidException, TaskListNameInvalidException {
        if (task.getName() == null ||
            task.getEstimation() == null ||
            task.getPriority() == null ||
            task.getStatus() == null ||
            task.getStatus().getName() == null) {
            throw new BusinessException("name, estimation, priority and status fields must be entered.");
        }

        if (task.getName().trim().length() == 0 ||
            task.getPriority().trim().length() == 0 ||
            task.getStatus().getName().trim().length() == 0) {
            throw new TaskEmptyFieldsException("Neither the name, estimation, priority and status might be empty or less than 1.");
        }

        if (task.getEstimation() <= 0) {
            throw new TaskEstimationInvalidException("The estimation entered cannot be less than 1 (one).");
        }

        try {
            TaskUtils.isPriorityValid(task.getPriority());
        } catch (Exception e) {
            throw new TaskPriorityInvalidException("The priority entered is not valid.");
        }

        if (!TaskUtils.isTaskListNameValid(task.getStatus().getName())) {
            throw new TaskListNameInvalidException("The task was assigned to the wrong list.");
        }

        try {
            int priorityValue = TaskUtils.mapPriority(task.getPriority());
            task.setPriorityValue(priorityValue);

            task.setCreatedAt(new Date());
            task.setLastModified(new Date());

            TaskList taskList = taskListDAO.findByName(task.getStatus().getName());
            task.setStatus(taskList);

            return taskDAO.save(task);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Task updateTask(Task task) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public Task deleteTask(Task task) throws BusinessException, NotFoundException {
        return null;
    }
}