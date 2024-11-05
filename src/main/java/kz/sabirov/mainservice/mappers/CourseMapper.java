package kz.sabirov.mainservice.mappers;

import kz.sabirov.mainservice.DTO.ChapterDTO;
import kz.sabirov.mainservice.DTO.CourseDTO;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.entities.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO entityToDTO(Course course);
    Course DTOToEntity(CourseDTO courseDTO);
}
