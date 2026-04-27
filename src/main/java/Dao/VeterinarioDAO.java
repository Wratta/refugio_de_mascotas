package Dao;

import model.Veterinario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAO {
    private final String URL = "jdbc:mysql://localhost:3306/refugio_mascotas";
    private final String USER = "root";
    private final String PASS = "";

    // Método necesario para el Reporte XML
    public List<Veterinario> obtenerTodos() {
        List<Veterinario> lista = new ArrayList<>();
        String sql = "SELECT * FROM veterinarios";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Veterinario v = new Veterinario();
                v.setId_vet(rs.getInt("id_vet"));
                v.setNombre(rs.getString("nombre"));
                v.setTelefono(rs.getString("telefono"));
                // Añade aquí más campos si tu clase Veterinario los tiene
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar veterinarios: " + e.getMessage());
        }
        return lista;
    }
}