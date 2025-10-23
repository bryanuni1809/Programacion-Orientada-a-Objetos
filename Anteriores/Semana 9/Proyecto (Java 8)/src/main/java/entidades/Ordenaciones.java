/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Comparator;
import java.util.List;

/**
 *
 * @author BRYAN
 */
public class Ordenaciones {
    private Ordenaciones() {}
    public static <T> void ordenar(List<T> lista, Comparator<T> comp) {
        if (lista==null || lista.size() <= 1) {
            return;
        }
        mergeSort(lista, 0, lista.size() - 1, comp);
    }
    
    private static <T> void mergeSort(List<T> lista,int izquierda,int derecha,Comparator<T> comp) {
        if (izquierda < derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;
            
            mergeSort(lista, izquierda, medio, comp);
            mergeSort(lista, medio + 1, derecha, comp);
            
            fusionar(lista, izquierda, medio, derecha, comp);
        }
    }
    
    @SuppressWarnings("unchecked")
    private static <T> void fusionar(List<T> lista, int izquierda,int medio,int derecha,Comparator<T> comp) {
        int n1 =medio- izquierda + 1;
        int n2 =derecha - medio;
        Object[] izquierdaArray =new Object[n1];
        Object[] derechaArray =new Object[n2];
        for (int i = 0; i < n1; i++) {
            izquierdaArray[i] = lista.get(izquierda + i);
        }
        for (int j = 0; j < n2; j++) {
            derechaArray[j] = lista.get(medio + 1 + j);
        }
        int i =0, j =0, k =izquierda;
        
        while (i< n1 && j< n2){
            if (comp.compare((T)izquierdaArray[i],(T) derechaArray[j])<= 0) {
                lista.set(k,(T)izquierdaArray[i]);
                i++;
            }else{
                lista.set(k,(T) derechaArray[j]);
                j++;
            }
            k++;
        }
        while(i< n1){
            lista.set(k,(T) izquierdaArray[i]);
            i++;
            k++;
        }
        while(j< n2){
            lista.set(k,(T) derechaArray[j]);
            j++;
            k++;
        }
    }
}
