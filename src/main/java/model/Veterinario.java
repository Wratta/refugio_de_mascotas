package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "veterinario")
// Definimos el orden de las etiquetas en el XML para que coincida con el XSD
@XmlType(propOrder = {"id_vet", "nombre", "telefono"})
public class Veterinario {

    private int id_vet;
    private String nombre;
    private String telefono;


    @XmlElement(name = "id_vet")
    public int getId_vet() {
        return id_vet;
    }

    public void setId_vet(int id_vet) {
        this.id_vet = id_vet;
    }

    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlElement(name = "telefono")
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}