/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package app;

import autenticacion.Autenticacion;
import java.util.Scanner;

/**
 *
 * @author BRYAN
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Autenticacion auth = new Autenticacion();
        int opcion;

        do {
            System.out.println("\n===== MULTILINGUA - AUTENTICACION =====");
            System.out.println("1. Iniciar Sesion");
            System.out.println("2. Registrar Nuevo Usuario");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero valido.");
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Usuario: ");
                    String usuario = scanner.nextLine();
                    System.out.print("Contraseña: ");
                    String contrasena = scanner.nextLine();

                    if (auth.validarCredenciales(usuario, contrasena)) {
                        System.out.println("Bienvenido, " + usuario);
                        new gestor.GestorAcademia().mostrarMenu();
                    } else {
                        System.out.println("Usuario o contraseña incorrectos.");
                    }
                    break;

                case 2:
                    System.out.print("Nuevo usuario: ");
                    String nuevoUsuario = scanner.nextLine().trim();
                    if (nuevoUsuario.isEmpty()) {
                        System.out.println(" El usuario no puede estar vacío.");
                        break;
                    }
                    System.out.print("Nueva contraseña: ");
                    String nuevaContrasena = scanner.nextLine().trim();
                    if (nuevaContrasena.isEmpty()) {
                        System.out.println("La contraseña no puede estar vacía.");
                        break;
                    }
                    auth.registrarUsuario(nuevoUsuario, nuevaContrasena);
                    break;
                    
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opcion invalida.");
                    break;
            }
            if (opcion != 0) {
                System.out.println("\nPresione ENTER para continuar...");
                scanner.nextLine();
            }
        } while (opcion != 0);
            scanner.close();
    }
}