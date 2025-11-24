/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;

import dao.CalificacionDAO;
import dao.CursoDAO;
import entidades.Calificacion;
import dao.EstudianteDAO; 
import dao.IdiomaNivelDAO;
import dao.MatriculaDAO;
import dao.ProfesorDAO;
import entidades.Curso;
import entidades.EntidadAcademica;
import entidades.Estudiante;
import entidades.IdiomaNivel;
import entidades.Matricula;
import entidades.Persona;
import entidades.Profesor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import util.ArchivoUtil;
import interfaces.IEntidad;
import interfaces.IValidable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import util.GeneradorReportes;
import util.SortUtil;
import util.Validador;

/**
 *
 * @author BRYAN
 */
public class GestorAcademia{
    private final Scanner scanner=new Scanner(System.in);
    private final Map<String,Estudiante>estudiantes =new HashMap<>();
    private final Map<String,Profesor>profesores =new HashMap<>(); 
    private final Map<String,Curso>cursos =new HashMap<>(); 
    private final ArrayList<Matricula>matriculas=new ArrayList<>();
    private final ArrayList<Calificacion>calificaciones=new ArrayList<>();
    private final Map<String,IdiomaNivel>nivelesIdioma=new HashMap<>();
    private final Map<String, Persona> personas = new HashMap<>();
    private final Map<String, EntidadAcademica> entidadesAcademicas = new HashMap<>();
    private final EstudianteDAO estudianteDAO = new EstudianteDAO();
    private final ProfesorDAO profesorDAO = new ProfesorDAO();
    private final CursoDAO cursoDAO = new CursoDAO();
    private final MatriculaDAO matriculaDAO = new MatriculaDAO();
    private final CalificacionDAO calificacionDAO = new CalificacionDAO();
    private final IdiomaNivelDAO idiomaNivelDAO = new IdiomaNivelDAO();
    
public GestorAcademia(){
    estudianteDAO.crearTabla();
    profesorDAO.crearTabla();
    cursoDAO.crearTabla();
    matriculaDAO.crearTabla();
    calificacionDAO.crearTabla();
    idiomaNivelDAO.crearTabla();
    
    if (new java.io.File("academia.db").exists()) {
        System.out.println("[DB] Cargando datos desde base de datos...");
        cargarDesdeBD();
    } else {
        System.out.println("[ARCHIVO] Base de datos no encontrada. Cargando desde archivos...");
        cargarEstudiantes();
        cargarProfesores();
        cargarCursos();
        cargarMatriculas();
        cargarCalificaciones();
        cargarNivelesIdioma();
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
                        if (e.validar()) {
                            estudiantes.put(e.getDni().toUpperCase(), e);
                            personas.put(e.getDni().toUpperCase(), e);
                        } else {
                            System.err.println("Estudiante inválido ignorado (DNI: " + e.getDni() + "): " + e.getMensajeError());
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al cargar estudiantes: " + e.getMessage());
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
                        if (p.validar()) {
                            profesores.put(p.getDni().toUpperCase(), p);
                            personas.put(p.getDni().toUpperCase(), p);
                        } else {
                            System.err.println("Profesor inválido ignorado (DNI: " + p.getDni() + "): " + p.getMensajeError());
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al cargar profesores: " + e.getMessage());
            }
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
                        c.setProfesorDni(c.getProfesorDni().toUpperCase().trim());
                        if (c.validar()) {
                            cursos.put(c.getCodigo().toUpperCase().trim(), c);
                            entidadesAcademicas.put(c.getCodigo().toUpperCase().trim(), c);
                        } else {
                            System.err.println("Curso inválido ignorado (Código: " + c.getCodigo() + "): " + c.getMensajeError());
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al cargar cursos: " + e.getMessage());
            }
        }

        private void cargarMatriculas() {
            try (BufferedReader br = new BufferedReader(new FileReader("matriculas.txt"))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length >= 4) {
                        Matricula m = new Matricula(
                            partes[0].trim().toUpperCase(),
                            partes[1].trim().toUpperCase(),
                            partes[2].trim(),
                            Double.parseDouble(partes[3])
                        );
                        if (m.validar()) {
                            matriculas.add(m);
                        } else {
                            System.err.println("Matrícula inválida ignorada (DNI=" + m.getDniEstudiante() + ", Curso=" + m.getCodigoCurso() + "): " + m.getMensajeError());
                        }
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
                            partes[0].trim().toUpperCase(),
                            partes[1].trim().toUpperCase(),
                            partes[2].trim(),
                            Double.parseDouble(partes[3].trim()),
                            partes[4].trim()
                        );
                        if (c.validar()) {
                            calificaciones.add(c);
                        } else {
                            System.err.println("Calificación inválida ignorada (DNI=" + c.getDniEstudiante() + ", Curso=" + c.getCodigoCurso() + "): " + c.getMensajeError());
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al cargar calificaciones: " + e.getMessage());
            }
        }

        private void cargarNivelesIdioma() {
            try (BufferedReader br = new BufferedReader(new FileReader("idiomas.txt"))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length >= 4) {
                        IdiomaNivel in = new IdiomaNivel(partes[0], partes[1], partes[2], partes[3]);
                        if (in.validar()) {
                            nivelesIdioma.put(in.getCodigo().toUpperCase(), in);
                            entidadesAcademicas.put(in.getCodigo().toUpperCase(), in);   
                        } else {
                            System.err.println("Nivel de idioma inválido ignorado (Código: " + in.getCodigo() + "): " + in.getMensajeError());
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al cargar niveles de idioma: " + e.getMessage());
            }
        }

    //MENU PRINCIPAL
    
    public void mostrarMenu(){
        int opcion;
        do{
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Gestion de Estudiantes");
            System.out.println("2. Gestion de Profesores");
            System.out.println("3. Gestion de Cursos");
            System.out.println("4. Matriculas y Calificaciones");
            System.out.println("5. Niveles de Idioma");
            System.out.println("6. Generar Reportes HTML");
            System.out.println("7. Ordenar listas (Estudiantes,Profesores,Cursos)");
            System.out.println("8. Búsqueda en Archivos");
            System.out.println("9. Validar Todas las Entidades");
            System.out.println("10. Mostrar Todas las Entidades");
            System.out.println("11. Listar Estudiantes por Nivel");
            System.out.println("12. Cursos con Más de 3 Matrículas");
            System.out.println("13. Estudiantes sin Calificaciones");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = leerOpcion(0, 10);

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
                    buscarEnTodosLosArchivos();
                    break;
                case 9:
                    validarTodasLasEntidades();
                    break;
                case 10:
                    mostrarTodasLasEntidades();
                    break;
                case 11: 
                    listarEstudiantesPorNivel(); 
                    break;
                case 12: 
                    mostrarCursosConMasMatriculas(); 
                    break;
                case 13: 
                    listarEstudiantesSinCalificaciones(); 
                    break;
                case 0:
                    System.out.println("Cerrando sesion...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }
        }while (opcion !=0);
    }

    private void menuEstudiantes(){
        int opcion;
        do{
            System.out.println("\n--- GESTION DE ESTUDIANTES ---");
            System.out.println("1. Registrar Estudiante");
            System.out.println("2. Buscar Estudiante");
            System.out.println("3. Modificar Estudiante");
            System.out.println("4. Eliminar Estudiante");
            System.out.println("5. Estudiantes menores de 18 años (Lambda)");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");
            opcion = leerOpcion(0, 4);

            switch(opcion){
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
                case 5: 
                    listarEstudiantesJovenes(); 
                    break;
                case 0:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }
        }while(opcion!= 0);
    }

            private void registrarEstudiante(){
                   try{
                   System.out.println("\nREGISTRO DE NUEVO ESTUDIANTE");
                   String dni = leerDNI().toUpperCase();
                   if(estudiantes.containsKey(dni)){
                       System.out.println("Ya existe un estudiante registrado con este DNI.");
                       return;
                   }
                   System.out.print("Nombres: ");
                   String nombres=Validador.formatearTexto(scanner.nextLine());
                   Validador.validarSoloLetras(nombres, "nombres");
                   System.out.print("Apellidos: ");
                   String apellidos=Validador.formatearTexto(scanner.nextLine());
                   Validador.validarSoloLetras(apellidos, "apellidos");
                   System.out.print("Dirección: ");
                   String direccion=scanner.nextLine().trim();
                   Validador.validarNoVacio(direccion, "dirección");     
                   String telefono=leerTelefono();
                   String correo=leerEmail();
                   String fechaNacimiento=leerFecha("Fecha de nacimiento (dd/MM/yyyy): ");
                   Validador.validarEdadEstudiante(fechaNacimiento,12,80);
                   System.out.print("Nivel de estudios: ");
                   String nivelEstudios=scanner.nextLine().trim();
                   Validador.validarNoVacio(nivelEstudios,"nivel de estudios");
                   Validador.validarDatosEstudiante(dni, nombres, apellidos, direccion, telefono, correo, fechaNacimiento, nivelEstudios);
                   Estudiante e=new Estudiante(dni, nombres, apellidos, direccion, telefono, correo, fechaNacimiento, nivelEstudios);
                       if(!e.validar()){
                       System.out.println("Error de validación: " + e.getMensajeError());
                       return;
                   }
                   estudiantes.put(dni, e);
                   personas.put(dni, e);
                   if (estudianteDAO.insertar(e)) {
                        ArchivoUtil.agregarEntidad(e, "estudiantes.txt");
                        System.out.println("Estudiante registrado en BD y archivo.");
                    } else {
                        System.out.println("Error en base de datos.");
                        estudiantes.remove(dni);
                        return;
                    }
               } catch (IllegalArgumentException e) {
                   System.out.println("Error de validación: " + e.getMessage());
               } catch (Exception e) {
                   System.out.println("Error inesperado: " + e.getMessage());
               }
           }
            
            private void buscarEstudiante(){
                System.out.print("Ingrese DNI del estudiante: ");
                String dni = scanner.nextLine().trim().toUpperCase();
                Estudiante e = estudiantes.get(dni);
                    if(e!= null){
                System.out.println("Estudiante encontrado: " +e.mostrarInfo());
                    }else{
                System.out.println("Estudiante no encontrado.");
                }
            }
            
            private void modificarEstudiante(){
            System.out.print("Ingrese DNI del estudiante a modificar: ");
            String dni = scanner.nextLine().trim().toUpperCase();

            Estudiante e = estudiantes.get(dni);
                if(e != null){
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
                    if(!e.validar()){
                    System.out.println("Error: Los datos modificados no son válidos: " + e.getMensajeError());
                    return;
                    }
                    ArchivoUtil.guardarLista(new ArrayList<>(estudiantes.values()),"estudiantes.txt");
                    System.out.println("Datos actualizados (solo en memoria).");
                }else{
                System.out.println("Estudiante no encontrado.");
                }
            }
            
            private void eliminarEstudiante() {
                System.out.print("Ingrese DNI del estudiante a eliminar: ");
                String dni = scanner.nextLine().trim().toUpperCase();
                if (estudianteDAO.eliminar(dni)) {
                    estudiantes.remove(dni);
                    ArchivoUtil.guardarLista(new ArrayList<>(estudiantes.values()), "estudiantes.txt");
                    System.out.println("Estudiante eliminado de la BD y archivo.");
                } else {
                    System.out.println("Estudiante no encontrado en la base de datos.");
                }
            }
            
            
            private void listarEstudiantesJovenes() {
                System.out.println("\n=== ESTUDIANTES MENORES DE 18 AÑOS ===");

                List<Estudiante> jovenes = estudiantes.values().stream()
                    .filter(e -> Validador.calcularEdad(e.getFechaNacimiento()) < 18)
                    .sorted(Comparator.comparing(Estudiante::getApellidos))
                    .collect(Collectors.toList());

                if (jovenes.isEmpty()) {
                    System.out.println("No hay estudiantes menores de 18 años.");
                } else {
                    System.out.println("Total: " + jovenes.size() + " estudiantes");
                    jovenes.forEach(e -> 
                        System.out.printf("• %s, %s | Edad: %d | DNI: %s%n",
                            e.getApellidos(), e.getNombres(),
                            Validador.calcularEdad(e.getFechaNacimiento()),
                            e.getDni())
                    );
                }
            }
            
            
    private void menuProfesores(){
        int opcion;
        do{
            System.out.println("\n--- GESTION DE PROFESORES ---");
            System.out.println("1. Registrar Profesor");
            System.out.println("2. Buscar Profesor");
            System.out.println("3. Modificar Profesor");
            System.out.println("4. Eliminar Profesor");
            System.out.println("5. Profesores con experiencia mínima (Lambda)");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");
            opcion = leerOpcion(0, 4);

            switch(opcion){
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
                case 5: 
                    listarProfesoresConExperienciaMinima(); 
                    break;
                case 0:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }
        }while(opcion!= 0);
    }
    
            private void registrarProfesor() {
               try {
                   System.out.println("\nREGISTRO DE NUEVO PROFESOR");
                   String dni = leerDNI().toUpperCase();
                   if (profesores.containsKey(dni)) {
                       System.out.println("Ya existe un profesor con este DNI.");
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

                   System.out.print("Especialidad: ");
                   String especialidad = scanner.nextLine().trim();
                   Validador.validarNoVacio(especialidad, "especialidad");

                   System.out.print("Años de experiencia: ");
                   int experiencia = Integer.parseInt(scanner.nextLine());
                   Validador.validarNumeroPositivo(experiencia, "experiencia");

                   Profesor p = new Profesor(dni, nombres, apellidos, direccion, telefono, correo, especialidad, experiencia);

                   if (!p.validar()) {
                       System.out.println("Error de validación: " + p.getMensajeError());
                       return;
                   }

                   profesores.put(dni, p);
                   personas.put(dni, p);
                   if (profesorDAO.insertar(p)) {
                        // También respaldo en .txt (modo híbrido)
                        ArchivoUtil.agregarEntidad(p, "profesores.txt");
                        System.out.println("Profesor registrado en BD y archivo.");
                    } else {
                        System.out.println("Error en base de datos.");
                        profesores.remove(dni); // revertir
                        return;
                    }

               } catch (IllegalArgumentException e) {
                   System.out.println("Error de validación: " + e.getMessage());
               } catch (Exception e) {
                   System.out.println("Error inesperado: " + e.getMessage());
               }
            }
            
            private void modificarProfesor() {
               System.out.print("Ingrese el DNI del profesor a modificar: ");
               String dni = scanner.nextLine().trim().toUpperCase();

               Profesor p = profesores.get(dni);
               if (p == null) {
                   System.out.println("Profesor no encontrado.");
                   return;
               }

               System.out.println("Datos actuales: " + p.mostrarInfo());
               System.out.print("Nuevo teléfono (ENTER para mantener): ");
               String telefono = scanner.nextLine().trim();
               if (!telefono.isEmpty()) p.setTelefono(telefono);

               System.out.print("Nuevo correo (ENTER para mantener): ");
               String correo = scanner.nextLine().trim();
               if (!correo.isEmpty()) p.setCorreo(correo);

               System.out.print("Nueva especialidad (ENTER para mantener): ");
               String esp = scanner.nextLine().trim();
               if (!esp.isEmpty()) p.setEspecialidad(esp);

               System.out.print("Nueva experiencia (ENTER para mantener): ");
               String expStr = scanner.nextLine().trim();
               if (!expStr.isEmpty()) p.setExperiencia(Integer.parseInt(expStr));

               if (!p.validar()) {
                   System.out.println("Error de validación: " + p.getMensajeError());
                   return;
               }

               profesores.put(p.getDni(), p);
               ArchivoUtil.guardarLista(new ArrayList<>(profesores.values()), "profesores.txt");
               System.out.println("Profesor modificado correctamente.");
            }
            
            private void buscarProfesor() {
               System.out.print("Ingrese DNI del profesor: ");
               String dni = scanner.nextLine().trim().toUpperCase();
               Profesor p = profesores.get(dni);
               if (p != null) {
                   System.out.println("Profesor encontrado: " + p.mostrarInfo());
               } else {
                   System.out.println("Profesor no encontrado.");
               }
            }
           
           private void eliminarProfesor() {
               System.out.print("Ingrese el DNI del profesor a eliminar: ");
               String dni = scanner.nextLine().trim().toUpperCase();

               if (profesorDAO.eliminar(dni)) {
                    profesores.remove(dni);
                    ArchivoUtil.guardarLista(new ArrayList<>(profesores.values()), "profesores.txt");
                    System.out.println("Profesor eliminado de la base de datos y archivo.");
                } else {
                    System.out.println("Profesor no encontrado en la base de datos.");
                }
            }
           
           
           private void listarProfesoresConExperienciaMinima() {
                System.out.print("\nIngrese años mínimos de experiencia: ");
                try {
                    int minExp = Integer.parseInt(scanner.nextLine().trim());
                    if (minExp < 0) {
                        System.out.println("La experiencia mínima no puede ser negativa.");
                        return;
                    }

                    List<Profesor> experimentados = profesores.values().stream()
                        .filter(p -> p.getExperiencia() >= minExp)
                        .sorted(Comparator.comparingInt(Profesor::getExperiencia).reversed())
                        .collect(Collectors.toList());

                    System.out.println("\n=== PROFESORES CON ≥ " + minExp + " AÑOS DE EXPERIENCIA ===");
                    if (experimentados.isEmpty()) {
                        System.out.println("Ningún profesor cumple con ese criterio.");
                    } else {
                        experimentados.forEach(p -> 
                            System.out.printf("• %s, %s | %d años | %s%n",
                                p.getApellidos(), p.getNombres(),
                                p.getExperiencia(), p.getEspecialidad())
                        );
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Debe ingresar un número entero válido.");
                }
            }
           
           
    private void menuCursos(){
        int opcion;
        do{
            System.out.println("\n--- GESTION DE CURSOS ---");
            System.out.println("1. Registrar Curso");
            System.out.println("2. Buscar Curso");
            System.out.println("3. Modificar Curso");
            System.out.println("4. Eliminar Curso");
            System.out.println("5. Cursos por idioma (Streams + Grouping)");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");
            opcion = leerOpcion(0, 4);

            switch(opcion){
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
                case 5: 
                    agruparCursosPorIdioma(); 
                    break;
                case 0:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }while(opcion!=0);
    }
    
            private void registrarCurso() {
                try {
                    System.out.println("\nREGISTRO DE NUEVO CURSO");
                    System.out.print("Código del curso: ");
                    String codigo = scanner.nextLine().trim().toUpperCase();
                    if (cursos.containsKey(codigo)) {
                        System.out.println("Ya existe un curso con este código.");
                        return;
                    }

                    System.out.print("Nombre del curso: ");
                    String nombre = scanner.nextLine().trim();
                    Validador.validarNoVacio(nombre, "nombre del curso");

                    System.out.print("Idioma: ");
                    String idioma = scanner.nextLine().trim();
                    Validador.validarIdioma(idioma);

                    System.out.print("Nivel (Básico / Intermedio / Avanzado): ");
                    String nivel = scanner.nextLine().trim();
                    Validador.validarNivelIdioma(nivel);

                    System.out.print("DNI del profesor asignado: ");
                    String profesorDni = scanner.nextLine().trim().toUpperCase();
                    if (!profesores.containsKey(profesorDni)) {
                        System.out.println("No existe un profesor con ese DNI.");
                        return;
                    }

                    System.out.print("Horario (ej. Lunes y Miércoles 8-10am): ");
                    String horario = scanner.nextLine().trim();
                    Validador.validarNoVacio(horario, "horario");

                    System.out.print("Duración (en semanas): ");
                    int duracion = leerEnteroValidado("Duración (en semanas): ", 1, 100);
                    Validador.validarDuracionCurso(duracion);

                    System.out.print("Capacidad máxima: ");
                    int capacidad = leerEnteroValidado("Capacidad máxima: ", 1, 100);
                    Validador.validarCapacidadCurso(capacidad);

                    System.out.print("Precio (S/): ");
                    double precio = leerDoubleValidado("Precio (S/): ", 0.0, 10000.0);
                    Validador.validarPrecio(precio);

                    System.out.print("Observaciones: ");
                    String obs = scanner.nextLine().trim();
                    Validador.validarNoVacio(obs, "observaciones");

                    Curso c = new Curso(codigo, nombre, idioma, nivel, profesorDni, horario, duracion, capacidad, precio, obs);

                    if (!c.validar()) {
                        System.out.println("Error de validación: " + c.getMensajeError());
                        return;
                    }

                    cursos.put(codigo, c);
                    entidadesAcademicas.put(codigo, c);
                    if (cursoDAO.insertar(c)) {
                        ArchivoUtil.agregarEntidad(c, "cursos.txt");
                        System.out.println("Curso registrado en BD y archivo.");
                    } else {
                        System.out.println("Error en base de datos.");
                        cursos.remove(codigo);
                        return;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Error en formato numérico: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error de validación: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Error inesperado: " + e.getMessage());
                }
            }
            
            private void modificarCurso() {
                System.out.print("Ingrese el código del curso a modificar: ");
                String codigo = scanner.nextLine().trim().toUpperCase();

                Curso c = cursos.get(codigo);
                if (c == null) {
                    System.out.println("Curso no encontrado.");
                    return;
                }

                System.out.println("Datos actuales: " + c.mostrarInfo());
                System.out.print("Nuevo horario (ENTER para mantener): ");
                String horario = scanner.nextLine().trim();
                if (!horario.isEmpty()) {
                    try {
                        Validador.validarNoVacio(horario, "horario");
                        c.setHorario(horario);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Horario no válido: " + e.getMessage());
                        return;
                    }
                }

                System.out.print("Nuevo precio (ENTER para mantener): ");
                String precioStr = scanner.nextLine().trim();
                if (!precioStr.isEmpty()) {
                    try {
                        double nuevoPrecio = leerDoubleValidado("Confirme nuevo precio (S/): ", 0.0, 10000.0);
                        c.setPrecio(nuevoPrecio);
                    } catch (Exception e) {
                        System.out.println("Precio no válido.");
                        return;
                    }
                }


                System.out.print("Nueva capacidad máxima (ENTER para mantener): ");
                String capStr = scanner.nextLine().trim();
                if (!capStr.isEmpty()) {
                    try {
                        int nuevaCapacidad = leerEnteroValidado("Confirme nueva capacidad: ", 1, 100);
                        c.setCapacidadMaxima(nuevaCapacidad);
                    } catch (Exception e) {
                        System.out.println("Capacidad no válida.");
                        return;
                    }
                }

                if (!c.validar()) {
                    System.out.println("Error de validación: " + c.getMensajeError());
                    return;
                }
                ArchivoUtil.guardarLista(new ArrayList<>(cursos.values()), "cursos.txt");
                System.out.println("Curso modificado correctamente.");
            }
            
            private void buscarCurso() {
                System.out.print("Ingrese el código del curso: ");
                String codigo = scanner.nextLine().trim().toUpperCase();

                Curso c = cursos.get(codigo);
                if (c != null) {
                    System.out.println("Curso encontrado: " + c.mostrarInfo());
                } else {
                    System.out.println("Curso no encontrado.");
                }
            }
            
            private void eliminarCurso() {
                System.out.print("Ingrese el código del curso a eliminar: ");
                String codigo = scanner.nextLine().trim().toUpperCase();

                if (cursoDAO.eliminar(codigo)) {
                    cursos.remove(codigo);
                    ArchivoUtil.guardarLista(new ArrayList<>(cursos.values()), "cursos.txt");
                    System.out.println("Curso eliminado de la base de datos y archivo.");
                } else {
                    System.out.println("Curso no encontrado en la base de datos.");
                }
            }
            
            
            private void agruparCursosPorIdioma() {
                System.out.println("\n=== CURSOS AGRUPADOS POR IDIOMA ===");

                Map<String, Long> conteoPorIdioma = cursos.values().stream()
                    .collect(Collectors.groupingBy(Curso::getIdioma, Collectors.counting()));

                if (conteoPorIdioma.isEmpty()) {
                    System.out.println("No hay cursos registrados.");
                    return;
                }

                conteoPorIdioma.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(entry -> 
                        System.out.println("• " + entry.getKey() + ": " + entry.getValue() + " curso(s)")
                    );

                // Opcional: Mostrar listado detallado
                System.out.print("\n¿Mostrar listado detallado? (s/n): ");
                if (scanner.nextLine().trim().toLowerCase().startsWith("s")) {
                    System.out.println("\n--- LISTADO DETALLADO ---");
                    cursos.values().stream()
                        .sorted(Comparator.comparing(Curso::getIdioma)
                                           .thenComparing(Curso::getNombre))
                        .forEach(c -> 
                            System.out.printf("  %s | %s | %s%n",
                                c.getIdioma(), c.getNombre(), c.getCodigo())
                        );
                }
            }
            
            
    private void menuMatriculasNotas() {
        int opcion;
        do {
            System.out.println("\n--- MATRÍCULAS Y CALIFICACIONES ---");
            System.out.println("1. Registrar Matrícula");
            System.out.println("2. Registrar Calificación");
            System.out.println("3. Eliminar Matrícula");
            System.out.println("4. Eliminar Calificación");
            System.out.println("5. Listar Calificaciones");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = leerOpcion(0, 4);

            switch (opcion) {
                case 1:
                    registrarMatricula();
                    break;
                case 2:
                    registrarCalificacion();
                    break;
                case 3:
                    eliminarMatricula();
                    break;
                case 4:
                    eliminarCalificacion();
                    break;
                case 5:
                    listarCalificaciones();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 0);
    }
    
            private void registrarMatricula() {
                System.out.println("\n=== REGISTRO DE MATRÍCULA ===");

                System.out.print("DNI del estudiante: ");
                String dni = scanner.nextLine().trim().toUpperCase();
                Estudiante estudiante = estudiantes.get(dni);

                if (estudiante == null) {
                    System.out.println("Estudiante no encontrado. Regístrelo primero.");
                    return;
                }

                if (cursos.isEmpty()) {
                    System.out.println("No hay cursos disponibles. Registre al menos uno.");
                    return;
                }

                System.out.println("Cursos disponibles:");
                for (Curso c : cursos.values()) {
                    System.out.println("- " + c.getCodigo() + ": " + c.getNombre());
                }

                System.out.print("Ingrese el código del curso: ");
                String codigoCurso = scanner.nextLine().trim().toUpperCase();
                Curso cursoSeleccionado = cursos.get(codigoCurso);

                if (cursoSeleccionado == null) {
                    System.out.println("Curso no encontrado.");
                    return;
                }

                System.out.print("Fecha de matrícula (dd/MM/yyyy): ");
                String fecha = scanner.nextLine().trim();

                double monto = cursoSeleccionado.getPrecio();
                Matricula m = new Matricula(codigoCurso, dni, fecha, monto);

                if (!m.validar()) {
                    System.out.println("Error de validación: " + m.getMensajeError());
                    return;
                }

                matriculas.add(m);
                if (matriculaDAO.insertar(m)) {
                    ArchivoUtil.agregarEntidad(m, "matriculas.txt");
                    System.out.println("Matrícula registrada en BD y archivo.");
                } else {
                    System.out.println("Error en base de datos.");
                    matriculas.remove(m);
                    return;
                }
            }
            
            private void registrarCalificacion() {
                System.out.println("\n=== REGISTRO DE CALIFICACIÓN ===");

                System.out.print("DNI del estudiante: ");
                String dni = scanner.nextLine().trim().toUpperCase();
                Estudiante estudiante = estudiantes.get(dni);

                if (estudiante == null) {
                    System.out.println("Estudiante no encontrado.");
                    return;
                }

                System.out.print("Código del curso: ");
                String codigoCurso = scanner.nextLine().trim().toUpperCase();
                Curso curso = cursos.get(codigoCurso);

                if (curso == null) {
                    System.out.println("Curso no encontrado.");
                    return;
                }

                System.out.print("Fecha de calificación (dd/MM/yyyy): ");
                String fecha = scanner.nextLine();

                System.out.print("Nota (0 a 20): ");
                double nota = leerDoubleValidado("Nota (0 a 20): ", 0.0, 20.0);

                System.out.print("Observaciones: ");
                String observaciones = scanner.nextLine();

                Calificacion c = new Calificacion(codigoCurso, dni, fecha, nota, observaciones);

                if (!c.validar()) {
                    System.out.println("Error de validación: " + c.getMensajeError());
                    return;
                }

                calificaciones.add(c);
                if (calificacionDAO.insertar(c)) {
                    ArchivoUtil.agregarEntidad(c, "calificaciones.txt");
                    System.out.println("Calificacion registrada en BD y archivo.");
                } else {
                    System.out.println("Error en base de datos.");
                    calificaciones.remove(c);
                    return;
                }
            }
            
            private void eliminarMatricula() {
                System.out.print("Ingrese código de curso de la matrícula a eliminar: ");
                String codigoCurso = scanner.nextLine().trim().toUpperCase();
                System.out.print("Ingrese DNI del estudiante: ");
                String dni = scanner.nextLine().trim().toUpperCase();

                if (matriculaDAO.eliminar(codigoCurso, dni)) {
                    boolean eliminadoEnMemoria = matriculas.removeIf(m -> 
                        m.getCodigoCurso().equals(codigoCurso) && 
                        m.getDniEstudiante().equals(dni)
                    );
                    ArchivoUtil.guardarLista(matriculas, "matriculas.txt");
                    if (eliminadoEnMemoria) {
                        System.out.println("Matrícula eliminada de la BD, memoria y archivo.");
                    } else {
                        System.out.println("⚠Matrícula eliminada de la BD, pero no estaba en memoria.");
                    }
                } else {
                    System.out.println("Matrícula no encontrada en la base de datos.");
                }
            }
            
            private void eliminarCalificacion() {
                System.out.print("Ingrese código de curso: ");
                String codigoCurso = scanner.nextLine().trim().toUpperCase();
                System.out.print("Ingrese DNI del estudiante: ");
                String dni = scanner.nextLine().trim().toUpperCase();
                System.out.print("Ingrese fecha de calificación (dd/MM/yyyy): ");
                String fecha = scanner.nextLine().trim();

                if (calificacionDAO.eliminar(codigoCurso, dni, fecha)) {
                    calificaciones.removeIf(cal -> 
                        cal.getCodigoCurso().equals(codigoCurso) &&
                        cal.getDniEstudiante().equals(dni) &&
                        cal.getFecha().equals(fecha)
                    );
                    ArchivoUtil.guardarLista(calificaciones, "calificaciones.txt");
                    System.out.println("Calificación eliminada de la BD y archivo.");
                } else {
                    System.out.println("Calificación no encontrada en la base de datos.");
                }
            }
            private void listarCalificaciones() {
                if (calificaciones.isEmpty()) {
                    System.out.println("No hay calificaciones registradas.");
                    return;
                }

                System.out.println("\n=== LISTA DE CALIFICACIONES ===");
                for (Calificacion c : calificaciones) {
                    System.out.println(c.mostrarInfo());
                }
            }
            
    private void menuNivelesIdioma(){
        int opcion;
        do{
            System.out.println("\n--- GESTION DE NIVELES DE IDIOMA ---");
            System.out.println("1. Registrar Nivel de Idioma");
            System.out.println("2. Buscar Nivel de Idioma");
            System.out.println("3. Modificar Nivel de Idioma");
            System.out.println("4. Eliminar Nivel de Idioma");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");
            opcion = leerOpcion(0, 4);

            switch(opcion){
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
        }while(opcion!=0);
    }
    
            private void registrarNivelIdioma(){
                System.out.println("Registro de Nivel de Idioma:");
                System.out.print("Codigo: ");
                String codigo = scanner.nextLine().trim().toUpperCase();
                if(nivelesIdioma.containsKey(codigo)){
                        System.out.println("Ya existe un nivel de idioma con este código.");
                        return;
                    }
                System.out.print("Idioma: ");
                String idioma =scanner.nextLine();
                System.out.print("Nivel: ");
                String nivel=scanner.nextLine();
                System.out.print("Descripcion: ");
                String descripcion=scanner.nextLine();

                IdiomaNivel in =new IdiomaNivel(codigo,idioma,nivel,descripcion);
                if(!in.validar()){
                        System.out.println("Error de validación: "+in.getMensajeError());
                        return;
                    }
                nivelesIdioma.put(codigo, in);
                entidadesAcademicas.put(codigo, in);
                if (idiomaNivelDAO.insertar(in)) {
                    ArchivoUtil.agregarEntidad(in, "idiomas.txt");
                    System.out.println("Nivel de idioma registrado en BD y archivo.");
                } else {
                    System.out.println("Error en base de datos.");
                    nivelesIdioma.remove(codigo);
                    return;
                }
            }

            private void modificarNivelIdioma(){
                System.out.print("Ingrese codigo del nivel de idioma a modificar: ");
                String codigo = scanner.nextLine().trim().toUpperCase();
                IdiomaNivel in=nivelesIdioma.get(codigo);
                    if (in != null) {
                        System.out.println("Nivel encontrado:");
                        System.out.println(in.mostrarInfo());

                        System.out.println("Que desea modificar?");
                        System.out.println("1. Nivel");
                        System.out.println("2. Descripcion");
                        System.out.print("Opcion: ");
                        int opcion=Integer.parseInt(scanner.nextLine());

                        switch(opcion){
                            case 1:
                                System.out.print("Nuevo nivel: ");
                                String nuevoNivel=scanner.nextLine();
                                in.setNivel(nuevoNivel);
                                break;
                            case 2:
                                System.out.print("Nueva descripcion: ");
                                String nuevaDesc=scanner.nextLine();
                                in.setDescripcion(nuevaDesc);
                                break;
                            default:
                                System.out.println("Opcion invalida.");
                                break;
                        }
                        if(!in.validar()){
                        System.out.println("Error: Los datos modificados no son válidos: "+in.getMensajeError());
                        return;
                        }
                        ArchivoUtil.guardarLista(new ArrayList<>(nivelesIdioma.values()),"idiomas.txt");
                        System.out.println("Nivel actualizado.");
                    }else{
                    System.out.println("Nivel no encontrado.");    
                }
            }

            private void buscarNivelIdioma(){
                System.out.print("Ingrese codigo del nivel de idioma: ");
                String codigo = scanner.nextLine().trim().toUpperCase();
                IdiomaNivel in=nivelesIdioma.get(codigo);
                    if(in!=null){
                        System.out.println("Nivel encontrado:");
                        System.out.println(in.mostrarInfo());
                    }else{
                        System.out.println("Nivel no encontrado.");
                    }   
            }

            private void eliminarNivelIdioma(){
                System.out.print("Ingrese codigo del nivel de idioma a eliminar: ");
                String codigo = scanner.nextLine().trim().toUpperCase();
                if (idiomaNivelDAO.eliminar(codigo)) {
                    nivelesIdioma.remove(codigo);
                    ArchivoUtil.guardarLista(new ArrayList<>(nivelesIdioma.values()), "idiomas.txt");
                    System.out.println("Nivel de idioma eliminado de la BD y archivo.");
                } else {
                    System.out.println("Nivel de idioma no encontrado en la base de datos.");
                }
            }

    private void mostrarMenuReportesHTML() {
        int opcion;
        do {
            System.out.println("\n=== GENERAR REPORTES HTML ===");
            System.out.println("1. Reporte de Estudiantes");
            System.out.println("2. Reporte de Profesores");
            System.out.println("3. Reporte de Cursos");
            System.out.println("4. Reporte de Matrículas");
            System.out.println("5. Reporte de Calificaciones");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = leerOpcion(0, 5);

            switch (opcion) {
                case 1:
                    generarReporteEstudiantes();
                    break;
                case 2:
                    generarReporteProfesores();
                    break;
                case 3:
                    generarReporteCursos();
                    break;
                case 4:
                    generarReporteMatriculas();
                    break;
                case 5:
                    generarReporteCalificaciones();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 0);
    }
    
            private void generarReporteEstudiantes(){
                if (estudiantes.isEmpty()) {
                    System.out.println("No hay estudiantes registrados.");
                    return;
                }
                GeneradorReportes.generarHTML(
                    "Reporte de Estudiantes",
                    new ArrayList<>(estudiantes.values()),
                    "reporte_estudiantes"
                );
            }

            private void generarReporteProfesores(){
                if (profesores.isEmpty()) {
                    System.out.println("No hay profesores registrados.");
                    return;
                }
                GeneradorReportes.generarHTML(
                    "Reporte de Profesores",
                    new ArrayList<>(profesores.values()),
                    "reporte_profesores"
                );
            }

            private void generarReporteCursos(){
                if (cursos.isEmpty()) {
                    System.out.println("No hay cursos registrados.");
                    return;
                }
                GeneradorReportes.generarHTML(
                    "Reporte de Cursos",
                    new ArrayList<>(cursos.values()),
                    "reporte_cursos"
                );
            }

            private void generarReporteMatriculas(){
                if (matriculas.isEmpty()) {
                    System.out.println("No hay matrículas registradas.");
                    return;
                }
                GeneradorReportes.generarHTML(
                    "Reporte de Matrículas",
                    new ArrayList<>(matriculas),
                    "reporte_matriculas"
                );
            }

            private void generarReporteCalificaciones(){
                if (calificaciones.isEmpty()) {
                    System.out.println("No hay calificaciones registradas.");
                    return;
                }
                GeneradorReportes.generarHTML(
                    "Reporte de Calificaciones",
                    new ArrayList<>(calificaciones),
                    "reporte_calificaciones"
                );
            }
            
    private void menuOrdenamientos(){
        int opcion;
        do{
            System.out.println("\n=== SISTEMA DE ORDENAMIENTO - MERGE SORT ===");
            System.out.println("1. Ordenar Estudiantes");
            System.out.println("2. Ordenar Profesores");
            System.out.println("3. Ordenar Cursos");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = leerOpcion(0, 3);

            switch(opcion){
                case 1:
                    ordenarEstudiantes();
                    break;
                case 2:
                    ordenarProfesores();
                    break;
                case 3:
                    ordenarCursos();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion!=0);
    }

            private void ordenarEstudiantes(){
                if(estudiantes.isEmpty()){
                    System.out.println("No hay estudiantes registrados.");
                    return;
                }

                List<Estudiante>listaEstudiantes=new ArrayList<>(estudiantes.values());

                System.out.println("\n=== ORDENAR ESTUDIANTES - MERGE SORT ===");
                System.out.println("1. Por Apellidos (A-Z)");
                System.out.println("2. Por Nombres (A-Z)");
                System.out.println("3. Por DNI (Ascendente)");
                System.out.print("Seleccione criterio: ");
                int criterio=Integer.parseInt(scanner.nextLine());

                long startTime=System.currentTimeMillis();

                switch(criterio){
                    case 1:
                        SortUtil.sortBy(listaEstudiantes,Comparator.comparing(Estudiante::getApellidos));
                        System.out.println("Estudiantes ordenados por APELLIDOS");
                        break;
                    case 2:
                        SortUtil.sortBy(listaEstudiantes,Comparator.comparing(Estudiante::getNombres));
                        System.out.println("Estudiantes ordenados por NOMBRES");
                        break;
                    case 3:
                        SortUtil.sortBy(listaEstudiantes,Comparator.comparing(Estudiante::getDni));
                        System.out.println("Estudiantes ordenados por DNI");
                        break;
                    default:
                        System.out.println("Criterio inválido.");
                        return;
                }

                long endTime=System.currentTimeMillis();

                System.out.println("Tiempo de ordenamiento: "+(endTime-startTime)+" ms");
                System.out.println("Total de elementos ordenados: "+listaEstudiantes.size());
                System.out.println("\n=== RESULTADOS ORDENADOS ===");
                for(int i=0;i<listaEstudiantes.size();i++){
                    System.out.println((i+1)+". "+listaEstudiantes.get(i).mostrarInfo());
                }
            }

            private void ordenarProfesores(){
                if(profesores.isEmpty()){
                    System.out.println("No hay profesores registrados.");
                    return;
                }

                List<Profesor>listaProfesores=new ArrayList<>(profesores.values());

                System.out.println("\n=== ORDENAR PROFESORES - MERGE SORT ===");
                System.out.println("1. Por Apellidos (A-Z)");
                System.out.println("2. Por Especialidad (A-Z)");
                System.out.println("3. Por Experiencia (Mayor a menor)");
                System.out.print("Seleccione criterio: ");
                int criterio=Integer.parseInt(scanner.nextLine());

                long startTime=System.currentTimeMillis();

                switch(criterio){
                    case 1:
                        SortUtil.sortBy(listaProfesores, Comparator.comparing(Profesor::getApellidos));
                        System.out.println("Profesores ordenados por APELLIDOS");
                        break;
                    case 2:
                        SortUtil.sortBy(listaProfesores, Comparator.comparing(Profesor::getEspecialidad));
                        System.out.println("Profesores ordenados por ESPECIALIDAD");
                        break;
                    case 3:
                        SortUtil.sortBy(listaProfesores, Comparator.comparingInt(Profesor::getExperiencia).reversed());
                        System.out.println("Profesores ordenados por EXPERIENCIA (Mayor a menor)");
                        break;
                    default:
                        System.out.println("Criterio inválido.");
                        return;
                }

                long endTime=System.currentTimeMillis();
                System.out.println("Tiempo de ordenamiento: "+(endTime-startTime)+" ms");

                System.out.println("\n=== RESULTADOS ORDENADOS ===");
                for (int i=0; i<listaProfesores.size();i++){
                    System.out.println((i + 1) + ". " + listaProfesores.get(i).mostrarInfo());
                }
            }

            private void ordenarCursos(){
                if(cursos.isEmpty()){
                    System.out.println("No hay cursos registrados.");
                    return;
                }
                List<Curso>listaCursos=new ArrayList<>(cursos.values());
                System.out.println("\n=== ORDENAR CURSOS - MERGE SORT ===");
                System.out.println("1. Por Nombre (A-Z)");
                System.out.println("2. Por Idioma (A-Z)");
                System.out.println("3. Por Precio (Menor a mayor)");
                System.out.print("Seleccione criterio: ");
                int criterio=Integer.parseInt(scanner.nextLine());

                long startTime=System.currentTimeMillis();

                switch(criterio){
                    case 1:
                        SortUtil.sortBy(listaCursos,Comparator.comparing(Curso::getNombre));
                        System.out.println("Cursos ordenados por NOMBRE");
                        break;
                    case 2:
                        SortUtil.sortBy(listaCursos,Comparator.comparing(Curso::getIdioma));
                        System.out.println("Cursos ordenados por IDIOMA");
                        break;
                    case 3:
                        SortUtil.sortBy(listaCursos,Comparator.comparingDouble(Curso::getPrecio));
                        System.out.println("Cursos ordenados por PRECIO (Menor a mayor)");
                        break;
                    default:
                        System.out.println("Criterio inválido.");
                        return;
                }

                long endTime=System.currentTimeMillis();
                System.out.println("Tiempo de ordenamiento: "+(endTime-startTime)+" ms");
                System.out.println("\n=== RESULTADOS ORDENADOS ===");
                for(int i=0;i<listaCursos.size();i++){
                    System.out.println((i + 1)+". "+listaCursos.get(i).mostrarInfo());
                }
            }

    private void buscarEnTodosLosArchivos() {
        System.out.print("\nIngrese término de búsqueda global (mín. 3 caracteres): ");
        String termino = scanner.nextLine().trim().toLowerCase();

        if (termino.length() < 3) {
            System.out.println("El término debe tener al menos 3 caracteres.");
            return;
        }

        long inicio = System.currentTimeMillis();
        int totalResultados = 0;

        System.out.println("\n=== RESULTADOS DE BÚSQUEDA GLOBAL ===");

        // === Estudiantes ===
        System.out.println("\nEstudiantes encontrados:");
        boolean encontrado = false;
        for (Estudiante e : estudiantes.values()) {
            String nombreCompleto = (e.getNombres() + " " + e.getApellidos()).toLowerCase();
            if (nombreCompleto.contains(termino) || e.getDni().equalsIgnoreCase(termino)) {
                System.out.println(" - " + e.mostrarInfo());
                totalResultados++;
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("   No se encontraron estudiantes.");
        }

        // === Profesores ===
        System.out.println("\nProfesores encontrados:");
        encontrado = false;
        for (Profesor p : profesores.values()) {
            if (p.getNombres().toLowerCase().contains(termino) ||
                p.getApellidos().toLowerCase().contains(termino) ||
                p.getDni().equalsIgnoreCase(termino) ||
                p.getEspecialidad().toLowerCase().contains(termino)) {
                System.out.println(" - " + p.mostrarInfo());
                totalResultados++;
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("   No se encontraron profesores.");
        }

        // === Cursos ===
        System.out.println("\nCursos encontrados:");
        encontrado = false;
        for (Curso c : cursos.values()) {
            if (c.getNombre().toLowerCase().contains(termino) ||
                c.getIdioma().toLowerCase().contains(termino) ||
                c.getCodigo().equalsIgnoreCase(termino) ||
                c.getNivel().toLowerCase().contains(termino)) {
                System.out.println(" - " + c.mostrarInfo());
                totalResultados++;
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("   No se encontraron cursos.");
        }

        long fin = System.currentTimeMillis();
        System.out.println("\n=== RESUMEN ===");
        System.out.println("Total de resultados: " + totalResultados);
        System.out.println("Tiempo de búsqueda: " + (fin - inicio) + " ms");
    }
    
    
//polimorfismo
    private void validarTodasLasEntidades(){
        System.out.println("\n=== VALIDACIÓN GENERAL DE ENTIDADES ===");
        List<IValidable> todasLasEntidades=new ArrayList<>();
        todasLasEntidades.addAll(estudiantes.values());
        todasLasEntidades.addAll(profesores.values());
        todasLasEntidades.addAll(cursos.values());
        todasLasEntidades.addAll(nivelesIdioma.values());
        todasLasEntidades.addAll(matriculas);
        todasLasEntidades.addAll(calificaciones);
        int validas=0,invalidas=0;
        Map<String,Integer>invalidosPorTipo=new HashMap<>();
        for(IValidable entidad:todasLasEntidades){
            if(entidad.validar()){
                validas++;
            }else{
                invalidas++;
                if (entidad instanceof IEntidad){
                    IEntidad entidadConTipo=(IEntidad) entidad;
                    String tipo = entidadConTipo.getTipo();
                    System.out.println("" +tipo+" inválido: "+entidad.getMensajeError());
                    invalidosPorTipo.put(tipo,invalidosPorTipo.getOrDefault(tipo,0)+ 1);
                }
            }
        }

        System.out.println("\nResumen de validación:");
        System.out.println("Entidades válidas: "+validas);
        System.out.println("Entidades inválidas: " +invalidas);
        System.out.println("Total: "+todasLasEntidades.size());
        if (!invalidosPorTipo.isEmpty()){
            System.out.println("\nDetalle de entidades inválidas por tipo:");
            for (Map.Entry<String, Integer> entry:invalidosPorTipo.entrySet()){
                System.out.println("   • "+entry.getKey()+ ": "+entry.getValue()+" inválidos");
            }
        }
        System.out.println("\n?ESTADÍSTICAS POR TIPO:");
        System.out.println("Estudiantes: "+estudiantes.size());
        System.out.println("Profesores: "+profesores.size());
        System.out.println("Cursos: "+cursos.size());
        System.out.println("Niveles de Idioma: "+nivelesIdioma.size());
        System.out.println("Matrículas: "+matriculas.size());
        System.out.println("Calificaciones: "+calificaciones.size());
    }
    
    private void mostrarTodasLasEntidades(){
        System.out.println("\n=== INFORMACIÓN GENERAL DEL SISTEMA ===");

        List<IEntidad> todasLasEntidades=new ArrayList<>();
        todasLasEntidades.addAll(estudiantes.values());
        todasLasEntidades.addAll(profesores.values());
        todasLasEntidades.addAll(cursos.values());
        todasLasEntidades.addAll(nivelesIdioma.values());
        todasLasEntidades.addAll(matriculas);
        todasLasEntidades.addAll(calificaciones);

        for (IEntidad entidad:todasLasEntidades){
            System.out.println("["+entidad.getTipo()+"] "+entidad.mostrarInfo());
            System.out.println("------------------------------------------------------");
        }
        System.out.println("Total de entidades en el sistema: "+todasLasEntidades.size());
    }
    
    private String leerDNI(){
        while(true){
            try{
                System.out.print("DNI: ");
                String dni=scanner.nextLine().trim();
                Validador.validarDNI(dni);
                return dni;
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
                System.out.println("Por favor, ingrese un DNI válido (8 dígitos).");
            }
        }
    }

    private String leerEmail(){
        while(true){
            try{
                System.out.print("Correo electrónico: ");
                String email=scanner.nextLine().trim();
                Validador.validarEmail(email);
                return email;
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private String leerTelefono(){
        while(true){
            try{
                System.out.print("Teléfono: ");
                String telefono=scanner.nextLine().trim();
                Validador.validarTelefono(telefono);
                return telefono;
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private String leerFecha(String mensaje){
        while(true){
            try{
                System.out.print(mensaje);
                String fecha=scanner.nextLine().trim();
                Validador.validarFecha(fecha, "fecha");
                return fecha;
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private int leerEnteroValidado(String mensaje,int min,int max){
        while(true){
            try{
                System.out.print(mensaje);
                int valor=Integer.parseInt(scanner.nextLine().trim());
                Validador.validarRangoEntero(valor, min, max, "valor");
                return valor;
            }catch(NumberFormatException e){
                System.out.println("Error: Debe ingresar un número entero válido.");
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    private double leerDoubleValidado(String mensaje,double min,double max){
        while(true){
            try{
                System.out.print(mensaje);
                double valor=Double.parseDouble(scanner.nextLine().trim());
                Validador.validarRangoDouble(valor,min,max,"valor");
                return valor;
            }catch(NumberFormatException e){
                System.out.println("Error: Debe ingresar un número válido.");
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    private int leerOpcion(int min, int max) {
    while (true) {
        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;
            int valor = Integer.parseInt(input);
            if (valor >= min && valor <= max) return valor;
            System.out.println("Opción fuera de rango. Intente de nuevo.");
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Ingrese un número.");
        }
    }
}
    
            private void listarEstudiantesPorNivel() {
            System.out.println("\n=== ESTUDIANTES AGRUPADOS POR NIVEL DE ESTUDIOS ===");

            Map<String, List<Estudiante>> agrupados = estudiantes.values().stream()
                .collect(Collectors.groupingBy(Estudiante::getNivelEstudios));

            if (agrupados.isEmpty()) {
                System.out.println("No hay estudiantes registrados.");
                return;
            }

            agrupados.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    System.out.println("\n🔹 " + entry.getKey() + " (" + entry.getValue().size() + " estudiantes):");
                    entry.getValue().stream()
                        .sorted(Comparator.comparing(Estudiante::getApellidos))
                        .forEach(e -> System.out.println("   • " + e.getApellidos() + ", " + e.getNombres() + " (DNI: " + e.getDni() + ")"));
                });
        }
            
            private void mostrarCursosConMasMatriculas() {
                System.out.println("\n=== CURSOS CON MÁS DE 3 MATRÍCULAS ===");

                // Contar matrículas por curso
                Map<String, Long> conteoMatriculas = matriculas.stream()
                    .collect(Collectors.groupingBy(Matricula::getCodigoCurso, Collectors.counting()));

                // Filtrar y ordenar
                conteoMatriculas.entrySet().stream()
                    .filter(entry -> entry.getValue() > 3)
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(entry -> {
                        Curso c = cursos.get(entry.getKey());
                        String nombre = (c != null) ? c.getNombre() : "Curso no encontrado";
                        System.out.printf("• %s (%s): %d matrículas%n", entry.getKey(), nombre, entry.getValue());
                    });

                if (conteoMatriculas.values().stream().noneMatch(c -> c > 3)) {
                    System.out.println("Ningún curso tiene más de 3 matrículas.");
                }
            }
            
            private void listarEstudiantesSinCalificaciones() {
                System.out.println("\n=== ESTUDIANTES SIN CALIFICACIONES REGISTRADAS ===");

                Set<String> dnisConCalificaciones = calificaciones.stream()
                    .map(Calificacion::getDniEstudiante)
                    .collect(Collectors.toSet());

                List<Estudiante> sinCalif = estudiantes.values().stream()
                    .filter(e -> !dnisConCalificaciones.contains(e.getDni()))
                    .sorted(Comparator.comparing(Estudiante::getApellidos))
                    .collect(Collectors.toList());

                if (sinCalif.isEmpty()) {
                    System.out.println("✅ Todos los estudiantes tienen al menos una calificación.");
                } else {
                    System.out.println("Estudiantes sin calificaciones (" + sinCalif.size() + "):");
                    sinCalif.forEach(e -> 
                        System.out.println("• " + e.getApellidos() + ", " + e.getNombres() + " — DNI: " + e.getDni())
                    );
                }
            }
        
            private void cargarDesdeBD() {
               estudiantes.clear();
                try (BufferedReader br = new BufferedReader(new FileReader("estudiantes.txt"))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        String[] partes = linea.split(",");
                        if (partes.length >= 8) {
                            Estudiante e = new Estudiante(
                                partes[0], partes[1], partes[2], partes[3],
                                partes[4], partes[5], partes[6], partes[7]
                            );
                            if (e.validar()) {
                                estudiantes.put(e.getDni().toUpperCase(), e);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Advertencia: archivo estudiantes.txt no encontrado o vacío.");
                }

                // Profesores
                profesores.clear();
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
                            if (p.validar()) {
                                profesores.put(p.getDni().toUpperCase(), p);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Advertencia: archivo profesores.txt no encontrado o vacío.");
                }

                // Cursos
                cursos.clear();
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
                            c.setProfesorDni(c.getProfesorDni().toUpperCase().trim());
                            if (c.validar()) {
                                cursos.put(c.getCodigo().toUpperCase().trim(), c);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Advertencia: archivo cursos.txt no encontrado o vacío.");
                }

                // Matrículas
                matriculas.clear();
                try (BufferedReader br = new BufferedReader(new FileReader("matriculas.txt"))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        String[] partes = linea.split(",");
                        if (partes.length >= 4) {
                            Matricula m = new Matricula(
                                partes[0].trim().toUpperCase(),
                                partes[1].trim().toUpperCase(),
                                partes[2].trim(),
                                Double.parseDouble(partes[3])
                            );
                            if (m.validar()) {
                                matriculas.add(m);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Advertencia: archivo matriculas.txt no encontrado o vacío.");
                }

                // Calificaciones
                calificaciones.clear();
                try (BufferedReader br = new BufferedReader(new FileReader("calificaciones.txt"))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        String[] partes = linea.split(",");
                        if (partes.length >= 5) {
                            Calificacion c = new Calificacion(
                                partes[0].trim().toUpperCase(),
                                partes[1].trim().toUpperCase(),
                                partes[2].trim(),
                                Double.parseDouble(partes[3].trim()),
                                partes[4].trim()
                            );
                            if (c.validar()) {
                                calificaciones.add(c);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Advertencia: archivo calificaciones.txt no encontrado o vacío.");
                }

                // Niveles de idioma
                nivelesIdioma.clear();
                try (BufferedReader br = new BufferedReader(new FileReader("idiomas.txt"))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        String[] partes = linea.split(",");
                        if (partes.length >= 4) {
                            IdiomaNivel in = new IdiomaNivel(partes[0], partes[1], partes[2], partes[3]);
                            if (in.validar()) {
                                nivelesIdioma.put(in.getCodigo().toUpperCase(), in);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Advertencia: archivo idiomas.txt no encontrado o vacío.");
                }

                System.out.println("[DB] Datos cargados desde archivos (modo híbrido).");
            }
            }