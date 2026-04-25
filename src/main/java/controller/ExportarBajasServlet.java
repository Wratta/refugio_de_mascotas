package controller;

import Dao.AnimalDAO;
import model.Animal;
import model.ReporteBajas;
import model.Rol;
import model.Usuario;

// IMPORTS NECESARIOS PARA XML (JAXB)
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/exportarBajas")
public class ExportarBajasServlet extends HttpServlet {
    private AnimalDAO animalDAO = new AnimalDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Validación de seguridad (Solo veterinarios/dueños)
        HttpSession session = request.getSession(false);
        Usuario user = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;

        if (user == null || user.getRol() == Rol.VOLUNTARIO) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tiene permisos para exportar datos legales.");
            return;
        }

        try {
            // 2. Obtener los datos de la base de datos (solo los fallecidos)
            List<Animal> bajas = animalDAO.obtenerSoloBajas(); // Debes crear este método en tu DAO
            ReporteBajas reporte = new ReporteBajas(bajas);

            // 3. Configurar la respuesta del navegador para descargar un archivo
            response.setContentType("application/xml");
            response.setHeader("Content-Disposition", "attachment; filename=reporte_bajas.xml");

            // 4. Crear el contexto de JAXB y el Marshaller (Convertidor)
            JAXBContext context = JAXBContext.newInstance(ReporteBajas.class);
            Marshaller marshaller = context.createMarshaller();

            // Configurar para que el XML salga "bonito" (con sangrías)
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // 5. Escribir el XML directamente en el Response Writer
            marshaller.marshal(reporte, response.getWriter());

        } catch (JAXBException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generando el XML");
        }
    }
}