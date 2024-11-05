package kz.sabirov.mainservice.services.implementations;

import kz.sabirov.mainservice.DTO.ChapterDTO;
import kz.sabirov.mainservice.DTO.CourseDTO;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.entities.Course;
import kz.sabirov.mainservice.mappers.ChapterMapper;
import kz.sabirov.mainservice.mappers.CourseMapper;
import kz.sabirov.mainservice.repositories.ChapterRepository;
import kz.sabirov.mainservice.repositories.CourseRepository;
import kz.sabirov.mainservice.services.ChapterService;
import kz.sabirov.mainservice.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public void addCourse(CourseDTO courseDTO) {
        if(Objects.isNull(courseDTO) || courseDTO.getName() == null
                || courseDTO.getDescription() == null){
            throw new IllegalArgumentException("Fields cannot be empty");
        }
        LocalDateTime ld = LocalDateTime.now();
        Course course = courseMapper.DTOToEntity(courseDTO);
        course.setCreatedTime(ld);
        course.setUpdatedTime(ld);
        courseRepository.save(course);
    }

    @Override
    public Course getCourse(Long id) {
        Course course = courseRepository.findAllById(id);
        if(course == null){
            throw new IllegalArgumentException("Course not found");
        }
        return course;
    }

    @Override
    public void deleteCourse(Long id){
        Course course = courseRepository.findAllById(id);
        if(course == null){
            throw new IllegalArgumentException("Course not found");
        }
        courseRepository.deleteById(id);
    }

    @Override
    public void updateCourse(Long id, CourseDTO courseDTO){
        if(Objects.isNull(courseDTO) || courseDTO.getName() == null
                || courseDTO.getDescription() == null){
            throw new IllegalArgumentException("Fields cannot be empty");
        }
        Course course = courseRepository.findAllById(id);
        if(course == null){
            throw new IllegalArgumentException("Course not found");
        }
        LocalDateTime created = course.getCreatedTime();
        LocalDateTime ld = LocalDateTime.now();
        course = courseMapper.DTOToEntity(courseDTO);
        course.setCreatedTime(created);
        course.setUpdatedTime(ld);
        courseRepository.save(course);

    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
