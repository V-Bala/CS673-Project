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

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "pfiles")
    private byte[] pfiles;

    @Column(name = "pfiles_content_type")
    private String pfilesContentType;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Issue> issues = new HashSet<>();

    @ManyToOne
    private User powner;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_pmember",
               joinColumns = @JoinColumn(name="projects_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="pmembers_id", referencedColumnName="id"))
    private Set<User> pmembers = new HashSet<>();

    @OneToMany(mappedBy = "projectcomment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> projectcomments = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Userstory> userstories = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requirement> requirements = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPfiles() {
        return pfiles;
    }

    public Project pfiles(byte[] pfiles) {
        this.pfiles = pfiles;
        return this;
    }

    public void setPfiles(byte[] pfiles) {
        this.pfiles = pfiles;
    }

    public String getPfilesContentType() {
        return pfilesContentType;
    }

    public Project pfilesContentType(String pfilesContentType) {
        this.pfilesContentType = pfilesContentType;
        return this;
    }

    public void setPfilesContentType(String pfilesContentType) {
        this.pfilesContentType = pfilesContentType;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public Project issues(Set<Issue> issues) {
        this.issues = issues;
        return this;
    }

    public Project addIssue(Issue issue) {
        this.issues.add(issue);
        issue.setProject(this);
        return this;
    }

    public Project removeIssue(Issue issue) {
        this.issues.remove(issue);
        issue.setProject(null);
        return this;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    public User getPowner() {
        return powner;
    }

    public Project powner(User user) {
        this.powner = user;
        return this;
    }

    public void setPowner(User user) {
        this.powner = user;
    }

    public Set<User> getPmembers() {
        return pmembers;
    }

    public Project pmembers(Set<User> users) {
        this.pmembers = users;
        return this;
    }

    public Project addPmember(User user) {
        this.pmembers.add(user);
        return this;
    }

    public Project removePmember(User user) {
        this.pmembers.remove(user);
        return this;
    }

    public void setPmembers(Set<User> users) {
        this.pmembers = users;
    }

    public Set<Comment> getProjectcomments() {
        return projectcomments;
    }

    public Project projectcomments(Set<Comment> comments) {
        this.projectcomments = comments;
        return this;
    }

    public Project addProjectcomment(Comment comment) {
        this.projectcomments.add(comment);
        comment.setProjectcomment(this);
        return this;
    }

    public Project removeProjectcomment(Comment comment) {
        this.projectcomments.remove(comment);
        comment.setProjectcomment(null);
        return this;
    }

    public void setProjectcomments(Set<Comment> comments) {
        this.projectcomments = comments;
    }

    public Set<Userstory> getUserstories() {
        return userstories;
    }

    public Project userstories(Set<Userstory> userstories) {
        this.userstories = userstories;
        return this;
    }

    public Project addUserstory(Userstory userstory) {
        this.userstories.add(userstory);
        userstory.setProject(this);
        return this;
    }

    public Project removeUserstory(Userstory userstory) {
        this.userstories.remove(userstory);
        userstory.setProject(null);
        return this;
    }

    public void setUserstories(Set<Userstory> userstories) {
        this.userstories = userstories;
    }

    public Set<Requirement> getRequirements() {
        return requirements;
    }

    public Project requirements(Set<Requirement> requirements) {
        this.requirements = requirements;
        return this;
    }

    public Project addRequirement(Requirement requirement) {
        this.requirements.add(requirement);
        requirement.setProject(this);
        return this;
    }

    public Project removeRequirement(Requirement requirement) {
        this.requirements.remove(requirement);
        requirement.setProject(null);
        return this;
    }

    public void setRequirements(Set<Requirement> requirements) {
        this.requirements = requirements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        if (project.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", pfiles='" + pfiles + "'" +
            ", pfilesContentType='" + pfilesContentType + "'" +
            '}';
    }
}
