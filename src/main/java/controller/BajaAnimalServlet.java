package controller;

import Dao.AnimalDAO;
import model.Rol;
import model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/bajaAnimal")
public class BajaAnimalServlet extends HttpServlet {
    private AnimalDAO animalDAO = new AnimalDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Verificar sesión y ROL
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;

        if (usuario == null || usuario.getRol() != Rol.VETERINARIO) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Solo un veterinario puede dar de baja por defunción.");
            return;
        }

        // 2. Obtener datos de la URL (ejemplo: bajaAnimal?id=5&causa=Parvovirus)
        int idAnimal = Integer.parseInt(request.getParameter("id"));
        String causa = request.getParameter("causa");

        // 3. Ejecutar en BD
        boolean exito = animalDAO.registrarDefuncion(idAnimal, causa, usuario.getId());

        if (exito) {
            response.getWriter().write("Baja registrada correctamente para fines legales.");
        } else {
            response.sendError(500, "Error al registrar la baja.");
        }
    }
}