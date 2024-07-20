package ch.fhnw.deardevbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MostVotedWorkKindDTO {
    private Integer workKindId;
    private String workKindName;
    private Long voteCount;
}
