/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USER
 */
public class Profesor extends Persona {
    private String especialidad; // ej: "Inglés", "Francés"

    public Profesor(String dni, String nombre, String telefono, String especialidad) {
        super(dni, nombre, telefono);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() { return especialidad; }

    @Override
    public String getTipo() {
        return "Profesor";
    }
}
