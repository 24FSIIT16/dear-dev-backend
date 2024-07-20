package ch.fhnw.deardevbackend.dto;

import lombok.Data;

@Data
public class SubmitEmotionSurveyDTO {
    private int userId;
    private int emotionId;
}
