package model;

import Dao.AnimalDAO;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

public class ExportadorXML {

    public static void generarReporte() {
        AnimalDAO dao = new AnimalDAO();
        List<Animal> bajas = dao.obtenerSoloBajas();

        if (bajas.isEmpty()) {
            System.out.println("No hay animales fallecidos para exportar.");
            return;
        }

        try {
            // 1. Preparamos el contenedor de datos
            ReporteBajas reporte = new ReporteBajas(bajas);

            // 2. Cargamos el archivo XSD
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL xsdURL = ExportadorXML.class.getClassLoader().getResource("animales.xsd");
            if (xsdURL == null) {
                throw new FileNotFoundException("No se encontró el archivo animales.xsd en resources");
            }
            Schema miEsquema = sf.newSchema(xsdURL);

            // 3. Configuramos JAXB
            JAXBContext context = JAXBContext.newInstance(ReporteBajas.class);
            Marshaller marshaller = context.createMarshaller();

            // Vincular el XSD con el Marshaller
            marshaller.setSchema(miEsquema);

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // 4. Guardar el archivo
            File archivo = new File("reporte_bajas.xml");
            marshaller.marshal(reporte, archivo);

            System.out.println("¡Reporte generado y VALIDADO con éxito!");

        } catch (Exception e) {
            // Si el XML no cumple con el XSD, el error saltará aquí
            System.out.println("Error en la exportación o validación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}