/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entidades.Calificacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DBUtil;

/**
 *
 * @author USER
 */
public class CalificacionDAO {

    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS calificaciones (" +
                     "codigoCurso TEXT NOT NULL," +
                     "dniEstudiante TEXT NOT NULL," +
                     "fecha TEXT NOT NULL," +
                     "nota REAL NOT NULL," +
                     "observaciones TEXT NOT NULL," +
                     "PRIMARY KEY (codigoCurso, dniEstudiante, fecha)" +
                     ");";
        try (Connection conn = DBUtil.obtenerConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ex) {
            System.err.println("[DB] Error al crear tabla calificaciones: " + ex.getMessage());
        }
    }

    public boolean insertar(Calificacion c) {
        String sql = "INSERT INTO calificaciones(codigoCurso, dniEstudiante, fecha, nota, observaciones) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, c.getCodigoCurso());
            pstmt.setString(2, c.getDniEstudiante());
            pstmt.setString(3, c.getFecha());
            pstmt.setDouble(4, c.getNota());
            pstmt.setString(5, c.getObservaciones());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("[DB] Error al insertar calificación (Estudiante=" + c.getDniEstudiante() + ", Curso=" + c.getCodigoCurso() + "): " + ex.getMessage());
            return false;
        }
    }

    public List<Calificacion> listarTodas() {
        List<Calificacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM calificaciones ORDER BY fecha DESC";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Calificacion(
                    rs.getString("codigoCurso"),
                    rs.getString("dniEstudiante"),
                    rs.getString("fecha"),
                    rs.getDouble("nota"),
                    rs.getString("observaciones")
                ));
            }
        } catch (SQLException ex) {
            System.err.println("[DB] Error al listar calificaciones: " + ex.getMessage());
        }
        return lista;
    }

    public boolean eliminar(String codigoCurso, String dniEstudiante, String fecha) {
        String sql = "DELETE FROM calificaciones WHERE codigoCurso = ? AND dniEstudiante = ? AND fecha = ?";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigoCurso);
            pstmt.setString(2, dniEstudiante);
            pstmt.setString(3, fecha);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("[DB] Error al eliminar calificación: " + ex.getMessage());
            return false;
        }
    }
}
