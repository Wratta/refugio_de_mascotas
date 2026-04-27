package Dao;

import model.Adoptante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdoptanteDAO {
    private final String url = "jdbc:mysql://localhost:3306/refugio_mascotas";
    private final String user = "root";
    private final String pass = "";

    // Método para insertar un nuevo adoptante (Opción 6 del menú)
    public boolean guardar(Adoptante a) {
        String sql = "INSERT INTO adoptantes (dni, nombre, apellidos, telefono, email, curso_competencias, contrato_firmado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getDni());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getApellidos());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getEmail());
            ps.setBoolean(6, a.isCurso_competencias());
            ps.setBoolean(7, a.isContrato_firmado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar adoptante: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener todos (necesario para el Reporte XML)
    public List<Adoptante> obtenerTodos() {
        List<Adoptante> lista = new ArrayList<>();
        String sql = "SELECT * FROM adoptantes";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

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
            System.err.println("Error al listar adoptantes: " + e.getMessage());
        }
        return lista;
    }
}