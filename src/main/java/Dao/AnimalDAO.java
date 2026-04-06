package dao;

import model.Animal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AnimalDAO {

    public void guardarAnimal(Animal animal) {
        String sql = "INSERT INTO animales (nombre, especie, peso) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
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
}