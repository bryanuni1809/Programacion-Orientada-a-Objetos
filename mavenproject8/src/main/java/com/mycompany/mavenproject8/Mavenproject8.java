/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject8;

import java.util.Scanner;

/**
 *
 * @author LAB-USR-AREQUIPA
 */
public class Mavenproject8 {

    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        //Creamos un arreglo de numeros enteros
        int[] numeros ={3,8,15,23,42,7,19,27,30};
        //Pedimos al usuario el numero que desea buscar
         System.out.print("Ingrese el numero a buscar: ");
         int buscado =scanner.nextInt();
         boolean posicion =busquedaLineal(numeros,buscado);
        if(posicion){
            System.out.println("Elemento encontrado");
        }else{
            System.out.println("Elemento no encontrado");
        }
    }
    public static boolean busquedaLineal(int[] arreglo,int A){
        //El for-each simplifica la lectura del arreglo
        for (int num :arreglo){
            //Esta es la operacion basica que nos sirve para analizar la complejidad O(n).
            //O(1): si el elemento está al inicio.
            //O(n): si está al final o no está.
            if (num ==A){
                return true;
            }
        }
        return false;
    }
}
