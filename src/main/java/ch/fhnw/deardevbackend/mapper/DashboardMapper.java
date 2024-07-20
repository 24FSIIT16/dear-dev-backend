package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.DashboardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DashboardMapper {
    DashboardMapper INSTANCE = Mappers.getMapper(DashboardMapper.class);

    default DashboardDTO toDashboardDTO(Integer workKindId, String workKindName, Long voteCount,  Integer averageScore) {
        DashboardDTO.WorkKindDTO workKindDTO = new DashboardDTO.WorkKindDTO(workKindId, workKindName, voteCount);

        return new DashboardDTO(workKindDTO, averageScore);
    }
}
