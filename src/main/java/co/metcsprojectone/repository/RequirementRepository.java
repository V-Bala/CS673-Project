package co.metcsprojectone.repository;

import co.metcsprojectone.domain.Requirement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Requirement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequirementRepository extends JpaRepository<Requirement,Long> {

}
