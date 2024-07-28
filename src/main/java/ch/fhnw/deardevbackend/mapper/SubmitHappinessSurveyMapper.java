package ch.fhnw.deardevbackend.mapper;
import ch.fhnw.deardevbackend.dto.SubmitHappinessSurveyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ch.fhnw.deardevbackend.entities.HappinessSurvey;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubmitHappinessSurveyMapper {
    SubmitHappinessSurveyMapper INSTANCE = Mappers.getMapper(SubmitHappinessSurveyMapper.class);

    @Mapping(target = "submitted", expression = "java(java.time.LocalDateTime.now())")
    HappinessSurvey toHappinessSurvey(SubmitHappinessSurveyDTO dto);
}

