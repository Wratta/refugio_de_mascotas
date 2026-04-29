package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/refugio_mascotas";
    private static final String USER = "root";
    private static final String PASS = "";

    // La única instancia de la conexión
    private static Connection conexion = null;

    // Constructor privado para evitar que se creen instancias con 'new'
    private ConexionDB() {}

    public static Connection getConexion() {
        try {
            // Si la conexión no existe o se ha cerrado, se crea una nueva
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Conexion establecida con la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }

    // Metodo opcional para cerrar la conexion al salir de la app
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexion cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
    }
}