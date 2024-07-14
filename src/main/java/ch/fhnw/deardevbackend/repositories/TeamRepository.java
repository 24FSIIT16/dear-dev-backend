package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    boolean existsByCode(String code);
}
