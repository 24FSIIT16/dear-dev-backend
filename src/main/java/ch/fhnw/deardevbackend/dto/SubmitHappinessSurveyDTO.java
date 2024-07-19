package ch.fhnw.deardevbackend.dto;

import lombok.Data;

@Data
public class SubmitHappinessSurveyDTO {
    private int userId;
    private int score;
}
