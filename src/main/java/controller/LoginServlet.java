package controller;

import Dao.UsuarioDAO;
import model.Usuario;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Obtener datos del formulario (enviados por fetch)
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        // 2. Validar con la base de datos
        Usuario usuario = usuarioDAO.validar(user, pass);

        response.setContentType("application/json");
        Map<String, Object> resultado = new HashMap<>();

        if (usuario != null) {
            // 3. ¡Éxito! Creamos la sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuario);

            resultado.put("success", true);
            resultado.put("rol", usuario.getRol().toString());
            resultado.put("nombre", usuario.getNombre());
        } else {
            // 4. Fallo
            resultado.put("success", false);
            resultado.put("message", "Usuario o contraseña incorrectos");
        }

        response.getWriter().write(gson.toJson(resultado));
    }
}