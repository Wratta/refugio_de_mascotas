package Dao;

import model.Usuario;
import model.Rol;
import java.sql.*;

public class UsuarioDAO {

    /**
     * Valida las credenciales del usuario y devuelve el objeto Usuario si es correcto.
     * Se ha unificado con la logica de login para evitar duplicidad.
     */
    public Usuario login(String user, String pass) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";

        // Obtenemos la instancia unica del Singleton
        Connection conn = ConexionDB.getConexion();

        // En el try-with-resources SOLO los recursos que deben cerrarse (PreparedStatement)
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user);
            pstmt.setString(2, pass);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Creamos el objeto usando el constructor o setters segun prefieras
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setNombre(rs.getString("nombre"));

                    // Convertimos el String de la BD al Enum Rol (en mayusculas por seguridad)
                    String rolBD = rs.getString("rol");
                    if (rolBD != null) {
                        usuario.setRol(Rol.valueOf(rolBD.toUpperCase().trim()));
                    }

                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la operacion de login: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: El rol definido en la base de datos no coincide con el Enum Rol.");
        }

        return null; // Si no hay coincidencia o hay error, devuelve null
    }
}