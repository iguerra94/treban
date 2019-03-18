package ar.edu.iua.treban;

import ar.edu.iua.treban.business.ITaskBusiness;
import ar.edu.iua.treban.dao.FactoryDAO;
import ar.edu.iua.treban.model.Task;
import ar.edu.iua.treban.model.TaskList;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TrebanAppConfig.class})
@TestPropertySource("classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskBusinessImplTest {

    @Autowired
    private ITaskBusiness taskBusiness;

    private static final String TASK_NAME = "Task " + UUID.randomUUID();
    private static final int TASK_ESTIMATION = 1;
    private static final String TASK_PRIORITY = "low";
    private static final Date TASK_DATE = new Date();
    private int TASK_LIST_COUNT = 1;
    private static final long TASK_LIST_COUNT_ZERO = 0;

    private static Task taskValid;

    @BeforeClass
    public static void setup() {
        taskValid = new Task();

        taskValid.setName(TASK_NAME);
        taskValid.setEstimation(TASK_ESTIMATION);
        taskValid.setPriority(TASK_PRIORITY);
        taskValid.setCreatedAt(TASK_DATE);
        taskValid.setLastModified(TASK_DATE);
    }

    @Test
    @Disabled
    public void addTaskSuccess() {
        TaskList list = new TaskList();
        list.setName("backlog");

        TaskList status = (TaskList) FactoryDAO.getInstance().getTaskListDAO().findByName(list.getName());

        taskValid.setStatus(status);

        Task taskCreated = taskBusiness.addTask(taskValid);

        assertEquals(taskValid.getName(), taskCreated.getName());
        assertEquals(taskValid.getEstimation(), taskCreated.getEstimation());
        assertEquals(taskValid.getPriority(), taskCreated.getPriority());
        assertEquals(taskValid.getCreatedAt(), taskCreated.getCreatedAt());
        assertEquals(taskValid.getLastModified(), taskCreated.getLastModified());

        assertEquals(taskValid.getStatus().getName(), taskCreated.getStatus().getName());
    }

    @Test
    @Disabled
    public void addTaskListFail() {
        Task taskCreated = null;
        try {
            taskCreated = taskBusiness.addTask(taskValid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assertNull(taskCreated);
        }
    }

    @Test
    @Disabled
    public void getTaskSuccess() {
        Task task = null;

        task = taskBusiness.getOne(1);
        assertEquals("Successfully fetched a single task from the table!",1, task.getId());
    }

    @Test
    @Disabled
    public void moveTaskSuccess() {
        Task task1 = new Task();

        task1.setStatus(new TaskList());
        task1.getStatus().setName("todo");

        assertEquals("Successfully updated a single task in the table!", 2, taskBusiness.moveTask(1, task1).getStatus().getId());
    }

    @Test
    @Disabled
    public void testDeleteByIdTask() {
        assertEquals("Successfully deleted a single category in the table!", 1 , taskBusiness.deleteById(1).getId());
    }

    @Test
    @Disabled
    public void getTaskListAfterInsertionSuccess() {
        List<Task> taskList = taskBusiness.getTaskList();
        assertEquals(++TASK_LIST_COUNT, taskList.size());
    }

    @Test
    @Disabled
    public void getTaskListFail() {
        List<Task> taskList = taskBusiness.getTaskList();
        assertNotEquals(TASK_LIST_COUNT_ZERO, taskList.size());
    }

}