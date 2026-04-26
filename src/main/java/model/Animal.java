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
// Definimos el orden para que el XML sea idéntico al XSD de Lenguajes de Marcas
public class Animal {
    // 1. Atributos
    private int id_animal;
    private String nombre;
    private String microchip;
    private double peso;
    private boolean esterilizado;
    private String fechaUltimaRabia; // Antes era LocalDate, pero da un conflicto con Java 8+
    private TipoAnimal especie;
    private String raza; // <--- Añadimos esta línea
    private String fechaIngreso; // Antes era LocalDate, pero da un conflicto con Java 8+
    private String idAdoptante;
    private String estado; // ACTIVO, FALLECIDO, ADOPTADO
    private String causaBaja; // Solo para fallecidos
    private String fechaBaja;  // Solo para fallecidos
    private boolean vacunasAlDia;
    private int idVeterinario;

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