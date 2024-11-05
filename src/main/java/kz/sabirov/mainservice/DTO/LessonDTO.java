package kz.sabirov.mainservice.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import kz.sabirov.mainservice.entities.Chapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonDTO {
    private String name;
    private String description;
    private String content;
    @Column(name = "lesson_order")
    private int order;
    private Long chapterId;
}
