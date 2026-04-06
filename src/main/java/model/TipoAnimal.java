package model;

public enum TipoAnimal {
    PERRO("Canino", 0.02),
    GATO("Felino", 0.015);

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