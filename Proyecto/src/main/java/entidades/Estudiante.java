/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author BRYAN
 */
public class Estudiante extends Persona {
    private String fechaNacimiento;
    private String nivelEstudios;

    public Estudiante(String dni, String nombres, String apellidos, String direccion,
                      String telefono, String correo, String fechaNacimiento, String nivelEstudios) {
        super(dni, nombres, apellidos, direccion, telefono, correo);
        this.fechaNacimiento = fechaNacimiento;
        this.nivelEstudios = nivelEstudios;
    }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getNivelEstudios() { return nivelEstudios; }
    public void setNivelEstudios(String nivelEstudios) { this.nivelEstudios = nivelEstudios; }

    @Override
    public String mostrarInfo() {
        return super.mostrarInfo() + " | Fecha Nac: " + fechaNacimiento + " | Nivel: " + nivelEstudios;
    }
}
