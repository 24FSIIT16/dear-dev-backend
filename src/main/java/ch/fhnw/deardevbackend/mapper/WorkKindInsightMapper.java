package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.WorkKindInsightDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface WorkKindInsightMapper {
    WorkKindInsightMapper INSTANCE = Mappers.getMapper(WorkKindInsightMapper.class);

    @Mapping(source = "teamId", target = "teamId")
    @Mapping(source = "workKindId", target = "workKindId")
    @Mapping(source = "workKindName", target = "workKindName")
    @Mapping(source = "averageHappiness", target = "averageHappiness")
    @Mapping(source = "totalCount", target = "totalCount")
    WorkKindInsightDTO toDTO(Integer teamId, Integer workKindId, String workKindName, Double averageHappiness, Long totalCount);
}
