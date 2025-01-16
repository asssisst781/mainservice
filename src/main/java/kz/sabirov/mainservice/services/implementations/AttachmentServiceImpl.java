package kz.sabirov.mainservice.services.implementations;

import kz.sabirov.mainservice.entities.Attachment;
import kz.sabirov.mainservice.repositories.AttachmentRepository;
import kz.sabirov.mainservice.services.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;


    @Override
    public Attachment saveAttachment(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }
}
