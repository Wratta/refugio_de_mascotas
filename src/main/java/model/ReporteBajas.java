package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "reporteBajas") // Nombre de la etiqueta raíz
public class ReporteBajas {

    private List<Animal> animales;

    public ReporteBajas() {} // Constructor vacío obligatorio para JAXB

    public ReporteBajas(List<Animal> animales) {
        this.animales = animales;
    }

    @XmlElement(name = "animal") // Cada objeto de la lista será una etiqueta <animal>
    public List<Animal> getAnimales() {
        return animales;
    }

    public void setAnimales(List<Animal> animales) {
        this.animales = animales;
    }
}