package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.CreateSprintDTO;
import ch.fhnw.deardevbackend.entities.SprintConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SprintConfigMapper {

    SprintConfigMapper INSTANCE = Mappers.getMapper(SprintConfigMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    SprintConfig toEntity(CreateSprintDTO dto);
}
