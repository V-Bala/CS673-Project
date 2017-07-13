package co.metcsprojectone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "commenttext")
    private String commenttext;

    @Column(name = "date")
    private ZonedDateTime date;

    @ManyToOne
    private User usercomment;

    @ManyToOne
    private Project projectcomment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommenttext() {
        return commenttext;
    }

    public Comment commenttext(String commenttext) {
        this.commenttext = commenttext;
        return this;
    }

    public void setCommenttext(String commenttext) {
        this.commenttext = commenttext;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Comment date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public User getUsercomment() {
        return usercomment;
    }

    public Comment usercomment(User user) {
        this.usercomment = user;
        return this;
    }

    public void setUsercomment(User user) {
        this.usercomment = user;
    }

    public Project getProjectcomment() {
        return projectcomment;
    }

    public Comment projectcomment(Project project) {
        this.projectcomment = project;
        return this;
    }

    public void setProjectcomment(Project project) {
        this.projectcomment = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        if (comment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + id +
            ", commenttext='" + commenttext + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
