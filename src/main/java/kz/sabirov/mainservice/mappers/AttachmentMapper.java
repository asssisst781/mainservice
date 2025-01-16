package kz.sabirov.mainservice.mappers;

import kz.sabirov.mainservice.DTO.AttachmentDTO;
import kz.sabirov.mainservice.entities.Attachment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    AttachmentDTO AttachmentToDTO(Attachment attachment);
    Attachment DTOToAttachment(AttachmentDTO attachmentDTO);
}
