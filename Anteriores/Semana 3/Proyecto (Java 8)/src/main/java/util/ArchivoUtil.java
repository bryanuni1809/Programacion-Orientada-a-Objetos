/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import entidades.Calificacion;
import entidades.Curso;
import entidades.Estudiante;
import entidades.IdiomaNivel;
import entidades.Matricula;
import entidades.Profesor;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author BRYAN
 */
public class ArchivoUtil {
    public static void guardarEstudiante(Estudiante e, String ruta) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true))) {
            writer.write(e.getDni() + "," + e.getNombres() + "," + e.getApellidos() + "," +
                         e.getDireccion() + "," + e.getTelefono() + "," + e.getCorreo() + "," +
                         e.getFechaNacimiento() + "," + e.getNivelEstudios());
            writer.newLine();
        } catch (IOException ex) {
            System.out.println("Error al guardar estudiante: " + ex.getMessage());
        }
    }

    public static void guardarProfesor(Profesor p, String ruta) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true))) {
            writer.write(p.getDni() + "," + p.getNombres() + "," + p.getApellidos() + "," +
                         p.getDireccion() + "," + p.getTelefono() + "," + p.getCorreo() + "," +
                         p.getEspecialidad() + "," + p.getExperiencia());
            writer.newLine();
        } catch (IOException ex) {
            System.out.println("Error al guardar profesor: " + ex.getMessage());
        }
    }

    public static void guardarCurso(Curso c, String ruta) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true))) {
            writer.write(c.getCodigo() + "," + c.getNombre() + "," + c.getIdioma() + "," + c.getNivel() + "," +
                         c.getProfesorDni() + "," + c.getHorario() + "," + c.getDuracion() + "," +
                         c.getCapacidadMaxima() + "," + c.getPrecio() + "," + c.getObservaciones());
            writer.newLine();
        } catch (IOException ex) {
            System.out.println("Error al guardar curso: " + ex.getMessage());
        }
    }

    public static void guardarMatricula(Matricula m, String ruta) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true))) {
            writer.write(m.getCodigoCurso() + "," + m.getDniEstudiante() + "," + m.getFechaMatricula() + "," + m.getMonto());
            writer.newLine();
        } catch (IOException ex) {
            System.out.println("Error al guardar matrícula: " + ex.getMessage());
        }
    }

    public static void guardarCalificacion(Calificacion c, String ruta) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true))) {
            writer.write(c.getCodigoCurso() + "," + c.getDniEstudiante() + "," + c.getFecha() + "," +
                         c.getNota() + "," + c.getObservaciones());
            writer.newLine();
        } catch (IOException ex) {
            System.out.println("Error al guardar calificación: " + ex.getMessage());
        }
    }
    public static void sobrescribirEstudiantes(ArrayList<Estudiante> lista, String ruta) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
        for (Estudiante e : lista) {
            writer.write(e.getDni() + "," + e.getNombres() + "," + e.getApellidos() + "," +
                         e.getDireccion() + "," + e.getTelefono() + "," + e.getCorreo() + "," +
                         e.getFechaNacimiento() + "," + e.getNivelEstudios());
            writer.newLine();
        }
    } catch (IOException ex) {
        System.out.println("Error al sobrescribir estudiantes: " + ex.getMessage());
    }
}
    public static void sobrescribirProfesores(ArrayList<Profesor> lista, String ruta) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
        for (Profesor p : lista) {
            writer.write(p.getDni() + "," + p.getNombres() + "," + p.getApellidos() + "," +
                         p.getDireccion() + "," + p.getTelefono() + "," + p.getCorreo() + "," +
                         p.getEspecialidad() + "," + p.getExperiencia());
            writer.newLine();
        }
    } catch (IOException ex) {
        System.out.println("Error al sobrescribir profesores: " + ex.getMessage());
    }
}
    public static void sobrescribirCursos(ArrayList<Curso> lista, String ruta) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
        for (Curso c : lista) {
            writer.write(c.getCodigo() + "," + c.getNombre() + "," + c.getIdioma() + "," + c.getNivel() + "," +
                         c.getProfesorDni() + "," + c.getHorario() + "," + c.getDuracion() + "," +
                         c.getCapacidadMaxima() + "," + c.getPrecio() + "," + c.getObservaciones());
            writer.newLine();
        }
    } catch (IOException ex) {
        System.out.println("Error al sobrescribir cursos: " + ex.getMessage());
    }
}
    public static void guardarNivelIdioma(IdiomaNivel in, String ruta) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true))) {
        writer.write(in.getCodigo() + "," + in.getIdioma() + "," + in.getNivel() + "," + in.getDescripcion());
        writer.newLine();
    } catch (IOException ex) {
        System.out.println("Error al guardar nivel de idioma: " + ex.getMessage());
    }
}
    public static void sobrescribirNivelesIdioma(ArrayList<IdiomaNivel> lista, String ruta) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
        for (IdiomaNivel in : lista) {
            writer.write(in.getCodigo() + "," + in.getIdioma() + "," + in.getNivel() + "," + in.getDescripcion());
            writer.newLine();
        }
    } catch (IOException ex) {
        System.out.println("Error al sobrescribir niveles de idioma: " + ex.getMessage());
    }
}
}
