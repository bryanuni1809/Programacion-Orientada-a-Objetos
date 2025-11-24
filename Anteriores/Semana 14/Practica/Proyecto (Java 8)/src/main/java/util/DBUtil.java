/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class DBUtil {
    private static final String URL = "jdbc:sqlite:./academia.db";
    private static Connection conexion;

    private DBUtil() {}

    public static Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                conexion = DriverManager.getConnection(URL);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Driver SQLite no encontrado", e);
            }
        }
        return conexion;
    }

    public static void cerrar() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
            } catch (SQLException e) {
                System.err.println("[DB] Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
