package testJAXBConfiguracion;

import model.ExportadorXML;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class testJAXBConfiguracion {
    @Test
    void testJAXBConfiguracion() {
        assertDoesNotThrow(() -> {
            JAXBContext context = JAXBContext.newInstance(model.Animal.class);
            Marshaller marshaller = context.createMarshaller();
            assertNotNull(marshaller);
        }, "Si esto falla, las anotaciones de JAXB siguen mal configuradas");
    }
    @Test
    void testExportacionConValidacionXSD() {
        // Este método llamará a AnimalDAO, buscará los FALLECIDOS,
        // los validará contra el XSD en resources y creará el XML.
        ExportadorXML.generarReporte();

        File archivo = new File("reporte_bajas.xml");
        assertTrue(archivo.exists(), "El archivo XML debería haberse creado.");
    }
}
