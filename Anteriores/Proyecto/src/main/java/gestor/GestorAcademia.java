/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;

import entidades.Calificacion;
import entidades.Curso;
import entidades.Estudiante;
import entidades.IdiomaNivel;
import entidades.Matricula;
import entidades.Profesor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import util.ArchivoUtil;
import entidades.Ordenaciones;

/**
 *
 * @author BRYAN
 */
public class GestorAcademia {
    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Estudiante> estudiantes = new ArrayList<>();
    private final ArrayList<Profesor> profesores = new ArrayList<>();
    private final ArrayList<Curso> cursos = new ArrayList<>();
    private final ArrayList<Matricula> matriculas = new ArrayList<>();
    private final ArrayList<Calificacion> calificaciones = new ArrayList<>();
    private final ArrayList<IdiomaNivel> nivelesIdioma = new ArrayList<>();
    
    public GestorAcademia() {
    cargarEstudiantes();
    cargarProfesores();
    cargarCursos();
    cargarMatriculas();
    cargarCalificaciones();
    cargarNivelesIdioma();
    }
    private void cargarCursos() {
    try (BufferedReader br = new BufferedReader(new FileReader("cursos.txt"))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length >= 10) {
                Curso c = new Curso(
                    partes[0], partes[1], partes[2], partes[3],
                    partes[4], partes[5], Integer.parseInt(partes[6]),
                    Integer.parseInt(partes[7]),
                    Double.parseDouble(partes[8]),
                    partes[9]
                );
                cursos.add(c);
            }
        }
    } catch (IOException e) {
        System.out.println("Error al cargar cursos: " + e.getMessage());
    }
    }
    private void cargarProfesores() {
    try (BufferedReader br = new BufferedReader(new FileReader("profesores.txt"))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length >= 8) {
                Profesor p = new Profesor(
                    partes[0], partes[1], partes[2], partes[3],
                    partes[4], partes[5], partes[6],
                    Integer.parseInt(partes[7])
                );
                profesores.add(p);
            }
        }
    } catch (IOException e) {
        System.out.println("Error al cargar profesores: " + e.getMessage());
    }
}
    private void cargarNivelesIdioma() {
    try (BufferedReader br = new BufferedReader(new FileReader("idiomas.txt"))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length >= 4) {
                IdiomaNivel in = new IdiomaNivel(partes[0], partes[1], partes[2], partes[3]);
                nivelesIdioma.add(in);
            }
        }
    } catch (IOException e) {
        System.out.println("Error al cargar niveles de idioma: " + e.getMessage());
    }
}
    private void cargarEstudiantes() {
    try (BufferedReader br = new BufferedReader(new FileReader("estudiantes.txt"))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length >= 8) {
                Estudiante e = new Estudiante(
                    partes[0], partes[1], partes[2], partes[3],
                    partes[4], partes[5], partes[6], partes[7]
                );
                estudiantes.add(e);
            }
        }
    } catch (IOException e) {
        System.out.println("Error al cargar estudiantes: " + e.getMessage());
    }
}
    private void cargarMatriculas() {
    try (BufferedReader br = new BufferedReader(new FileReader("matriculas.txt"))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length >= 4) {
                Matricula m = new Matricula(
                    partes[0], partes[1], partes[2],
                    Double.parseDouble(partes[3])
                );
                matriculas.add(m);
            }
        }
    } catch (IOException e) {
        System.out.println("Error al cargar matrículas: " + e.getMessage());
    }
}
    private void cargarCalificaciones() {
    try (BufferedReader br = new BufferedReader(new FileReader("calificaciones.txt"))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length >= 5) {
                Calificacion c = new Calificacion(
                    partes[0], partes[1], partes[2],
                    Double.parseDouble(partes[3]), partes[4]
                );
                calificaciones.add(c);
            }
        }
    } catch (IOException e) {
        System.out.println("Error al cargar calificaciones: " + e.getMessage());
    }
}
    public void mostrarMenu() {
    int opcion;
    do {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Gestion de Estudiantes");
        System.out.println("2. Gestion de Profesores");
        System.out.println("3. Gestion de Cursos");
        System.out.println("4. Matriculas y Calificaciones");
        System.out.println("5. Niveles de Idioma");
        System.out.println("6. Generar Reportes HTML");
        System.out.println("7. Ordena Listas (Alumnos, Profesores, etc)");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                menuEstudiantes();
                break;
            case 2:
                menuProfesores();
                break;
            case 3:
                menuCursos();
                break;
            case 4:
                menuMatriculasNotas();
                break;
            case 5:
                menuNivelesIdioma();
                break;
            case 6:
                mostrarMenuReportesHTML();
                break;
            case 7:
                System.out.println("=== ORDENAR LISTAS ===");
                System.out.println("1. Ordenar Estudiantes");
                System.out.println("2. Ordenar Profesores");
                System.out.println("0. Volver al menu principal");
                int lista =Integer.parseInt(scanner.nextLine());

                switch (lista){
                    case 1:
                        if (estudiantes.isEmpty()) {
                        System.out.println("No hay estudiantes registrados.");
                        break;
                        }
                    System.out.println("=== ORDENAR ESTUDIANTES ===");
                    System.out.println("1. Burbuja");
                    System.out.println("2. Seleccion");
                    System.out.println("3. Insercion");
                    int ordest = Integer.parseInt(scanner.nextLine());
                        switch (ordest){
                            case 1:
                                Ordenaciones.burbuja(estudiantes,Comparator.comparing(Estudiante::getApellidos));
                                break;
                            case 2:
                                Ordenaciones.seleccion(estudiantes,Comparator.comparing(Estudiante::getApellidos));
                                break;
                            case 3:
                                Ordenaciones.insercion(estudiantes,Comparator.comparing(Estudiante::getApellidos));
                                break;
                            default:
                                System.out.println("Opcion invalida.");
                                break;
                        }
                    System.out.println("=== Lista de Estudiantes Ordenados ===");
                        for (Estudiante e : estudiantes) {
                        System.out.println(e.mostrarInfo());
                        }
                    break;

                    case 2:
                        if (profesores.isEmpty()) {
                        System.out.println("No hay profesores registrados.");
                        break;
                        }
                    System.out.println("=== ORDENAR PROFESORES ===");
                    System.out.println("1. Burbuja");
                    System.out.println("2. Seleccion");
                    System.out.println("3. Insercion");
                    int ordprof = Integer.parseInt(scanner.nextLine());
                        switch (ordprof) {
                            case 1:
                                Ordenaciones.burbuja(profesores,Comparator.comparing(Profesor::getApellidos));
                                break;
                            case 2:
                                Ordenaciones.seleccion(profesores,Comparator.comparing(Profesor::getApellidos));
                                break;
                            case 3:
                                Ordenaciones.insercion(profesores,Comparator.comparingInt(Profesor::getExperiencia));
                                break;
                            default:
                                System.out.println("Opcion invalida.");
                                break;
                        }
                        System.out.println("=== Lista de Profesores Ordenados ===");
                            for (Profesor p : profesores) {
                                System.out.println(p.mostrarInfo());
                            }
                break;

        case 0:
            System.out.println("Volviendo al menú principal...");
            break;

        default:
            System.out.println("Opción inválida.");
            break;
    }
    break;
            case 0:
                System.out.println("Cerrando sesion...");
                break;
            default:
                System.out.println("Opcion invalida.");
                break;
        }
    } while (opcion != 0);
}

