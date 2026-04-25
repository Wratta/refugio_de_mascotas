package controller;

import Dao.AnimalDAO;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Animal;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/animales")
public class AnimalServlet extends HttpServlet {
    private AnimalDAO animalDAO = new AnimalDAO();
    Gson gson = Converters.registerAll(new GsonBuilder()).create();

    // 1. ENVIAR DATOS A LA TABLA (Censo)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Animal> lista = animalDAO.obtenerTodos();
        String json = gson.toJson(lista);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    // 2. RECIBIR DATOS DEL FORMULARIO (Registro)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leer el JSON que viene del script.js
        BufferedReader reader = request.getReader();
        Animal nuevoAnimal = gson.fromJson(reader, Animal.class);

        // Guardar en MySQL usando tu método existente
        animalDAO.guardarAnimal(nuevoAnimal);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
