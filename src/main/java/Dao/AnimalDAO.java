package Dao;

import model.Animal;
import model.TipoAnimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    public boolean guardarAnimal(Animal animal) {
        // 1. Añadimos 'estado' a la consulta para que no se guarde vacío
        String sql = "INSERT INTO animales (nombre, microchip, especie, peso, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, animal.getNombre());
            pstmt.setString(2, animal.getMicrochip());

            // 2. Usamos .name() para guardar "PERRO" o "GATO" (el nombre del Enum)
            pstmt.setString(3, animal.getEspecie().name());

            pstmt.setDouble(4, animal.getPeso());

            // Forzamos que al guardar siempre sea ACTIVO si no tiene otro estado
            String estadoAGuardar = (animal.getEstado() != null) ? animal.getEstado() : "ACTIVO";
            pstmt.setString(5, estadoAGuardar);

            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                System.out.println("¡Registro exitoso! " + animal.getNombre() + " ya está en la base de datos.");
            }
            return true;

        } catch (SQLException e) {
            // 4. Capturamos si el microchip está duplicado (si es UNIQUE en la BD)
            if (e.getErrorCode() == 1062) { // Código de error de MySQL para duplicados
                System.out.println("Error: El microchip " + animal.getMicrochip() + " ya existe.");
            } else {
                System.out.println("Error de persistencia: " + e.getMessage());
            }
            return false;
        }
    }

    public List<Animal> obtenerTodos() {
        List<Animal> lista = new ArrayList<>();
        String sql = "SELECT * FROM animales";

        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Animal a = new Animal();
                a.setId(rs.getInt("id_animal"));
                a.setNombre(rs.getString("nombre"));
                a.setMicrochip(rs.getString("microchip")); // Recogemos el microchip
                a.setPeso(rs.getDouble("peso"));
                a.setEspecie(TipoAnimal.valueOf(rs.getString("especie")));
                a.setIdAdoptante(rs.getString("id_adoptante")); // FK hacia adoptante
                a.setEstado(rs.getString("estado"));

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

        try (Connection conn = ConexionDB.getConexion();
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

        try (Connection conn = ConexionDB.getConexion();
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

    public boolean registrarDefuncion(int idAnimal, String causa, int idVeterinario) {
        String sql = "UPDATE animales SET estado='FALLECIDO', causa_baja=?, fecha_baja=CURDATE(), veterinario_id=? WHERE id=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, causa);
            ps.setInt(2, idVeterinario);
            ps.setInt(3, idAnimal);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Animal> obtenerSoloBajas() {
        List<Animal> lista = new ArrayList<>();
        // Asegúrate de que la columna se llame 'estado' en tu DB
        String sql = "SELECT * FROM animales WHERE estado = 'FALLECIDO'";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Animal a = new Animal();
                // 1. Corregido: Usar el nombre de columna correcto y el tipo correcto
                a.setId(rs.getInt("id_animal"));
                a.setNombre(rs.getString("nombre"));

                // 2. Corregido: Conversión de String a Enum TipoAnimal
                String especieBD = rs.getString("especie");
                if (especieBD != null) {
                    a.setEspecie(TipoAnimal.valueOf(especieBD.toUpperCase().trim()));
                }

                // 3. Estos campos deben existir en tu clase Animal.java
                a.setCausaBaja(rs.getString("causa_baja"));
                a.setFechaBaja(rs.getString("fecha_baja"));

                lista.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Error en obtenerSoloBajas: " + e.getMessage());
        }
        return lista;
    }


}