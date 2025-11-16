/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import interfaces.IEntidad;
/**
 *
 * @author BRYAN
 */
public class GeneradorReportes {

    public static void generarHTML(String titulo, List<? extends IEntidad> lista, String nombreArchivo) {
        File carpeta = new File("reportes");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
        File archivo = new File(carpeta, nombreArchivo + ".html");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write("<!DOCTYPE html>");
            writer.write("<html><head><meta charset='UTF-8'><title>" + titulo + "</title>");
            writer.write("<style>");
            writer.write("body { font-family: Arial; margin: 20px; }");
            writer.write("h1 { color: #003366; }");
            writer.write("table { width: 100%; border-collapse: collapse; }");
            writer.write("th, td { border: 1px solid #999; padding: 8px; text-align: left; }");
            writer.write("th { background-color: #cce5ff; }");
            writer.write("</style></head><body>");
            writer.write("<h1>" + titulo + "</h1>");
            writer.write("<table><tr><th>#</th><th>Información</th></tr>");
            int contador = 1;
            for (IEntidad e : lista) {
                writer.write("<tr><td>" + contador++ + "</td><td>" + e.mostrarInfo() + "</td></tr>");
            }
            writer.write("</table>");
            writer.write("<p>Total de registros: " + lista.size() + "</p>");
            writer.write("</body></html>");
            System.out.println("Reporte generado: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }
}
