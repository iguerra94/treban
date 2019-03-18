package ar.edu.iua.treban.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="tasks")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int id;

    @Column(name="name", length = 200, nullable = false)
    private String name;

    @Column(name="created_at", nullable = false)
    private Date createdAt;

    @Column(name="last_modified", nullable = false)
    private Date lastModified;

    @Transient
    private String priority;

    @Column(name="priority", length = 6, nullable = false)
    @JsonIgnore
    private int priorityValue;

    @Column(name="estimation", nullable = false)
    private Integer estimation;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="task_list_id")
    private TaskList status;

    public Task() {}

    public Task(String name, Date createdAt, Date lastModified, int priorityValue, Integer estimation, TaskList status) {
        this.name = name;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
        this.priorityValue = priorityValue;
        this.estimation = estimation;
        this.status = status;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getPriorityValue() {
        return priorityValue;
    }

    public void setPriorityValue(int priorityValue) {
        this.priorityValue = priorityValue;
    }

    public Integer getEstimation() {
        return estimation;
    }

    public void setEstimation(Integer estimation) {
        this.estimation = estimation;
    }

    public TaskList getStatus() {
        return status;
    }

    public void setStatus(TaskList status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task: (name = " + name + ", createdAt = " + createdAt + ", lastModified = " + lastModified +
                ", priority = " + priority + ", priorityValue = " + priorityValue + ", estimation = " + estimation + ", status = " + status + ')';
    }
}