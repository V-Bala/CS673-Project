package co.metcsprojectone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import co.metcsprojectone.domain.enumeration.Status;

import co.metcsprojectone.domain.enumeration.Priority;

/**
 * A Userstory.
 */
@Entity
@Table(name = "userstory")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userstory")
public class Userstory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "comments")
    private String comments;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @OneToMany(mappedBy = "userstory")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "userstory_tmember",
               joinColumns = @JoinColumn(name="userstories_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tmembers_id", referencedColumnName="id"))
    private Set<Tmember> tmembers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Userstory title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Userstory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public Userstory comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Status getStatus() {
        return status;
    }

    public Userstory status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public Userstory priority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Userstory tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Userstory addTask(Task task) {
        this.tasks.add(task);
        task.setUserstory(this);
        return this;
    }

    public Userstory removeTask(Task task) {
        this.tasks.remove(task);
        task.setUserstory(null);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Tmember> getTmembers() {
        return tmembers;
    }

    public Userstory tmembers(Set<Tmember> tmembers) {
        this.tmembers = tmembers;
        return this;
    }

    public Userstory addTmember(Tmember tmember) {
        this.tmembers.add(tmember);
        tmember.getUserstories().add(this);
        return this;
    }

    public Userstory removeTmember(Tmember tmember) {
        this.tmembers.remove(tmember);
        tmember.getUserstories().remove(this);
        return this;
    }

    public void setTmembers(Set<Tmember> tmembers) {
        this.tmembers = tmembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Userstory userstory = (Userstory) o;
        if (userstory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userstory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Userstory{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", comments='" + getComments() + "'" +
            ", status='" + getStatus() + "'" +
            ", priority='" + getPriority() + "'" +
            "}";
    }
}