private void menuEstudiantes() {
    int opcion;
    do {
        System.out.println("\n--- GESTION DE ESTUDIANTES ---");
        System.out.println("1. Registrar Estudiante");
        System.out.println("2. Buscar Estudiante");
        System.out.println("3. Modificar Estudiante");
        System.out.println("4. Eliminar Estudiante");
        System.out.println("0. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                registrarEstudiante();
                break;
            case 2:
                buscarEstudiante();
                break;
            case 3:
                modificarEstudiante();
                break;
            case 4:
                eliminarEstudiante();
                break;
            case 0:
                System.out.println("Volviendo al menu principal...");
                break;
            default:
                System.out.println("Opcion invalida.");
                break;
        }
    } while (opcion != 0);
}

private void menuProfesores() {
    int opcion;
    do {
        System.out.println("\n--- GESTION DE PROFESORES ---");
        System.out.println("1. Registrar Profesor");
        System.out.println("2. Buscar Profesor");
        System.out.println("3. Modificar Profesor");
        System.out.println("4. Eliminar Profesor");
        System.out.println("0. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                registrarProfesor();
                break;
            case 2:
                buscarProfesor();
                break;
            case 3:
                modificarProfesor();
                break;
            case 4:
                eliminarProfesor();
                break;
            case 0:
                System.out.println("Volviendo al menu principal...");
                break;
            default:
                System.out.println("Opcion invalida.");
                break;
        }
    } while (opcion != 0);
}
    private void menuCursos() {
    int opcion;
    do {
        System.out.println("\n--- GESTION DE CURSOS ---");
        System.out.println("1. Registrar Curso");
        System.out.println("2. Buscar Curso");
        System.out.println("3. Modificar Curso");
        System.out.println("4. Eliminar Curso");
        System.out.println("0. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                registrarCurso();
                break;
            case 2:
                buscarCurso();
                break;
            case 3:
                modificarCurso();
                break;
            case 4:
                eliminarCurso();
                break;
            case 0:
                System.out.println("Volviendo al menu principal...");
                break;
            default:
                System.out.println("Opcion invalida.");
        }
    } while (opcion != 0);
}

