/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autenticacion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author BRYAN
 */
public class Autenticacion {
    public boolean validarCredenciales(String usuario, String contrasena) {
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String u = partes[0].trim();
                    String c = partes[1].trim();
                    if (u.equals(usuario) && c.equals(contrasena)) {
                        return true;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al leer usuarios.txt: " + ex.getMessage());
        }
        return false;
    }
    public void registrarUsuario(String usuario, String contrasena) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios.txt", true))) {
        writer.write(usuario + "," + contrasena);
        writer.newLine();
        System.out.println("Usuario registrado con exito.");
    } catch (IOException e) {
        System.out.println("Error al registrar usuario: " + e.getMessage());
    }
}

}