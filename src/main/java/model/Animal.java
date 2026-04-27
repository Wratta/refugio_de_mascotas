package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "animal")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "id_animal",
        "nombre",
        "microchip",
        "peso",
        "esterilizado",
        "fechaUltimaRabia",
        "especie",
        "raza",
        "fechaIngreso",
        "idAdoptante",
        "estado",
        "causaBaja",
        "fechaBaja",
        "vacunasAlDia",
        "veterinario_id"
})
public class Animal {
    // 1. Atributos
    private int id_animal;
    private String nombre;
    private String microchip;
    private double peso;
    private boolean esterilizado;
    private String fechaUltimaRabia;
    private TipoAnimal especie;
    private String raza;
    private String fechaIngreso;
    private String idAdoptante;
    private String estado;
    private String causaBaja;
    private String fechaBaja;
    private boolean vacunasAlDia;
    private int veterinario_id;

    // 3. Constructor para el Controller (El de 3 parámetros)
    public Animal(String nombre, double peso, TipoAnimal especie) {
        this.nombre = nombre;
        this.peso = peso;
        this.especie = especie;
    }

    public void setId(int id) {
        this.id_animal = id;
    }

    // 4. Getters y Setters (Manuales para evitar líos de versiones)
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public TipoAnimal getEspecie() { return especie; }
    public void setEspecie(TipoAnimal especie) { this.especie = especie; }

    // 5. Lógica de negocio
    public double calcularRacionDiaria() {
        if (especie == null) return 0;
        return this.peso * especie.getFactorComida();
    }
}