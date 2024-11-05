package kz.sabirov.mainservice.services;

import kz.sabirov.mainservice.DTO.ChapterDTO;
import kz.sabirov.mainservice.entities.Chapter;
import kz.sabirov.mainservice.entities.Course;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChapterService {
    void addChapter(ChapterDTO chapterDTO);
    Chapter getChapter(Long id);
    void deleteChapter(Long id);
    void updateChapter(Long id, ChapterDTO chapterDTO);
    List<Chapter> getAllChapters();
}
