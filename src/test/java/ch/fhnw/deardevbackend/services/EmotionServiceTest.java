package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.entities.Emotion;
import ch.fhnw.deardevbackend.repositories.EmotionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EmotionServiceTest {

    @Mock
    private EmotionRepository emotionRepository;

    @InjectMocks
    private EmotionService emotionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEmotions() {
        List<Emotion> emotions = Arrays.asList(
                new Emotion(1, "Happy"),
                new Emotion(2, "Sad"),
                new Emotion(3, "Angry")
        );
        when(emotionRepository.findAll()).thenReturn(emotions);

        List<Emotion> result = emotionService.getEmotions();

        assertEquals(3, result.size());
        assertEquals("Happy", result.get(0).getName());
        assertEquals("Sad", result.get(1).getName());
        assertEquals("Angry", result.get(2).getName());
    }

    @Test
    void getEmotions_EmptyList() {
        when(emotionRepository.findAll()).thenReturn(List.of());

        List<Emotion> result = emotionService.getEmotions();

        assertEquals(0, result.size());
    }
}
