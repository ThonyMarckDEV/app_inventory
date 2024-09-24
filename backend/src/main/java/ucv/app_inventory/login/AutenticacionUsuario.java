package ucv.app_inventory.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ucv.app_inventory.login.excepciones.Credenciales;

/**
 * Servicio que maneja la autenticación de usuarios y generación de tokens JWT.
 */
@Service
public class AutenticacionUsuario {

    private final AuthenticationManager authenticationManager;
    private final TokenUsuario jwtTokenUsuario;

    @Autowired
    public AutenticacionUsuario(AuthenticationManager authenticationManager, TokenUsuario jwtTokenUsuario) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUsuario = jwtTokenUsuario;
    }

    /**
     * Autentica un usuario y genera un token JWT si las credenciales son
     * correctas.
     *
     * @param usuario Nombre de usuario.
     * @param clave Contraseña del usuario.
     * @return Token JWT generado.
     * @throws Credenciales si la autenticación falla.
     */
    public String autenticarUsuario(String usuario, String clave) {
        if (usuario == null || clave == null || usuario.isEmpty() || clave.isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario y la contraseña no deben estar vacíos");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario, clave));
            return jwtTokenUsuario.generarToken(usuario);
        } catch (AuthenticationException e) {
            throw new Credenciales("Usuario o contraseña incorrectos");
        }
    }
}
