/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import util.Validador;
import java.io.Serializable;

/**
 *
 * @author BRYAN
 */
public class Profesor extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    private String especialidad;
    private int experiencia;

    public Profesor(String dni, String nombres, String apellidos, String direccion,
                    String telefono, String correo, String especialidad, int experiencia) {
        super(dni, nombres, apellidos, direccion, telefono, correo);
        this.especialidad =especialidad;
        this.experiencia =experiencia;
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public int getExperiencia() { return experiencia; }
    public void setExperiencia(int experiencia) { this.experiencia = experiencia; }

    @Override
    public String mostrarInfo() {
        return super.mostrarInfo()
                + " | Especialidad: " + especialidad
                + " | Experiencia: " + experiencia + " años";
    }

    @Override
    public String getTipo() { return "Profesor"; }

    @Override
    public boolean validar() {
        if (!super.validar()) return false;
        try {
            Validador.validarNoVacio(especialidad, "especialidad");
            if (experiencia < 0 || experiencia > 60) {
                throw new IllegalArgumentException("La experiencia debe estar entre 0 y 60 años.");
            }
            mensajeError = "";
            return true;
        } catch (IllegalArgumentException e) {
            mensajeError = e.getMessage();
            return false;
        }
    }
}
