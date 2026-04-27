import Dao.*;
import model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static final AnimalDAO animalDAO = new AnimalDAO();
    private static final AdoptanteDAO adoptanteDAO = new AdoptanteDAO();
    private static final VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO(); // Añadido aquí
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // 1. Fase de Login
        System.out.println("--- ACCESO AL SISTEMA ---");
        System.out.print("Usuario: ");
        String userStr = scanner.nextLine();
        System.out.print("Contraseña: ");
        String passStr = scanner.nextLine();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioLogueado = usuarioDAO.login(userStr, passStr);

        if (usuarioLogueado == null) {
            System.out.println("Credenciales incorrectas. Cerrando aplicación.");
            return;
        }

        // 2. Inicio del Menú con el usuario validado
        int opcion = 0;
        Rol rol = usuarioLogueado.getRol();

        do {
            System.out.println("\n--- SISTEMA DE GESTIÓN: PROTECTORA (V2.0) ---");
            System.out.println("Bienvenido/a: " + usuarioLogueado.getNombre() + " [" + rol + "]");

            // --- OPCIONES DINÁMICAS (Visualización) ---
            if (rol != Rol.VOLUNTARIO) {
                System.out.println("1. Registrar nuevo animal");
            }

            System.out.println("2. Ver lista de animales (Todos)");
            System.out.println("3. Buscar animal por Microchip");

            if (rol != Rol.VOLUNTARIO) { // Corregido: quitado paréntesis extra
                System.out.println("4. Registrar Defunción (Baja Veterinaria)");
            }

            if (rol == Rol.DUENO) { // Corregido: quitado paréntesis extra
                System.out.println("5. Exportar Reporte de Bajas (XML)");
            }

            System.out.println("6. Registrar Adoptante (Fase 2)");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        if (rol != Rol.VOLUNTARIO) {
                            registrarAnimal();
                        } else {
                            System.out.println("Acceso denegado para Voluntarios.");
                        }
                        break; // Corregido: añadido punto y coma
                    case 2:
                        listarAnimales();
                        break;
                    case 3:
                        buscarAnimal();
                        break;
                    case 4:
                        if (rol != Rol.VOLUNTARIO) { // Corregido: estructura de llaves
                            gestionarBaja();
                        } else {
                            System.out.println("Permiso denegado.");
                        }
                        break;
                    case 5:
                        if (rol == Rol.DUENO) {
                            generarReporte();
                        } else {
                            System.out.println("Acceso denegado. Solo el Dueño tiene permiso.");
                        }
                        break; // Corregido: quitado doble punto y coma
                    case 6:
                        registrarAdoptante();
                        break;
                    case 7:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Introduce un número válido.");
                opcion = 0;
            }

        } while (opcion != 7);
    }
    private static void registrarAnimal() {
        System.out.println("\n--- NUEVO REGISTRO ---");
        Animal a = new Animal();

        System.out.print("Nombre: ");
        a.setNombre(scanner.nextLine());

        System.out.print("Microchip: ");
        a.setMicrochip(scanner.nextLine());

        System.out.print("Especie (PERRO/GATO): ");
        try {
            String esp = scanner.nextLine().toUpperCase().trim();
            a.setEspecie(TipoAnimal.valueOf(esp));
        } catch (IllegalArgumentException e) {
            System.out.println("Especie no válida. Se asignará PERRO por defecto.");
            a.setEspecie(TipoAnimal.PERRO);
        }

        System.out.print("Peso (kg): ");
        a.setPeso(Double.parseDouble(scanner.nextLine()));

        System.out.print("Fecha de Ingreso (AAAA-MM-DD): ");
        String fechaS = scanner.nextLine();

        try {
            // Intentamos parsear para verificar que sea una fecha real
            // valueOf lanzará una excepción si el formato no es AAAA-MM-DD o si el día/mes no existe
            java.sql.Date.valueOf(fechaS);

            // Si llegamos aquí, la fecha es válida. La guardamos como String.
            a.setFechaIngreso(fechaS);

        } catch (IllegalArgumentException e) {
            // Si entra aquí, es que la fecha estaba mal escrita
            System.out.println("Fecha no válida o formato incorrecto. Se asignará la fecha de hoy.");

            // Obtenemos la fecha de hoy en formato AAAA-MM-DD para guardarla como String
            String hoy = LocalDate.now().toString();
            a.setFechaIngreso(hoy);
        }

        animalDAO.guardarAnimal(a);
    }

    private static void listarAnimales() {
        List<Animal> lista = animalDAO.obtenerTodos();
        System.out.println("\n--- LISTADO DE ANIMALES EN EL REFUGIO ---");
        // Añadimos la columna ESTADO
        System.out.printf("%-10s %-15s %-10s %-10s %-12s %-10s%n",
                "MICROCHIP", "NOMBRE", "ESPECIE", "PESO", "ESTADO", "RACIÓN");
        System.out.println("---------------------------------------------------------------------------");

        for (Animal a : lista) {
            // Usamos un operador ternario para que si el estado es null no rompa el programa
            String estado = (a.getEstado() != null) ? a.getEstado() : "S/D";

            System.out.printf("%-10s %-15s %-10s %-10.2f %-12s %-10.2f kg%n",
                    a.getMicrochip(),
                    a.getNombre(),
                    a.getEspecie(),
                    a.getPeso(),
                    estado, // <--- Nueva columna visual
                    a.calcularRacionDiaria());
        }
    }

    private static void buscarAnimal() {
        System.out.print("Ingrese microchip a buscar: ");
        String m = scanner.nextLine();
        Animal a = animalDAO.buscarPorMicrochip(m);
        if (a != null) {
            System.out.println("Encontrado: " + a.getNombre() + " [" + a.getEspecie() + "]");
        } else {
            System.out.println("No se encontró ningún animal con ese microchip.");
        }
    }

    private static void gestionarBaja() {
        System.out.println("\n--- REGISTRO MÉDICO DE BAJA ---");
        System.out.print("ID del Animal: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Causa del fallecimiento: ");
        String causa = scanner.nextLine();
        System.out.print("ID del Veterinario: ");
        int idVet = Integer.parseInt(scanner.nextLine());

        if (animalDAO.registrarDefuncion(id, causa, idVet)) {
            System.out.println("Operación completada. El animal ha sido dado de baja.");
        } else {
            System.out.println("Error: No se pudo actualizar el registro.");
        }
    }
    private static void generarReporte() {
        System.out.println("\n--- Generando Reporte XML Completo ---");

        // El try-with-resources gestiona la conexión
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/refugio_mascotas", "root", "")) {

            if (conn == null) {
                System.out.println("Error: No se pudo establecer la conexión.");
                return;
            }

            Refugio refugio = new Refugio();

            // IntelliJ ya no debería marcar error aquí porque estamos dentro del bloque try que captura SQLException
            List<Veterinario> vets = veterinarioDAO.obtenerTodos();
            List<Adoptante> adops = adoptanteDAO.obtenerTodos();
            List<Animal> anis = animalDAO.obtenerTodos();

            // Verificación de nulidad para silenciar avisos de Inspección
            refugio.setVeterinarios(vets != null ? vets : new ArrayList<>());
            refugio.setAdoptantes(adops != null ? adops : new ArrayList<>());
            refugio.setAnimales(anis != null ? anis : new ArrayList<>());

            // JAXB Marshalling
            JAXBContext context = JAXBContext.newInstance(Refugio.class);
            Marshaller ms = context.createMarshaller();
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File f = new File("reporte_refugio.xml");
            ms.marshal(refugio, f);

            System.out.println("Éxito: Reporte guardado en " + f.getAbsolutePath());

        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
        } catch (JAXBException e) {
            System.err.println("Error de configuración JAXB: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void registrarAdoptante() {
        System.out.println("\n--- Nuevo Registro de Adoptante ---");
        Adoptante a = new Adoptante();

        System.out.print("DNI: "); a.setDni(scanner.nextLine());
        System.out.print("Nombre: "); a.setNombre(scanner.nextLine());
        System.out.print("Apellidos: "); a.setApellidos(scanner.nextLine());
        System.out.print("Teléfono: "); a.setTelefono(scanner.nextLine());
        System.out.print("Email: "); a.setEmail(scanner.nextLine());

        a.setCurso_competencias(false);
        a.setContrato_firmado(false);


        adoptanteDAO.guardar(a);

        System.out.println("Adoptante capturado correctamente.");
    }
}