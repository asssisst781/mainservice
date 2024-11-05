package kz.sabirov.mainservice.mappers;

import kz.sabirov.mainservice.DTO.CourseDTO;
import kz.sabirov.mainservice.DTO.LessonDTO;
import kz.sabirov.mainservice.entities.Course;
import kz.sabirov.mainservice.entities.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    LessonDTO entityToDTO(Lesson lesson);
    Lesson DTOToEntity(LessonDTO lessonDTO);
}
