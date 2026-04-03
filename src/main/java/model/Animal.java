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

    // Método para calcular la ración diaria (Lógica de Negocio)
    public double calcularRacionDiaria() {
        if (especie == TipoAnimal.PERRO) {
            return this.peso * 0.02; // 2% de su peso corporal aprox.
        } else {
            return this.peso * 0.015; // Los gatos tienen otro ratio
        }
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
    }
}