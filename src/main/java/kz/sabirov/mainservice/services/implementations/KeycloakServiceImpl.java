package kz.sabirov.mainservice.services.implementations;

import kz.sabirov.mainservice.DTO.*;
import kz.sabirov.mainservice.services.KeycloakService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakServiceImpl implements KeycloakService {
    @Autowired
    private Keycloak keycloak;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${keycloak.url}")
    private String url;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client}")
    private String client;
    @Value("${keycloak.client-secret}")
    private String clientSecret;
    @Value("${keycloak.username}")
    private String username;
    @Value("${keycloak.password}")
    private String password;
    @Value("${keycloak.grant-type}")
    private String grantType;

    public UserRepresentation createUser(UserCreateDTO user){
        UserRepresentation newUser = new UserRepresentation();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentation.setTemporary(false);
        newUser.setCredentials(List.of(credentialRepresentation));

        Response response = keycloak
                .realm(realm)
                .users()
                .create(newUser);
        List<UserRepresentation> searchUsers = keycloak
                .realm(realm)
                .users()
                .search(user.getUsername());
        return searchUsers.get(0);

    }

    public TokensDTO signIn(UserSigninDTO user){
        String tokenEndpoint = url + "/realms/" + realm + "/protocol/openid-connect/token";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", client);
        formData.add("client_secret", clientSecret);
        formData.add("username", user.getUsername());
        formData.add("password", user.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, new HttpEntity<>(formData, headers), Map.class);
        Map<String, Object> responseBody = response.getBody();




        return new TokensDTO(
                (String) responseBody.get("access_token"),
                (String) responseBody.get("refresh_token"),
                (String) responseBody.get("token_type"),
                ((Integer) responseBody.get("expires_in")).longValue(),
                ((Integer) responseBody.get("refresh_expires_in")).longValue()
        );
    }
    public TokensDTO refreshToken(RefreshTokenDTO token) {
        String tokenEndpoint = url + "/realms/" + realm + "/protocol/openid-connect/token";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", client);
        formData.add("client_secret", clientSecret);
        formData.add("refresh_token", token.getRefreshToken());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, new HttpEntity<>(formData, headers), Map.class);
        Map<String, Object> responseBody = response.getBody();

        String accessToken = (String) responseBody.getOrDefault("access_token", null);
        String refreshToken = (String) responseBody.getOrDefault("refresh_token", null);
        String tokenType = (String) responseBody.getOrDefault("token_type", null);


            return new TokensDTO(
                    accessToken,
                    refreshToken,
                    tokenType,
                    ((Integer) responseBody.get("expires_in")).longValue(),
                    ((Integer) responseBody.get("refresh_expires_in")).longValue()
            );
    }

    public UserRepresentation updateUser(UpdateUserDTO userUpdateDTO) {
        String username = userUpdateDTO.getUsername();
        UserRepresentation userRepresentation = keycloak
                .realm(realm)
                .users()
                .search(username)
                .get(0);
        if (userUpdateDTO.getEmail() != null) {
            userRepresentation.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getFirstName() != null) {
            userRepresentation.setFirstName(userUpdateDTO.getFirstName());
        }
        if (userUpdateDTO.getLastName() != null) {
            userRepresentation.setLastName(userUpdateDTO.getLastName());
        }

        keycloak.realm(realm).users().get(userRepresentation.getId()).update(userRepresentation);

        if (userUpdateDTO.getPassword() != null) {
            CredentialRepresentation passwordCredential = new CredentialRepresentation();
            passwordCredential.setType(CredentialRepresentation.PASSWORD);
            passwordCredential.setValue(userUpdateDTO.getPassword());
            passwordCredential.setTemporary(false);

            keycloak.realm(realm).users().get(userRepresentation.getId()).resetPassword(passwordCredential);
        }

        return userRepresentation;
    }
    public UserRepresentation updateUserRoles(UserRolesDTO user) {
        String username = user.getUsername();
            UserRepresentation userRepresentation = keycloak
                    .realm(realm)
                    .users()
                    .search(username)
                    .get(0);

            List<RoleRepresentation> roles = user.getRoles().stream()
                    .map(roleName -> keycloak.realm(realm).roles().get(roleName).toRepresentation())
                    .toList();

            keycloak.realm(realm).users().get(userRepresentation.getId()).roles().realmLevel().add(roles);

            return userRepresentation;
    }

}
