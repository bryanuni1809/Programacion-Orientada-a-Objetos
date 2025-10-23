/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author BRYAN
 */
public class ExternalSort {
    public static List<File> dividirYOrdenar(File archivoEntrada,int tamanoBloque) throws IOException {
        List<File> archivosTemporales =new ArrayList<>();
        try(BufferedReader br =new BufferedReader(new FileReader(archivoEntrada))){
            List<String> buffer=new ArrayList<>();
            String linea;
            while((linea=br.readLine()) != null) {
                buffer.add(linea);
                if (buffer.size() >=tamanoBloque) {
                    archivosTemporales.add(guardarOrdenado(buffer));
                    buffer.clear();
                }
            }
            if(!buffer.isEmpty()){
                archivosTemporales.add(guardarOrdenado(buffer));
            }
        }
        return archivosTemporales;
    }
    private static File guardarOrdenado(List<String> datos) throws IOException{
        datos.sort(Comparator.naturalOrder());
        File temp=File.createTempFile("bloque", ".txt");
        temp.deleteOnExit();
        try (BufferedWriter bw=new BufferedWriter(new FileWriter(temp))) {
            for (String linea:datos){
                bw.write(linea);
                bw.newLine();
            }
        }
        return temp;
    }
    public static void mezclarArchivos(List<File> archivos, File archivoSalida) throws IOException {
        PriorityQueue<BufferedReader> cola=new PriorityQueue<>(
            Comparator.comparing(br -> {
                try{
                    br.mark(1000);
                    String linea = br.readLine();
                    br.reset();
                    return linea;
                }catch(IOException e){
                    return null;
                }
            })
        );

        for (File f :archivos) {
            cola.add(new BufferedReader(new FileReader(f)));
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))) {
            while (!cola.isEmpty()) {
                BufferedReader br = cola.poll();
                String linea = br.readLine();
                if (linea != null) {
                    bw.write(linea);
                    bw.newLine();
                    br.mark(1000);
                    String siguiente = br.readLine();
                    if (siguiente != null) {
                        br.reset();
                        cola.add(br);
                    } else {
                        br.close();
                    }
                }
            }
        }
    }
    public static void externalSort(File archivoEntrada, File archivoSalida, int tamanoBloque) throws IOException {
        List<File> archivos = dividirYOrdenar(archivoEntrada, tamanoBloque);
        mezclarArchivos(archivos, archivoSalida);
    }

    public static void main(String[]args) throws IOException {
        File entrada = new File("estudiantes.txt");
        File salida = new File("estudiantes_ordenados.txt");
        externalSort(entrada,salida,1000);
        System.out.println("Archivo ordenado generado: "+salida.getAbsolutePath());
    }
}

