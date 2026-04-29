package Dao;

import model.Animal;
import model.TipoAnimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    // Quitamos la conexión del try-with-resources para que el Singleton no se cierre
    public boolean guardarAnimal(Animal animal) {
        String sql = "INSERT INTO animales (nombre, microchip, especie, peso, estado) VALUES (?, ?, ?, ?, ?)";
        Connection conn = ConexionDB.getConexion();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, animal.getNombre());
            pstmt.setString(2, animal.getMicrochip());
            pstmt.setString(3, animal.getEspecie().name());
            pstmt.setDouble(4, animal.getPeso());

            String estadoAGuardar = (animal.getEstado() != null) ? animal.getEstado() : "ACTIVO";
            pstmt.setString(5, estadoAGuardar);

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Registro exitoso! " + animal.getNombre() + " ya esta en la base de datos.");
            }
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
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
        Connection conn = ConexionDB.getConexion();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Animal a = new Animal();
                a.setId(rs.getInt("id_animal"));
                a.setNombre(rs.getString("nombre"));
                a.setMicrochip(rs.getString("microchip"));
                a.setPeso(rs.getDouble("peso"));
                a.setEspecie(TipoAnimal.valueOf(rs.getString("especie")));
                a.setIdAdoptante(rs.getString("id_adoptante"));
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
        Connection conn = ConexionDB.getConexion();
        Animal animal = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, microchip);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    animal = new Animal();
                    animal.setId(rs.getInt("id_animal"));
                    animal.setNombre(rs.getString("nombre"));
                    animal.setMicrochip(rs.getString("microchip"));
                    animal.setPeso(rs.getDouble("peso"));
                    animal.setEspecie(TipoAnimal.valueOf(rs.getString("especie")));
                    animal.setIdAdoptante(rs.getString("id_adoptante"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en la busqueda: " + e.getMessage());
        }
        return animal;
    }

    public boolean eliminarAnimal(String microchip) {
        String sql = "DELETE FROM animales WHERE microchip = ? AND id_adoptante IS NULL";
        Connection conn = ConexionDB.getConexion();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, microchip);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarDefuncion(int id_animal, String causa_baja, int veterinario_id) {
        String sql = "UPDATE animales SET estado='FALLECIDO', causa_baja=?, fecha_baja=CURDATE(), veterinario_id=? WHERE id_animal=?";
        Connection conn = ConexionDB.getConexion();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, causa_baja);
            ps.setInt(2, veterinario_id);
            ps.setInt(3, id_animal);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Animal buscarPorId(int id) {
        String sql = "SELECT * FROM animales WHERE id_animal = ?";
        Connection conn = ConexionDB.getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Animal a = new Animal();
                    a.setId(rs.getInt("id_animal"));
                    a.setNombre(rs.getString("nombre"));
                    a.setMicrochip(rs.getString("microchip"));
                    a.setEstado(rs.getString("estado"));
                    a.setCausaBaja(rs.getString("causa_baja"));
                    a.setFechaBaja(rs.getString("fecha_baja"));
                    return a;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Animal> obtenerSoloBajas() {
        List<Animal> lista = new ArrayList<>();
        String sql = "SELECT * FROM animales WHERE estado = 'FALLECIDO'";
        Connection conn = ConexionDB.getConexion();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Animal a = new Animal();
                a.setId(rs.getInt("id_animal"));
                a.setNombre(rs.getString("nombre"));
                String especieBD = rs.getString("especie");
                if (especieBD != null) {
                    a.setEspecie(TipoAnimal.valueOf(especieBD.toUpperCase().trim()));
                }
                a.setCausaBaja(rs.getString("causa_baja"));
                a.setFechaBaja(rs.getString("fecha_baja"));
                lista.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Error en obtenerSoloBajas: " + e.getMessage());
        }
        return lista;
    }

    public boolean asignarAdoptante(int idAnimal, String dni) {
        String sqlBuscarAdoptante = "SELECT dni FROM adoptantes WHERE dni = ?";
        String sqlUpdateAnimal = "UPDATE animales SET id_adoptante = ?, estado = 'ADOPTADO' WHERE id_animal = ?";
        String sqlCheckEstado = "SELECT estado FROM animales WHERE id_animal = ?";

        Connection conn = ConexionDB.getConexion();

        try {
            // Verificamos estado
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheckEstado)) {
                psCheck.setInt(1, idAnimal);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next()) {
                    String estado = rs.getString("estado");
                    if ("FALLECIDO".equalsIgnoreCase(estado)) {
                        System.out.println("Operacion cancelada: El animal con ID " + idAnimal + " esta fallecido.");
                        return false;
                    }
                    if ("ADOPTADO".equalsIgnoreCase(estado)) {
                        System.out.println("Operacion cancelada: El animal ya ha sido adoptado.");
                        return false;
                    }
                }
            }

            // Verificamos adoptante
            String dniExistente = null;
            try (PreparedStatement psAdopt = conn.prepareStatement(sqlBuscarAdoptante)) {
                psAdopt.setString(1, dni);
                ResultSet rsA = psAdopt.executeQuery();
                if (rsA.next()) {
                    dniExistente = rsA.getString("dni");
                } else {
                    System.out.println("Error: No se encontro adoptante con DNI " + dni);
                    return false;
                }
            }

            // Actualización
            try (PreparedStatement psUp = conn.prepareStatement(sqlUpdateAnimal)) {
                psUp.setString(1, dniExistente);
                psUp.setInt(2, idAnimal);
                return psUp.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error SQL en la asignacion: " + e.getMessage());
            return false;
        }
    }
}