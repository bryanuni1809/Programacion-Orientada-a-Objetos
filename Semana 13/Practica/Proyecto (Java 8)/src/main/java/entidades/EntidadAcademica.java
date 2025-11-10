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
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío o ser nulo.");
        }
        this.codigo = codigo.trim();
        this.mensajeError = "";
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo.trim(); }

    @Override
    public String getMensajeError() { return mensajeError; }

    public abstract boolean validar();
    public abstract String mostrarInfo();
    public abstract String getTipo();

    @Override
    public String toString() {
        return getTipo() + " [Código: " + codigo + "]";
    }
}
