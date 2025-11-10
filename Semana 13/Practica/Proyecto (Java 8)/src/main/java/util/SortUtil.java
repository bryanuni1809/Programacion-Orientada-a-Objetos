/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.Comparator;
import java.util.List;
/**
 *
 * @author BRYAN
 */
public class SortUtil {

    private SortUtil() {} // Evita instanciación

    /**
     * Ordena una lista según un comparador.
     * Ejemplo: SortUtil.sortBy(list, Comparator.comparing(Persona::getNombre));
     */
    public static <T> void sortBy(List<T> list, Comparator<T> comparator) {
        if (list != null && list.size() > 1) {
            list.sort(comparator);
        }
    }

    /**
     * Ordena una lista ascendente usando su orden natural (Comparable).
     */
    public static <T extends Comparable<T>> void sortNatural(List<T> list) {
        if (list != null && list.size() > 1) {
            list.sort(Comparator.naturalOrder());
        }
    }

    /**
     * Ordena una lista descendente usando su orden natural.
     */
    public static <T extends Comparable<T>> void sortReverse(List<T> list) {
        if (list != null && list.size() > 1) {
            list.sort(Comparator.reverseOrder());
        }
    }
}
