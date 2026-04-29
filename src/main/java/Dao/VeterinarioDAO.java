package Dao;

import model.Veterinario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAO {

    // Metodo necesario para el Reporte XML y gestion general
    public List<Veterinario> obtenerTodos() {
        List<Veterinario> lista = new ArrayList<>();
        String sql = "SELECT * FROM veterinarios";

        // Obtenemos la instancia unica de la conexion
        Connection conn = ConexionDB.getConexion();

        // En el try-with-resources SOLO ponemos Statement y ResultSet
        // para no cerrar la conexion Singleton al terminar.
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Veterinario v = new Veterinario();
                v.setId_vet(rs.getInt("id_vet"));
                v.setNombre(rs.getString("nombre"));
                v.setTelefono(rs.getString("telefono"));

                // Si en tu DB tienes mas campos, dejalos listos aqui:
                // v.setApellidos(rs.getString("apellidos"));
                // v.setEspecialidad(rs.getString("especialidad"));

                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar veterinarios: " + e.getMessage());
        }
        return lista;
    }

    // Metodo util para buscar un veterinario especifico por ID
    public Veterinario buscarPorId(int id) {
        String sql = "SELECT * FROM veterinarios WHERE id_vet = ?";
        Connection conn = ConexionDB.getConexion();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Veterinario v = new Veterinario();
                    v.setId_vet(rs.getInt("id_vet"));
                    v.setNombre(rs.getString("nombre"));
                    v.setTelefono(rs.getString("telefono"));
                    return v;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar veterinario: " + e.getMessage());
        }
        return null;
    }
}