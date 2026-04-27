package testJAXBConfiguracion;

import model.ExportadorXML;
import model.Veterinario;
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
    @Test
    public void testVeterinarioToXml() throws Exception {
        Veterinario v = new Veterinario(7, "Dra. Elena Martínez", "600111222");

        JAXBContext context = JAXBContext.newInstance(Veterinario.class);
        Marshaller mar = context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // Imprime por consola para verificar visualmente
        mar.marshal(v, System.out);
    }
}
