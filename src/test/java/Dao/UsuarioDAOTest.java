package Dao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.Usuario;
import model.Rol;

public class UsuarioDAOTest {

    @Test
    void probarLoginCorrecto() {
        UsuarioDAO dao = new UsuarioDAO();
        // Usamos los datos de Pedro que insertamos antes
        Usuario user = dao.login("pedro_vet", "1234");

        assertNotNull(user, "El usuario Pedro debería existir");
        assertEquals(Rol.VETERINARIO, user.getRol(), "Pedro debe tener rol VETERINARIO");
    }

    @Test
    void probarLoginFallido() {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario user = dao.login("usuario_falso", "0000");

        assertNull(user, "El login debería ser null para usuarios inexistentes");
    }

    @Test
    void testLoginRoles() {
        UsuarioDAO dao = new UsuarioDAO();
        // CASO 1: Login correcto de Dueño
        Usuario admin = dao.login("admin", "admin123");
        assertNotNull(admin);
        assertEquals(Rol.DUENO, admin.getRol());

        // CASO 2: Login con contraseña mal (Caso de error)
        Usuario error = dao.login("admin", "error123");
        assertNull(error, "Si la contraseña está mal, debe devolver null");

        // CASO 3: Usuario que no existe
        Usuario inexistente = dao.login("fantasma", "1234");
        assertNull(inexistente);
    }
}
