package dao;

import model.Animal;
import model.TipoAnimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    public void guardarAnimal(Animal animal) {
        // Añadimos 'microchip' a la consulta SQL
        String sql = "INSERT INTO animales (nombre, microchip, especie, peso) VALUES (?, ?, ?, ?)";

        try (Connection conn = dao.ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, animal.getNombre());
            pstmt.setString(2, animal.getMicrochip()); // <-- Nuevo campo legal
            pstmt.setString(3, animal.getEspecie().toString());
            pstmt.setDouble(4, animal.getPeso());

            pstmt.executeUpdate();
            System.out.println("¡Animal " + animal.getNombre() + " registrado con microchip " + animal.getMicrochip() + "!");

        } catch (SQLException e) {
            System.out.println("Error de persistencia: " + e.getMessage());
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
                a.setId(rs.getString("id_animal"));
                a.setNombre(rs.getString("nombre"));
                a.setMicrochip(rs.getString("microchip")); // Recogemos el microchip
                a.setPeso(rs.getDouble("peso"));
                a.setEspecie(TipoAnimal.valueOf(rs.getString("especie")));
                a.setIdAdoptante(rs.getString("id_adoptante")); // FK hacia adoptante

                lista.add(a);
            }
        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    public Animal buscarPorMicrochip(String microchip) {
        String sql = "SELECT * FROM animales WHERE microchip = ?";
        Animal animal = null;

        try (Connection conn = dao.ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, microchip);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                animal = new Animal();
                animal.setId(rs.getInt("id_animal"));
                animal.setNombre(rs.getString("nombre"));
                animal.setMicrochip(rs.getString("microchip"));
                animal.setPeso(rs.getDouble("peso"));
                animal.setEspecie(TipoAnimal.valueOf(rs.getString("especie")));
                animal.setIdAdoptante(rs.getString("id_adoptante"));
            }
        } catch (SQLException e) {
            System.out.println("Error en la búsqueda: " + e.getMessage());
        }
        return animal;
    }
    public boolean eliminarAnimal(String microchip) {
        String sql = "DELETE FROM animales WHERE microchip = ? AND id_adoptante IS NULL";

        try (Connection conn = dao.ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, microchip);
            int filasAfectadas = pstmt.executeUpdate();

            // Si filasAfectadas > 0, es que se borró con éxito
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }

}