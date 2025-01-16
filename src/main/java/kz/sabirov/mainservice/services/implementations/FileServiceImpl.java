package kz.sabirov.mainservice.services.implementations;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import kz.sabirov.mainservice.DTO.LessonIdDTO;
import kz.sabirov.mainservice.entities.Attachment;
import kz.sabirov.mainservice.entities.Lesson;
import kz.sabirov.mainservice.repositories.AttachmentRepository;
import kz.sabirov.mainservice.services.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private LessonServiceImpl lessonService;

    @Value("${minio.bucket-name}")
    private String bucket;
    @Autowired
    private MinioClient minioClient;



    @Override
    public void uploadFile(MultipartFile file, Long l) throws Exception{
        Lesson lesson = lessonService.getLesson(l);
        LocalDateTime ld = LocalDateTime.now();
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setLesson(lesson);
        attachment.setCreatedTime(ld);
        attachment.setUrl("temp" + ld);
        attachment = attachmentRepository.save(attachment);
        attachment.setUrl(DigestUtils.sha1Hex(attachment.getId() + "_My_file"));


        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(file.getOriginalFilename())
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());
        attachmentRepository.save(attachment);
    }

    @Override
    public ByteArrayResource downloadFile(String url) throws Exception{
        Attachment attachment = attachmentRepository.findByUrl(url);
        String fileName = attachment.getName();

        GetObjectArgs getObjectArgs = GetObjectArgs
                .builder()
                .bucket(bucket)
                .object(fileName)
                .build();

        InputStream stream = minioClient.getObject(getObjectArgs);
        byte[] byteArray = IOUtils.toByteArray(stream);
        return new ByteArrayResource(byteArray);
    }
}
