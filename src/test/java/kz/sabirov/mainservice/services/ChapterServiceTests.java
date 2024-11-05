package kz.sabirov.mainservice.services.implementations;

import kz.sabirov.mainservice.DTO.ChapterDTO;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.entities.Course;
import kz.sabirov.mainservice.mappers.ChapterMapper;
import kz.sabirov.mainservice.repositories.ChapterRepository;
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

public class ChapterServiceTests {

    @InjectMocks
    private ChapterServiceImpl chapterService;

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private CourseService courseService;

    @Mock
    private ChapterMapper chapterMapper;

    private ChapterDTO chapterDTO;
    private Chapter chapter;
    private Course course;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample data for tests
        chapterDTO = new ChapterDTO();
        chapterDTO.setCourseId(1L);
        chapterDTO.setName("Test Chapter");
        chapterDTO.setDescription("Chapter Description");
        chapterDTO.setOrder(1);

        course = new Course();
        course.setId(1L);

        chapter = new Chapter();
        chapter.setId(1L);
        chapter.setName("Test Chapter");
        chapter.setDescription("Chapter Description");
        chapter.setOrder(1);
        chapter.setCourse(course);
        chapter.setCreatedTime(LocalDateTime.now());
        chapter.setUpdatedTime(LocalDateTime.now());
    }

    @Test
    public void testAddChapter_Success() {
        when(courseService.getCourse(anyLong())).thenReturn(course);
        when(chapterMapper.DTOToEntity(any(ChapterDTO.class))).thenReturn(chapter);
        when(chapterRepository.save(any(Chapter.class))).thenReturn(chapter);

        assertDoesNotThrow(() -> chapterService.addChapter(chapterDTO));
        verify(chapterRepository, times(1)).save(any(Chapter.class));
    }

    @Test
    public void testAddChapter_EmptyFields() {
        chapterDTO.setName(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> chapterService.addChapter(chapterDTO));
        assertEquals("Fields cannot be empty", exception.getMessage());
    }

    @Test
    public void testAddChapter_CourseNotFound() {
        when(courseService.getCourse(anyLong())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> chapterService.addChapter(chapterDTO));
        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    public void testGetChapter_Success() {
        when(chapterRepository.findAllById(anyLong())).thenReturn(chapter);

        Chapter result = chapterService.getChapter(1L);
        assertNotNull(result);
        assertEquals("Test Chapter", result.getName());
    }

    @Test
    public void testGetChapter_NotFound() {
        when(chapterRepository.findAllById(anyLong())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> chapterService.getChapter(1L));
        assertEquals("Chapter not found", exception.getMessage());
    }

    @Test
    public void testDeleteChapter_Success() {
        when(chapterRepository.findAllById(anyLong())).thenReturn(chapter);
        doNothing().when(chapterRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> chapterService.deleteChapter(1L));
        verify(chapterRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteChapter_NotFound() {
        when(chapterRepository.findAllById(anyLong())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> chapterService.deleteChapter(1L));
        assertEquals("Chapter not found", exception.getMessage());
    }

    @Test
    public void testUpdateChapter_Success() {
        when(chapterRepository.findAllById(anyLong())).thenReturn(chapter);
        when(courseService.getCourse(anyLong())).thenReturn(course);
        when(chapterMapper.DTOToEntity(any(ChapterDTO.class))).thenReturn(chapter);
        when(chapterRepository.save(any(Chapter.class))).thenReturn(chapter);

        assertDoesNotThrow(() -> chapterService.updateChapter(1L, chapterDTO));
        verify(chapterRepository, times(1)).save(any(Chapter.class));
    }

    @Test
    public void testUpdateChapter_EmptyFields() {
        chapterDTO.setName(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> chapterService.updateChapter(1L, chapterDTO));
        assertEquals("Fields cannot be empty", exception.getMessage());
    }

    @Test
    public void testUpdateChapter_ChapterNotFound() {
        when(chapterRepository.findAllById(anyLong())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> chapterService.updateChapter(1L, chapterDTO));
        assertEquals("Chapter not found", exception.getMessage());
    }

    @Test
    public void testGetAllChapters() {
        when(chapterRepository.findAll()).thenReturn(Collections.singletonList(chapter));

        List<Chapter> chapters = chapterService.getAllChapters();
        assertNotNull(chapters);
        assertEquals(1, chapters.size());
        assertEquals("Test Chapter", chapters.get(0).getName());
    }
}
