package ch.fhnw.deardevbackend.dto;

import lombok.Data;

@Data
public class SubmitEmotionSurveyDTO {
    private Integer userId;
    private Integer emotionId;
}
