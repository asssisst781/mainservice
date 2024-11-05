package kz.sabirov.mainservice.repositories;

import jakarta.transaction.Transactional;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findAllById(Long id);
}
