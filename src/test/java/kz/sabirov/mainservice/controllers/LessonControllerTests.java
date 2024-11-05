package kz.sabirov.mainservice.controllers;

import kz.sabirov.mainservice.DTO.LessonDTO;
import kz.sabirov.mainservice.entities.Lesson;
import kz.sabirov.mainservice.services.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class LessonControllerTests {

    @InjectMocks
    private LessonController lessonController;

    @Mock
    private LessonService lessonService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddLesson_Success() {
        LessonDTO lessonDTO = new LessonDTO();
        ResponseEntity<?> response = lessonController.addLesson(lessonDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Lesson added", response.getBody());
        verify(lessonService, times(1)).addLesson(lessonDTO);
    }

    @Test
    public void testAddLesson_Failure() {
        LessonDTO lessonDTO = new LessonDTO();
        doThrow(new IllegalArgumentException("Invalid lesson")).when(lessonService).addLesson(any(LessonDTO.class));

        ResponseEntity<?> response = lessonController.addLesson(lessonDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid lesson", response.getBody());
        verify(lessonService, times(1)).addLesson(lessonDTO);
    }

    @Test
    public void testDeleteLesson_Success() {
        ResponseEntity<?> response = lessonController.deleteLesson(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Lesson deleted", response.getBody());
        verify(lessonService, times(1)).deleteLesson(1L);
    }

    @Test
    public void testDeleteLesson_Failure() {
        doThrow(new IllegalArgumentException("Invalid lesson ID")).when(lessonService).deleteLesson(anyLong());

        ResponseEntity<?> response = lessonController.deleteLesson(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid lesson ID", response.getBody());
        verify(lessonService, times(1)).deleteLesson(1L);
    }

    @Test
    public void testGetLesson_Success() {
        Lesson lesson = new Lesson();
        when(lessonService.getLesson(anyLong())).thenReturn(lesson);

        ResponseEntity<?> response = lessonController.getLesson(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lesson, response.getBody());
        verify(lessonService, times(1)).getLesson(1L);
    }

    @Test
    public void testGetLesson_Failure() {
        doThrow(new IllegalArgumentException("Lesson not found")).when(lessonService).getLesson(anyLong());

        ResponseEntity<?> response = lessonController.getLesson(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Lesson not found", response.getBody());
        verify(lessonService, times(1)).getLesson(1L);
    }

    @Test
    public void testGetAllLessons_Success() {
        List<Lesson> lessons = new ArrayList<>();
        when(lessonService.getAllLessons()).thenReturn(lessons);

        ResponseEntity<?> response = lessonController.getAllLessons();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lessons, response.getBody());
        verify(lessonService, times(1)).getAllLessons();
    }

    @Test
    public void testGetAllLessons_Failure() {
        doThrow(new RuntimeException("Unexpected error")).when(lessonService).getAllLessons();

        ResponseEntity<?> response = lessonController.getAllLessons();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", response.getBody());
        verify(lessonService, times(1)).getAllLessons();
    }
}
