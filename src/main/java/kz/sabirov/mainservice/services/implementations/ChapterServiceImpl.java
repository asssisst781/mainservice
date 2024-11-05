package kz.sabirov.mainservice.services.implementations;

import kz.sabirov.mainservice.DTO.ChapterDTO;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.entities.Course;
import kz.sabirov.mainservice.mappers.ChapterMapper;
import kz.sabirov.mainservice.repositories.ChapterRepository;
import kz.sabirov.mainservice.services.ChapterService;
import kz.sabirov.mainservice.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterMapper chapterMapper;
    @Override
    public void addChapter(ChapterDTO chapterDTO) {
        LocalDateTime ld = LocalDateTime.now();
        if(Objects.isNull(chapterDTO) || chapterDTO.getCourseId() == null || chapterDTO.getName() == null
        || chapterDTO.getOrder() == 0 || chapterDTO.getDescription() == null){
            throw new IllegalArgumentException("Fields cannot be empty");
        }
        Course course = courseService.getCourse(chapterDTO.getCourseId());
        if(course == null){
            throw new IllegalArgumentException("Course not found");
        }
        Chapter chapter = chapterMapper.DTOToEntity(chapterDTO);
        chapter.setCourse(course);
        chapter.setCreatedTime(ld);
        chapter.setUpdatedTime(ld);
        chapterRepository.save(chapter);
    }
    @Override
    public Chapter getChapter(Long id) {
        Chapter chapter = chapterRepository.findAllById(id);
        if(chapter == null){
            throw new IllegalArgumentException("Chapter not found");
        }
        return chapter;
    }

    @Override
    public void deleteChapter(Long id){
        Chapter chapter = chapterRepository.findAllById(id);
        if(chapter == null){
            throw new IllegalArgumentException("Chapter not found");
        }
        chapterRepository.deleteById(id);

        
    }

    @Override
    public void updateChapter(Long id, ChapterDTO chapterDTO) {
        if(Objects.isNull(chapterDTO) || chapterDTO.getCourseId() == null || chapterDTO.getName() == null
                || chapterDTO.getOrder() == 0 || chapterDTO.getDescription() == null){
            throw new IllegalArgumentException("Fields cannot be empty");
        }
        Chapter chapter = chapterRepository.findAllById(id);
        if(chapter == null){
            throw new IllegalArgumentException("Chapter not found");
        }
        Course course = courseService.getCourse(chapterDTO.getCourseId());
        if(course == null){
            throw new IllegalArgumentException("Course not found");
        }
        LocalDateTime created = chapter.getCreatedTime();
        LocalDateTime ld = LocalDateTime.now();
        chapter = chapterMapper.DTOToEntity(chapterDTO);
        chapter.setCourse(course);
        chapter.setCreatedTime(created);
        chapter.setUpdatedTime(ld);
        chapterRepository.save(chapter);

    }

    @Override
    public List<Chapter> getAllChapters() {
        return chapterRepository.findAll();
    }
}
