package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.WorkKindSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface WorkKindSurveyRepository extends JpaRepository<WorkKindSurvey, Integer> {

    @Query("SELECT w.workKindId, COUNT(w.workKindId) as voteCount " +
            "FROM WorkKindSurvey w " +
            "WHERE w.userId = :userId " +
            "GROUP BY w.workKindId " +
            "ORDER BY voteCount DESC")
    List<Object[]> findWorkKindCountByUserId(Integer userId);

    @Query("SELECT AVG(w.score) " +
            "FROM WorkKindSurvey w " +
            "WHERE w.workKindId = :workKindId AND w.userId = :userId")
    Optional<Integer> findAverageHappinessScoreByWorkKindIdAndUserId(Integer workKindId, Integer userId);

    @Query("SELECT COUNT(DISTINCT wk.id) FROM WorkKind wk WHERE wk.teamId = :teamId")
    int findDistinctWorkKindCountByTeamId(@Param("teamId") Integer teamId);
}
