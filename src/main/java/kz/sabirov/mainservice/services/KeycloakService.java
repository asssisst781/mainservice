package kz.sabirov.mainservice.services;

import kz.sabirov.mainservice.DTO.*;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {
    UserRepresentation createUser(UserCreateDTO user);
    TokensDTO signIn(UserSigninDTO user);
    TokensDTO refreshToken(RefreshTokenDTO token);
    UserRepresentation updateUser(UpdateUserDTO userUpdateDTO);
    UserRepresentation updateUserRoles(UserRolesDTO user);
}
