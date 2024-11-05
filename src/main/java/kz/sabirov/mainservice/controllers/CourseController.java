package kz.sabirov.mainservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import kz.sabirov.mainservice.DTO.ChapterDTO;
import kz.sabirov.mainservice.DTO.CourseDTO;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.entities.Course;
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
@RequestMapping(value = "/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Operation(summary = "Add course", description = "Course details")
    @PostMapping(value = "/add")
    public ResponseEntity<?> addCourse(@RequestBody CourseDTO courseDTO){
        log.info("Attempting to add a new course");
        try {
            courseService.addCourse(courseDTO);
            log.debug("Course added successfully: {}", courseDTO);
            return new ResponseEntity<>("Course added", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Failed to add course");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error");
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Delete course by id", description = "Course id")
    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteCourse(@RequestParam Long id){
        log.info("Attempting to delete course with ID");
        try {
            courseService.deleteCourse(id);
            log.info("Course deleted successfully");
            return new ResponseEntity<>("Course deleted", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Failed to delete course");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error deleting course");
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Return course by id", description = "Course id")
    @GetMapping(value = "/get")
    public ResponseEntity<?> getCourse(@RequestParam Long id){
        log.info("Trying to get course");
        try {
            Course course = courseService.getCourse(id);
            log.debug("Course returned successfully: {}", course);
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Failed to get course");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error");
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Return all courses", description = "No parameters required")
    @GetMapping(value = "/return")
    public ResponseEntity<?> getAllChapters(){
        log.info("Getting all courses");
        try {
            List<Course> courses = courseService.getAllCourses();
            log.debug("All courses returned successfully: {}", courses);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unexpected error");
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
