package Dao;

import static org.junit.jupiter.api.Assertions.*;
import model.Animal;
import model.TipoAnimal;
import org.junit.jupiter.api.Test;


public class AnimalDAOTest {

    @Test
    void testGuardarAnimal() {
        AnimalDAO dao = new AnimalDAO();

        Animal a = new Animal();
        a.setNombre("Tobi");
        a.setMicrochip("111111111111111");
        a.setEspecie(TipoAnimal.valueOf("PERRO"));
        a.setPeso(10.5);
        a.setEstado("ACTIVO");

        // Llamada a la función
        boolean resultado = dao.guardarAnimal(a);

        // Verificación
        assertTrue(resultado, "El método guardarAnimal debería devolver true");
    }
}