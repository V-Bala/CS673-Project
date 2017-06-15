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
 * A Tmember.
 */
@Entity
@Table(name = "tmember")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tmember")
public class Tmember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "membername")
    private String membername;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "tmember")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany(mappedBy = "tmembers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Userstory> userstories = new HashSet<>();

    @ManyToMany(mappedBy = "tmembers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Team> teams = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMembername() {
        return membername;
    }

    public Tmember membername(String membername) {
        this.membername = membername;
        return this;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public User getUser() {
        return user;
    }

    public Tmember user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Tmember tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Tmember addTask(Task task) {
        this.tasks.add(task);
        task.setTmember(this);
        return this;
    }

    public Tmember removeTask(Task task) {
        this.tasks.remove(task);
        task.setTmember(null);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Userstory> getUserstories() {
        return userstories;
    }

    public Tmember userstories(Set<Userstory> userstories) {
        this.userstories = userstories;
        return this;
    }

    public Tmember addUserstory(Userstory userstory) {
        this.userstories.add(userstory);
        userstory.getTmembers().add(this);
        return this;
    }

    public Tmember removeUserstory(Userstory userstory) {
        this.userstories.remove(userstory);
        userstory.getTmembers().remove(this);
        return this;
    }

    public void setUserstories(Set<Userstory> userstories) {
        this.userstories = userstories;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Tmember teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Tmember addTeam(Team team) {
        this.teams.add(team);
        team.getTmembers().add(this);
        return this;
    }

    public Tmember removeTeam(Team team) {
        this.teams.remove(team);
        team.getTmembers().remove(this);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tmember tmember = (Tmember) o;
        if (tmember.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tmember.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tmember{" +
            "id=" + getId() +
            ", membername='" + getMembername() + "'" +
            "}";
    }
}
