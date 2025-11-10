/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author BRYAN
 */
public class BusquedaExterna {

    /**
     * Busca coincidencias en un archivo de texto o CSV.
     * Si columna es null, busca en toda la línea.
     */
    public static List<String> buscar(String archivo, String termino, Integer columna) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            return br.lines()
                     .filter(linea -> {
                         if (columna == null) return linea.toLowerCase().contains(termino.toLowerCase());
                         String[] partes = linea.split(",");
                         return partes.length > columna &&
                                partes[columna].toLowerCase().contains(termino.toLowerCase());
                     })
                     .collect(Collectors.toList());
        }
    }

    // Sobrecarga simple: busca en todo el archivo
    public static List<String> buscar(String archivo, String termino) throws IOException {
        return buscar(archivo, termino, null);
    }
}
