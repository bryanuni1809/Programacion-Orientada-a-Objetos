/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author BRYAN
 */
public class Ordenaciones {
    private Ordenaciones() {}

    public static <T> void burbuja(List<T> lista, Comparator<T> comp) {
    int n = lista.size();
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (comp.compare(lista.get(j), lista.get(j + 1)) > 0) {
                // Intercambiar
                T temp = lista.get(j);
                lista.set(j, lista.get(j + 1));
                lista.set(j + 1, temp);
            }
        }
    }
}
    public static <T> void seleccion(List<T> lista, Comparator<T> comp) {
    int n = lista.size();
    for (int i = 0; i < n - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < n; j++) {
            if (comp.compare(lista.get(j), lista.get(minIndex)) < 0) {
                minIndex = j;
            }
        }
        // Intercambiar
        T temp = lista.get(i);
        lista.set(i, lista.get(minIndex));
        lista.set(minIndex, temp);
    }
}
    public static <T> void insercion(List<T> lista, Comparator<T> comp) {
    int n = lista.size();
    for (int i = 1; i < n; i++) {
        T key = lista.get(i);
        int j = i - 1;
        while (j >= 0 && comp.compare(lista.get(j), key) > 0) {
            lista.set(j + 1, lista.get(j));
            j--;
        }
        lista.set(j + 1, key);
    }
}
}
