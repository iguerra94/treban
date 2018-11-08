package ar.edu.iua.treban.dao;

import ar.edu.iua.treban.model.TaskList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class TaskListImplDAO implements IGenericDAO<TaskList, Integer> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private static TaskListImplDAO instance;

    private TaskListImplDAO() {}

    @Bean
    public static TaskListImplDAO getInstance() {
        if (instance == null) {
            instance = new TaskListImplDAO();
        }
        return instance;
    }

    @Override
    public TaskList add(TaskList taskList) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;
        TaskList taskListCreated;

        try {
            tx = session.beginTransaction();

            int id = (Integer) session.save(taskList);

            taskListCreated = session.get(TaskList.class, id);

            if (taskListCreated == null) {
                throw new RuntimeException();
            }

            tx.commit();

            log.info("The task list with id = {} was created", id);
        } catch (RuntimeException e) {
            if (tx != null)
                tx.rollback();
            log.error("Error when trying to create the task list: {}", e.getMessage());
            throw e;
        } finally {
            session.close();
        }

        return taskListCreated;
    }

    @Override
    public TaskList findByName(String name) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        Transaction tx;
        TaskList taskListFound = null;

        try {
            tx = session.beginTransaction();

            session.flush();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<TaskList> query = builder.createQuery(TaskList.class);
            Root<TaskList> from = query.from(TaskList.class);

            query.select(from).where(builder.equal(from.get("name"), name));
            taskListFound = session.createQuery(query).uniqueResult();

            tx.commit();
            log.info("The list with name {} was found.", name);
        } catch (HibernateException e) {
            e.printStackTrace();
            log.error("The list with name {} was not found.", name);
        } finally {
            session.close();
        }

        return taskListFound;
    }
}