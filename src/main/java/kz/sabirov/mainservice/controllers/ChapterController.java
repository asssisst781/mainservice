package kz.sabirov.mainservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kz.sabirov.mainservice.DTO.ChapterDTO;
import kz.sabirov.mainservice.DTO.CourseDTO;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.entities.Course;
import kz.sabirov.mainservice.entities.Lesson;
import kz.sabirov.mainservice.mappers.CourseMapper;
import kz.sabirov.mainservice.services.ChapterService;
import kz.sabirov.mainservice.services.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/chapters")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;
    @Operation(summary = "Add a new chapter", description = "Lesson details")
    @PostMapping(value = "/add")
    public ResponseEntity<?> addChapter(@RequestBody ChapterDTO chapterDTO){
        log.info("Adding new lesson", chapterDTO);
        try {
            chapterService.addChapter(chapterDTO);
            log.debug("Chapter added successfully: {}", chapterDTO);
            return new ResponseEntity<>("Chapter added", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Failed to add chapter", e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "Delete chapter by id", description = "Chapter id")
    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteChapter(@RequestParam Long id) throws ChangeSetPersister.NotFoundException {

        log.info("deleting chapter");
        try {
            chapterService.deleteChapter(id);
            log.info("Chapter deleted successfully");
            return new ResponseEntity<>("Chapter deleted", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Failed to delete chapter", e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "Return chapter by id", description = "Chapter id")
    @GetMapping(value = "/get")
    public ResponseEntity<?> getChapter(@RequestParam Long id){
        log.info("Trying to get chapter");
        try {
            Chapter chapter = chapterService.getChapter(id);
            log.debug("Chapter returned successfully: {}", chapter);
            return new ResponseEntity<>(chapter, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Chapter with provided id not found");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error occured");
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Return all chapter", description = "No parameters required")
    @GetMapping(value = "/return")
    public ResponseEntity<?> getAllChapters(){
        log.info("Getting all chapters by order");
        try {
            List<Chapter> chapters = chapterService.getAllChapters();
            List<Chapter> sortedChapters = chapters.stream()
                    .sorted(Comparator.comparingInt(Chapter::getOrder))
                    .collect(Collectors.toList());
            log.debug("All chapters returned successfully: {}", sortedChapters);
            return new ResponseEntity<>(sortedChapters, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unexpected error occurred");
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
