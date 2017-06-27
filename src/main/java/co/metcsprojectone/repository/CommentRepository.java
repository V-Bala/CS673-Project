package co.metcsprojectone.repository;

import co.metcsprojectone.domain.Comment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Comment entity.
 */
@SuppressWarnings("unused")
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select comment from Comment comment where comment.usercomment.login = ?#{principal.username}")
    List<Comment> findByUsercommentIsCurrentUser();



}
