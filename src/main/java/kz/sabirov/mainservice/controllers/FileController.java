package kz.sabirov.mainservice.controllers;

import kz.sabirov.mainservice.DTO.LessonIdDTO;
import kz.sabirov.mainservice.services.FileService;
import kz.sabirov.mainservice.services.implementations.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping(value = "/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile f,
                                             @RequestParam("lesson") Long lessonId
                                             ) {
        try {
            fileService.uploadFile(f, lessonId);
            return new ResponseEntity<>("File uploaded", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("File wasnt uploaded" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/download/{url}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable(name = "url") String url) throws Exception {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + url + "\"" )
                .body(fileService.downloadFile(url));
    }

}
