import controller.AnimalController;
import model.Animal;
import model.TipoAnimal;

public class main {
    public static void main(String[] args) {
        Animal miPerro = new Animal();
        miPerro.setNombre("Tobby");
        miPerro.setEspecie(TipoAnimal.PERRO);
        miPerro.setPeso(12.5);

        System.out.println("--- Prueba de Refugio ---");
        System.out.println("Animal: " + miPerro.getNombre());
        System.out.println("Especie: " + miPerro.getEspecie());
        System.out.println("Comida diaria: " + miPerro.calcularRacionDiaria() + " kg");

        AnimalController control = new AnimalController();

        System.out.println("Intentando guardar animal en XAMPP...");

        // Probamos a registrar un perro
        // El controller llamará al DAO y el DAO a ConexionDB
        control.registrarNuevoAnimal("Tobby", 15.4, "PERRO");

        System.out.println("Revisa tu phpMyAdmin en http://localhost/phpmyadmin");
    }
}