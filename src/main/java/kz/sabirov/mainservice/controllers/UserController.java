package kz.sabirov.mainservice.controllers;

import kz.sabirov.mainservice.DTO.UpdateUserDTO;
import kz.sabirov.mainservice.DTO.UserCreateDTO;
import kz.sabirov.mainservice.DTO.UserRolesDTO;
import kz.sabirov.mainservice.DTO.UserSigninDTO;
import kz.sabirov.mainservice.services.KeycloakService;
import kz.sabirov.mainservice.services.implementations.KeycloakServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private KeycloakServiceImpl keycloak;
    @PostMapping(value = "/createUser")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO user){
        return new ResponseEntity<>(keycloak.createUser(user), HttpStatus.CREATED);
    }
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserSigninDTO user){
        return new ResponseEntity<>(keycloak.signIn(user), HttpStatus.CREATED);
    }
    @GetMapping("/debug")
    public String debug() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Roles: " + authentication.getAuthorities();
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateUserData(@RequestBody UpdateUserDTO user) {
        return new ResponseEntity<>(keycloak.updateUser(user), HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/roleMapping")
    public ResponseEntity<?> roleMapping(@RequestBody UserRolesDTO user) {
        return new ResponseEntity<>(keycloak.updateUserRoles(user), HttpStatus.ACCEPTED);
    }
}
