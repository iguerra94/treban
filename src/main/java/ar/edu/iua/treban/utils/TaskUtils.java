package ar.edu.iua.treban.utils;

import ar.edu.iua.treban.model.exception.TaskPriorityInvalidException;
import ar.edu.iua.treban.web.services.exception.GetTaskListInvalidOrderByParamException;

import java.util.Arrays;

public class TaskUtils {
    public enum TaskPriority {
        LOW(1),
        MEDIUM(2),
        HIGH(3);

        private int value;

        public int getValue() {
            return value;
        }

        TaskPriority(int value) {
            this.value = value;
        }
    }

    public enum TaskOrderByParam {
        CREATED_AT("created_at"),
        PRIORITY("priority");

        private String value;

        public String getValue() {
            return value;
        }

        TaskOrderByParam(String value) {
            this.value = value;
        }
    }

    public static int mapPriority(String priority) {
        String priorityNormalized = priority.toUpperCase();
        int priorityValue = TaskPriority.valueOf(priorityNormalized).getValue();
        return priorityValue;
    }

    public static String deMapPriority(int priorityValue) {
        String priority = TaskUtils.TaskPriority.values()[priorityValue-1].name().toLowerCase();
        return priority;
    }

    public static boolean isPriorityValid(String priority) throws TaskPriorityInvalidException {
        String priorityNormalized = priority.toUpperCase();
        return Arrays.asList(TaskPriority.values()).contains(TaskPriority.valueOf(priorityNormalized));
    }

    public static boolean isTaskListNameValid(String name) {
        boolean taskListNameValid = name.equalsIgnoreCase(TaskListUtils.TaskListName.BACKLOG.getValue());
        return taskListNameValid;
    }

    public static boolean isOrderByParamValid(String orderBy) throws GetTaskListInvalidOrderByParamException {
        String orderByNormalized = orderBy.toUpperCase();
        return Arrays.asList(TaskOrderByParam.values()).contains(TaskOrderByParam.valueOf(orderByNormalized));
    }
}