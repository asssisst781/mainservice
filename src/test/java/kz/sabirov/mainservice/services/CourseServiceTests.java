package kz.sabirov.mainservice.services;

import kz.sabirov.mainservice.DTO.CourseDTO;
import kz.sabirov.mainservice.entities.Course;
import kz.sabirov.mainservice.mappers.CourseMapper;
import kz.sabirov.mainservice.repositories.CourseRepository;
import kz.sabirov.mainservice.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CourseServiceTests {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    private CourseDTO courseDTO;
    private Course course;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample data for tests
        courseDTO = new CourseDTO();
        courseDTO.setName("Test Course");
        courseDTO.setDescription("Course Description");

        course = new Course();
        course.setId(1L);
        course.setName("Test Course");
        course.setDescription("Course Description");
        course.setCreatedTime(LocalDateTime.now());
        course.setUpdatedTime(LocalDateTime.now());
    }

    @Test
    public void testAddCourse_Success() {
        when(courseMapper.DTOToEntity(any(CourseDTO.class))).thenReturn(course);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        assertDoesNotThrow(() -> courseService.addCourse(courseDTO));
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    public void testAddCourse_EmptyFields() {
        courseDTO.setName(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> courseService.addCourse(courseDTO));
        assertEquals("Fields cannot be empty", exception.getMessage());
    }

    @Test
    public void testGetCourse_Success() {
        when(courseRepository.findAllById(anyLong())).thenReturn(course);

        Course result = courseService.getCourse(1L);
        assertNotNull(result);
        assertEquals("Test Course", result.getName());
    }

    @Test
    public void testGetCourse_NotFound() {
        when(courseRepository.findAllById(anyLong())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> courseService.getCourse(1L));
        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    public void testDeleteCourse_Success() {
        when(courseRepository.findAllById(anyLong())).thenReturn(course);
        doNothing().when(courseRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> courseService.deleteCourse(1L));
        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCourse_NotFound() {
        when(courseRepository.findAllById(anyLong())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> courseService.deleteCourse(1L));
        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    public void testUpdateCourse_Success() {
        when(courseRepository.findAllById(anyLong())).thenReturn(course);
        when(courseMapper.DTOToEntity(any(CourseDTO.class))).thenReturn(course);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        assertDoesNotThrow(() -> courseService.updateCourse(1L, courseDTO));
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    public void testUpdateCourse_EmptyFields() {
        courseDTO.setName(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> courseService.updateCourse(1L, courseDTO));
        assertEquals("Fields cannot be empty", exception.getMessage());
    }

    @Test
    public void testUpdateCourse_CourseNotFound() {
        when(courseRepository.findAllById(anyLong())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> courseService.updateCourse(1L, courseDTO));
        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    public void testGetAllCourses() {
        when(courseRepository.findAll()).thenReturn(Collections.singletonList(course));

        List<Course> courses = courseService.getAllCourses();
        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals("Test Course", courses.get(0).getName());
    }
}
