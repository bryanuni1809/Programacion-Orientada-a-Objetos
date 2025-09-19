/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author BRYAN
 */
public class Calificacion {
    private String codigoCurso;
    private String dniEstudiante;
    private String fecha;
    private double nota;
    private String observaciones;

    public Calificacion(String codigoCurso, String dniEstudiante, String fecha, double nota, String observaciones) {
        this.codigoCurso = codigoCurso;
        this.dniEstudiante = dniEstudiante;
        this.fecha = fecha;
        this.nota = nota;
        this.observaciones = observaciones;
    }

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

    public String mostrarInfo() {
        return "Curso: " + codigoCurso + " | Estudiante: " + dniEstudiante + " | Nota: " + nota + "\nObservaciones: " + observaciones;
    }
}
