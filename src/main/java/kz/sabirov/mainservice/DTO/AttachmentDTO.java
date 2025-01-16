package kz.sabirov.mainservice.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AttachmentDTO {
    private Long id;
    private String name;
    private String url;
}