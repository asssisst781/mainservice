package kz.sabirov.mainservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserDTO {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
}