package kz.sabirov.mainservice.services;

import kz.sabirov.mainservice.DTO.LessonDTO;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.entities.Lesson;
import kz.sabirov.mainservice.mappers.LessonMapper;
import kz.sabirov.mainservice.repositories.LessonRepository;
import kz.sabirov.mainservice.services.ChapterService;
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

public class LessonServiceTests {

    @InjectMocks
    private LessonService lessonService;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private ChapterService chapterService;

    @Mock
    private LessonMapper lessonMapper;

    private LessonDTO lessonDTO;
    private Lesson lesson;
    private Chapter chapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample data for tests
        lessonDTO = new LessonDTO();
        lessonDTO.setName("Test Lesson");
        lessonDTO.setDescription("Lesson Description");
        lessonDTO.setContent("Lesson Content");
        lessonDTO.setChapterId(1L);
        lessonDTO.setOrder(1);

        chapter = new Chapter();
        chapter.setId(1L);
        chapter.setName("Test Chapter");

        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setName("Test Lesson");
        lesson.setDescription("Lesson Description");
        lesson.setContent("Lesson Content");
        lesson.setChapter(chapter);
        lesson.setCreatedTime(LocalDateTime.now());
        lesson.setUpdatedTime(LocalDateTime.now());
    }

    @Test
    public void testAddLesson_Success() {
        when(chapterService.getChapter(anyLong())).thenReturn(chapter);
        when(lessonMapper.DTOToEntity(any(LessonDTO.class))).thenReturn(lesson);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        assertDoesNotThrow(() -> lessonService.addLesson(lessonDTO));
        verify(lessonRepository, times(1)).save(any(Lesson.class));
    }

    @Test
    public void testAddLesson_EmptyFields() {
        lessonDTO.setName(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> lessonService.addLesson(lessonDTO));
        assertEquals("Fields cannot be empty", exception.getMessage());
    }

    @Test
    public void testGetLesson_Success() {
        when(lessonRepository.findAllById(anyLong())).thenReturn(lesson);

        Lesson result = lessonService.getLesson(1L);
        assertNotNull(result);
        assertEquals("Test Lesson", result.getName());
    }

    @Test
    public void testGetLesson_NotFound() {
        when(lessonRepository.findAllById(anyLong())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> lessonService.getLesson(1L));
        assertEquals("Lesson not found", exception.getMessage());
    }

    @Test
    public void testDeleteLesson_Success() {
        when(lessonRepository.findAllById(anyLong())).thenReturn(lesson);
        doNothing().when(lessonRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> lessonService.deleteLesson(1L));
        verify(lessonRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteLesson_NotFound() {
        when(lessonRepository.findAllById(anyLong())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> lessonService.deleteLesson(1L));
        assertEquals("Lesson not found", exception.getMessage());
    }

    @Test
    public void testUpdateLesson_Success() {
        when(lessonRepository.findAllById(anyLong())).thenReturn(lesson);
        when(lessonMapper.DTOToEntity(any(LessonDTO.class))).thenReturn(lesson);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        assertDoesNotThrow(() -> lessonService.updateLesson(1L, lessonDTO));
        verify(lessonRepository, times(1)).save(any(Lesson.class));
    }

    @Test
    public void testUpdateLesson_EmptyFields() {
        lessonDTO.setName(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> lessonService.updateLesson(1L, lessonDTO));
        assertEquals("Fields cannot be empty", exception.getMessage());
    }

    @Test
    public void testUpdateLesson_LessonNotFound() {
        when(lessonRepository.findAllById(anyLong())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> lessonService.updateLesson(1L, lessonDTO));
        assertEquals("Lesson not found", exception.getMessage());
    }

    @Test
    public void testGetAllLessons() {
        when(lessonRepository.findAll()).thenReturn(Collections.singletonList(lesson));

        List<Lesson> lessons = lessonService.getAllLessons();
        assertNotNull(lessons);
        assertEquals(1, lessons.size());
        assertEquals("Test Lesson", lessons.get(0).getName());
    }
}
