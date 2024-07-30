package ch.fhnw.deardevbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamConfigDTO {
    private Integer id;
    private String teamName;
    private List<WorkKindDTO> workKinds;
    private Boolean happinessSurvey;
    private Boolean workKindSurvey;
    private Boolean emotionSurvey;
}
