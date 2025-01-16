package kz.sabirov.mainservice.services;

import kz.sabirov.mainservice.DTO.LessonIdDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public interface FileService {
    void uploadFile(MultipartFile file, Long lessonIdDTO) throws Exception;
    ByteArrayResource downloadFile(String fileName) throws Exception;
}
