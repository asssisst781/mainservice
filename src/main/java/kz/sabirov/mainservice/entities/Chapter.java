package kz.sabirov.mainservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "chapters")
@Entity
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 255)
    private String name;
    @Column(columnDefinition="TEXT")
    private String description;
    @Column(name = "chapter_order")
    private int order;
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