private void menuMatriculasNotas() {
    int opcion;
    do {
        System.out.println("\n--- MATRICULAS Y CALIFICACIONES ---");
        System.out.println("1. Registrar Matricula");
        System.out.println("2. Registrar Calificaciones");
        System.out.println("0. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                registrarMatricula();
                break;
            case 2:
                registrarCalificacion();
                break;
            case 0:
                System.out.println("Volviendo al menu principal...");
                break;
            default:
                System.out.println("Opcion invalida.");
        }
    } while (opcion != 0);
}

private void menuNivelesIdioma() {
    int opcion;
    do {
        System.out.println("\n--- GESTION DE NIVELES DE IDIOMA ---");
        System.out.println("1. Registrar Nivel de Idioma");
        System.out.println("2. Buscar Nivel de Idioma");
        System.out.println("3. Modificar Nivel de Idioma");
        System.out.println("4. Eliminar Nivel de Idioma");
        System.out.println("0. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                registrarNivelIdioma();
                break;
            case 2:
                buscarNivelIdioma();
                break;
            case 3:
                modificarNivelIdioma();
                break;
            case 4:
                eliminarNivelIdioma();
                break;
            case 0:
                System.out.println("Volviendo al menu principal...");
                break;
            default:
                System.out.println("Opcion invalida.");
        }
    } while (opcion != 0);
}

