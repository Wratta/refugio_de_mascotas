package model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "adoptante")
@XmlAccessorType(XmlAccessType.FIELD) // Indica a JAXB que mire las variables, no los getters
@XmlType(propOrder = {"dni", "nombre", "apellidos", "telefono", "email", "curso_competencias", "contrato_firmado"})
public class Adoptante {

    @XmlElement
    private String dni;

    @XmlElement
    private String nombre;

    @XmlElement
    private String apellidos;

    @XmlElement
    private String telefono;

    @XmlElement
    private String email;

    @XmlElement
    private boolean curso_competencias;

    @XmlElement
    private boolean contrato_firmado;
}