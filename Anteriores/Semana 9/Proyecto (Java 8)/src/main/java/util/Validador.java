/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 *
 * @author BRYAN
 */
public class Validador {
    // Patrones de validación
    private static final Pattern EMAIL_REGEX= 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    private static final Pattern DNI_REGEX= 
        Pattern.compile("^[0-9]{8}$");
    
    private static final Pattern TELEFONO_REGEX= 
        Pattern.compile("^9[0-9]{8}$");
    
    private static final Pattern SOLO_LETRAS_REGEX = 
        Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    
    private static final Pattern CODIGO_CURSO_REGEX = 
        Pattern.compile("^[A-Z]{3}-[0-9]{3}$");
    
    private static final Pattern SOLO_NUMEROS_REGEX= 
        Pattern.compile("^[0-9]+$");
    
    private static final DateTimeFormatter DATE_FORMATTER= 
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ========== VALIDACIONES BÁSICAS ==========
    
    public static void validarNoVacio(String texto,String nombreCampo){
        if (texto==null || texto.trim().isEmpty()){
            throw new IllegalArgumentException("El campo '" + nombreCampo + "' no puede estar vacío");
        }
    }
    
    public static void validarLongitud(String texto, String nombreCampo, int min, int max){
        validarNoVacio(texto, nombreCampo);
        if (texto.length()<min || texto.length()>max){
            throw new IllegalArgumentException(
                "El campo '" + nombreCampo + "' debe tener entre " + min + " y " + max + " caracteres"
            );
        }
    }
    
    // ========== VALIDACIONES ESPECÍFICAS ==========
    
    public static void validarDNI(String dni){
        validarNoVacio(dni, "DNI");
        
        if (!DNI_REGEX.matcher(dni).matches()){
            throw new IllegalArgumentException("El DNI debe tener exactamente 8 dígitos numéricos");
        }
    }
    
    public static void validarEmail(String email){
        validarNoVacio(email,"correo electrónico");
        
        if (!EMAIL_REGEX.matcher(email).matches()){
            throw new IllegalArgumentException("Formato de correo electrónico inválido");
        }
    }
    
    public static void validarTelefono(String telefono){
        validarNoVacio(telefono, "teléfono");
        
        if (!TELEFONO_REGEX.matcher(telefono).matches()){
            throw new IllegalArgumentException("El teléfono debe tener 9 dígitos y comenzar con 9");
        }
    }
    
    public static void validarSoloLetras(String texto,String nombreCampo){
        validarNoVacio(texto,nombreCampo);
        
        if (!SOLO_LETRAS_REGEX.matcher(texto).matches()){
            throw new IllegalArgumentException("El campo '"+nombreCampo+"' solo puede contener letras y espacios");
        }
    }
    
    public static void validarSoloNumeros(String texto,String nombreCampo){
        validarNoVacio(texto,nombreCampo);
        
        if (!SOLO_NUMEROS_REGEX.matcher(texto).matches()){
            throw new IllegalArgumentException("El campo '"+ nombreCampo+"' solo puede contener números");
        }
    }
    
    public static void validarCodigoCurso(String codigo){
        validarNoVacio(codigo,"código de curso");
        
        if (!CODIGO_CURSO_REGEX.matcher(codigo).matches()){
            throw new IllegalArgumentException("Formato de código de curso inválido. Use: XXX-999 (ej: ENG-101)");
        }
    }
    
    public static void validarFecha(String fecha,String nombreCampo){
        validarNoVacio(fecha,nombreCampo);
        
        try{
            LocalDate.parse(fecha,DATE_FORMATTER);
        }catch(DateTimeParseException e){
            throw new IllegalArgumentException("Formato de fecha inválido para '"+nombreCampo+"'. Use: dd/MM/yyyy");
        }
    }
    
    public static void validarFechaPasada(String fecha,String nombreCampo){
        validarFecha(fecha,nombreCampo);
        LocalDate fechaDate=LocalDate.parse(fecha, DATE_FORMATTER);
        LocalDate hoy=LocalDate.now();
        
        if(fechaDate.isAfter(hoy)){
            throw new IllegalArgumentException("La fecha '" +nombreCampo + "' no puede ser futura");
        }
    }
    
    public static void validarRangoEntero(int valor,int min,int max,String nombreCampo){
        if(valor < min || valor > max){
            throw new IllegalArgumentException(
                "El campo '" + nombreCampo + "' debe estar entre "+min+ " y " + max
            );
        }
    }
    
    public static void validarRangoDouble(double valor,double min,double max, String nombreCampo){
        if (valor< min || valor>max){
            throw new IllegalArgumentException(
                "El campo '" +nombreCampo +"' debe estar entre "+min +" y " + max
            );
        }
    }
    
    public static void validarNota(double nota){
        validarRangoDouble(nota,0,20,"nota");
    }
    
