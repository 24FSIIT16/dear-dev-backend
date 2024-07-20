package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.SubmitEmotionSurveyDTO;
import ch.fhnw.deardevbackend.entities.EmotionSurvey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubmitEmotionSurveyMapper {
    SubmitEmotionSurveyMapper INSTANCE = Mappers.getMapper(SubmitEmotionSurveyMapper.class);

    @Mapping(target = "submitted", expression = "java(java.time.OffsetDateTime.now().toString())")
    EmotionSurvey toEmotionSurvey(SubmitEmotionSurveyDTO dto);
}

