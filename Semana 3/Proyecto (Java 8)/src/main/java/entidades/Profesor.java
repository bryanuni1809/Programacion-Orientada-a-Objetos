/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author BRYAN
 */
// Profesor hereda de Persona. Esto evita repetir atributos comunes
public class Profesor extends Persona {
    private String especialidad;
    private int experiencia;

    public Profesor(String dni, String nombres, String apellidos, String direccion,
                    String telefono, String correo, String especialidad, int experiencia) {
        super(dni, nombres, apellidos, direccion, telefono, correo);
        this.especialidad = especialidad;
        this.experiencia = experiencia;
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public int getExperiencia() { return experiencia; }
    public void setExperiencia(int experiencia) { this.experiencia = experiencia; }

    //Sobrescritura de método
    @Override
    public String mostrarInfo() {
        return super.mostrarInfo() + " | Especialidad: " + especialidad + " | Años experiencia: " + experiencia;
    }
}
