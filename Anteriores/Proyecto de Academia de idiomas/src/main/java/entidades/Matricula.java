/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author BRYAN
 */
public class Matricula {
    private String codigoCurso;
    private String dniEstudiante;
    private String fechaMatricula;
    private double monto;

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

    public String mostrarInfo() {
        return "Curso: " + codigoCurso + " | Estudiante: " + dniEstudiante + " | Fecha: " + fechaMatricula + " | Monto: S/" + monto;
    }

    public String getFecha() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getMontoPagado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
