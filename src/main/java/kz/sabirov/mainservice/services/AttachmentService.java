package kz.sabirov.mainservice.services;

import kz.sabirov.mainservice.entities.Attachment;
import org.springframework.stereotype.Service;

@Service
public interface AttachmentService {
    Attachment saveAttachment(Attachment attachment);
}
