package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "reporteBajas")
@XmlAccessorType(XmlAccessType.FIELD) // <--- ESTA LÍNEA ES LA QUE QUITA LOS 2 ERRORES
public class ReporteBajas {

    @XmlElement(name = "animal")
    private List<Animal> animales;

}