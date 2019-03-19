package ar.edu.iua.treban.utils;

import ar.edu.iua.treban.model.exception.TaskListNameInvalidException;

import java.util.Arrays;

public class TaskListUtils {

    public enum TaskListName {
        BACKLOG("Backlog"),
        TODO("TODO"),
        IN_PROGRESS("In Progress"),
        WAITING("Waiting"),
        DONE("DONE");

        private String value;

        public String getValue() {
            return value;
        }

        TaskListName(String value) {
            this.value = value;
        }
    }

    public static boolean isValid(String name) throws TaskListNameInvalidException {
        String[] nameSplitted = name.split("\\s+");
        String nameNormalized = String.join("_", nameSplitted).toUpperCase();

        return Arrays.asList(TaskListUtils.TaskListName.values()).contains(TaskListUtils.TaskListName.valueOf(nameNormalized));
    }

}