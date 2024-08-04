package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.SprintConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintConfigRepository extends JpaRepository<SprintConfig, Integer> {
    List<SprintConfig> findAllByCreatedBy(Integer createdBy);
}
