package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.TeamConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamConfigRepository extends JpaRepository<TeamConfig, Integer> {
}
