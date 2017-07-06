package co.metcsprojectone.repository;

import co.metcsprojectone.domain.Requirement;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Requirement entity.
 */
@SuppressWarnings("unused")
public interface RequirementRepository extends JpaRepository<Requirement,Long> {

    @Query
    List<Requirement> findAllByProjectId(long id);

}
