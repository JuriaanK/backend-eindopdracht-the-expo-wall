package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.controller;


import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.AuthenticationDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.security.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }


    @PostMapping("/auth")
    public ResponseEntity<Object> signIn(@RequestBody AuthenticationDTO authDto) {
        UsernamePasswordAuthenticationToken up =
                new UsernamePasswordAuthenticationToken(authDto.username, authDto.password);

        try {
            Authentication auth = authManager.authenticate(up);

            UserDetails ud = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(ud);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(token);
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
