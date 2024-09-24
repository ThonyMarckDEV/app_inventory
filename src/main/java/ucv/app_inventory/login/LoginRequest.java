package ucv.app_inventory.login;

import jakarta.validation.constraints.NotEmpty;

/**
 * Representa una solicitud de inicio de sesión con validaciones de campos.
 */
public class LoginRequest {

    @NotEmpty(message = "El usuario es requerido")
    private final String usuario;

    @NotEmpty(message = "La contraseña es requerida")
    private final String clave;

    /**
     * Constructor para inicializar los campos de LoginRequest.
     *
     * @param usuario El nombre de usuario.
     * @param clave   La contraseña del usuario.
     */
    public LoginRequest(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }
}
