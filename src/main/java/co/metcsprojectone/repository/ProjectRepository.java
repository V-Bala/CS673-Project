package co.metcsprojectone.repository;

import co.metcsprojectone.domain.Project;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Project entity.
 */
@SuppressWarnings("unused")
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("select project from Project project where project.powner.login = ?#{principal.username}")
    List<Project> findByPownerIsCurrentUser();

    @Query("select distinct project from Project project left join fetch project.pmembers")
    List<Project> findAllWithEagerRelationships();

    @Query("select project from Project project left join fetch project.pmembers where project.id =:id")
    Project findOneWithEagerRelationships(@Param("id") Long id);

    @Query
    List<Project> findByPmembers_Login(String login);


}
