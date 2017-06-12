package co.metcsprojectone.repository;

import co.metcsprojectone.domain.Team;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Team entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {

    @Query("select distinct team from Team team left join fetch team.tmembers")
    List<Team> findAllWithEagerRelationships();

    @Query("select team from Team team left join fetch team.tmembers where team.id =:id")
    Team findOneWithEagerRelationships(@Param("id") Long id);

}
