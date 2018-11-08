package ar.edu.iua.treban.model.persistence;

import ar.edu.iua.treban.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t FROM Task t JOIN t.status tl WHERE tl.name = ?1")
    List<Task> findAllByListName(String name);

    @Query("SELECT t FROM Task t JOIN t.status tl WHERE tl.name = ?1 ORDER BY t.createdAt DESC")
    List<Task> findAllByListNameOrderByCreatedAtDesc(String name);

    @Query("SELECT t FROM Task t JOIN t.status tl WHERE tl.name = ?1 ORDER BY t.priorityValue DESC")
    List<Task> findAllByListNameOrderByPriorityDesc(String name);

    Task findByName(String name);
}