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
public class Matricula implements IEntidad, IValidable, Serializable {
    private static final long serialVersionUID = 1L;

    private String codigoCurso;
    private String dniEstudiante;
    private String fechaMatricula; // Formato: dd/MM/yyyy
    private double monto;
    private String mensajeError;

    public Matricula(String codigoCurso, String dniEstudiante, String fechaMatricula, double monto) {
        this.codigoCurso = codigoCurso.trim();
        this.dniEstudiante = dniEstudiante.trim();
        this.fechaMatricula = fechaMatricula.trim();
        this.monto = monto;
        this.mensajeError = "";
    }

    // ===== Getters y Setters =====
    public String getCodigoCurso() { return codigoCurso; }
    public void setCodigoCurso(String codigoCurso) { this.codigoCurso = codigoCurso.trim(); }

    public String getDniEstudiante() { return dniEstudiante; }
    public void setDniEstudiante(String dniEstudiante) { this.dniEstudiante = dniEstudiante.trim(); }

    public String getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(String fechaMatricula) { this.fechaMatricula = fechaMatricula.trim(); }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    @Override
    public String mostrarInfo() {
        return String.format(
            "Curso: %s | Estudiante: %s | Fecha: %s | Monto: S/%.2f",
            codigoCurso, dniEstudiante, fechaMatricula, monto
        );
    }

    @Override
    public String getTipo() {
        return "Matrícula";
    }

    @Override
    public boolean validar() {
        try {
            Validador.validarCodigoCurso(codigoCurso);
            Validador.validarDNI(dniEstudiante);
            Validador.validarFecha(fechaMatricula, "fecha de matrícula");
            Validador.validarPrecio(monto);
            mensajeError = "";
            return true;
        } catch (IllegalArgumentException e) {
            mensajeError = "[MATRÍCULA] " + e.getMessage();
            return false;
        }
    }

    @Override
    public String getMensajeError() {
        return mensajeError;
    }

    @Override
    public String toString() {
        return mostrarInfo();
    }
}