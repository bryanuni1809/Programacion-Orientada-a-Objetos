/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author BRYAN
 */
public abstract class Persona {
    protected String dni;
    protected String nombres;
    protected String apellidos;
    protected String direccion;
    protected String telefono;
    protected String correo;

    public Persona(String dni, String nombres, String apellidos, String direccion, String telefono, String correo) {
        this.dni= dni;
        this.nombres =nombres;
        this.apellidos=apellidos;
        this.direccion =direccion;
        this.telefono =telefono;
        this.correo=correo;
    }
    public String getDni(){return dni;}
    public void setDni(String dni){this.dni= dni;}

    public String getNombres(){return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos(){return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDireccion() {return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() {return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() {return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String mostrarInfo() {
        return "DNI: " + dni +" | Nombre: " + nombres + " " + apellidos;
    }
    public abstract String getTipo();
}