private void mostrarMenuReportesHTML() {
    int opcion;
    do {
        System.out.println("\n--- GENERACION DE REPORTES HTML ---");
        System.out.println("1. Reporte de Estudiantes");
        System.out.println("2. Reporte de Profesores");
        System.out.println("3. Reporte de Cursos");
        System.out.println("4. Reporte de Matriculas");
        System.out.println("5. Reporte de Calificaciones");
        System.out.println("6. Reporte de Niveles de Idioma");
        System.out.println("0. Volver al Menu Principal");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                generarReporteEstudiantesHTML();
                break;
            case 2:
                generarReporteProfesoresHTML();
                break;
            case 3:
                generarReporteCursosHTML();
                break;
            case 4:
                generarReporteMatriculasHTML();
                break;
            case 5:
                generarReporteCalificacionesHTML();
                break;
            case 6:
                generarReporteNivelesIdiomaHTML();
                break;
            case 0:
                System.out.println("Volviendo al menu principal...");
                break;
            default:
                System.out.println("Opcion invalida.");
        }

    } while (opcion != 0);
}
 private void registrarEstudiante() {
        System.out.println("Registro de Estudiante:");
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Nombres: ");
        String nombres = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Direccion: ");
        String direccion = scanner.nextLine();
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Fecha de nacimiento: ");
        String fechaNac = scanner.nextLine();
        System.out.print("Nivel de estudios: ");
        String nivel = scanner.nextLine();

        Estudiante e = new Estudiante(dni, nombres, apellidos, direccion, telefono, correo, fechaNac, nivel);
        estudiantes.add(e);
        ArchivoUtil.guardarEstudiante(e, "estudiantes.txt");

        System.out.println("Estudiante registrado y guardado.");
    }

    private void buscarEstudiante() {
        System.out.print("Ingrese DNI del estudiante: ");
        String dni = scanner.nextLine();
        for (Estudiante e : estudiantes) {
            if (e.getDni().equals(dni)) {
                System.out.println("Estudiante encontrado:");
                System.out.println(e.mostrarInfo());
                return;
            }
        }
        System.out.println("Estudiante no encontrado.");
    }

    private void registrarProfesor() {
        System.out.println("Registro de Profesor:");
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Nombres: ");
        String nombres = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Direccion: ");
        String direccion = scanner.nextLine();
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();
        System.out.print("Años de experiencia: ");
        int experiencia = Integer.parseInt(scanner.nextLine());

        Profesor p = new Profesor(dni, nombres, apellidos, direccion, telefono, correo, especialidad, experiencia);
        profesores.add(p);
        ArchivoUtil.guardarProfesor(p, "profesores.txt");

        System.out.println("Profesor registrado y guardado.");
    }

    private void registrarCurso() {
        System.out.println("Registro de Curso:");
        System.out.print("Codigo del curso: ");
        String codigo = scanner.nextLine();
        System.out.print("Nombre del curso: ");
        String nombre = scanner.nextLine();
        System.out.print("Idioma: ");
        String idioma = scanner.nextLine();
        System.out.print("Nivel: ");
        String nivel = scanner.nextLine();
        System.out.print("DNI del profesor: ");
        String dniProfesor = scanner.nextLine();
        System.out.print("Horario: ");
        String horario = scanner.nextLine();
        System.out.print("Duracion (en semanas): ");
        int duracion = Integer.parseInt(scanner.nextLine());
        System.out.print("Capacidad maxima: ");
        int capacidad = Integer.parseInt(scanner.nextLine());
        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());
        System.out.print("Observaciones: ");
        String obs = scanner.nextLine();

        Curso c = new Curso(codigo, nombre, idioma, nivel, dniProfesor, horario, duracion, capacidad, precio, obs);
        cursos.add(c);
        ArchivoUtil.guardarCurso(c, "cursos.txt");

        System.out.println("Curso registrado correctamente.");
    }
   private void registrarMatricula() {
        System.out.println("Registro de Matricula:");
        System.out.print("DNI del estudiante: ");
        String dni = scanner.nextLine();

        Estudiante estudiante = null;
        for (Estudiante e : estudiantes) {
            if (e.getDni().equals(dni)) {
                estudiante = e;
                break;
            }
        }

        if (estudiante == null) {
            System.out.println("Estudiante no encontrado. Primero debe registrarlo.");
            return;
        }

        if (cursos.isEmpty()) {
            System.out.println("No hay cursos disponibles. Primero registre cursos.");
            return;
        }

        System.out.println("Cursos disponibles:");
        for (Curso c : cursos) {
            System.out.println("- " + c.getCodigo() + ": " + c.getNombre());
        }

        System.out.print("Ingrese el codigo del curso: ");
        String codigoCurso = scanner.nextLine();

        Curso cursoSeleccionado = null;
        for (Curso c : cursos) {
            if (c.getCodigo().equals(codigoCurso)) {
                cursoSeleccionado = c;
                break;
            }
        }

        if (cursoSeleccionado == null) {
            System.out.println("Curso no encontrado.");
            return;
        }

        System.out.print("Fecha de matricula (ej. 25/06/2025): ");
        String fecha = scanner.nextLine();
        double monto = cursoSeleccionado.getPrecio();

        Matricula m = new Matricula(codigoCurso, dni, fecha, monto);
        matriculas.add(m);
        ArchivoUtil.guardarMatricula(m, "matriculas.txt");

        System.out.println("Matricula registrada para " + estudiante.getNombres() + " en el curso " + cursoSeleccionado.getNombre());
    }

    private void registrarCalificacion() {
        System.out.println("Registro de Calificaciones:");
        System.out.print("Ingrese el codigo del curso: ");
        String codigoCurso = scanner.nextLine();

        Curso cursoSeleccionado = null;
        for (Curso c : cursos) {
            if (c.getCodigo().equals(codigoCurso)) {
                cursoSeleccionado = c;
                break;
            }
        }

        if (cursoSeleccionado == null) {
            System.out.println("Curso no encontrado.");
            return;
        }

        System.out.print("Fecha del registro de calificacion: ");
        String fecha = scanner.nextLine();

        boolean encontrado = false;

        for (Matricula m : matriculas) {
            if (m.getCodigoCurso().equals(codigoCurso)) {
                String dniEst = m.getDniEstudiante();

                Estudiante est = null;
                for (Estudiante e : estudiantes) {
                    if (e.getDni().equals(dniEst)) {
                        est = e;
                        break;
                    }
                }

                if (est != null) {
                    System.out.println("Estudiante: " + est.getNombres() + " " + est.getApellidos());
                    System.out.print("Nota: ");
                    double nota = Double.parseDouble(scanner.nextLine());
                    System.out.print("Observaciones: ");
                    String obs = scanner.nextLine();

                    Calificacion c = new Calificacion(codigoCurso, dniEst, fecha, nota, obs);
                    calificaciones.add(c);
                    ArchivoUtil.guardarCalificacion(c, "calificaciones.txt");

                    System.out.println("Calificacion registrada.");
                    encontrado = true;
                }
            }
        }

        if (!encontrado) {
            System.out.println("No hay estudiantes matriculados en este curso.");
        }
    }
    private void modificarEstudiante() {
    System.out.print("Ingrese DNI del estudiante a modificar: ");
    String dni = scanner.nextLine();

    for (Estudiante e : estudiantes) {
        if (e.getDni().equals(dni)) {
            System.out.println("Estudiante encontrado:");
            System.out.println(e.mostrarInfo());

            System.out.println("Que desea modificar?");
            System.out.println("1. Direccion");
            System.out.println("2. Telefono");
            System.out.println("3. Nivel de estudios");
            System.out.print("Opcion: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("Nueva direccion: ");
                    String nuevaDireccion = scanner.nextLine();
                    e.setDireccion(nuevaDireccion);
                    break;
                case 2:
                    System.out.print("Nuevo telefono: ");
                    String nuevoTelefono = scanner.nextLine();
                    e.setTelefono(nuevoTelefono);
                    break;
                case 3:
                    System.out.print("Nuevo nivel de estudios: ");
                    String nuevoNivel = scanner.nextLine();
                    e.setNivelEstudios(nuevoNivel);
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }

            System.out.println("Datos actualizados (solo en memoria).");
            ArchivoUtil.sobrescribirEstudiantes(estudiantes, "estudiantes.txt");
            return;
        }
    }

    System.out.println("Estudiante no encontrado.");
}

