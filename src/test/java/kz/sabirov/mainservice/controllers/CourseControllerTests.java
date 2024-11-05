package kz.sabirov.mainservice.controllers;

import kz.sabirov.mainservice.DTO.CourseDTO;
import kz.sabirov.mainservice.entities.Course;
import kz.sabirov.mainservice.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CourseControllerTests {

    @InjectMocks
    private CourseController courseController;

    @Mock
    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCourse_Success() {
        CourseDTO courseDTO = new CourseDTO();
        ResponseEntity<?> response = courseController.addCourse(courseDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course added", response.getBody());
        verify(courseService, times(1)).addCourse(courseDTO);
    }

    @Test
    public void testAddCourse_Failure() {
        CourseDTO courseDTO = new CourseDTO();
        doThrow(new IllegalArgumentException("Invalid course")).when(courseService).addCourse(any(CourseDTO.class));

        ResponseEntity<?> response = courseController.addCourse(courseDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid course", response.getBody());
        verify(courseService, times(1)).addCourse(courseDTO);
    }

    @Test
    public void testDeleteCourse_Success() {
        ResponseEntity<?> response = courseController.deleteCourse(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course deleted", response.getBody());
        verify(courseService, times(1)).deleteCourse(1L);
    }

    @Test
    public void testDeleteCourse_Failure() {
        doThrow(new IllegalArgumentException("Invalid course ID")).when(courseService).deleteCourse(anyLong());

        ResponseEntity<?> response = courseController.deleteCourse(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid course ID", response.getBody());
        verify(courseService, times(1)).deleteCourse(1L);
    }

    @Test
    public void testGetCourse_Success() {
        Course course = new Course();
        when(courseService.getCourse(anyLong())).thenReturn(course);

        ResponseEntity<?> response = courseController.getCourse(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
        verify(courseService, times(1)).getCourse(1L);
    }

    @Test
    public void testGetCourse_Failure() {
        doThrow(new IllegalArgumentException("Course not found")).when(courseService).getCourse(anyLong());

        ResponseEntity<?> response = courseController.getCourse(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Course not found", response.getBody());
        verify(courseService, times(1)).getCourse(1L);
    }

    @Test
    public void testGetAllCourses_Success() {
        ResponseEntity<?> response = courseController.getAllChapters();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    public void testGetAllCourses_Failure() {
        doThrow(new RuntimeException("Unexpected error")).when(courseService).getAllCourses();

        ResponseEntity<?> response = courseController.getAllChapters();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", response.getBody());
        verify(courseService, times(1)).getAllCourses();
    }
}
