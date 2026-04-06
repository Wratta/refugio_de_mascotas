package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data // Genera Getters, Setters, toString...
@NoArgsConstructor // Genera constructor vacío Animal()
@AllArgsConstructor // Genera constructor con TODOS los campos
public class Animal {
    // 1. Atributos
    private String id;
    private String nombre;
    private String microchip;
    private double peso;
    private boolean esterilizado;
    private LocalDate fechaUltimaRabia;
    private TipoAnimal especie;

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
}