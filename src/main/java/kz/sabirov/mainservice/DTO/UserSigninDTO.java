package kz.sabirov.mainservice.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserSigninDTO {

    private String username;
    private String password;

}
