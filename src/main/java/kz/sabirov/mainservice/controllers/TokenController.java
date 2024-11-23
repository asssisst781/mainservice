package kz.sabirov.mainservice.controllers;

import kz.sabirov.mainservice.DTO.RefreshTokenDTO;
import kz.sabirov.mainservice.DTO.TokensDTO;
import kz.sabirov.mainservice.DTO.UserSigninDTO;
import kz.sabirov.mainservice.services.implementations.KeycloakServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/token")
public class TokenController {
    @Autowired
    private KeycloakServiceImpl keycloak;
    @PostMapping("/refresh")
    public ResponseEntity<TokensDTO> refreshToken(@RequestBody RefreshTokenDTO token) {
        return ResponseEntity.ok(keycloak.refreshToken(token));
    }
}
