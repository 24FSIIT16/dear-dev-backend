package ch.fhnw.deardevbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MostVotedWorkKindDTO {
    private Integer workKindId;
    private Long voteCount;

    public MostVotedWorkKindDTO(Integer workKindId, Long voteCount) {
        this.workKindId = workKindId;
        this.voteCount = voteCount;
    }
}