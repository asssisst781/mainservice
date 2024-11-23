package kz.sabirov.mainservice.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateDTO {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;

}
