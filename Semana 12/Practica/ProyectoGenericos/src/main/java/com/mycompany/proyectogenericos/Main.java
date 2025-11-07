/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectogenericos;

/**
 *
 * @author BRYAN
 */
public class Main{
    public static void main(String[]args){
        Caja<String>cajaTexto=new Caja<>("Hola mundo");
        cajaTexto.mostrarTipo();
        System.out.println("Contenido: "+cajaTexto.getContenido());
        Caja<Integer>cajaNumero=new Caja<>(123);
        cajaNumero.mostrarTipo();
        System.out.println("Contenido: "+cajaNumero.getContenido());
    }
}
