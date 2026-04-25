import Dao.AnimalDAO;
import model.Animal;
import model.TipoAnimal;
import java.util.List;
import java.util.Scanner;

public class main {
    private static final AnimalDAO animalDAO = new AnimalDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;

        do {
            System.out.println("\n--- SISTEMA DE GESTIÓN: PROTECTORA (V2.0) ---");
            System.out.println("1. Registrar nuevo animal");
            System.out.println("2. Ver lista de animales (Todos)");
            System.out.println("3. Buscar animal por Microchip");
            System.out.println("4. Registrar Defunción (Baja Veterinaria)");
            System.out.println("5. Exportar Reporte de Bajas (XML)");
            System.out.println("6. Registrar Adoptante (Fase 2)");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarAnimal();
                        break;
                    case 2:
                        listarAnimales();
                        break;
                    case 3:
                        buscarAnimal();
                        break;
                    case 4:
                        gestionarBaja();
                        break;
                    case 5:
                        System.out.println("Llamando al módulo de Lenguajes de Marcas...");
                        System.out.println("Generando 'reporte_bajas.xml' mediante JAXB...");
                        // Aquí iría la llamada a tu ExportadorXML.java
                        break;
                    case 6:
                        System.out.println("Módulo en desarrollo para la Fase 2.");
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
        a.setFechaIngreso(scanner.nextLine());

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
}