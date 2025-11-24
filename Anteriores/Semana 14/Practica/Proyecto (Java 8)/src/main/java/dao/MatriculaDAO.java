/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entidades.Matricula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DBUtil;

/**
 *
 * @author USER
 */
public class MatriculaDAO {

    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS matriculas (" +
                     "codigoCurso TEXT NOT NULL," +
                     "dniEstudiante TEXT NOT NULL," +
                     "fechaMatricula TEXT NOT NULL," +
                     "monto REAL NOT NULL," +
                     "PRIMARY KEY (codigoCurso, dniEstudiante)" +
                     ");";
        try (Connection conn = DBUtil.obtenerConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ex) {
            System.err.println("[DB] Error al crear tabla matriculas: " + ex.getMessage());
        }
    }

    public boolean insertar(Matricula m) {
        String sql = "INSERT INTO matriculas(codigoCurso, dniEstudiante, fechaMatricula, monto) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, m.getCodigoCurso());
            pstmt.setString(2, m.getDniEstudiante());
            pstmt.setString(3, m.getFechaMatricula());
            pstmt.setDouble(4, m.getMonto());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("[DB] Error al insertar matrícula (Estudiante=" + m.getDniEstudiante() + ", Curso=" + m.getCodigoCurso() + "): " + ex.getMessage());
            return false;
        }
    }

    public List<Matricula> listarTodas() {
        List<Matricula> lista = new ArrayList<>();
        String sql = "SELECT * FROM matriculas ORDER BY fechaMatricula DESC";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Matricula(
                    rs.getString("codigoCurso"),
                    rs.getString("dniEstudiante"),
                    rs.getString("fechaMatricula"),
                    rs.getDouble("monto")
                ));
            }
        } catch (SQLException ex) {
            System.err.println("[DB] Error al listar matrículas: " + ex.getMessage());
        }
        return lista;
    }

    public boolean eliminar(String codigoCurso, String dniEstudiante) {
        String sql = "DELETE FROM matriculas WHERE codigoCurso = ? AND dniEstudiante = ?";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigoCurso);
            pstmt.setString(2, dniEstudiante);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("[DB] Error al eliminar matrícula: " + ex.getMessage());
            return false;
        }
    }
}
