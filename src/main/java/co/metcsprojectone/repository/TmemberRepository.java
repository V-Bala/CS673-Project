package co.metcsprojectone.repository;

import co.metcsprojectone.domain.Tmember;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tmember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TmemberRepository extends JpaRepository<Tmember,Long> {

}
