package model;

import java.time.LocalDate;

public class Animal {
    private String id;
    private String nombre;
    private String microchip;
    private LocalDate fechaEntrada;
    private double peso;
    private boolean esterilizado;
    private LocalDate fechaUltimaRabia;
    private TipoAnimal especie; // Enum: PERRO, GATO

    public Animal(String nombre, double peso, TipoAnimal especie) {
        this.nombre = nombre;
        this.peso = peso;
        this.especie = especie;
    }
    // Metodo para calcular la ración diaria (Lógica de Negocio)
    public double calcularRacionDiaria() {
        return this.peso * especie.getFactorComida();
    }

    // Lógica de Alerta de Rabia
    public String getEstadoVacunacion() {
        LocalDate hoy = LocalDate.now();
        LocalDate proxima = fechaUltimaRabia.plusYears(1);
        if (hoy.isAfter(proxima)) return "CADUCADA (URGENTE)";
        if (hoy.isAfter(proxima.minusDays(15))) return "PRÓXIMA CADUCIDAD";
        return "AL DÍA";
    }
    public enum TipoAnimal {
        PERRO("Canino", 0.02),  // 2% de su peso en comida
        GATO("Felino", 0.015), // 1.5% de su peso en comida
        OTRO("Exótico", 0.01);

        private final String descripcion;
        private final double factorComida;

        // Constructor del enum
        TipoAnimal(String descripcion, double factorComida) {
            this.descripcion = descripcion;
            this.factorComida = factorComida;
        }

        // Getters para usar en los cálculos
        public String getDescripcion() { return descripcion; }
        public double getFactorComida() { return factorComida; }
        public TipoAnimal getEspecie() { return this.especie }
        public void setEspecie(TipoAnimal especie) { this.especie = especie; }
    }
    }
}