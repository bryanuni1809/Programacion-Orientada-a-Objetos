/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import util.Validador;

/**
 *
 * @author BRYAN
 */
public class Curso extends EntidadAcademica implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String idioma;
    private String nivel;
    private String profesorDni;
    private String horario;
    private int duracion;
    private int capacidadMaxima;
    private double precio;
    private String observaciones;

    public Curso(String codigo, String nombre, String idioma, String nivel, String profesorDni,
                 String horario, int duracion, int capacidadMaxima, double precio, String observaciones) {
        super(codigo);
        this.nombre=nombre.trim();
        this.idioma=idioma.trim();
        this.nivel=nivel.trim();
        this.profesorDni=profesorDni.trim();
        this.horario=horario.trim();
        this.duracion=duracion;
        this.capacidadMaxima=capacidadMaxima;
        this.precio=precio;
        this.observaciones=observaciones.trim();
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre.trim(); }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma.trim(); }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel.trim(); }

    public String getProfesorDni() { return profesorDni; }
    public void setProfesorDni(String profesorDni) { this.profesorDni = profesorDni.trim(); }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario.trim(); }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public int getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(int capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones.trim(); }

    @Override
    public String mostrarInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Código: ").append(codigo)
          .append(" | Nombre: ").append(nombre)
          .append(" | Idioma: ").append(idioma)
          .append(" | Nivel: ").append(nivel)
          .append(" | Profesor DNI: ").append(profesorDni)
          .append(" | Horario: ").append(horario)
          .append(" | Precio: S/").append(precio)
          .append("\nObservaciones: ").append(observaciones);
        return sb.toString();
    }

    @Override
    public boolean validar() {
        try {
            Validador.validarCodigoCurso(codigo);
            Validador.validarNoVacio(nombre, "nombre del curso");
            Validador.validarIdioma(idioma);
            Validador.validarNivelIdioma(nivel);
            Validador.validarDNI(profesorDni);
            Validador.validarNoVacio(horario, "horario");
            Validador.validarDuracionCurso(duracion);
            Validador.validarCapacidadCurso(capacidadMaxima);
            Validador.validarPrecio(precio);
            Validador.validarNoVacio(observaciones, "observaciones");
            mensajeError = "";
            return true;
        } catch (IllegalArgumentException e) {
            mensajeError = e.getMessage();
            return false;
        }
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String toString() {
        return nombre + " (" + idioma + " - " + nivel + ")";
    }
}