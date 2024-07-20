package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmotionRepository extends JpaRepository<Emotion, Integer> {

}
