package model;

public enum TipoAnimal {
    PERRO(0.02),
    GATO(0.015);

    private final double factorComida; // El atributo

    // El constructor
    TipoAnimal(double factorComida) {
        this.factorComida = factorComida;
    }


    public double getFactorComida() {
        return factorComida;
    }
}