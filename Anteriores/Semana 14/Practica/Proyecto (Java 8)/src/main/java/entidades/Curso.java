/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private List<SesionClase> sesiones;

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
        this.sesiones = new ArrayList<>();
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
    
    public List<SesionClase> getSesiones() {
        return new ArrayList<>(sesiones);
    }
  
    public boolean agregarSesion(SesionClase sesion) {
        if (sesion == null || !sesion.validar()) return false;
        // Asignar ID automático si no tiene (ej: C101-S01)
        if (sesion.getId().isEmpty() || sesion.getId().equals("0")) {
            String nuevoId = this.codigo + "-S" + String.format("%02d", sesiones.size() + 1);
            // Usa reflexión o constructor copia — o mejor: crea un método auxiliar
            // Por simplicidad, asumimos que el ID se asigna antes.
        }
        sesiones.add(sesion);
        return true;
    }
    
     public boolean eliminarSesion(String idSesion) {
        return sesiones.removeIf(s -> s.getId().equals(idSesion));
    }

    public SesionClase buscarSesion(String idSesion) {
        return sesiones.stream()
                      .filter(s -> s.getId().equals(idSesion))
                      .findFirst()
                      .orElse(null);
    }
    
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
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString())
          .append(" | Nombre: ").append(nombre)
          .append(" | Sesiones: ").append(sesiones.size());
        if (!sesiones.isEmpty()) {
            sb.append("\n  Sesiones:");
            for (SesionClase s : sesiones) {
                sb.append("\n    • ").append(s.mostrarInfo());
            }
        }
        return sb.toString();
    }
}