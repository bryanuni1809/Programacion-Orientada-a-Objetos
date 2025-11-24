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
 * @author USER
 */
public class SesionClase implements IEntidad, IValidable, Serializable {
    private static final long serialVersionUID = 1L;

    private String id; // e.g. "C101-S01"
    private String titulo;
    private String fecha; // dd/MM/yyyy
    private String horaInicio; // HH:mm
    private String horaFin;    // HH:mm
    private String tema;
    private String descripcion;
    private String mensajeError;

    public SesionClase(String id, String titulo, String fecha, String horaInicio, String horaFin,
                       String tema, String descripcion) {
        this.id = id.trim();
        this.titulo = titulo.trim();
        this.fecha = fecha.trim();
        this.horaInicio = horaInicio.trim();
        this.horaFin = horaFin.trim();
        this.tema = tema.trim();
        this.descripcion = descripcion.trim();
        this.mensajeError = "";
    }

    // ===== Getters =====
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getFecha() { return fecha; }
    public String getHoraInicio() { return horaInicio; }
    public String getHoraFin() { return horaFin; }
    public String getTema() { return tema; }
    public String getDescripcion() { return descripcion; }

    // ===== Setters (con trim) =====
    public void setTitulo(String titulo) { this.titulo = titulo.trim(); }
    public void setFecha(String fecha) { this.fecha = fecha.trim(); }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio.trim(); }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin.trim(); }
    public void setTema(String tema) { this.tema = tema.trim(); }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion.trim(); }

    @Override
    public String mostrarInfo() {
        return String.format(
            "Sesión %s | %s | %s %s-%s | Tema: %s",
            id, titulo, fecha, horaInicio, horaFin, tema
        );
    }

    @Override
    public String getTipo() {
        return "Sesión de Clase";
    }

    @Override
    public boolean validar() {
        try {
            Validador.validarNoVacio(id, "ID de sesión");
            Validador.validarNoVacio(titulo, "título");
            Validador.validarFecha(fecha, "fecha de sesión");
            Validador.validarFormatoHora(horaInicio, "hora de inicio");
            Validador.validarFormatoHora(horaFin, "hora de fin");
            if (horaFin.compareTo(horaInicio) <= 0) {
                throw new IllegalArgumentException("La hora de fin debe ser posterior a la de inicio.");
            }
            Validador.validarNoVacio(tema, "tema");
            Validador.validarNoVacio(descripcion, "descripción");
            mensajeError = "";
            return true;
        } catch (IllegalArgumentException e) {
            mensajeError = "[SESIÓN] " + e.getMessage();
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

    // Para compatibilidad en colecciones ordenadas
    public int getNumero() {
        // Extraer "S01" → 1, "S12" → 12
        if (id.contains("-S")) {
            try {
                String numPart = id.split("-S")[1];
                return Integer.parseInt(numPart.replaceAll("\\D+", ""));
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }
}
