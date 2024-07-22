package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.entities.Emotion;
import ch.fhnw.deardevbackend.repositories.EmotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmotionService {

    @Autowired
    private EmotionRepository emotionRepository;

    public List<Emotion> getEmotions() {
        return emotionRepository.findAll();
    }
}
