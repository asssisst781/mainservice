package kz.sabirov.mainservice.services;

import kz.sabirov.mainservice.DTO.ChapterDTO;
import kz.sabirov.mainservice.DTO.LessonDTO;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.entities.Course;
import kz.sabirov.mainservice.entities.Lesson;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LessonService {
    void addLesson(LessonDTO lessonDTO);
    Lesson getLesson(Long id);
    void deleteLesson(Long id);
    void updateLesson(Long id, LessonDTO lessonDTO);
    List<Lesson> getAllLessons();
}
