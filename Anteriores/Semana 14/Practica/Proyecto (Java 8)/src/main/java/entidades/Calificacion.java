/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import interfaces.IEntidad;
import interfaces.IValidable;
import java.io.Serializable;
import util.Validador;

/**
 *
 * @author BRYAN
 */
public class Calificacion implements IEntidad, IValidable, Serializable {
    private static final long serialVersionUID = 1L;

    private String codigoCurso;
    private String dniEstudiante;
    private String fecha;
    private double nota;
    private String observaciones;
    private String mensajeError;

    public Calificacion(String codigoCurso, String dniEstudiante, String fecha, double nota, String observaciones) {
        this.codigoCurso = codigoCurso;
        this.dniEstudiante = dniEstudiante;
        this.fecha = fecha;
        this.nota = nota;
        this.observaciones = observaciones;
        this.mensajeError = "";
    }

    // ===== Getters y Setters =====
    public String getCodigoCurso() { return codigoCurso; }
    public void setCodigoCurso(String codigoCurso) { this.codigoCurso = codigoCurso; }

    public String getDniEstudiante() { return dniEstudiante; }
    public void setDniEstudiante(String dniEstudiante) { this.dniEstudiante = dniEstudiante; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String mostrarInfo() {
        return String.format("Curso: %s | Estudiante: %s | Fecha: %s | Nota: %.2f | Observaciones: %s",
                codigoCurso, dniEstudiante, fecha, nota, observaciones);
    }

    @Override
    public String getTipo() {
        return "Calificación";
    }

    @Override
    public boolean validar() {
        try {
            Validador.validarCodigoCurso(codigoCurso);
            Validador.validarDNI(dniEstudiante);
            Validador.validarFecha(fecha, "fecha de calificación");
            Validador.validarNota(nota);
            Validador.validarNoVacio(observaciones, "observaciones");

            mensajeError = "";
            return true;
        } catch (IllegalArgumentException e) {
            mensajeError = "[CALIFICACIÓN] " + e.getMessage();
            return false;
        }
    }

    @Override
    public String getMensajeError() {
        return mensajeError;
    }

    @Override
    public String toString() {
        return String.format(
                "Calificacion[codigoCurso=%s, dniEstudiante=%s, fecha=%s, nota=%.2f, observaciones=%s]",
                codigoCurso, dniEstudiante, fecha, nota, observaciones
        );
    }
}