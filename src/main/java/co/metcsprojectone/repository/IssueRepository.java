package co.metcsprojectone.repository;

import co.metcsprojectone.domain.Issue;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Issue entity.
 */
@SuppressWarnings("unused")
public interface IssueRepository extends JpaRepository<Issue,Long> {

}
