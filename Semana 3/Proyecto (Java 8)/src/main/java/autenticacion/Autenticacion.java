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
private static final String ARCHIVO = "usuarios.txt";

    // Método que valida si un usuario y contraseña existen en el archivo
    // Complejidad: O(n) (búsqueda lineal)
    public boolean validarCredenciales(String usuario, String contrasena) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
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
            System.out.println("Error al leer usuarios: " + ex.getMessage());
        }
        return false;
    }

    private boolean usuarioExiste(String usuario) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String u = partes[0].trim();
                    if (u.equals(usuario)) {
                        return true;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al verificar usuario: " + ex.getMessage());
        }
        return false;
    }

    // Método que registra un nuevo usuario en el archivo
    // Complejidad: O(1) (agrega al final del archivo)
    public void registrarUsuario(String usuario, String contrasena) {
        if (usuarioExiste(usuario)) {
            System.out.println("El usuario '" + usuario + "' ya está registrado.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            writer.write(usuario + "," + contrasena);
            writer.newLine();
            System.out.println("Usuario registrado con éxito.");
        } catch (IOException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
    }
}
