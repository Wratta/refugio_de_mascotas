package model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    private String id;
    private String nombre;
    private String microchip;
    private double peso;
    private boolean esterilizado;
    private LocalDate fechaUltimaRabia;
    private TipoAnimal especie;

    public double calcularRacionDiaria() {
        return this.peso * especie.getFactorComida();
    }
}