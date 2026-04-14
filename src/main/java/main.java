import controller.AnimalController;
import model.Animal;
import model.TipoAnimal;

import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AnimalController control = new AnimalController();
        int opcion = 0;

        do {
            System.out.println("\n--- SISTEMA DE GESTIÓN: PROTECTORA ---");
            System.out.println("1. Registrar nuevo animal");
            System.out.println("2. Ver lista de animales");
            System.out.println("3. Registrar Adoptante (Fase 2)");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        System.out.println("\n-- Registro de Animal --");
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();

                        System.out.print("Peso (ej: 12.5): ");
                        double peso = Double.parseDouble(sc.nextLine());

                        System.out.print("Especie (PERRO/GATO): ");
                        String especie = sc.nextLine();

                        // Llamamos al controlador
                        control.registrarNuevoAnimal(nombre, peso, especie);
                        break;
                    case 2:
                        System.out.println("\n--- LISTADO DE ANIMALES EN EL REFUGIO ---");
                        List<Animal> animales = control.listarAnimales();

                        if (animales.isEmpty()) {
                            System.out.println("No hay animales registrados todavía.");
                        } else {
                            // Formato de tabla simple
                            System.out.printf("%-15s | %-10s | %-10s%n", "NOMBRE", "ESPECIE", "PESO (kg)");
                            System.out.println("-------------------------------------------");
                            for (Animal a : animales) {
                                System.out.printf("%-15s | %-10s | %-10.2f%n",
                                        a.getNombre(), a.getEspecie(), a.getPeso());
                            }
                        }
                        break;
                    case 4:
                        System.out.println("Saliendo del sistema... ¡Guau!");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduce un número válido.");
            } catch (Exception e) {
                System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
            }

        } while (opcion != 4);

        sc.close();
    }
}