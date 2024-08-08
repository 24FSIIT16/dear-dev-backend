package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.EmotionSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmotionSurveyRepository extends JpaRepository<EmotionSurvey, Integer> {
    @Query(value = "SELECT e.name FROM emotion_survey es " +
            "JOIN emotion e ON es.emotion_id = e.id " +
            "WHERE es.user_id = :userId " +
            "GROUP BY e.name " +
            "ORDER BY COUNT(e.name) DESC " +
            "LIMIT 2",
            nativeQuery = true)
    List<String> findTopTwoTrackedEmotions(Integer userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM EmotionSurvey es WHERE es.userId = :userId")
    void deleteByUserId(Integer userId);
}
