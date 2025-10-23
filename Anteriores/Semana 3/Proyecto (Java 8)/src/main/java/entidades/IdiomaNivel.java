/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author BRYAN
 */
public class IdiomaNivel {
    private String codigo;
    private String idioma;
    private String nivel;
    private String descripcion;

    public IdiomaNivel(String codigo, String idioma, String nivel, String descripcion) {
        this.codigo = codigo;
        this.idioma = idioma;
        this.nivel = nivel;
        this.descripcion = descripcion;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String mostrarInfo() {
        return "Codigo: " + codigo + " | Idioma: " + idioma + " | Nivel: " + nivel +
               "\nDescripcion: " + descripcion;
    }

    public String getDuracion() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
