/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.io.IOException;

/**
 *
 * @author BRYAN
 */
public interface IPersistible {
    void guardar() throws IOException;
    void cargar() throws IOException;
}
