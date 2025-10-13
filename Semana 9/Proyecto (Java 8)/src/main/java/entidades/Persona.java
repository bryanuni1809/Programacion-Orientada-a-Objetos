/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import interfaces.IEntidad;
import interfaces.IValidable;
import util.Validador;

/**
 *
 * @author BRYAN
 */
public abstract class Persona implements IEntidad, IValidable{
    protected String dni;
    protected String nombres;
    protected String apellidos;
    protected String direccion;
    protected String telefono;
    protected String correo;
    protected String mensajeError;

    public Persona(String dni,String nombres,String apellidos,String direccion,String telefono,String correo) {
        this.dni=dni;
        this.nombres=nombres;
        this.apellidos=apellidos;
        this.direccion=direccion;
        this.telefono=telefono;
        this.correo=correo;
        this.mensajeError = "";
    }
    public String getDni(){return dni;}
    public void setDni(String dni){this.dni=dni;}

    public String getNombres(){return nombres;}
    public void setNombres(String nombres){this.nombres=nombres;}

    public String getApellidos(){return apellidos;}
    public void setApellidos(String apellidos){this.apellidos=apellidos;}

    public String getDireccion(){return direccion;}
    public void setDireccion(String direccion){this.direccion=direccion;}

    public String getTelefono(){return telefono;}
    public void setTelefono(String telefono){this.telefono=telefono;}

    public String getCorreo(){return correo;}
    public void setCorreo(String correo){this.correo=correo;}

   @Override
    public String mostrarInfo(){
        return "DNI: " +dni+" | Nombre: "+nombres+" "+apellidos;
    }
    @Override
    public boolean validar() {
        try {
            Validador.validarDNI(dni);
            Validador.validarSoloLetras(nombres,"nombres");
            Validador.validarSoloLetras(apellidos,"apellidos");
            Validador.validarNoVacio(direccion,"dirección");
            Validador.validarTelefono(telefono);
            Validador.validarEmail(correo);
            mensajeError = "";
            return true;
        }catch(IllegalArgumentException e){
            mensajeError=e.getMessage();
            return false;
        }
    }
     @Override
    public String getMensajeError() {
        return mensajeError;
    }
    public abstract String getTipo();
}
