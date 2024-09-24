package ucv.app_inventory.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase utilitaria para manejar operaciones JWT como generación y validación de
 * tokens.
 */
@Service
public class TokenUsuario {

    private static final Logger logger = LoggerFactory.getLogger(TokenUsuario.class);

    private final SecretKey claveSecreta;
    private final long tiempoExp;
    private final JwtParser jwtParser;

    public TokenUsuario(@Value("${jwt.secret.key}") String claveSecreta,
            @Value("${jwt.expiration.time}") long tiempoExp) {
        this.claveSecreta = Keys.hmacShaKeyFor(claveSecreta.getBytes(StandardCharsets.UTF_8));
        this.tiempoExp = tiempoExp;
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(this.claveSecreta)
                .build();
    }

    /**
     * Genera un token JWT para el usuario proporcionado.
     *
     * @param username Nombre de usuario para el que se generará el token.
     * @return Token JWT como cadena de texto.
     */
    public String generarToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tiempoExp * 1000))
                .signWith(claveSecreta, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Valida el token JWT proporcionado.
     *
     * @param token Token JWT a validar.
     * @return {@code true} si el token es válido, {@code false} en caso
     * contrario.
     */
    public boolean validarToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Extrae el nombre de usuario del token JWT proporcionado.
     *
     * @param token Token JWT del cual extraer el nombre de usuario.
     * @return Nombre de usuario contenido en el token.
     */
    public String getUsuarioToken(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
