package controller;

import model.Animal;
import Dao.AnimalDAO;
import model.TipoAnimal;

import java.util.List;

public class AnimalController {
    private AnimalDAO animalDAO = new AnimalDAO();

    public void registrarNuevoAnimal(String nombre, double peso, String especieTexto) {
        try {
            // Convertimos a mayúsculas para que coincida con PERRO o GATO
            TipoAnimal especieEnum = TipoAnimal.valueOf(especieTexto.toUpperCase());

            Animal nuevo = new Animal(nombre, peso, especieEnum);

            AnimalDAO dao = new AnimalDAO();
            dao.guardarAnimal(nuevo);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: La especie '" + especieTexto + "' no es válida.");
        }
    }
    public List<Animal> listarAnimales() {
        return animalDAO.obtenerTodos();
    }

    public Animal obtenerAnimalPorMicrochip(String microchip) {
        if (microchip == null || microchip.length() != 15) {
            System.out.println("Error: Formato de microchip no válido para búsqueda.");
            return null;
        }
        return animalDAO.buscarPorMicrochip(microchip);
    }
}