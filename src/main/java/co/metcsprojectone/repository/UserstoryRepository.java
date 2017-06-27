package co.metcsprojectone.repository;

import co.metcsprojectone.domain.Userstory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Userstory entity.
 */
@SuppressWarnings("unused")
public interface UserstoryRepository extends JpaRepository<Userstory,Long> {

}
