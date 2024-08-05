package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.SprintConfig;
import ch.fhnw.deardevbackend.entities.SprintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SprintConfigRepository extends JpaRepository<SprintConfig, Integer> {

    List<SprintConfig> findByTeamIdAndStatus(Integer teamId, SprintStatus status);

    List<SprintConfig> findAllByCreatedBy(Integer createdBy);

    List<SprintConfig> findByCreatedByAndStatusAndStartDateAfter(Integer createdBy, SprintStatus status, LocalDate startDate);
}
