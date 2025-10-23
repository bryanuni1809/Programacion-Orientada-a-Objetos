/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author BRYAN
 */
public class QuickSortUtil {
    // Método público principal
    public static <T> void quickSort(List<T>lista,Comparator<T>comp){
        if (lista==null||lista.size()<=1)return;
        quickSort(lista,0,lista.size()-1,comp);
    }
    private static <T> void quickSort(List<T> lista,int low,int high,Comparator<T> comp){
        if(low < high){
            int pi = particionar(lista, low, high, comp);
            quickSort(lista, low, pi - 1, comp);
            quickSort(lista, pi + 1, high, comp);
        }
    }
    // Partición usando pivote mediano (optimizado)
    private static <T> int particionar(List<T> lista,int low,int high,Comparator<T> comp){
        int medio=low+(high-low) / 2;
        T pivote=elegirPivoteMediano(lista.get(low),lista.get(medio),lista.get(high),comp);
        if(comp.compare(lista.get(medio), pivote)==0)Collections.swap(lista,medio,high);
        else if(comp.compare(lista.get(low), pivote)==0)Collections.swap(lista,low,high);
        int i=low-1;
        for (int j=low;j<high;j++) {
            if(comp.compare(lista.get(j),pivote)<=0){
                i++;
                Collections.swap(lista,i,j);
            }
        }
        Collections.swap(lista,i+1,high);
        return i+1;
    }
    // Elección del pivote mediano simplificada
    private static <T>T elegirPivoteMediano(T a,T b,T c,Comparator<T>comp) {
        List<T> elementos=Arrays.asList(a,b,c);
        elementos.sort(comp); // Ordena los tres elementos
        return elementos.get(1); // Retorna el mediano
    }
}

