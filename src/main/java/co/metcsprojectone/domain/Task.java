package co.metcsprojectone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "targetdate")
    private ZonedDateTime targetdate;

    @ManyToOne
    private Userstory userstory;

    @ManyToOne
    private Tmember tmember;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Task title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getTargetdate() {
        return targetdate;
    }

    public Task targetdate(ZonedDateTime targetdate) {
        this.targetdate = targetdate;
        return this;
    }

    public void setTargetdate(ZonedDateTime targetdate) {
        this.targetdate = targetdate;
    }

    public Userstory getUserstory() {
        return userstory;
    }

    public Task userstory(Userstory userstory) {
        this.userstory = userstory;
        return this;
    }

    public void setUserstory(Userstory userstory) {
        this.userstory = userstory;
    }

    public Tmember getTmember() {
        return tmember;
    }

    public Task tmember(Tmember tmember) {
        this.tmember = tmember;
        return this;
    }

    public void setTmember(Tmember tmember) {
        this.tmember = tmember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        if (task.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", targetdate='" + getTargetdate() + "'" +
            "}";
    }
}
