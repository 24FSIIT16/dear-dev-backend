package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.dto.MostVotedWorkKindDTO;
import ch.fhnw.deardevbackend.entities.WorkKindSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface WorkKindSurveyRepository extends JpaRepository<WorkKindSurvey, Integer> {

    @Query("SELECT new ch.fhnw.deardevbackend.dto.MostVotedWorkKindDTO(w.workKindId, COUNT(w.workKindId)) " +
            "FROM WorkKindSurvey w " +
            "WHERE w.userId = :userId " +
            "GROUP BY w.workKindId " +
            "ORDER BY COUNT(w.workKindId) DESC")
    List<MostVotedWorkKindDTO> findMostVotedWorkKindByUserId(@Param("userId") int userId);

    @Query("SELECT AVG(w.score) " +
            "FROM WorkKindSurvey w " +
            "WHERE w.workKindId = :workKindId AND w.userId = :userId")
    Optional<Double> findAverageHappinessScoreByWorkKindIdAndUserId(Integer workKindId, int userId);

}
