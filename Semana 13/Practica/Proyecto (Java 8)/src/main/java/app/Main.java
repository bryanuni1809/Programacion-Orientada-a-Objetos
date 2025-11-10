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
        Scanner scanner=new Scanner(System.in);
        Autenticacion auth=new Autenticacion();
        int opcion;
        do{
            mostrarMenu();
            opcion=leerOpcion(scanner);
            switch(opcion){
                case 1:
                    iniciarSesion(scanner,auth);
                    break;
                case 2:
                    registrarUsuario(scanner,auth);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
            if(opcion!=0){
                System.out.println("\nPresione ENTER para continuar...");
                scanner.nextLine();
            }
        }while(opcion!= 0);
    }
    private static void mostrarMenu(){
        System.out.println("\n===== MULTILINGUA - AUTENTICACION =====");
        System.out.println("1. Iniciar Sesión");
        System.out.println("2. Registrar Nuevo Usuario");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }
    private static int leerOpcion(Scanner scanner){
        try{
            return Integer.parseInt(scanner.nextLine());
        }catch(NumberFormatException e){
            System.out.println("Debe ingresar un número válido.");
            return -1;
        }
    }

    private static String leerEntrada(Scanner scanner,String mensaje){
        String entrada;
        do{
            System.out.print(mensaje);
            entrada=scanner.nextLine().trim();
            if(entrada.isEmpty()){
                System.out.println("No puede estar vacío.");
            }
        }while(entrada.isEmpty());
        return entrada;
    }

    private static void iniciarSesion(Scanner scanner,Autenticacion auth){
        String usuario=leerEntrada(scanner,"Usuario: ");
        String contrasena=leerEntrada(scanner,"Contraseña: ");
        if(auth.validarCredenciales(usuario,contrasena)) {
            System.out.println("Bienvenido," + usuario);
            new gestor.GestorAcademia().mostrarMenu();
        }else{
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    private static void registrarUsuario(Scanner scanner,Autenticacion auth){
        String nuevoUsuario=leerEntrada(scanner,"Nuevo usuario: ");
        String nuevaContrasena=leerEntrada(scanner,"Nueva contraseña: ");
        auth.registrarUsuario(nuevoUsuario, nuevaContrasena);
    }
}