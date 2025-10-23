/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BRYAN
 */
public class BusquedaExterna{
     public static List<String>buscarEnArchivo(String archivo, String termino, int columna) throws IOException {
        List<String> resultados=new ArrayList<>();
        try (BufferedReader br =new BufferedReader(new FileReader(archivo))){
            String linea;
            while ((linea=br.readLine())!=null){
                String[] partes= linea.split(",");
                if (partes.length >columna && partes[columna].toLowerCase().contains(termino.toLowerCase())) {
                    resultados.add(linea);
                }
            }
        }
        return resultados;
    }
    
    public static List<String>buscarEnArchivoMultiple(String archivo, String termino) throws IOException {
        List<String> resultados=new ArrayList<>();
        try (BufferedReader br=new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine())!= null) {
                if (linea.toLowerCase().contains(termino.toLowerCase())) {
                    resultados.add(linea);
                }
            }
        }
        return resultados;
    }
}
