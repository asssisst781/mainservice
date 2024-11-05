package kz.sabirov.mainservice.mappers;

import kz.sabirov.mainservice.DTO.ChapterDTO;
import kz.sabirov.mainservice.entities.Chapter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    ChapterDTO entityToDTO(Chapter chapter);
    Chapter DTOToEntity(ChapterDTO chapterDTO);
}
