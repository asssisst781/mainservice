package kz.sabirov.mainservice.controllers;

import kz.sabirov.mainservice.DTO.ChapterDTO;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.services.ChapterService;
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

public class ChapterControllerTests {

    @InjectMocks
    private ChapterController chapterController;

    @Mock
    private ChapterService chapterService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddChapter_Success() {
        ChapterDTO chapterDTO = new ChapterDTO();
        ResponseEntity<?> response = chapterController.addChapter(chapterDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Chapter added", response.getBody());
        verify(chapterService, times(1)).addChapter(chapterDTO);
    }

    @Test
    public void testAddChapter_Failure() {
        ChapterDTO chapterDTO = new ChapterDTO();
        doThrow(new IllegalArgumentException("Invalid chapter")).when(chapterService).addChapter(any(ChapterDTO.class));

        ResponseEntity<?> response = chapterController.addChapter(chapterDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid chapter", response.getBody());
        verify(chapterService, times(1)).addChapter(chapterDTO);
    }

    @Test
    public void testDeleteChapter_Success() throws Exception {
        ResponseEntity<?> response = chapterController.deleteChapter(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Chapter deleted", response.getBody());
        verify(chapterService, times(1)).deleteChapter(1L);
    }

    @Test
    public void testDeleteChapter_Failure() throws Exception {
        doThrow(new IllegalArgumentException("Invalid chapter ID")).when(chapterService).deleteChapter(anyLong());

        ResponseEntity<?> response = chapterController.deleteChapter(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid chapter ID", response.getBody());
        verify(chapterService, times(1)).deleteChapter(1L);
    }

    @Test
    public void testGetChapter_Success() {
        Chapter chapter = new Chapter();
        when(chapterService.getChapter(anyLong())).thenReturn(chapter);

        ResponseEntity<?> response = chapterController.getChapter(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(chapter, response.getBody());
        verify(chapterService, times(1)).getChapter(1L);
    }

    @Test
    public void testGetChapter_Failure() {
        doThrow(new IllegalArgumentException("Chapter not found")).when(chapterService).getChapter(anyLong());

        ResponseEntity<?> response = chapterController.getChapter(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Chapter not found", response.getBody());
        verify(chapterService, times(1)).getChapter(1L);
    }

    @Test
    public void testGetAllChapters_Success() {
        ResponseEntity<?> response = chapterController.getAllChapters();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(chapterService, times(1)).getAllChapters();
    }

    @Test
    public void testGetAllChapters_Failure() {
        doThrow(new RuntimeException("Unexpected error")).when(chapterService).getAllChapters();

        ResponseEntity<?> response = chapterController.getAllChapters();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", response.getBody());
        verify(chapterService, times(1)).getAllChapters();
    }
}