package kz.sabirov.mainservice.services;

import kz.sabirov.mainservice.DTO.ChapterDTO;
import kz.sabirov.mainservice.DTO.CourseDTO;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.entities.Course;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    void addCourse(CourseDTO courseDTO);
    Course getCourse(Long id);
    void deleteCourse(Long id);
    void updateCourse(Long id, CourseDTO courseDTO);
    List<Course> getAllCourses();
}
