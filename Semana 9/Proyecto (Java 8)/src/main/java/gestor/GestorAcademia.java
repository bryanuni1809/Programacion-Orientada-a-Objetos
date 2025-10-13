/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;

import entidades.Calificacion;
import entidades.Curso;
import entidades.Estudiante;
import entidades.ExternalSort;
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
import interfaces.IEntidad;
import interfaces.IValidable;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.BusquedaExterna;
import util.Validador;

/**
 *
 * @author BRYAN
 */
public class GestorAcademia{
    private final Scanner scanner = new Scanner(System.in);
    private final Map<String, Estudiante> estudiantes = new HashMap<>();
    private final Map<String, Profesor> profesores = new HashMap<>(); 
    private final Map<String, Curso> cursos = new HashMap<>(); 
    private final ArrayList<Matricula> matriculas = new ArrayList<>();
    private final ArrayList<Calificacion> calificaciones = new ArrayList<>();
    private final Map<String, IdiomaNivel> nivelesIdioma = new HashMap<>();
    
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
                cursos.put(c.getCodigo(), c);
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
                profesores.put(p.getDni(), p);
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
                nivelesIdioma.put(in.getCodigo(), in);
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
            estudiantes.put(e.getDni(), e);
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
    private void cargarCalificaciones(){
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
    } catch(IOException e){
        System.out.println("Error al cargar calificaciones: " + e.getMessage());
    }
}
    public void mostrarMenu(){
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
        System.out.println("8. Búsqueda Interna");
        System.out.println("9. Búsqueda Externa");
        System.out.println("10. Validar Todas las Entidades");
        System.out.println("11. Mostrar Todas las Entidades");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(scanner.nextLine());

        switch(opcion){
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
                menuOrdenamientos();
                break;
            case 8:
                menuBusquedaInterna();
                break;
            case 9:
                menuBusquedaExterna();
                break;
            case 10:
                validarTodasLasEntidades();
                break;
            case 11:
                mostrarTodasLasEntidades();
                break;
            case 0:
                System.out.println("Cerrando sesion...");
                break;
            default:
                System.out.println("Opcion invalida.");
                break;
        }
    } while (opcion !=0);
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

        switch (opcion){
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
    private void menuCursos(){
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
    } while (opcion !=0);
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
    } while (opcion !=0);
}

private void menuNivelesIdioma(){
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
    } while(opcion != 0);
}

