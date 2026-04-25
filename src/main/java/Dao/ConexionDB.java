package Dao;

import java.sql.*;

public class ConexionDB {
    // Configuración para XAMPP
    private static final String URL = "jdbc:mysql://localhost:3306/refugio_mascotas";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL", e);
        }
    }
}