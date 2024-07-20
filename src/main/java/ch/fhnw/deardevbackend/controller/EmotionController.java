package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.entities.Emotion;
import ch.fhnw.deardevbackend.services.EmotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/emotions")
public class EmotionController {

    @Autowired
    private EmotionService emotionService;

    @GetMapping()
    public ResponseEntity<List<Emotion>> getAllEmotions() {
        List<Emotion> emotions = emotionService.getEmotions();
        return ResponseEntity.ok(emotions);
    }
}
