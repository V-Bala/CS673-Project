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
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "teamname")
    private String teamname;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Project> projects = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "team_tmember",
               joinColumns = @JoinColumn(name="teams_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tmembers_id", referencedColumnName="id"))
    private Set<Tmember> tmembers = new HashSet<>();

    @ManyToOne
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamname() {
        return teamname;
    }

    public Team teamname(String teamname) {
        this.teamname = teamname;
        return this;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Team projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Team addProject(Project project) {
        this.projects.add(project);
        project.setTeam(this);
        return this;
    }

    public Team removeProject(Project project) {
        this.projects.remove(project);
        project.setTeam(null);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Tmember> getTmembers() {
        return tmembers;
    }

    public Team tmembers(Set<Tmember> tmembers) {
        this.tmembers = tmembers;
        return this;
    }

    public Team addTmember(Tmember tmember) {
        this.tmembers.add(tmember);
        tmember.getTeams().add(this);
        return this;
    }

    public Team removeTmember(Tmember tmember) {
        this.tmembers.remove(tmember);
        tmember.getTeams().remove(this);
        return this;
    }

    public void setTmembers(Set<Tmember> tmembers) {
        this.tmembers = tmembers;
    }

    public User getOwner() {
        return owner;
    }

    public Team owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        if (team.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + id +
            ", teamname='" + teamname + "'" +
            '}';
    }
}
