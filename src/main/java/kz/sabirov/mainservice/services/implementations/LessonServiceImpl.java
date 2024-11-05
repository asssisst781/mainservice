package kz.sabirov.mainservice.services.implementations;

import kz.sabirov.mainservice.DTO.CourseDTO;
import kz.sabirov.mainservice.DTO.LessonDTO;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.entities.Course;
import kz.sabirov.mainservice.entities.Lesson;
import kz.sabirov.mainservice.mappers.CourseMapper;
import kz.sabirov.mainservice.mappers.LessonMapper;
import kz.sabirov.mainservice.repositories.CourseRepository;
import kz.sabirov.mainservice.repositories.LessonRepository;
import kz.sabirov.mainservice.services.ChapterService;
import kz.sabirov.mainservice.services.CourseService;
import kz.sabirov.mainservice.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private LessonMapper lessonMapper;
    @Override
    public void addLesson(LessonDTO lessonDTO) {
        if(Objects.isNull(lessonDTO) || lessonDTO.getName() == null
                || lessonDTO.getDescription() == null || lessonDTO.getContent() == null ||
                lessonDTO.getChapterId() == null || lessonDTO.getOrder() <= 0){
            throw new IllegalArgumentException("Fields cannot be empty");
        }
        Chapter chapter = chapterService.getChapter(lessonDTO.getChapterId());
        if(chapter == null){
            throw new IllegalArgumentException("Chapter not found");
        }
        LocalDateTime ld = LocalDateTime.now();
        Lesson lesson = lessonMapper.DTOToEntity(lessonDTO);
        lesson.setChapter(chapter);
        lesson.setCreatedTime(ld);
        lesson.setUpdatedTime(ld);
        lessonRepository.save(lesson);
    }
    @Override
    public Lesson getLesson(Long id){
        Lesson lesson = lessonRepository.findAllById(id);
        if(lesson == null){
            throw new IllegalArgumentException("Lesson not found");
        }
        return lesson;
    }

    @Override
    public void deleteLesson(Long id){
        Lesson lesson = lessonRepository.findAllById(id);
        if(lesson == null){
            throw new IllegalArgumentException("Lesson not found");
        }
        lessonRepository.deleteById(id);
    }

    @Override
    public void updateLesson(Long id, LessonDTO lessonDTO){
        if(Objects.isNull(lessonDTO) || lessonDTO.getName() == null
                || lessonDTO.getDescription() == null || lessonDTO.getContent() == null ||
                lessonDTO.getChapterId() == null || lessonDTO.getOrder() <= 0){
            throw new IllegalArgumentException("Fields cannot be empty");
        }
        Lesson lesson = lessonRepository.findAllById(id);
        if(lesson == null){
            throw new IllegalArgumentException("Lesson not found");
        }
        LocalDateTime created = lesson.getCreatedTime();
        LocalDateTime ld = LocalDateTime.now();
        lesson = lessonMapper.DTOToEntity(lessonDTO);
        lesson.setCreatedTime(created);
        lesson.setUpdatedTime(ld);
        lessonRepository.save(lesson);

    }

    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }
}