private void modificarProfesor() {
    System.out.print("Ingrese DNI del profesor a modificar: ");
    String dni = scanner.nextLine();

    for (Profesor p : profesores) {
        if (p.getDni().equals(dni)) {
            System.out.println("Profesor encontrado:");
            System.out.println(p.mostrarInfo());

            System.out.println("Que desea modificar?");
            System.out.println("1. Especialidad");
            System.out.println("2. Años de experiencia");
            System.out.print("Opcion: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("Nueva especialidad: ");
                    String nuevaEsp = scanner.nextLine();
                    p.setEspecialidad(nuevaEsp);
                    break;
                case 2:
                    System.out.print("Nuevo numero de años de experiencia: ");
                    int nuevaExp = Integer.parseInt(scanner.nextLine());
                    p.setExperiencia(nuevaExp);
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }

            ArchivoUtil.sobrescribirProfesores(profesores, "profesores.txt");
            System.out.println("Datos actualizados.");
            return;
        }
    }

    System.out.println("Profesor no encontrado.");
}
private void modificarCurso() {
    System.out.print("Ingrese codigo del curso a modificar: ");
    String codigo = scanner.nextLine();

    for (Curso c : cursos) {
        if (c.getCodigo().equals(codigo)) {
            System.out.println("Curso encontrado:");
            System.out.println(c.mostrarInfo());

            System.out.println("Que desea modificar?");
            System.out.println("1. Horario");
            System.out.println("2. Capacidad maxima");
            System.out.println("3. Observaciones");
            System.out.print("Opcion: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("Nuevo horario: ");
                    String horario = scanner.nextLine();
                    c.setHorario(horario);
                    break;
                case 2:
                    System.out.print("Nueva capacidad maxima: ");
                    int capacidad = Integer.parseInt(scanner.nextLine());
                    c.setCapacidadMaxima(capacidad);
                    break;
                case 3:
                    System.out.print("Nuevas observaciones: ");
                    String obs = scanner.nextLine();
                    c.setObservaciones(obs);
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }

            ArchivoUtil.sobrescribirCursos(cursos, "cursos.txt");
            System.out.println("Datos del curso actualizados.");
            return;
        }
    }

    System.out.println("Curso no encontrado.");
}
private void registrarNivelIdioma() {
    System.out.println("Registro de Nivel de Idioma:");
    System.out.print("Codigo: ");
    String codigo = scanner.nextLine();
    System.out.print("Idioma: ");
    String idioma = scanner.nextLine();
    System.out.print("Nivel: ");
    String nivel = scanner.nextLine();
    System.out.print("Descripcion: ");
    String descripcion = scanner.nextLine();

    IdiomaNivel in = new IdiomaNivel(codigo, idioma, nivel, descripcion);
    nivelesIdioma.add(in);
    ArchivoUtil.guardarNivelIdioma(in, "idiomas.txt");

    System.out.println("Nivel de idioma registrado.");
}

