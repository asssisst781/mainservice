package kz.sabirov.mainservice.Utils;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class UserUtils {
    public static Jwt getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof JwtAuthenticationToken){
            return ((JwtAuthenticationToken) authentication).getToken();
        }
        return null;
    }
    public static String getCurrentUserName(){
        Jwt jwt = getCurrentUser();
        if(jwt != null){
            return jwt.getClaimAsString("preferred_username");
        }
        return null;
    }
}
