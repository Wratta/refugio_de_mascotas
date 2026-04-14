package model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Adoptante {
    private String dni; // Identificador legal obligatorio
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;

    // Requisitos Ley de Bienestar Animal
    private boolean cursoCompetencias; // ¿Ha hecho el curso para perros?
    private boolean antecedentesMaltrato; // Declaración responsable (debe ser false)
    private boolean contratoFirmado;
}