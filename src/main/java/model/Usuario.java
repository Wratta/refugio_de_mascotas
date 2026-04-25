package model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {
    private int id;
    private String username;
    private String password;
    private String nombre;
    private Rol rol; // Usamos el Enum que creamos arriba
}