package model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@AllArgsConstructor
@NoArgsConstructor

@XmlRootElement(name = "adoptante")
@XmlType(propOrder = {"dni", "nombre", "apellidos", "telefono", "email", "curso_competencias", "contrato_firmado"})
public class Adoptante {

    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private boolean curso_competencias;
    private boolean contrato_firmado;

    public Adoptante() {}

    @XmlElement
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    @XmlElement
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @XmlElement
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    @XmlElement
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @XmlElement
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @XmlElement
    public boolean isCurso_competencias() { return curso_competencias; }
    public void setCurso_competencias(boolean curso_competencias) { this.curso_competencias = curso_competencias; }

    @XmlElement
    public boolean isContrato_firmado() { return contrato_firmado; }
    public void setContrato_firmado(boolean contrato_firmado) { this.contrato_firmado = contrato_firmado; }
}