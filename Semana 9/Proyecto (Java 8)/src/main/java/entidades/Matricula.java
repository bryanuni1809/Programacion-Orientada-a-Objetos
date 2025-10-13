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
public class Matricula implements IEntidad, IValidable {
    private String codigoCurso;
    private String dniEstudiante;
    private String fechaMatricula;
    private double monto;
    private String mensajeError;

    public Matricula(String codigoCurso, String dniEstudiante, String fechaMatricula, double monto) {
        this.codigoCurso = codigoCurso;
        this.dniEstudiante = dniEstudiante;
        this.fechaMatricula = fechaMatricula;
        this.monto = monto;
    }

    public String getCodigoCurso() { return codigoCurso; }
    public void setCodigoCurso(String codigoCurso) { this.codigoCurso = codigoCurso; }

    public String getDniEstudiante() { return dniEstudiante; }
    public void setDniEstudiante(String dniEstudiante) { this.dniEstudiante = dniEstudiante; }

    public String getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(String fechaMatricula) { this.fechaMatricula = fechaMatricula; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    @Override
    public String mostrarInfo() {
        return "Curso: " + codigoCurso + " | Estudiante: " + dniEstudiante + 
               " | Fecha: " + fechaMatricula + " | Monto: S/" + monto;
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
            mensajeError = e.getMessage();
            return false;
        }
    }

    @Override
    public String getMensajeError() {
        return mensajeError;
    }
}