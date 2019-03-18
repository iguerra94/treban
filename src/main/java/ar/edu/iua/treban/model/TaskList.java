package ar.edu.iua.treban.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "task_lists")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TaskList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_list_id")
    private int id;
    @Column(name = "name", length = 11, nullable = false)
    private String name;

    @Column(name = "sprint_name", length = 50, nullable = false)
    private String sprintName;

    @OneToMany(mappedBy="status")
    @JsonIgnore
    private List<Task> tasks;

    public TaskList() {}

    public TaskList(int id, String name, String sprintName, List<Task> tasks) {
        super();
        this.id = id;
        this.name = name;
        this.sprintName = sprintName;
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "TaskList: (name = " + name + ", sprintName = " + sprintName + ')';
    }
}