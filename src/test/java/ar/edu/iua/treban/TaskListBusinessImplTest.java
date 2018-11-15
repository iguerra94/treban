package ar.edu.iua.treban;

import ar.edu.iua.treban.business.ITaskListBusiness;
import ar.edu.iua.treban.model.TaskList;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TrebanAppConfig.class})
@TestPropertySource("classpath:application.properties")
public class TaskListBusinessImplTest {

    @Autowired
    private ITaskListBusiness taskListBusiness;

    private static final String LIST_NAME_VALID = "in progress";
    private static final String LIST_NAME_NOT_VALID = "in progress 1";
    private static final String LIST_SPRINT_NAME = "Sprint 1";
    private static TaskList taskListValid;
    private static TaskList taskListNotValid;

    @BeforeClass
    public static void setup() {
        taskListValid = new TaskList();

        taskListValid.setName(LIST_NAME_VALID);
        taskListValid.setSprintName(LIST_SPRINT_NAME);

        taskListNotValid = new TaskList();

        taskListNotValid.setName(LIST_NAME_NOT_VALID);
        taskListNotValid.setSprintName(LIST_SPRINT_NAME);
    }

    @Test
    public void addTaskListSuccess() {
        TaskList taskListCreated = taskListBusiness.addTaskList(taskListValid);

        assertEquals(taskListValid.getName(), taskListCreated.getName());
        assertEquals(taskListValid.getSprintName(), taskListCreated.getSprintName());
    }

    @Test
    public void addTaskListFail() {
        TaskList taskListCreated = null;
        try {
            taskListCreated = taskListBusiness.addTaskList(taskListNotValid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assertNull(taskListCreated);
        }
    }

}