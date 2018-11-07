package ar.edu.iua.treban.model.persistence;

import ar.edu.iua.treban.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Integer> {
    TaskList findByName(String name);
}