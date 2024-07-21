package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.WorkKindSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    List<Object[]> findMostVotedWorkKindByUserId(int userId);

    @Query("SELECT w.score FROM WorkKindSurvey w WHERE w.workKindId = :workKindId GROUP BY w.score ORDER BY COUNT(w.score) DESC")
    Optional<Integer> findMostSubmittedScoreByWorkKindId(Integer workKindId);
}
