package Dao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexionDBTest {

    @Test
    void testConexionExitosa() throws SQLException {
        // 1. Intentamos obtener la conexión desde tu clase de utilidad
        // Cambia 'ConexionDB' por el nombre de tu clase de conexión
        Connection conn = ConexionDB.getConexion();

        // 2. Verificamos que no sea nula
        assertNotNull(conn, "La conexión a la base de datos no debería ser nula");

        try {
            // 3. Verificamos que la conexión esté realmente activa
            assertFalse(conn.isClosed(), "La conexión debería estar abierta");

            // Opcional: Cerrarla después del test
            conn.close();
        } catch (SQLException e) {
            fail("Se lanzó una excepción al intentar verificar la conexión: " + e.getMessage());
        }
    }
}