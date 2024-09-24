package ucv.app_inventory.login.excepciones;

public class Credenciales extends RuntimeException {

    /**
     * Crea una excepción con un mensaje de error específico.
     *
     * @param message Descripción del error.
     */
    public Credenciales(String message) {
        super(message);
    }
}
