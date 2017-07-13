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

/**
 * A Requirement.
 */
@Entity
@Table(name = "requirement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "requirement")
public class Requirement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "requirement")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Userstory> userstories = new HashSet<>();

    @ManyToOne
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Requirement name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public Requirement status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Userstory> getUserstories() {
        return userstories;
    }

    public Requirement userstories(Set<Userstory> userstories) {
        this.userstories = userstories;
        return this;
    }

    public Requirement addUserstory(Userstory userstory) {
        this.userstories.add(userstory);
        userstory.setRequirement(this);
        return this;
    }

    public Requirement removeUserstory(Userstory userstory) {
        this.userstories.remove(userstory);
        userstory.setRequirement(null);
        return this;
    }

    public void setUserstories(Set<Userstory> userstories) {
        this.userstories = userstories;
    }

    public Project getProject() {
        return project;
    }

    public Requirement project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Requirement requirement = (Requirement) o;
        if (requirement.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, requirement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Requirement{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
