package model;

import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "gestion_refugio")
@XmlAccessorType(XmlAccessType.FIELD)
public class Refugio {

    @XmlElementWrapper(name = "lista_veterinarios")
    @XmlElement(name = "veterinario")
    private List<Veterinario> veterinarios;

    @XmlElementWrapper(name = "lista_adoptantes")
    @XmlElement(name = "adoptante")
    private List<Adoptante> adoptantes;

    @XmlElementWrapper(name = "lista_animales")
    @XmlElement(name = "animal")
    private List<Animal> animales;
}