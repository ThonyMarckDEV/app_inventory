package ucv.app_inventory.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador REST para manejar la autenticación de usuarios.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class ControladorLogin {

    private static final Logger logger = LoggerFactory.getLogger(ControladorLogin.class);

    private final AutenticacionUsuario autenticacionUsuario;

    public ControladorLogin(AutenticacionUsuario autenticacionUsuario) {
        this.autenticacionUsuario = autenticacionUsuario;
    }

    /**
     * Autentica al usuario y devuelve un token JWT.
     *
     * @param loginRequest Objeto que contiene las credenciales del usuario.
     * @return ResponseEntity con el token JWT o un mensaje de error.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtRequest> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = autenticacionUsuario.autenticarUsuario(loginRequest.getUsuario(), loginRequest.getClave());
        logger.info("Usuario autenticado: {}", loginRequest.getUsuario());
        return ResponseEntity.ok(new JwtRequest(token));
    }
}
