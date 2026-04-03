import model.Animal; // Importamos la clase específica
import model.TipoAnimal; // Importamos el enum también

// public class main {
    public static void main(String[] args) {
        // Creamos una instancia concreta (un perro real)
        Animal miPerro = new Animal("Tobby", 15.5, TipoAnimal.PERRO);

        // Ahora sí podemos usar el getter sobre ese objeto
        System.out.println("La especie de " + miPerro.getNombre() + " es: " + miPerro.getEspecie());
        System.out.println("Ración diaria: " + miPerro.calcularRacionDiaria() + " kg");
    }
}
