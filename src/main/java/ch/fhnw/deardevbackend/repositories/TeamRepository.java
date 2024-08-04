package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    boolean existsByCode(String code);

    Optional<Team> findByCode(String code);

    @Query("SELECT t from Team t JOIN TeamMember tm ON t.id = tm.teamId WHERE tm.userId = :userId AND t.active = TRUE")
    List<Team> findActiveTeamsByUserId(Integer userId);
}
