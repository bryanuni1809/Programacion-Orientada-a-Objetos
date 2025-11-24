/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USER
 */
public class Estudiante extends Persona {
    public Estudiante(String dni, String nombre, String telefono) {
        super(dni, nombre, telefono);
    }

    @Override
    public String getTipo() {
        return "Estudiante";
    }
}
