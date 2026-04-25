package model;

import Dao.AnimalDAO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;

public class ExportadorXML {

    // Necesitamos esta clase interna para que el XML tenga una etiqueta raíz <reporte>
    @XmlRootElement(name = "ReporteBajas")
    private static class ReporteWrapper {
        private List<Animal> animales;

        @XmlElement(name = "animal")
        public List<Animal> getAnimales() { return animales; }
        public void setAnimales(List<Animal> animales) { this.animales = animales; }
    }

    public static void generarReporte() {
        AnimalDAO dao = new AnimalDAO();
        List<Animal> bajas = dao.obtenerSoloBajas();

        if (bajas.isEmpty()) {
            System.out.println("No hay animales fallecidos para exportar.");
            return;
        }

        try {
            // AQUÍ USAMOS TU CLASE: Metemos la lista de animales en el contenedor
            ReporteBajas reporte = new ReporteBajas(bajas);

            // Configuramos JAXB para que reconozca tu clase ReporteBajas
            JAXBContext context = JAXBContext.newInstance(ReporteBajas.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Guardamos el archivo
            File archivo = new File("reporte_bajas.xml");
            marshaller.marshal(reporte, archivo);

            System.out.println("¡Reporte generado con éxito!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}