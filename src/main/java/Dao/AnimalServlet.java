package Dao;

import com.google.gson.Gson;
import model.Animal;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/animales")
public class AnimalServlet extends HttpServlet {
    private dao.AnimalDAO animalDAO = new dao.AnimalDAO();
    private Gson gson = new Gson();

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
