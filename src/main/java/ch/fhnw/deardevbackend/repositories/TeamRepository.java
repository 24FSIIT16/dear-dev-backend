package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    boolean existsByCode(String code);

    Optional<Team> findByCode(String code);
}
