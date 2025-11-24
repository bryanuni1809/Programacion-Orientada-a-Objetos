/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BRYAN
 */
public class ArchivoUtil {

    private ArchivoUtil() {} // Evita instanciación

    /**
     * Guarda una lista de objetos serializables en un archivo binario.
     */
    public static <T extends Serializable> void guardarLista(List<T> lista, String ruta) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(lista);
            System.out.println("[ARCHIVO] Lista guardada correctamente en " + ruta);
        } catch (IOException e) {
            System.out.println("[ARCHIVO] Error al guardar lista: " + e.getMessage());
        }
    }

    /**
     * Carga una lista de objetos serializables desde un archivo binario.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> List<T> cargarLista(String ruta) {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            System.out.println("[ARCHIVO] El archivo no existe, se devolverá una lista vacía.");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                return (List<T>) obj;
            } else {
                System.out.println("[ARCHIVO] El contenido no es una lista válida.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[ARCHIVO] Error al cargar lista: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Añade una sola entidad al final del archivo (modo append seguro).
     */
    public static <T extends Serializable> void agregarEntidad(T entidad, String ruta) {
        List<T> lista = cargarLista(ruta);
        lista.add(entidad);
        guardarLista(lista, ruta);
    }
}