private void modificarNivelIdioma() {
    System.out.print("Ingrese codigo del nivel de idioma a modificar: ");
    String codigo = scanner.nextLine();

    for (IdiomaNivel in : nivelesIdioma) {
        if (in.getCodigo().equals(codigo)) {
            System.out.println("Nivel encontrado:");
            System.out.println(in.mostrarInfo());

            System.out.println("Que desea modificar?");
            System.out.println("1. Nivel");
            System.out.println("2. Descripcion");
            System.out.print("Opcion: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("Nuevo nivel: ");
                    String nuevoNivel = scanner.nextLine();
                    in.setNivel(nuevoNivel);
                    break;
                case 2:
                    System.out.print("Nueva descripcion: ");
                    String nuevaDesc = scanner.nextLine();
                    in.setDescripcion(nuevaDesc);
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }

            ArchivoUtil.sobrescribirNivelesIdioma(nivelesIdioma, "idiomas.txt");
            System.out.println("Nivel actualizado.");
            return;
        }
    }

    System.out.println("Nivel no encontrado.");
}
private void buscarProfesor() {
    System.out.print("Ingrese DNI del profesor: ");
    String dni = scanner.nextLine();

    for (Profesor p : profesores) {
        if (p.getDni().equals(dni)) {
            System.out.println("Profesor encontrado:");
            System.out.println(p.mostrarInfo());
            return;
        }
    }

    System.out.println("Profesor no encontrado.");
}
private void buscarCurso() {
    System.out.print("Ingrese codigo del curso: ");
    String codigo = scanner.nextLine();

    for (Curso c : cursos) {
        if (c.getCodigo().equals(codigo)) {
            System.out.println("Curso encontrado:");
            System.out.println(c.mostrarInfo());
            return;
        }
    }

    System.out.println("Curso no encontrado.");
}
private void buscarNivelIdioma() {
    System.out.print("Ingrese codigo del nivel de idioma: ");
    String codigo = scanner.nextLine();

    for (IdiomaNivel in : nivelesIdioma) {
        if (in.getCodigo().equals(codigo)) {
            System.out.println("Nivel encontrado:");
            System.out.println(in.mostrarInfo());
            return;
        }
    }

    System.out.println("Nivel no encontrado.");
}
private void eliminarProfesor() {
    System.out.print("Ingrese DNI del profesor a eliminar: ");
    String dni = scanner.nextLine();

    for (int i = 0; i < profesores.size(); i++) {
        if (profesores.get(i).getDni().equals(dni)) {
            profesores.remove(i);
            ArchivoUtil.sobrescribirProfesores(profesores, "profesores.txt");
            System.out.println("Profesor eliminado.");
            return;
        }
    }

    System.out.println("Profesor no encontrado.");
}
private void eliminarCurso() {
    System.out.print("Ingrese codigo del curso a eliminar: ");
    String codigo = scanner.nextLine();

    for (int i = 0; i < cursos.size(); i++) {
        if (cursos.get(i).getCodigo().equals(codigo)) {
            cursos.remove(i);
            ArchivoUtil.sobrescribirCursos(cursos, "cursos.txt");
            System.out.println("Curso eliminado.");
            return;
        }
    }

    System.out.println("Curso no encontrado.");
}
private void eliminarNivelIdioma() {
    System.out.print("Ingrese codigo del nivel de idioma a eliminar: ");
    String codigo = scanner.nextLine();

    for (int i = 0; i < nivelesIdioma.size(); i++) {
        if (nivelesIdioma.get(i).getCodigo().equals(codigo)) {
            nivelesIdioma.remove(i);
            ArchivoUtil.sobrescribirNivelesIdioma(nivelesIdioma, "idiomas.txt");
            System.out.println("Nivel de idioma eliminado.");
            return;
        }
    }

    System.out.println("Nivel de idioma no encontrado.");
}
private void eliminarEstudiante() {
    System.out.print("Ingrese DNI del estudiante a eliminar: ");
    String dni = scanner.nextLine();

    for (int i = 0; i < estudiantes.size(); i++) {
        if (estudiantes.get(i).getDni().equals(dni)) {
            estudiantes.remove(i);
            ArchivoUtil.sobrescribirEstudiantes(estudiantes, "estudiantes.txt");
            System.out.println("Estudiante eliminado correctamente.");
            return;
        }
    }

    System.out.println("Estudiante no encontrado.");
}
private void generarReporteEstudiantesHTML() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_estudiantes.html"))) {
        bw.write("<html><head><title>Reporte de Estudiantes</title><style>");
        bw.write("table { width: 100%; border-collapse: collapse; } th, td { border: 1px solid black; padding: 8px; }");
        bw.write("</style></head><body><h1>Estudiantes Registrados</h1><table>");
        bw.write("<tr><th>DNI</th><th>Nombres</th><th>Apellidos</th><th>Dirección</th><th>Teléfono</th><th>Correo</th><th>Fecha Nac.</th><th>Nivel</th></tr>");
        for (Estudiante e : estudiantes) {
            bw.write("<tr><td>" + e.getDni() + "</td><td>" + e.getNombres() + "</td><td>" + e.getApellidos() +
                     "</td><td>" + e.getDireccion() + "</td><td>" + e.getTelefono() + "</td><td>" + e.getCorreo() +
                     "</td><td>" + e.getFechaNacimiento() + "</td><td>" + e.getNivelEstudios() + "</td></tr>");
        }
        bw.write("</table></body></html>");
        System.out.println("Reporte generado: reporte_estudiantes.html");
    } catch (IOException e) {
        System.out.println("Error al generar reporte de estudiantes: " + e.getMessage());
    }
}
private void generarReporteProfesoresHTML() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_profesores.html"))) {
        bw.write("<html><head><title>Reporte de Profesores</title><style>");
        bw.write("table { width: 100%; border-collapse: collapse; } th, td { border: 1px solid black; padding: 8px; }");
        bw.write("</style></head><body><h1>Profesores Registrados</h1><table>");
        bw.write("<tr><th>DNI</th><th>Nombres</th><th>Apellidos</th><th>Dirección</th><th>Teléfono</th><th>Correo</th><th>Especialidad</th><th>Experiencia</th></tr>");
        for (Profesor p : profesores) {
            bw.write("<tr><td>" + p.getDni() + "</td><td>" + p.getNombres() + "</td><td>" + p.getApellidos() +
                     "</td><td>" + p.getDireccion() + "</td><td>" + p.getTelefono() + "</td><td>" + p.getCorreo() +
                     "</td><td>" + p.getEspecialidad() + "</td><td>" + p.getExperiencia() + "</td></tr>");
        }
        bw.write("</table></body></html>");
        System.out.println("Reporte generado: reporte_profesores.html");
    } catch (IOException e) {
        System.out.println("Error al generar reporte de profesores: " + e.getMessage());
    }
}
private void generarReporteCursosHTML() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_cursos.html"))) {
        bw.write("<html><head><title>Reporte de Cursos</title><style>");
        bw.write("table { width: 100%; border-collapse: collapse; } th, td { border: 1px solid black; padding: 8px; }");
        bw.write("</style></head><body><h1>Cursos Registrados</h1><table>");
        bw.write("<tr><th>Código</th><th>Nombre</th><th>Idioma</th><th>Nivel</th><th>Profesor</th><th>Horario</th><th>Duración</th><th>Capacidad</th><th>Precio</th><th>Observaciones</th></tr>");
        for (Curso c : cursos) {
            bw.write("<tr><td>" + c.getCodigo() + "</td><td>" + c.getNombre() + "</td><td>" + c.getIdioma() + "</td><td>" + c.getNivel() + "</td><td>" +
                     c.getProfesorDni() + "</td><td>" + c.getHorario() + "</td><td>" + c.getDuracion() + "</td><td>" + c.getCapacidadMaxima() +
                     "</td><td>" + c.getPrecio() + "</td><td>" + c.getObservaciones() + "</td></tr>");
        }
        bw.write("</table></body></html>");
        System.out.println("Reporte generado: reporte_cursos.html");
    } catch (IOException e) {
        System.out.println("Error al generar reporte de cursos: " + e.getMessage());
    }
}
private void generarReporteMatriculasHTML() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_matriculas.html"))) {
        bw.write("<html><head><title>Reporte de Matriculas</title><style>");
        bw.write("table { width: 100%; border-collapse: collapse; } th, td { border: 1px solid black; padding: 8px; }");
        bw.write("</style></head><body><h1>Matrículas Registradas</h1><table>");
        bw.write("<tr><th>DNI Estudiante</th><th>Código Curso</th><th>Fecha</th><th>Monto Pagado</th></tr>");
        for (Matricula m : matriculas) {
            bw.write("<tr><td>" + m.getDniEstudiante() + "</td><td>" + m.getCodigoCurso() + "</td><td>" + m.getFechaMatricula()+ "</td><td>" + m.getMonto() + "</td></tr>");
        }
        bw.write("</table></body></html>");
        System.out.println("Reporte generado: reporte_matriculas.html");
    } catch (IOException e) {
        System.out.println("Error al generar reporte de matrículas: " + e.getMessage());
    }
}
private void generarReporteCalificacionesHTML() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_calificaciones.html"))) {
        bw.write("<html><head><title>Reporte de Calificaciones</title><style>");
        bw.write("table { width: 100%; border-collapse: collapse; } th, td { border: 1px solid black; padding: 8px; }");
        bw.write("</style></head><body><h1>Calificaciones Registradas</h1><table>");
        bw.write("<tr><th>Código Curso</th><th>DNI Estudiante</th><th>Fecha</th><th>Nota</th><th>Observaciones</th></tr>");
        for (Calificacion c : calificaciones) {
            bw.write("<tr><td>" + c.getCodigoCurso() + "</td><td>" + c.getDniEstudiante() + "</td><td>" + c.getFecha() + "</td><td>" + c.getNota() + "</td><td>" + c.getObservaciones() + "</td></tr>");
        }
        bw.write("</table></body></html>");
        System.out.println("Reporte generado: reporte_calificaciones.html");
    } catch (IOException e) {
        System.out.println("Error al generar reporte de calificaciones: " + e.getMessage());
    }
}
private void generarReporteNivelesIdiomaHTML() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_niveles_idioma.html"))) {
        bw.write("<html><head><title>Reporte de Niveles de Idioma</title><style>");
        bw.write("table { width: 100%; border-collapse: collapse; } th, td { border: 1px solid black; padding: 8px; }");
        bw.write("</style></head><body><h1>Niveles de Idioma Registrados</h1><table>");
        bw.write("<tr><th>Idioma</th><th>Nivel</th><th>Descripción</th></tr>");
        for (IdiomaNivel n : nivelesIdioma) {
            bw.write("<tr><td>" + n.getIdioma() + "</td><td>" + n.getNivel() + "</td><td>" + n.getDescripcion() + "</td></tr>");
        }
        bw.write("</table></body></html>");
        System.out.println("Reporte generado: reporte_niveles_idioma.html");
    } catch (IOException e) {
        System.out.println("Error al generar reporte de niveles de idioma: " + e.getMessage());
    }
}
}

