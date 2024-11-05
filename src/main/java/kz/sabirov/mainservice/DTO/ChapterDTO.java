package kz.sabirov.mainservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import kz.sabirov.mainservice.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChapterDTO {
    private String name;
    private String description;
    @Column(name = "chapter_order")
    private int order;
    private Long courseId;
}
