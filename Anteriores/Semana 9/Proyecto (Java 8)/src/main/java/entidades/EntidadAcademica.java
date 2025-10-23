/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import interfaces.IEntidad;
import interfaces.IValidable;

/**
 *
 * @author BRYAN
 */
public abstract class EntidadAcademica implements IEntidad, IValidable {
    protected String codigo;
    protected String mensajeError;

    public EntidadAcademica(String codigo) {
        this.codigo = codigo;
        this.mensajeError = "";
    }

    public String getCodigo() { 
        return codigo; 
    }
    
    public void setCodigo(String codigo) { 
        this.codigo = codigo; 
    }

    @Override
    public String getMensajeError() {
        return mensajeError;
    }

    // Métodos abstractos que deben implementar las clases hijas
    public abstract boolean validar();
    public abstract String mostrarInfo();
    public abstract String getTipo();
}
