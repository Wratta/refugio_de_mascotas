package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
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
    
    public enum TipoAnimal {
        // Definimos las constantes con su factor de comida
        PERRO("Canino", 0.02),  // 2% del peso corporal
        GATO("Felino", 0.015);  // 1.5% del peso corporal

        private final String descripcion;
        private final double factorComida;

        TipoAnimal(String descripcion, double factorComida) {
            this.descripcion = descripcion;
            this.factorComida = factorComida;
        }

        public double getFactorComida() {
            return factorComida;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }
}