    public static void validarPrecio(double precio){
        if (precio<0){
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
    }
    
    public static void validarEdad(int edad,int min,int max){
        validarRangoEntero(edad,min,max,"edad");
    }
    
    public static void validarExperiencia(int experiencia){
        if (experiencia<0){
            throw new IllegalArgumentException("Los años de experiencia no pueden ser negativos");
        }
        if (experiencia>50){
            throw new IllegalArgumentException("Los años de experiencia no pueden ser mayores a 50");
        }
    }
    
    // ========== VALIDACIONES DE NEGOCIO ==========
    
    public static void validarCapacidadCurso(int capacidad){
        validarRangoEntero(capacidad,1,50,"capacidad del curso");
    }
    
    public static void validarDuracionCurso(int duracion){
        validarRangoEntero(duracion,1,52,"duración del curso (semanas)");
    }
    
    public static void validarNivelIdioma(String nivel){
        validarNoVacio(nivel,"nivel de idioma");
        
        String[] nivelesValidos = {"A1","A2","B1","B2","C1","C2","Principiante","Intermedio","Avanzado"};
        boolean valido=false;
        
        for(String n :nivelesValidos){
            if(n.equalsIgnoreCase(nivel)){
                valido=true;
                break;
            }
        }
        
        if (!valido){
            throw new IllegalArgumentException(
                "Nivel de idioma inválido. Use: A1, A2, B1, B2, C1, C2, Principiante, Intermedio o Avanzado"
            );
        }
    }
    
    public static void validarIdioma(String idioma){
        validarSoloLetras(idioma,"idioma");
        
        String[] idiomasValidos={"Inglés","Francés","Alemán","Italiano","Portugués","Chino","Japonés"};
        boolean valido=false;
        
        for(String i :idiomasValidos){
            if (i.equalsIgnoreCase(idioma)){
                valido=true;
                break;
            }
        }
        
        if(!valido){
            throw new IllegalArgumentException(
                "Idioma no soportado. Idiomas disponibles: "+String.join(", ",idiomasValidos)
            );
        }
    }
    
    // ========== VALIDACIONES DE EDAD A PARTIR DE FECHA ==========
    
    public static int calcularEdad(String fechaNacimiento){
        validarFechaPasada(fechaNacimiento, "fecha de nacimiento");
        
        LocalDate nacimiento=LocalDate.parse(fechaNacimiento,DATE_FORMATTER);
        LocalDate hoy =LocalDate.now();
        
        return hoy.getYear()-nacimiento.getYear()- 
               (hoy.getDayOfYear()<nacimiento.getDayOfYear() ? 1 : 0);
    }
    
    public static void validarEdadEstudiante(String fechaNacimiento,int minEdad,int maxEdad){
        int edad=calcularEdad(fechaNacimiento);
        validarEdad(edad, minEdad, maxEdad);
    }
    
    // ========== MÉTODOS DE VALIDACIÓN CON RESPUESTA BOOLEANA ==========
    
    public static boolean esDNIValido(String dni){
        try{
            validarDNI(dni);
            return true;
        }catch(IllegalArgumentException e){
            return false;
        }
    }
    
    public static boolean esEmailValido(String email){
        try{
            validarEmail(email);
            return true;
        }catch(IllegalArgumentException e){
            return false;
        }
    }
    
    public static boolean esTelefonoValido(String telefono){
        try{
            validarTelefono(telefono);
            return true;
        } catch(IllegalArgumentException e){
            return false;
        }
    }
    
    // ========== UTILIDADES DE FORMATEO ==========
    
    public static String formatearTexto(String texto){
        if(texto==null)return "";
        
        texto=texto.trim();
        if(texto.isEmpty())return "";
        
        // Capitalizar primera letra de cada palabra
        String[] palabras =texto.split("\\s+");
        StringBuilder resultado=new StringBuilder();
        
        for (String palabra:palabras){
            if (!palabra.isEmpty()){
                resultado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        
        return resultado.toString().trim();
    }
    
    public static String formatearCodigoCurso(String codigo){
        if(codigo==null)return "";
        return codigo.trim().toUpperCase();
    }
    
    // ========== VALIDACIÓN DE DATOS COMPLETOS ==========
    
    public static void validarDatosEstudiante(String dni, String nombres, String apellidos, 
                                            String direccion, String telefono, String correo,
                                            String fechaNacimiento, String nivelEstudios) {
        
        validarDNI(dni);
        validarSoloLetras(nombres,"nombres");
        validarSoloLetras(apellidos,"apellidos");
        validarNoVacio(direccion,"dirección");
        validarTelefono(telefono);
        validarEmail(correo);
        validarEdadEstudiante(fechaNacimiento,12,80); // Edad entre 12 y 80 años
        validarNoVacio(nivelEstudios,"nivel de estudios");
    }
    
    public static void validarDatosProfesor(String dni,String nombres,String apellidos,
                                          String direccion,String telefono,String correo,
                                          String especialidad,int experiencia) {
        
        validarDNI(dni);
        validarSoloLetras(nombres,"nombres");
        validarSoloLetras(apellidos,"apellidos");
        validarNoVacio(direccion,"dirección");
        validarTelefono(telefono);
        validarEmail(correo);
        validarSoloLetras(especialidad,"especialidad");
        validarExperiencia(experiencia);
    }
    
    public static void validarDatosCurso(String codigo,String nombre,String idioma,String nivel,
                                       String profesorDni,String horario, int duracion,
                                       int capacidad, double precio, String observaciones) {
        
        validarCodigoCurso(codigo);
        validarNoVacio(nombre,"nombre del curso");
        validarIdioma(idioma);
        validarNivelIdioma(nivel);
        validarDNI(profesorDni);
        validarNoVacio(horario,"horario");
        validarDuracionCurso(duracion);
        validarCapacidadCurso(capacidad);
        validarPrecio(precio);
        validarNoVacio(observaciones,"observaciones");
    }
}

