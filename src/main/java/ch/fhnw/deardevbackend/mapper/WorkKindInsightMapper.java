package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.insights.WorkKindInsightDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface WorkKindInsightMapper {
    WorkKindInsightMapper INSTANCE = Mappers.getMapper(WorkKindInsightMapper.class);

    @Mapping(source = "workKindId", target = "workKindId")
    @Mapping(source = "workKindName", target = "workKindName")
    @Mapping(source = "userAverage", target = "userAverage")
    @Mapping(source = "userCount", target = "userCount")
    @Mapping(source = "teamAverage", target = "teamAverage")
    @Mapping(source = "teamCount", target = "teamCount")
    WorkKindInsightDTO toDTO(Integer workKindId, String workKindName, Double userAverage, Long userCount, Double teamAverage, Long teamCount);

    @Mapping(source = "workKindId", target = "workKindId")
    @Mapping(source = "workKindName", target = "workKindName")
    @Mapping(source = "userAverage", target = "userAverage")
    @Mapping(source = "userCount", target = "userCount")
    WorkKindInsightDTO toUserDTO(Integer workKindId, String workKindName, Double userAverage, Long userCount);

    @Mapping(source = "workKindId", target = "workKindId")
    @Mapping(source = "workKindName", target = "workKindName")
    @Mapping(source = "teamAverage", target = "teamAverage")
    @Mapping(source = "teamCount", target = "teamCount")
    WorkKindInsightDTO toTeamDTO(Integer workKindId, String workKindName, Double teamAverage, Long teamCount);
}
