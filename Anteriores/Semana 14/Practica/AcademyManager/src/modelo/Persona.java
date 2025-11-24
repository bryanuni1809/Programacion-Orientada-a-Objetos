/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USER
 */
public abstract class Persona {
    protected String dni;
    protected String nombre;
    protected String telefono;

    // Constructor
    public Persona(String dni, String nombre, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Getters (encapsulamiento)
    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }

    // Método abstracto → obliga a implementar en subclases
    public abstract String getTipo();

    @Override
    public String toString() {
        return nombre + " (" + getTipo() + ")";
    }
}