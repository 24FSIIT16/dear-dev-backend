package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.WorkKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface WorkKindRepository extends JpaRepository<WorkKind, Integer> {

    @Query("SELECT w FROM WorkKind w WHERE w.teamId IS NULL OR w.teamId IN (:teamIds)")
    List<WorkKind> findByTeamIdsOrNoTeam(@Param("teamIds") List<Integer> teamIds);

    Optional<WorkKind> findByNameAndTeamId(String name, Integer teamId);
}
