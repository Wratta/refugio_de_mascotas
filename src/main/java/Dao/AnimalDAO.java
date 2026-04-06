package dao;

import model.Animal;
import model.TipoAnimal;

import java.sql.*;
import java.util.*;

public class AnimalDAO {

    public void guardarAnimal(Animal animal) {
        String sql = "INSERT INTO animales (nombre, especie, peso) VALUES (?, ?, ?)";

        try (Connection conn = dao.ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, animal.getNombre());
            pstmt.setString(2, animal.getEspecie().toString());
            pstmt.setDouble(3, animal.getPeso());

            pstmt.executeUpdate();
            System.out.println("¡Animal guardado con éxito!");

        } catch (SQLException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
    public List<Animal> obtenerTodos() {
        List<Animal> lista = new ArrayList<>();
        String sql = "SELECT * FROM animales";

        try (Connection conn = dao.ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Animal a = new Animal();
                a.setNombre(rs.getString("nombre"));
                a.setPeso(rs.getDouble("peso"));
                // Convertimos el String de la DB al Enum de Java
                a.setEspecie(TipoAnimal.valueOf(rs.getString("especie")));

                lista.add(a);
            }
        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}