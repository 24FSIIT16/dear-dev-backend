package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.SprintConfig;
import ch.fhnw.deardevbackend.entities.SprintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SprintConfigRepository extends JpaRepository<SprintConfig, Integer> {

    List<SprintConfig> findByTeamIdAndStatus(Integer teamId, SprintStatus status);

    List<SprintConfig> findAllByCreatedBy(Integer createdBy);

    List<SprintConfig> findByCreatedByAndStatusAndStartDateAfter(Integer createdBy, SprintStatus status, LocalDate startDate);

    @Query(value = "SELECT s.end_date FROM sprint_config s " +
            "WHERE s.team_id IN (SELECT tm.team_id FROM team_member tm WHERE tm.user_id = :userId) " +
            "AND s.status = 'IN_PROGRESS' " +
            "ORDER BY s.end_date " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<LocalDate> findClosestActiveSprintEndDateByUserId(Integer userId);
}
