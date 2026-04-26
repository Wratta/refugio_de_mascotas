package testJAXBConfiguracion;

import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class testJAXBConfiguracion {
    @Test
    void testJAXBConfiguracion() {
        assertDoesNotThrow(() -> {
            JAXBContext context = JAXBContext.newInstance(model.Animal.class);
            Marshaller marshaller = context.createMarshaller();
            assertNotNull(marshaller);
        }, "Si esto falla, las anotaciones de JAXB siguen mal configuradas");
    }
}
