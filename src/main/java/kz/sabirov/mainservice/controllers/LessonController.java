package kz.sabirov.mainservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import kz.sabirov.mainservice.DTO.CourseDTO;
import kz.sabirov.mainservice.DTO.LessonDTO;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.entities.Course;
import kz.sabirov.mainservice.entities.Lesson;
import kz.sabirov.mainservice.services.CourseService;
import kz.sabirov.mainservice.services.LessonService;
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
@RequestMapping(value = "/lessons")
public class LessonController {
    @Autowired
    private LessonService lessonService;
    @Operation(summary = "Add lesson", description = "Lesson details")
    @PostMapping(value = "/add")
    public ResponseEntity<?> addLesson(@RequestBody LessonDTO lessonDTO){
        log.info("Adding new lesson");
        try {
            lessonService.addLesson(lessonDTO);
            log.debug("Lesson added successfully: {}", lessonDTO);
            return new ResponseEntity<>("Lesson added", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Failed to add lesson");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error adding lesson");
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Delete lesson by id", description = "Lesson id")
    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteLesson(@RequestParam Long id){
        log.info("Attempting to delete lesson");
        try {
            lessonService.deleteLesson(id);
            log.info("Lesson deleted successfully");
            return new ResponseEntity<>("Lesson deleted", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Failed to delete lesson");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error");
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Return lesson by id", description = "Lesson id")
    @GetMapping(value = "/get")
    public ResponseEntity<?> getLesson(@RequestParam Long id){
        log.info("Trying to get lesson");
        try {
            Lesson lesson = lessonService.getLesson(id);
            log.debug("Lesson returned successfully: {}", lesson);
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Failed to get lesson");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error");
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Return all lessons", description = "No parameters required")
    @GetMapping(value = "/return")
    public ResponseEntity<?> getAllLessons(){
        log.info("Getting all lessons");
        try {
            List<Lesson> lessons = lessonService.getAllLessons();
            List<Lesson> sortedLessons = lessons.stream()
                    .sorted(Comparator.comparingInt(Lesson::getOrder))
                    .collect(Collectors.toList());
            log.debug("All lessons returned: {}", sortedLessons);
            return new ResponseEntity<>(sortedLessons, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unexpected error");
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
