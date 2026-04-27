package controller;

import model.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainExportar {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/refugio_mascotas";
        String user = "root";
        String pass = ""; // Tu contraseña de MySQL

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            Refugio refugio = new Refugio();

            // 1. Cargar Veterinarios
            refugio.setVeterinarios(obtenerVeterinarios(conn));

            // 2. Cargar Adoptantes
            refugio.setAdoptantes(obtenerAdoptantes(conn));

            // 3. Cargar Animales
            // (Asumo que ya tienes la clase Animal con el mismo estilo Lombok/JAXB)
            // refugio.setAnimales(obtenerAnimales(conn));

            // --- PROCESO JAXB ---
            JAXBContext context = JAXBContext.newInstance(Refugio.class);
            Marshaller ms = context.createMarshaller();
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Exportar a archivo y consola
            ms.marshal(refugio, new File("refugio_datos.xml"));
            ms.marshal(refugio, System.out);

            System.out.println("\n¡XML generado con éxito!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Veterinario> obtenerVeterinarios(Connection conn) throws SQLException {
        List<Veterinario> lista = new ArrayList<>();
        String sql = "SELECT * FROM veterinarios";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            lista.add(new Veterinario(rs.getInt("id_vet"), rs.getString("nombre"), rs.getString("telefono")));
        }
        return lista;
    }

    private static List<Adoptante> obtenerAdoptantes(Connection conn) throws SQLException {
        List<Adoptante> lista = new ArrayList<>();
        String sql = "SELECT * FROM adoptantes";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            lista.add(new Adoptante(
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getBoolean("curso_competencias"),
                    rs.getBoolean("contrato_firmado")
            ));
        }
        return lista;
    }
    private static List<Animal> obtenerAnimales(Connection conn) throws SQLException {
        List<Animal> lista = new ArrayList<>();
        String sql = "SELECT * FROM animales";

        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Animal a = new Animal();

                // 1. Datos básicos
                a.setId_animal(rs.getInt("id_animal"));
                a.setNombre(rs.getString("nombre"));
                a.setMicrochip(rs.getString("microchip"));
                String especieBD = rs.getString("especie");
                if (especieBD != null) {
                    try {
                        a.setEspecie(TipoAnimal.valueOf(especieBD.toUpperCase().trim()));
                    } catch (IllegalArgumentException e) {
                        // Opcional: Manejar si en la BBDD hay algo que no es ni PERRO ni GATO
                        System.err.println("Error: Especie no reconocida en la BBDD: " + especieBD);
                    }
                }
                a.setRaza(rs.getString("raza"));
                a.setPeso(rs.getDouble("peso"));

                // 2. Fechas (Convertir de java.sql.Date a java.util.Date)
                a.setFechaIngreso(rs.getString("fecha_ingreso"));
                a.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
                a.setFechaBaja(rs.getString("fecha_baja"));

                // 3. Estado y Observaciones
                a.setEstado(rs.getString("estado"));
                a.setCausaBaja(rs.getString("causa_baja"));
                a.setObservaciones_veterinarias(rs.getString("observaciones_veterinarias"));

                // 4. Booleans (Tinyint en SQL)
                a.setEsterilizado(rs.getBoolean("esterilizado"));
                a.setVacunasAlDia(rs.getBoolean("vacunas_al_dia"));

                // 5. Relaciones (Foreign Keys)
                a.setIdAdoptante(rs.getString("id_adoptante"));
                a.setVeterinario_id(rs.getInt("veterinario_id"));

                lista.add(a);
            }
        }
        return lista;
    }
}