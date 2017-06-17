package co.metcsprojectone.repository;

import co.metcsprojectone.domain.Userstory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Userstory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserstoryRepository extends JpaRepository<Userstory,Long> {

    @Query("select distinct userstory from Userstory userstory left join fetch userstory.tmembers")
    List<Userstory> findAllWithEagerRelationships();

    @Query("select userstory from Userstory userstory left join fetch userstory.tmembers where userstory.id =:id")
    Userstory findOneWithEagerRelationships(@Param("id") Long id);

    List<Userstory> findAllByid(@Param("id") Long id);

}
