package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.SubmitWorkKindSurveyDTO;
import ch.fhnw.deardevbackend.entities.WorkKindSurvey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubmitWorkKindSurveyMapper {
    SubmitWorkKindSurveyMapper INSTANCE = Mappers.getMapper(SubmitWorkKindSurveyMapper.class);

    @Mapping(target = "submitted", expression = "java(java.time.LocalDateTime.now())")
   WorkKindSurvey toWorkKindSurvey(SubmitWorkKindSurveyDTO dto);
}

