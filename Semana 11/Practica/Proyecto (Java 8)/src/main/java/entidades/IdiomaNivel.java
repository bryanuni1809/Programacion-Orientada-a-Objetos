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
public class IdiomaNivel implements IEntidad, IValidable{
    private String codigo;
    private String idioma;
    private String nivel;
    private String descripcion;
    private String mensajeError;

    public IdiomaNivel(String codigo,String idioma,String nivel,String descripcion){
        this.codigo = codigo;
        this.idioma=idioma;
        this.nivel=nivel;
        this.descripcion=descripcion;
        this.mensajeError = "";
    }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getIdioma(){return idioma;}
    public void setIdioma(String idioma){this.idioma=idioma;}

    public String getNivel(){return nivel;}
    public void setNivel(String nivel){this.nivel=nivel;}

    public String getDescripcion(){return descripcion;}
    public void setDescripcion(String descripcion){this.descripcion=descripcion;}

    @Override
    public String mostrarInfo(){
        return "Codigo: " +codigo+" | Idioma: "+idioma+ " | Nivel: "+nivel+
               "\nDescripcion: "+descripcion;
    }
    @Override
    public String getTipo() {
        return "Nivel de Idioma";
    }
     @Override
    public boolean validar() {
        try {
            Validador.validarNoVacio(codigo, "código");
            Validador.validarIdioma(idioma);
            Validador.validarNivelIdioma(nivel);
            Validador.validarNoVacio(descripcion, "descripción");
            mensajeError = "";
            return true;
        } catch (IllegalArgumentException e) {
            mensajeError = e.getMessage();
            return false;
        }
    }

    @Override
    public String getMensajeError() {
        return mensajeError;
    }
}
