package kz.sabirov.mainservice.repositories;

import jakarta.transaction.Transactional;
import kz.sabirov.mainservice.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Attachment findByUrl(String url);
}