private void mostrarMenuReportesHTML(){
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

        switch(opcion){
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
 private void registrarEstudiante(){
         try {
        System.out.println("\nREGISTRO DE NUEVO ESTUDIANTE");
        String dni = leerDNI();
        if (estudiantes.containsKey(dni)) {
            System.out.println("Ya existe un estudiante registrado con este DNI.");
            return;
        }
        
        System.out.print("Nombres: ");
        String nombres = Validador.formatearTexto(scanner.nextLine());
        Validador.validarSoloLetras(nombres, "nombres");
        
        System.out.print("Apellidos: ");
        String apellidos = Validador.formatearTexto(scanner.nextLine());
        Validador.validarSoloLetras(apellidos, "apellidos");
        
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine().trim();
        Validador.validarNoVacio(direccion, "dirección");
        
        String telefono = leerTelefono();
        String correo = leerEmail();
        
        String fechaNacimiento = leerFecha("Fecha de nacimiento (dd/MM/yyyy): ");
        Validador.validarEdadEstudiante(fechaNacimiento, 12, 80);
        
        System.out.print("Nivel de estudios: ");
        String nivelEstudios = scanner.nextLine().trim();
        Validador.validarNoVacio(nivelEstudios, "nivel de estudios");
       
        Validador.validarDatosEstudiante(dni, nombres, apellidos, direccion, telefono, correo, fechaNacimiento, nivelEstudios);
        
        Estudiante e=new Estudiante(dni, nombres, apellidos, direccion, telefono, correo, fechaNacimiento, nivelEstudios);
            if (!e.validar()) {
            System.out.println("Error de validación: " + e.getMensajeError());
            return;
        }
        estudiantes.put(e.getDni(), e);
        ArchivoUtil.guardarEstudiante(e, "estudiantes.txt");
        System.out.println("Estudiante registrado y validado exitosamente!");
    } catch (IllegalArgumentException e) {
        System.out.println("Error de validación: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("Error inesperado: " + e.getMessage());
    }
}

    private void buscarEstudiante() {
        System.out.print("Ingrese DNI del estudiante: ");
        String dni = scanner.nextLine();
        Estudiante e = estudiantes.get(dni);
            if (e != null) {
        System.out.println("Estudiante encontrado: " + e.mostrarInfo());
            } else {
    System.out.println("Estudiante no encontrado.");
        }
    }

    private void registrarProfesor() {
        try {
        System.out.println("\nREGISTRO DE NUEVO PROFESOR");
        String dni = leerDNI();
        System.out.print("Nombres: ");
        String nombres = Validador.formatearTexto(scanner.nextLine());
        Validador.validarSoloLetras(nombres, "nombres");
        
        System.out.print("Apellidos: ");
        String apellidos = Validador.formatearTexto(scanner.nextLine());
        Validador.validarSoloLetras(apellidos, "apellidos");
        
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine().trim();
        Validador.validarNoVacio(direccion, "dirección");
        
        String telefono = leerTelefono();
        String correo = leerEmail();
        
        System.out.print("Especialidad: ");
        String especialidad = Validador.formatearTexto(scanner.nextLine());
        Validador.validarSoloLetras(especialidad, "especialidad");
        
        int experiencia = leerEnteroValidado("Años de experiencia: ",0,50);
        
        // Validación completa
        Validador.validarDatosProfesor(dni, nombres, apellidos, direccion, telefono, correo, especialidad, experiencia);
        
        Profesor p = new Profesor(dni, nombres, apellidos, direccion, telefono, correo, especialidad, experiencia);
            if (!p.validar()) {
            System.out.println("Error de validación: " + p.getMensajeError());
            return;
        }
        profesores.put(dni, p);
        ArchivoUtil.guardarProfesor(p, "profesores.txt");
        
        System.out.println("Profesor registrado y validado exitosamente!");
        
    } catch (IllegalArgumentException e) {
        System.out.println("Error de validación: " + e.getMessage());
    }
}
    private void registrarCurso() {
        try {
        System.out.println("\nREGISTRO DE NUEVO CURSO");
        
        System.out.print("Código del curso (Formato: XXX-999): ");
        String codigo = Validador.formatearCodigoCurso(scanner.nextLine());
        Validador.validarCodigoCurso(codigo);
        
        System.out.print("Nombre del curso: ");
        String nombre = scanner.nextLine().trim();
        Validador.validarNoVacio(nombre, "nombre del curso");
        
        System.out.print("Idioma: ");
        String idioma = Validador.formatearTexto(scanner.nextLine());
        Validador.validarIdioma(idioma);
        
        System.out.print("Nivel: ");
        String nivel = scanner.nextLine().trim();
        Validador.validarNivelIdioma(nivel);
        
        System.out.print("DNI del profesor: ");
        String dniProfesor = scanner.nextLine().trim();
        Validador.validarDNI(dniProfesor);
        
        System.out.print("Horario: ");
        String horario = scanner.nextLine().trim();
        Validador.validarNoVacio(horario, "horario");
        
        int duracion = leerEnteroValidado("Duración (en semanas): ", 1, 52);
        int capacidad = leerEnteroValidado("Capacidad máxima: ", 1, 50);
        double precio = leerDoubleValidado("Precio: S/", 0, 10000);
        
        System.out.print("Observaciones: ");
        String obs = scanner.nextLine().trim();
        Validador.validarNoVacio(obs, "observaciones");
        Validador.validarDatosCurso(codigo, nombre, idioma, nivel, dniProfesor, horario, duracion, capacidad, precio, obs);
        
        Curso c = new Curso(codigo, nombre, idioma, nivel, dniProfesor, horario, duracion, capacidad, precio, obs);
            if (!c.validar())   {
            System.out.println("Error de validación: " + c.getMensajeError());
            return;
        }
        cursos.put(codigo, c);
        ArchivoUtil.guardarCurso(c, "cursos.txt");
        
        System.out.println("Curso registrado y validado exitosamente!");
        
    } catch (IllegalArgumentException e) {
        System.out.println("Error de validación: " + e.getMessage());
    }
}
   private void registrarMatricula(){
        System.out.println("Registro de Matricula:");
        System.out.print("DNI del estudiante: ");
        String dni =scanner.nextLine();
        Estudiante estudiante =estudiantes.get(dni);
        if (estudiante == null){
            System.out.println("Estudiante no encontrado. Primero debe registrarlo.");
        return;
        }
        if (cursos.isEmpty()){
            System.out.println("No hay cursos disponibles. Primero registre cursos.");
            return;
        }
        System.out.println("Cursos disponibles:");
        for (Curso c : cursos.values()) {
            System.out.println("- " +c.getCodigo()+ ": " +c.getNombre());
        }
        System.out.print("Ingrese el codigo del curso: ");
        String codigoCurso =scanner.nextLine();
        Curso cursoSeleccionado=null;
        for (Curso c:cursos.values()){
            if (c.getCodigo().equals(codigoCurso)) {
                cursoSeleccionado=c;
                break;
            }
        }

        if (cursoSeleccionado==null){
            System.out.println("Curso no encontrado.");
            return;
        }

        System.out.print("Fecha de matricula (ej. 25/06/2025): ");
        String fecha=scanner.nextLine();
        double monto=cursoSeleccionado.getPrecio();
        Matricula m=new Matricula(codigoCurso, dni, fecha, monto);
        if (!m.validar()) {
            System.out.println("Error de validación: " + m.getMensajeError());
            return;
        }
        matriculas.add(m);
        ArchivoUtil.guardarMatricula(m,"matriculas.txt");
        System.out.println("Matricula registrada para "+estudiante.getNombres()+" en el curso "+cursoSeleccionado.getNombre());
    }

    private void registrarCalificacion(){
        try {
        System.out.println("Registro de Calificaciones:");
        System.out.print("Ingrese el código del curso: ");
        String codigoCurso = scanner.nextLine().trim();
        Validador.validarCodigoCurso(codigoCurso);

        Curso cursoSeleccionado = null;
        for (Curso c : cursos.values()) {
            if (c.getCodigo().equals(codigoCurso)) {
                cursoSeleccionado = c;
                break;
            }
        }
        
        if (cursoSeleccionado == null) {
            System.out.println("Curso no encontrado.");
            return;
        }

        String fecha = leerFecha("Fecha del registro de calificación (dd/MM/yyyy): ");

        boolean encontrado = false;
        for (Matricula m : matriculas) {
            if (m.getCodigoCurso().equals(codigoCurso)) {
                String dniEst = m.getDniEstudiante();
                Estudiante est = estudiantes.get(dniEst);   

                if (est != null) {
                    System.out.println("Estudiante: " + est.getNombres() + " " + est.getApellidos());
                    
                    double nota = leerDoubleValidado("Nota (0-20): ", 0, 20);
                    Validador.validarNota(nota);
                    
                    System.out.print("Observaciones: ");
                    
                    String obs = scanner.nextLine().trim();
                    Validador.validarNoVacio(obs, "observaciones");

                    Calificacion c = new Calificacion(codigoCurso, dniEst, fecha, nota, obs);
                    if (!c.validar()) {
                        System.out.println("Error de validación para " + est.getNombres() + ": " + c.getMensajeError());
                        continue;
                    }
                    calificaciones.add(c);
                    ArchivoUtil.guardarCalificacion(c, "calificaciones.txt");

                    System.out.println("Calificación registrada y validada.");
                    encontrado = true;
                }
            }
        }

        if (!encontrado) {
            System.out.println("No hay estudiantes matriculados en este curso.");
        }

    } catch (IllegalArgumentException e) {
        System.out.println("Error de validación: " + e.getMessage());
    }
}
    private void modificarEstudiante() {
    System.out.print("Ingrese DNI del estudiante a modificar: ");
    String dni = scanner.nextLine();

    Estudiante e = estudiantes.get(dni);
        if (e != null){
            System.out.println("Estudiante encontrado:");
            System.out.println(e.mostrarInfo());

            System.out.println("Que desea modificar?");
            System.out.println("1. Direccion");
            System.out.println("2. Telefono");
            System.out.println("3. Nivel de estudios");
            System.out.print("Opcion: ");
            int opcion=Integer.parseInt(scanner.nextLine());

            switch(opcion){
                case 1:
                    System.out.print("Nueva direccion: ");
                    String nuevaDireccion=scanner.nextLine();
                    e.setDireccion(nuevaDireccion);
                    break;
                case 2:
                    System.out.print("Nuevo telefono: ");
                    String nuevoTelefono=scanner.nextLine();
                    e.setTelefono(nuevoTelefono);
                    break;
                case 3:
                    System.out.print("Nuevo nivel de estudios: ");
                    String nuevoNivel=scanner.nextLine();
                    e.setNivelEstudios(nuevoNivel);
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }
            if (!e.validar()) {
            System.out.println("Error: Los datos modificados no son válidos: " + e.getMensajeError());
            return;
            }
            ArchivoUtil.sobrescribirEstudiantes(new ArrayList<>(estudiantes.values()), "estudiantes.txt");
            System.out.println("Datos actualizados (solo en memoria).");
        }else{
        System.out.println("Estudiante no encontrado.");
        }
}

private void modificarProfesor(){
    System.out.print("Ingrese DNI del profesor a modificar: ");
    String dni=scanner.nextLine();
    Profesor p=profesores.get(dni);
        if (p.getDni().equals(dni)){
            System.out.println("Profesor encontrado:");
            System.out.println(p.mostrarInfo());

            System.out.println("Que desea modificar?");
            System.out.println("1. Especialidad");
            System.out.println("2. Años de experiencia");
            System.out.print("Opcion: ");
            int opcion=Integer.parseInt(scanner.nextLine());

            switch (opcion){
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
            if (!p.validar()) {
            System.out.println("Error: Los datos modificados no son válidos: " + p.getMensajeError());
            return;
            }
            ArchivoUtil.sobrescribirProfesores(new ArrayList<>(profesores.values()), "profesores.txt");
            System.out.println("Datos actualizados.");
        }else{
        System.out.println("Profesor no encontrado.");
    }
}
private void modificarCurso(){
    System.out.print("Ingrese codigo del curso a modificar: ");
    String codigo =scanner.nextLine();
    Curso c=cursos.get(codigo);
        if(c.getCodigo().equals(codigo)){
            System.out.println("Curso encontrado:");
            System.out.println(c.mostrarInfo());
            System.out.println("Que desea modificar?");
            System.out.println("1. Horario");
            System.out.println("2. Capacidad maxima");
            System.out.println("3. Observaciones");
            System.out.print("Opcion: ");
            int opcion=Integer.parseInt(scanner.nextLine());

            switch(opcion){
                case 1:
                    System.out.print("Nuevo horario: ");
                    String horario=scanner.nextLine();
                    c.setHorario(horario);
                    break;
                case 2:
                    System.out.print("Nueva capacidad maxima: ");
                    int capacidad=Integer.parseInt(scanner.nextLine());
                    c.setCapacidadMaxima(capacidad);
                    break;
                case 3:
                    System.out.print("Nuevas observaciones: ");
                    String obs=scanner.nextLine();
                    c.setObservaciones(obs);
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }
            if (!c.validar()) {
            System.out.println("Error: Los datos modificados no son válidos: " + c.getMensajeError());
            return;
            }
            ArchivoUtil.sobrescribirCursos(new ArrayList<>(cursos.values()), "cursos.txt");
            System.out.println("Datos del curso actualizados.");
        }else{
            System.out.println("Curso no encontrado.");
    }
}
private void registrarNivelIdioma(){
    System.out.println("Registro de Nivel de Idioma:");
    System.out.print("Codigo: ");
    String codigo=scanner.nextLine();
    if (nivelesIdioma.containsKey(codigo)) {
            System.out.println("Ya existe un nivel de idioma con este código.");
            return;
        }
    System.out.print("Idioma: ");
    String idioma =scanner.nextLine();
    System.out.print("Nivel: ");
    String nivel=scanner.nextLine();
    System.out.print("Descripcion: ");
    String descripcion =scanner.nextLine();

    IdiomaNivel in =new IdiomaNivel(codigo, idioma, nivel, descripcion);
    if (!in.validar()) {
            System.out.println("Error de validación: " + in.getMensajeError());
            return;
        }
    nivelesIdioma.put(codigo, in);
    ArchivoUtil.guardarNivelIdioma(in,"idiomas.txt");

    System.out.println("Nivel de idioma registrado.");
}

private void modificarNivelIdioma(){
    System.out.print("Ingrese codigo del nivel de idioma a modificar: ");
    String codigo=scanner.nextLine();
    IdiomaNivel in=nivelesIdioma.get(codigo);
        if(in.getCodigo().equals(codigo)){
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
            if (!in.validar()) {
            System.out.println("Error: Los datos modificados no son válidos: " + in.getMensajeError());
            return;
            }
            ArchivoUtil.sobrescribirNivelesIdioma(new ArrayList<>(nivelesIdioma.values()), "idiomas.txt");
            System.out.println("Nivel actualizado.");
        }else{
        System.out.println("Nivel no encontrado.");    
    }
}
private void buscarProfesor(){
    System.out.print("Ingrese DNI del profesor: ");
    String dni = scanner.nextLine();
    Profesor p=profesores.get(dni);
        if (p !=null){
            System.out.println("Profesor encontrado:");
            System.out.println(p.mostrarInfo());
        }else{
    System.out.println("Profesor no encontrado.");
    }
}
private void buscarCurso(){
    System.out.print("Ingrese codigo del curso: ");
    String codigo=scanner.nextLine();
    Curso c=cursos.get(codigo);
        if (c !=null){
            System.out.println("Curso encontrado:");
            System.out.println(c.mostrarInfo());
        }else{
        System.out.println("Curso no encontrado.");
        }
    }
        
private void buscarNivelIdioma(){
    System.out.print("Ingrese codigo del nivel de idioma: ");
    String codigo = scanner.nextLine();
    IdiomaNivel in=nivelesIdioma.get(codigo);
        if (in !=null){
            System.out.println("Nivel encontrado:");
            System.out.println(in.mostrarInfo());
        }else{
            System.out.println("Nivel no encontrado.");
        }   
}
private void eliminarProfesor(){
    System.out.print("Ingrese DNI del profesor a eliminar: ");
    String dni=scanner.nextLine();
    if (profesores.remove(dni) != null){
            ArchivoUtil.sobrescribirProfesores(new ArrayList<>(profesores.values()), "profesores.txt");
            System.out.println("Profesor eliminado.");
        }else{
        System.out.println("Profesor no encontrado.");
    }
}
private void eliminarCurso(){
    System.out.print("Ingrese codigo del curso a eliminar: ");
    String codigo = scanner.nextLine();
        if (cursos.remove(codigo) !=null){
            ArchivoUtil.sobrescribirCursos(new ArrayList<>(cursos.values()), "cursos.txt");
            System.out.println("Curso eliminado.");
        }else{
        System.out.println("Curso no encontrado.");    
    }
}
private void eliminarNivelIdioma(){
    System.out.print("Ingrese codigo del nivel de idioma a eliminar: ");
    String codigo = scanner.nextLine();
        if (nivelesIdioma.remove(codigo) !=null) {
            ArchivoUtil.sobrescribirNivelesIdioma(new ArrayList<>(nivelesIdioma.values()), "idiomas.txt");
            System.out.println("Nivel de idioma eliminado.");
        }else{
        System.out.println("Nivel de idioma no encontrado.");    
    }   
}
private void eliminarEstudiante() {
    System.out.print("Ingrese DNI del estudiante a eliminar: ");
    String dni = scanner.nextLine();

    for (int i = 0; i < estudiantes.size(); i++) {
        if (estudiantes.containsKey(dni)) {
            estudiantes.remove(dni);
            ArchivoUtil.sobrescribirEstudiantes(new ArrayList<>(estudiantes.values()), "estudiantes.txt");
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
        for (Estudiante e : estudiantes.values()) {
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
        for (Profesor p : profesores.values()) {
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
        for (Curso c : cursos.values()) {
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
        for (Matricula m :matriculas) {
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
        for (Calificacion c :calificaciones) {
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
        for (IdiomaNivel n :nivelesIdioma.values()) {
            bw.write("<tr><td>" + n.getIdioma() + "</td><td>" + n.getNivel() + "</td><td>" + n.getDescripcion() + "</td></tr>");
        }
        bw.write("</table></body></html>");
        System.out.println("Reporte generado: reporte_niveles_idioma.html");
    } catch (IOException e) {
        System.out.println("Error al generar reporte de niveles de idioma: " + e.getMessage());
    }
}
private void menuOrdenamientos() {
    int opcion;
    do{
        System.out.println("\n=== SISTEMA DE ORDENAMIENTO - MERGE SORT ===");
        System.out.println("1. Ordenar Estudiantes");
        System.out.println("2. Ordenar Profesores");
        System.out.println("3. Ordenar Cursos");
        System.out.println("4. Ordenación Externa (Archivos Grandes)");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        opcion =Integer.parseInt(scanner.nextLine());

        switch (opcion){
            case 1:
                ordenarEstudiantes();
                break;
            case 2:
                ordenarProfesores();
                break;
            case 3:
                ordenarCursos();
                break;
            case 4:
                ordenacionExterna();
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida.");
        }
    } while (opcion != 0);
}

private void ordenarEstudiantes() {
    if (estudiantes.isEmpty()) {
        System.out.println("No hay estudiantes registrados.");
        return;
    }
    
    List<Estudiante> listaEstudiantes = new ArrayList<>(estudiantes.values());
    
    System.out.println("\n=== ORDENAR ESTUDIANTES - MERGE SORT ===");
    System.out.println("1. Por Apellidos (A-Z)");
    System.out.println("2. Por Nombres (A-Z)");
    System.out.println("3. Por DNI (Ascendente)");
    System.out.print("Seleccione criterio: ");
    int criterio = Integer.parseInt(scanner.nextLine());
    
    long startTime = System.currentTimeMillis();
    
    switch (criterio) {
        case 1:
            Ordenaciones.ordenar(listaEstudiantes, Comparator.comparing(Estudiante::getApellidos));
            System.out.println("Estudiantes ordenados por APELLIDOS");
            break;
        case 2:
            Ordenaciones.ordenar(listaEstudiantes, Comparator.comparing(Estudiante::getNombres));
            System.out.println("Estudiantes ordenados por NOMBRES");
            break;
        case 3:
            Ordenaciones.ordenar(listaEstudiantes, Comparator.comparing(Estudiante::getDni));
            System.out.println("Estudiantes ordenados por DNI");
            break;
        default:
            System.out.println("Criterio inválido.");
            return;
    }
    
    long endTime = System.currentTimeMillis();
    
    System.out.println("Tiempo de ordenamiento: " + (endTime - startTime) + " ms");
    System.out.println("Total de elementos ordenados: " + listaEstudiantes.size());
    System.out.println("\n=== RESULTADOS ORDENADOS ===");
    for (int i = 0; i < listaEstudiantes.size(); i++) {
        System.out.println((i + 1) + ". " + listaEstudiantes.get(i).mostrarInfo());
    }
}

private void ordenarProfesores() {
    if (profesores.isEmpty()) {
        System.out.println("No hay profesores registrados.");
        return;
    }
    
    List<Profesor> listaProfesores = new ArrayList<>(profesores.values());
    
    System.out.println("\n=== ORDENAR PROFESORES - MERGE SORT ===");
    System.out.println("1. Por Apellidos (A-Z)");
    System.out.println("2. Por Especialidad (A-Z)");
    System.out.println("3. Por Experiencia (Mayor a menor)");
    System.out.print("Seleccione criterio: ");
    int criterio =Integer.parseInt(scanner.nextLine());
    
    long startTime =System.currentTimeMillis();
    
    switch (criterio){
        case 1:
            Ordenaciones.ordenar(listaProfesores, Comparator.comparing(Profesor::getApellidos));
            System.out.println("Profesores ordenados por APELLIDOS");
            break;
        case 2:
            Ordenaciones.ordenar(listaProfesores, Comparator.comparing(Profesor::getEspecialidad));
            System.out.println("Profesores ordenados por ESPECIALIDAD");
            break;
        case 3:
            Ordenaciones.ordenar(listaProfesores, Comparator.comparingInt(Profesor::getExperiencia).reversed());
            System.out.println("Profesores ordenados por EXPERIENCIA (Mayor a menor)");
            break;
        default:
            System.out.println("Criterio inválido.");
            return;
    }
    
    long endTime = System.currentTimeMillis();
    System.out.println("Tiempo de ordenamiento: " + (endTime - startTime) + " ms");
    
    System.out.println("\n=== RESULTADOS ORDENADOS ===");
    for (int i = 0; i < listaProfesores.size(); i++) {
        System.out.println((i + 1) + ". " + listaProfesores.get(i).mostrarInfo());
    }
}

private void ordenarCursos(){
    if (cursos.isEmpty()) {
        System.out.println("No hay cursos registrados.");
        return;
    }
    List<Curso> listaCursos = new ArrayList<>(cursos.values());
    System.out.println("\n=== ORDENAR CURSOS - MERGE SORT ===");
    System.out.println("1. Por Nombre (A-Z)");
    System.out.println("2. Por Idioma (A-Z)");
    System.out.println("3. Por Precio (Menor a mayor)");
    System.out.print("Seleccione criterio: ");
    int criterio =Integer.parseInt(scanner.nextLine());
    
    long startTime =System.currentTimeMillis();
    
    switch (criterio){
        case 1:
            Ordenaciones.ordenar(listaCursos, Comparator.comparing(Curso::getNombre));
            System.out.println("Cursos ordenados por NOMBRE");
            break;
        case 2:
            Ordenaciones.ordenar(listaCursos, Comparator.comparing(Curso::getIdioma));
            System.out.println("Cursos ordenados por IDIOMA");
            break;
        case 3:
            Ordenaciones.ordenar(listaCursos, Comparator.comparingDouble(Curso::getPrecio));
            System.out.println("Cursos ordenados por PRECIO (Menor a mayor)");
            break;
        default:
            System.out.println("Criterio inválido.");
            return;
    }
    
    long endTime = System.currentTimeMillis();
    System.out.println("Tiempo de ordenamiento: " + (endTime - startTime) + " ms");
    System.out.println("\n=== RESULTADOS ORDENADOS ===");
    for (int i = 0; i < listaCursos.size(); i++) {
        System.out.println((i + 1) + ". " + listaCursos.get(i).mostrarInfo());
    }
}

private void ordenacionExterna(){
    System.out.println("\n=== ORDENACIÓN EXTERNA PARA ARCHIVOS GRANDES ===");
    System.out.println("1. Ordenar archivo de Estudiantes");
    System.out.println("2. Ordenar archivo de Profesores");
    System.out.print("Seleccione archivo: ");
    int archivo = Integer.parseInt(scanner.nextLine());
    
    try{
        File entrada,salida;
        switch (archivo){
            case 1:
                entrada =new File("estudiantes.txt");
                salida =new File("estudiantes_ordenados.txt");
                break;
            case 2:
                entrada =new File("profesores.txt");
                salida =new File("profesores_ordenados.txt");
                break;
            default:
                System.out.println("Opción inválida.");
                return;
        }
        if (!entrada.exists()) {
            System.out.println("El archivo " + entrada.getName() + " no existe.");
            return;
        }
        System.out.print("Ingrese tamaño de bloque (recomendado 100-1000): ");
        int tamanoBloque = Integer.parseInt(scanner.nextLine());
        
        long startTime = System.currentTimeMillis();
        ExternalSort.externalSort(entrada, salida, tamanoBloque);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Archivo ordenado generado: " + salida.getName());
        System.out.println("Tiempo total: " + (endTime - startTime) + " ms");
        
    }catch(IOException e){
        System.out.println("Error de E/S: " + e.getMessage());
    }catch(NumberFormatException e){
        System.out.println("Error en formato numérico: " + e.getMessage());
    }catch(IllegalArgumentException e){
        System.out.println("Error en parámetros: " + e.getMessage());
    }
}
private String leerDNI() {
    while (true) {
        try {
            System.out.print("DNI: ");
            String dni = scanner.nextLine().trim();
            Validador.validarDNI(dni);
            return dni;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Por favor, ingrese un DNI válido (8 dígitos).");
        }
    }
}

private String leerEmail() {
    while (true) {
        try {
            System.out.print("Correo electrónico: ");
            String email = scanner.nextLine().trim();
            Validador.validarEmail(email);
            return email;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

private String leerTelefono() {
    while (true) {
        try {
            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine().trim();
            Validador.validarTelefono(telefono);
            return telefono;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

private String leerFecha(String mensaje){
    while (true) {
        try {
            System.out.print(mensaje);
            String fecha = scanner.nextLine().trim();
            Validador.validarFecha(fecha, "fecha");
            return fecha;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

private int leerEnteroValidado(String mensaje, int min, int max){
    while (true) {
        try {
            System.out.print(mensaje);
            int valor = Integer.parseInt(scanner.nextLine().trim());
            Validador.validarRangoEntero(valor, min, max, "valor");
            return valor;
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número entero válido.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

private double leerDoubleValidado(String mensaje,double min,double max) {
    while (true) {
        try {
            System.out.print(mensaje);
            double valor = Double.parseDouble(scanner.nextLine().trim());
            Validador.validarRangoDouble(valor,min,max,"valor");
            return valor;
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
//polimorfismo
private void validarTodasLasEntidades(){
    System.out.println("\n=== VALIDACIÓN GENERAL DE ENTIDADES ===");
    List<IValidable> todasLasEntidades = new ArrayList<>();
    todasLasEntidades.addAll(estudiantes.values());
    todasLasEntidades.addAll(profesores.values());
    todasLasEntidades.addAll(cursos.values());
    todasLasEntidades.addAll(nivelesIdioma.values());
    todasLasEntidades.addAll(matriculas);
    todasLasEntidades.addAll(calificaciones);
    int validas=0, invalidas=0;
    Map<String, Integer> invalidosPorTipo = new HashMap<>();
    for(IValidable entidad : todasLasEntidades) {
        if (entidad.validar()) {
            validas++;
        } else {
            invalidas++;
            if (entidad instanceof IEntidad) {
                IEntidad entidadConTipo = (IEntidad) entidad;
                String tipo = entidadConTipo.getTipo();
                System.out.println("" + tipo + " inválido: " + entidad.getMensajeError());
                invalidosPorTipo.put(tipo, invalidosPorTipo.getOrDefault(tipo, 0) + 1);
            }
        }
    }
    
    System.out.println("\nResumen de validación:");
    System.out.println("Entidades válidas: "+validas);
    System.out.println("Entidades inválidas: " +invalidas);
    System.out.println("Total: " +todasLasEntidades.size());
    if (!invalidosPorTipo.isEmpty()) {
        System.out.println("\nDetalle de entidades inválidas por tipo:");
        for (Map.Entry<String, Integer> entry : invalidosPorTipo.entrySet()) {
            System.out.println("   • " + entry.getKey()+ ": " + entry.getValue() + " inválidos");
        }
    }
    System.out.println("\n?ESTADÍSTICAS POR TIPO:");
    System.out.println("Estudiantes: " +estudiantes.size());
    System.out.println("Profesores: " +profesores.size());
    System.out.println("Cursos: " +cursos.size());
    System.out.println("Niveles de Idioma: " + nivelesIdioma.size());
    System.out.println("Matrículas: " + matriculas.size());
    System.out.println("Calificaciones: " +calificaciones.size());
}
private void mostrarTodasLasEntidades(){
    System.out.println("\n=== INFORMACIÓN GENERAL DEL SISTEMA ===");
    
    List<IEntidad> todasLasEntidades = new ArrayList<>();
    todasLasEntidades.addAll(estudiantes.values());
    todasLasEntidades.addAll(profesores.values());
    todasLasEntidades.addAll(cursos.values());
    todasLasEntidades.addAll(nivelesIdioma.values());
    todasLasEntidades.addAll(matriculas);
    todasLasEntidades.addAll(calificaciones);
    
    for (IEntidad entidad : todasLasEntidades) {
        System.out.println("[" + entidad.getTipo() + "] " + entidad.mostrarInfo());
        System.out.println("------------------------------------------------------");
    }
    
    System.out.println("Total de entidades en el sistema: " + todasLasEntidades.size());
}
private void menuBusquedaInterna(){
    int opcion;
    do{
        System.out.println("\n=== BÚSQUEDA INTERNA (EN MEMORIA) ===");
        System.out.println("1. Buscar Estudiantes");
        System.out.println("2. Buscar Profesores");
        System.out.println("3. Buscar Cursos");
        System.out.println("4. Búsqueda Avanzada");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        opcion=Integer.parseInt(scanner.nextLine());

        switch(opcion){
            case 1:
                buscarEstudiantesInterna();
                break;
            case 2:
                buscarProfesoresInterna();
                break;
            case 3:
                buscarCursosInterna();
                break;
            case 4:
                busquedaAvanzada();
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida.");
        }
    } while (opcion != 0);
}

private void buscarEstudiantesInterna() {
    System.out.println("\n=== BÚSQUEDA DE ESTUDIANTES ===");
    System.out.println("1. Por DNI (Búsqueda Exacta)");
    System.out.println("2. Por Nombre/Apellido (Búsqueda Parcial)");
    System.out.println("3. Por Nivel de Estudios");
    System.out.print("Seleccione criterio: ");
    int criterio=Integer.parseInt(scanner.nextLine());
    System.out.print("Ingrese término de búsqueda: ");
    String termino = scanner.nextLine().toLowerCase();
    long startTime=System.currentTimeMillis();
    int resultados=0;
    switch (criterio){
        case 1:
            // Búsqueda exacta por DNI
            Estudiante estudiante = estudiantes.get(termino);
            if (estudiante != null) {
                System.out.println("ENCONTRADO: " + estudiante.mostrarInfo());
                resultados++;
            }
            break;
            
        case 2:
            // Búsqueda parcial por nombre/apellido
            for (Estudiante e : estudiantes.values()) {
                String nombreCompleto = (e.getNombres() + " " + e.getApellidos()).toLowerCase();
                if (nombreCompleto.contains(termino)) {
                    System.out.println( e.mostrarInfo());
                    resultados++;
                }
            }
            break;
            
        case 3:
            // Búsqueda por nivel de estudios
            for (Estudiante e : estudiantes.values()) {
                if (e.getNivelEstudios().toLowerCase().contains(termino)) {
                    System.out.println( e.mostrarInfo());
                    resultados++;
                }
            }
            break;
    }
    
    long endTime = System.currentTimeMillis();
    System.out.println("\nBúsqueda completada en " + (endTime - startTime) + " ms");
    System.out.println("Resultados encontrados: " + resultados);
}

private void buscarProfesoresInterna() {
    System.out.println("\n=== BÚSQUEDA DE PROFESORES ===");
    System.out.println("1. Por DNI");
    System.out.println("2. Por Nombre/Especialidad");
    System.out.println("3. Por Especialidad");
    System.out.println("4. Por Experiencia (años)");
    System.out.print("Seleccione criterio: ");
    int criterio = Integer.parseInt(scanner.nextLine());
    
    System.out.print("Ingrese término de búsqueda: ");
    String termino = scanner.nextLine().toLowerCase();
    
    long startTime = System.currentTimeMillis();
    int resultados = 0;
    
    switch (criterio) {
        case 1:
            Profesor profesor = profesores.get(termino);
            if (profesor != null) {
                System.out.println("ENCONTRADO: " +profesor.mostrarInfo());
                resultados++;
            }
            break;
            
        case 2:
            for (Profesor p : profesores.values()) {
                String infoCompleta = (p.getNombres() + " " +p.getApellidos() + " " + p.getEspecialidad()).toLowerCase();
                if (infoCompleta.contains(termino)) {
                    System.out.println(p.mostrarInfo());
                    resultados++;
                }
            }
            break;
        case 3:
            System.out.print("Ingrese especialidad: ");
            String especialidad = scanner.nextLine().toLowerCase();
            for (Profesor p : profesores.values()) {
                if (p.getEspecialidad().toLowerCase().contains(especialidad)) {
                    System.out.println(p.mostrarInfo());
                    resultados++;
                }
            }
            break;
        case 4:
            System.out.print("Ingrese años de experiencia (mínimo): ");
            try {
                int experienciaMin = Integer.parseInt(scanner.nextLine());
                for (Profesor p : profesores.values()) {
                    if (p.getExperiencia() >= experienciaMin) {
                        System.out.println(p.mostrarInfo());
                        resultados++;
                    }
                }
            }catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido");
            }
            break;
            
        default:
            System.out.println("Criterio inválido.");
            return;
    }
    long endTime = System.currentTimeMillis();
    System.out.println("\nBúsqueda completada en " + (endTime - startTime) + " ms");
    System.out.println("Resultados encontrados: " + resultados);
}
    private void buscarCursosInterna() {
    System.out.println("\n=== BÚSQUEDA DE CURSOS ===");
    System.out.println("1. Por Código (Búsqueda Exacta)");
    System.out.println("2. Por Nombre (Búsqueda Parcial)");
    System.out.println("3. Por Idioma");
    System.out.println("4. Por Nivel");
    System.out.println("5. Por Profesor");
    System.out.print("Seleccione criterio: ");
    int criterio = Integer.parseInt(scanner.nextLine());
    
    System.out.print("Ingrese término de búsqueda: ");
    String termino = scanner.nextLine().toLowerCase();
    
    long startTime = System.currentTimeMillis();
    int resultados = 0;
    
    switch (criterio) {
        case 1:
            // Búsqueda exacta por código
            Curso curso = cursos.get(termino.toUpperCase());
            if(curso != null) {
                System.out.println("ENCONTRADO: " + curso.mostrarInfo());
                resultados++;
            } else{
                System.out.println("No se encontró ningún curso con el código: " + termino);
            }
            break;
        case 2:
            // Búsqueda parcial por nombre
            for(Curso c : cursos.values()) {
                if(c.getNombre().toLowerCase().contains(termino)) {
                    System.out.println(c.mostrarInfo());
                    resultados++;
                }
            }
            break;
        case 3:
            // Búsqueda por idioma
            for (Curso c : cursos.values()) {
                if (c.getIdioma().toLowerCase().contains(termino)) {
                    System.out.println(c.mostrarInfo());
                    resultados++;
                }
            }
            break;
        case 4:
            // Búsqueda por nivel
            for(Curso c : cursos.values()) {
                if(c.getNivel().toLowerCase().contains(termino)) {
                    System.out.println(c.mostrarInfo());
                    resultados++;
                }
            }
            break;
        case 5:
            // Búsqueda por profesor (DNI o nombre)
            for(Curso c : cursos.values()) {
                // Buscar por DNI del profesor
                if(c.getProfesorDni().toLowerCase().contains(termino)){
                    System.out.println(c.mostrarInfo());
                    resultados++;
                }else{
                    // Buscar por nombre del profesor si está disponible
                    Profesor profesor = profesores.get(c.getProfesorDni());
                    if (profesor != null) {
                        String nombreProfesor = (profesor.getNombres() + " " + profesor.getApellidos()).toLowerCase();
                        if (nombreProfesor.contains(termino)) {
                            System.out.println(c.mostrarInfo());
                            resultados++;
                        }
                    }
                }
            }
            break;
        default:
            System.out.println("Criterio inválido.");
            return;
    }
    long endTime = System.currentTimeMillis();
    System.out.println("\nBúsqueda completada en " + (endTime - startTime) + " ms");
    System.out.println("Resultados encontrados: " + resultados);
    if(resultados == 0 && criterio != 1){
        System.out.println("Sugerencia: Intente con términos más generales o verifique la ortografía");
    }
}
private void busquedaAvanzada(){
    System.out.println("\n=== BÚSQUEDA AVANZADA ===");
    System.out.print("Ingrese término de búsqueda (buscará en todas las entidades): ");
    String termino = scanner.nextLine().toLowerCase();
    long startTime=System.currentTimeMillis();
    int resultados =0;
    System.out.println("\n--- ESTUDIANTES ---");
    for (Estudiante e : estudiantes.values()) {
        String info = (e.getDni() + " " + e.getNombres() + " " + e.getApellidos() + " " + e.getNivelEstudios()).toLowerCase();
        if (info.contains(termino)) {
            System.out.println(e.mostrarInfo());
            resultados++;
        }
    }
    System.out.println("\n--- PROFESORES ---");
    for (Profesor p : profesores.values()) {
        String info = (p.getDni() + " " + p.getNombres() + " " + p.getApellidos() + " " + p.getEspecialidad()).toLowerCase();
        if (info.contains(termino)) {
            System.out.println( p.mostrarInfo());
            resultados++;
        }
    }
    System.out.println("\n--- CURSOS ---");
    for (Curso c : cursos.values()) {
        String info = (c.getCodigo() + " " + c.getNombre() + " " + c.getIdioma() + " " + c.getNivel()).toLowerCase();
        if (info.contains(termino)) {
            System.out.println(c.mostrarInfo());
            resultados++;
        }
    }
    long endTime = System.currentTimeMillis();
    System.out.println("\nBúsqueda avanzada completada en " + (endTime - startTime) + " ms");
    System.out.println("Total de resultados: " + resultados);
}
private void menuBusquedaExterna(){
    int opcion;
    do{
        System.out.println("\n=== BÚSQUEDA EXTERNA (EN ARCHIVOS) ===");
        System.out.println("1. Buscar en Archivo de Estudiantes");
        System.out.println("2. Buscar en Archivo de Profesores");
        System.out.println("3. Buscar en Archivo de Cursos");
        System.out.println("4. Búsqueda en Todos los Archivos");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                buscarEnArchivoEstudiantes();
                break;
            case 2:
                buscarEnArchivoProfesores();
                break;
            case 3:
                buscarEnArchivoCursos();
                break;
            case 4:
                buscarEnTodosLosArchivos();
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida.");
        }
    } while (opcion != 0);
}
private void buscarEnArchivoEstudiantes(){
    try{
        System.out.print("Ingrese término de búsqueda: ");
        String termino =scanner.nextLine();
        System.out.println("\n=== OPCIONES DE BÚSQUEDA EN ESTUDIANTES ===");
        System.out.println("1. Buscar en todas las columnas");
        System.out.println("2. Buscar por columna específica");
        System.out.print("Seleccione opción: ");
        int opcionBusqueda=Integer.parseInt(scanner.nextLine());
        
        System.out.println("\nColumnas disponibles:");
        System.out.println("0 = DNI | 1 = Nombres | 2 = Apellidos | 3 = Dirección");
        System.out.println("4 = Teléfono | 5 = Correo | 6 = Fecha Nacimiento | 7 = Nivel Estudios");
        
        long startTime =System.currentTimeMillis();
        List<String> resultados;
        
        if(opcionBusqueda == 1){
            resultados = BusquedaExterna.buscarEnArchivoMultiple("estudiantes.txt", termino);
        }else{
            System.out.print("Número de columna: ");
            int columna = Integer.parseInt(scanner.nextLine());
            resultados = BusquedaExterna.buscarEnArchivo("estudiantes.txt", termino, columna);
        }
        
        long endTime = System.currentTimeMillis();
        
        // Mostrar resultados formateados
        System.out.println("\nRESULTADOS ENCONTRADOS EN ESTUDIANTES:");
        if(resultados.isEmpty()) {
            System.out.println("No se encontraron estudiantes que coincidan con: '" + termino + "'");
        } else{
            for (int i = 0; i < resultados.size(); i++) {
                String[] datos = resultados.get(i).split(",");
                System.out.println("\nESTUDIANTE #" + (i + 1) + ":");
                System.out.println("   DNI: " + (datos.length > 0 ? datos[0] : "N/A"));
                System.out.println("   Nombres: " + (datos.length > 1 ? datos[1] : "N/A"));
                System.out.println("   Apellidos: " + (datos.length > 2 ? datos[2] : "N/A"));
                System.out.println("   Dirección: " + (datos.length > 3 ? datos[3] : "N/A"));
                System.out.println("   Teléfono: " + (datos.length > 4 ? datos[4] : "N/A"));
                System.out.println("   Correo: " + (datos.length > 5 ? datos[5] : "N/A"));
                System.out.println("   Fecha Nacimiento: " + (datos.length > 6 ? datos[6] : "N/A"));
                System.out.println("   Nivel Estudios: " + (datos.length > 7 ? datos[7] : "N/A"));
                
                // Calcular edad si tenemos fecha de nacimiento
                if(datos.length >6 && !datos[6].isEmpty()) {
                    try{
                        int edad = Validador.calcularEdad(datos[6]);
                        System.out.println("   Edad: " + edad + " años");
                    } catch (Exception e) {
                    }
                }
            }
        }
        System.out.println("\nTiempo de búsqueda: " + (endTime - startTime) + " ms");
        System.out.println("Total de estudiantes encontrados: " + resultados.size());
        
    }catch(IOException e){
        System.out.println("Error al buscar en archivo de estudiantes: " + e.getMessage());
    } catch(NumberFormatException e) {
        System.out.println("Error: Debe ingresar un número válido para la columna");
    }
}

private void buscarEnArchivoProfesores(){
    try{
        System.out.print("Ingrese término de búsqueda: ");
        String termino = scanner.nextLine();
        System.out.println("\n=== OPCIONES DE BÚSQUEDA EN PROFESORES ===");
        System.out.println("1. Buscar en todas las columnas");
        System.out.println("2. Buscar por columna específica");
        System.out.print("Seleccione opción: ");
        int opcionBusqueda =Integer.parseInt(scanner.nextLine());
        System.out.println("\nColumnas disponibles:");
        System.out.println("0 = DNI | 1 = Nombres | 2 = Apellidos | 3 = Dirección");
        System.out.println("4 = Teléfono | 5 = Correo | 6 = Especialidad | 7 = Experiencia");
        
        long startTime =System.currentTimeMillis();
        List<String> resultados;
        
        if (opcionBusqueda== 1){
            resultados = BusquedaExterna.buscarEnArchivoMultiple("profesores.txt", termino);
        } else {
            System.out.print("Número de columna: ");
            int columna =Integer.parseInt(scanner.nextLine());
            resultados =BusquedaExterna.buscarEnArchivo("profesores.txt", termino, columna);
        }
        long endTime=System.currentTimeMillis();
        // Mostrar resultados formateados
        System.out.println("\nRESULTADOS ENCONTRADOS EN PROFESORES:");
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron profesores que coincidan con: '" + termino + "'");
        } else {
            for (int i = 0; i < resultados.size(); i++) {
                String[] datos = resultados.get(i).split(",");
                System.out.println("\nPROFESOR #" + (i + 1) + ":");
                System.out.println("   DNI: " + (datos.length > 0 ? datos[0] : "N/A"));
                System.out.println("   Nombres: " + (datos.length > 1 ? datos[1] : "N/A"));
                System.out.println("   Apellidos: " + (datos.length > 2 ? datos[2] : "N/A"));
                System.out.println("   Dirección: " + (datos.length > 3 ? datos[3] : "N/A"));
                System.out.println("   Teléfono: " + (datos.length > 4 ? datos[4] : "N/A"));
                System.out.println("   Correo: " + (datos.length > 5 ? datos[5] : "N/A"));
                System.out.println("   Especialidad: " + (datos.length > 6 ? datos[6] : "N/A"));
                System.out.println("   Experiencia: " + (datos.length > 7 ? datos[7] : "N/A") + " años");
            }
        }
        System.out.println("\nTiempo de búsqueda: " + (endTime - startTime) + " ms");
        System.out.println("Total de profesores encontrados: " + resultados.size());
    } catch(IOException e){
        System.out.println("Error al buscar en archivo de profesores: " + e.getMessage());
    }catch (NumberFormatException e){
        System.out.println("Error: Debe ingresar un número válido para la columna");
    }
}
private void buscarEnArchivoCursos(){
    try {
        System.out.print("Ingrese término de búsqueda: ");
        String termino = scanner.nextLine();
        System.out.println("\n=== OPCIONES DE BÚSQUEDA EN CURSOS ===");
        System.out.println("1. Buscar en todas las columnas");
        System.out.println("2. Buscar por columna específica");
        System.out.print("Seleccione opción: ");
        int opcionBusqueda = Integer.parseInt(scanner.nextLine());
        System.out.println("\nColumnas disponibles:");
        System.out.println("0 = Código | 1 = Nombre | 2 = Idioma | 3 = Nivel");
        System.out.println("4 = DNI Profesor | 5 = Horario | 6 = Duración");
        System.out.println("7 = Capacidad | 8 = Precio | 9 = Observaciones");
        long startTime = System.currentTimeMillis();
        List<String> resultados;
        
        if (opcionBusqueda == 1) {
            // Búsqueda en todas las columnas
            resultados = BusquedaExterna.buscarEnArchivoMultiple("cursos.txt", termino);
        }else{
            System.out.print("Número de columna: ");
            int columna = Integer.parseInt(scanner.nextLine());
            resultados = BusquedaExterna.buscarEnArchivo("cursos.txt", termino, columna);
        }
        long endTime=System.currentTimeMillis();
        // Mostrar resultados formateados
        System.out.println("\nRESULTADOS ENCONTRADOS EN CURSOS:");
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron cursos que coincidan con: '" + termino + "'");
        } else {
            for (int i = 0; i < resultados.size(); i++) {
                String[] datos = resultados.get(i).split(",");
                System.out.println("\nCURSO #" + (i + 1) + ":");
                System.out.println("   Código: " + (datos.length > 0 ? datos[0] : "N/A"));
                System.out.println("   Nombre: " + (datos.length > 1 ? datos[1] : "N/A"));
                System.out.println("   Idioma: " + (datos.length > 2 ? datos[2] : "N/A"));
                System.out.println("   Nivel: " + (datos.length > 3 ? datos[3] : "N/A"));
                System.out.println("   Profesor (DNI): " + (datos.length > 4 ? datos[4] : "N/A"));
                System.out.println("   Horario: " + (datos.length > 5 ? datos[5] : "N/A"));
                System.out.println("   Duración: " + (datos.length > 6 ? datos[6] : "N/A") + " semanas");
                System.out.println("   Capacidad: " + (datos.length > 7 ? datos[7] : "N/A") + " estudiantes");
                System.out.println("   Precio: S/" + (datos.length > 8 ? datos[8] : "N/A"));
                if (datos.length > 9) {
                    System.out.println("   Observaciones: " + datos[9]);
                }
            }
        }
        System.out.println("\nTiempo de búsqueda: " + (endTime - startTime) + " ms");
        System.out.println("Total de cursos encontrados: " + resultados.size());
        
    } catch (IOException e) {
        System.out.println("Error al buscar en archivo de cursos: " + e.getMessage());
        System.out.println("Asegúrate de que el archivo 'cursos.txt' exista y tenga datos");
    } catch (NumberFormatException e) {
        System.out.println("Error: Debe ingresar un número válido para la columna");
    }
}
private void buscarEnTodosLosArchivos(){
    try {
        System.out.print("Ingrese término de búsqueda global: ");
        String termino=scanner.nextLine();
        
        long startTime =System.currentTimeMillis();
        int totalResultados=0;
        System.out.println("\nREALIZANDO BÚSQUEDA GLOBAL...");
        // Buscar en estudiantes
        System.out.println("\n--- ESTUDIANTES.TXT ---");
        List<String> resultadosEstudiantes = BusquedaExterna.buscarEnArchivoMultiple("estudiantes.txt", termino);
        if (resultadosEstudiantes.isEmpty()) {
            System.out.println("No se encontraron estudiantes");
        } else {
            for (String resultado : resultadosEstudiantes) {
                String[] datos = resultado.split(",");
                System.out.println(datos[1] + " " + datos[2] + " (DNI: " + datos[0] + ")");
            }
        }
        totalResultados += resultadosEstudiantes.size();
        // Buscar en profesores
        System.out.println("\n--- PROFESORES.TXT ---");
        List<String> resultadosProfesores =BusquedaExterna.buscarEnArchivoMultiple("profesores.txt", termino);
        if (resultadosProfesores.isEmpty()) {
            System.out.println("No se encontraron profesores");
        } else {
            for (String resultado : resultadosProfesores) {
                String[] datos = resultado.split(",");
                System.out.println(datos[1] + " " + datos[2] + " - " + datos[6] + " (DNI: " + datos[0] + ")");
            }
        }
        totalResultados += resultadosProfesores.size();
        // Buscar en cursos
        System.out.println("\n--- CURSOS.TXT ---");
        List<String> resultadosCursos = BusquedaExterna.buscarEnArchivoMultiple("cursos.txt", termino);
        if (resultadosCursos.isEmpty()) {
            System.out.println("No se encontraron cursos");
        } else {
            for (String resultado : resultadosCursos) {
                String[] datos = resultado.split(",");
                System.out.println(datos[1] + " (" + datos[0] + ") - " + datos[2] + " " + datos[3]);
            }
        }
        totalResultados +=resultadosCursos.size();
        long endTime= System.currentTimeMillis();
        
        System.out.println("\nRESUMEN DE BÚSQUEDA GLOBAL:");
        System.out.println("Tiempo total de búsqueda: " + (endTime - startTime) + " ms");
        System.out.println("Estudiantes encontrados: " +resultadosEstudiantes.size());
        System.out.println("Profesores encontrados: " +resultadosProfesores.size());
        System.out.println("Cursos encontrados: " + resultadosCursos.size());
        System.out.println("Total general de resultados: " + totalResultados);
        
    }catch(IOException e){
        System.out.println("Error en búsqueda global: " +e.getMessage());
        System.out.println("Verifica que existan los archivos: estudiantes.txt, profesores.txt, cursos.txt");
    }
}
}

