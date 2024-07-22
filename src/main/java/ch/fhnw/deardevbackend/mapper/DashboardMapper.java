package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.DashboardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DashboardMapper {
    DashboardMapper INSTANCE = Mappers.getMapper(DashboardMapper.class);

    default DashboardDTO toDashboardDTO(Integer workKindId, String workKindName, Integer voteCount,  Integer averageScore, Integer happinessScore) {
        DashboardDTO.WorkKindDTO workKindDTO = new DashboardDTO.WorkKindDTO(workKindId, workKindName, voteCount, happinessScore);

        return new DashboardDTO(workKindDTO, averageScore);
    }
}
