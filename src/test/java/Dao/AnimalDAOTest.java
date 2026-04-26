package Dao;

import static org.junit.jupiter.api.Assertions.*;
import model.Animal;
import model.TipoAnimal;
import org.junit.jupiter.api.Test;

import java.util.List;


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
        assertTrue(resultado, "El método guardarAnimal debería devolver true, si no existe ya");
    }

    @Test
    void testBuscarMicrochipInexistente() {
        AnimalDAO dao = new AnimalDAO();
        Animal a = dao.buscarPorMicrochip("ESTO-NO-EXISTE");
        assertNull(a, "Debe devolver null si el microchip no está en la base de datos");
    }

    @Test
    void testBuscarAnimalExistente() {
        AnimalDAO dao = new AnimalDAO();
        // Usa un microchip que sepas que está en tu DB
        String microchipExistente = "111111111111111";

        Animal a = dao.buscarPorMicrochip(microchipExistente);

        assertNotNull(a, "El animal debería existir en la base de datos");
        assertEquals("Tobi", a.getNombre(), "El nombre del animal encontrado debería ser Tobi");
    }
    @Test
    void testEliminarAnimalExito() {
        AnimalDAO dao = new AnimalDAO();

        // 1. Necesitamos un ID que exista.
        // Si quieres ser muy pro, podrías insertar uno primero y luego borrarlo.
        int idABorrar = 10; // Cambia este ID por uno que tengas en tu DB

        // 2. Ejecutar el borrado
        boolean eliminado = dao.eliminarAnimal("idABorrar");
        assertTrue(eliminado, "El animal debería haberse eliminado correctamente");

        // 3. VERIFICACIÓN CRUCIAL: Intentar buscarlo de nuevo
        Animal buscado = dao.buscarPorId(idABorrar);
        assertNull(buscado, "Después de eliminar, buscarPorId debería devolver null");
    }

    @Test
    void testRegistrarDefuncionExito() {
        AnimalDAO dao = new AnimalDAO();

        // 1. Datos de prueba
        // IMPORTANTE: Asegúrate de que estos IDs existan en tu BBDD antes de correr el test
        int idAnimalExistente = 1;
        int idVeterinarioExistente = 1;
        String causa = "Fallo multiorgánico";

        // 2. Ejecutar la lógica
        boolean resultado = dao.registrarDefuncion(idAnimalExistente, causa, idVeterinarioExistente);

        // 3. Verificaciones
        assertTrue(resultado, "El método debería devolver true al actualizar un animal existente");

        // 4. Verificación profunda: ¿Realmente cambió en la base de datos?
        // Usamos el método buscar que ya probamos antes
        Animal animalActualizado = dao.buscarPorId(idAnimalExistente);

        assertNotNull(animalActualizado);
        assertEquals("FALLECIDO", animalActualizado.getEstado(), "El estado debería ser FALLECIDO");
        assertEquals(causa, animalActualizado.getCausaBaja(), "La causa de baja debe coincidir");
        assertNotNull(animalActualizado.getFechaBaja(), "La fecha de baja debería haberse generado (CURDATE)");
    }

    @Test
    void testListarAnimales() {
        AnimalDAO dao = new AnimalDAO();

        // Llamamos al método real de tu DAO
        List<Animal> lista = dao.obtenerTodos();

        assertNotNull(lista, "La lista no debería ser nula");

        // Si sabes que hay animales en la DB, comprobamos que no esté vacía
        assertFalse(lista.isEmpty(), "La lista debería contener animales");

        System.out.println("Se han recuperado " + lista.size() + " animales.");
    }

    @Test
    void testRegistrarDefuncionAnimalInexistente() {
        AnimalDAO dao = new AnimalDAO();
        // Un microchip que sabemos que no existe
        boolean resultado = dao.registrarDefuncion(999999999, "Causa inventada", 99999);

        assertFalse(resultado, "Debería devolver false porque el microchip no existe en la DB");
    }
}