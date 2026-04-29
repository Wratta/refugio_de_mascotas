package Dao;

import model.Adoptante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdoptanteDAO {

    // Metodo para insertar un nuevo adoptante (Opcion 6 del menu)
    public boolean guardar(Adoptante a) {
        String sql = "INSERT INTO adoptantes (dni, nombre, apellidos, telefono, email, curso_competencias, contrato_firmado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Obtenemos la conexion del Singleton
        Connection conn = ConexionDB.getConexion();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getDni());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getApellidos());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getEmail());
            ps.setBoolean(6, a.isCurso_competencias());
            ps.setBoolean(7, a.isContrato_firmado());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en la operacion guardar: " + e.getMessage());
            return false;
        }
    }

    public List<Adoptante> obtenerTodos() {
        List<Adoptante> lista = new ArrayList<>();
        String sql = "SELECT * FROM adoptantes";

        Connection conn = ConexionDB.getConexion();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Adoptante a = new Adoptante();
                a.setDni(rs.getString("dni"));
                a.setNombre(rs.getString("nombre"));
                a.setApellidos(rs.getString("apellidos"));
                a.setTelefono(rs.getString("telefono"));
                a.setEmail(rs.getString("email"));
                a.setCurso_competencias(rs.getBoolean("curso_competencias"));
                a.setContrato_firmado(rs.getBoolean("contrato_firmado"));

                lista.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Error en la operacion obtenerTodos: " + e.getMessage());
        }
        return lista;
    }
}