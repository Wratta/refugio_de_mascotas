package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.time.LocalDate;

@Data // Genera Getters, Setters, toString...
@NoArgsConstructor // Genera constructor vacío Animal()
@AllArgsConstructor // Genera constructor con TODOS los campos
@XmlRootElement(name = "animal")
// Definimos el orden para que el XML sea idéntico al XSD de Lenguajes de Marcas
@XmlType(propOrder = {"id", "nombre", "especie", "raza", "fechaIngreso", "estado", "causaBaja", "fechaBaja", "vacunasAlDia"})
public class Animal {
    // 1. Atributos
    private String id;
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

    // 3. Constructor para el Controller (El de 3 parámetros)
    public Animal(String nombre, double peso, TipoAnimal especie) {
        this.nombre = nombre;
        this.peso = peso;
        this.especie = especie;
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

    @XmlElement(name = "causaBaja")
    public String getCausaBaja() { return causaBaja; }

    @XmlElement(name = "fechaBaja")
    public String getFechaBaja() { return fechaBaja; }
}