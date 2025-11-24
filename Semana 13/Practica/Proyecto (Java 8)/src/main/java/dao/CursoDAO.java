/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entidades.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DBUtil;

/**
 *
 * @author USER
 */
public class CursoDAO {

    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS cursos (" +
                     "codigo TEXT PRIMARY KEY," +
                     "nombre TEXT NOT NULL," +
                     "idioma TEXT NOT NULL," +
                     "nivel TEXT NOT NULL," +
                     "profesorDni TEXT NOT NULL," +
                     "horario TEXT NOT NULL," +
                     "duracion INTEGER NOT NULL," +
                     "capacidadMaxima INTEGER NOT NULL," +
                     "precio REAL NOT NULL," +
                     "observaciones TEXT NOT NULL" +
                     ");";
        try (Connection conn = DBUtil.obtenerConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ex) {
            System.err.println("[DB] Error al crear tabla cursos: " + ex.getMessage());
        }
    }

    public boolean insertar(Curso c) {
        String sql = "INSERT INTO cursos(codigo, nombre, idioma, nivel, profesorDni, horario, duracion, capacidadMaxima, precio, observaciones) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, c.getCodigo());
            pstmt.setString(2, c.getNombre());
            pstmt.setString(3, c.getIdioma());
            pstmt.setString(4, c.getNivel());
            pstmt.setString(5, c.getProfesorDni());
            pstmt.setString(6, c.getHorario());
            pstmt.setInt(7, c.getDuracion());
            pstmt.setInt(8, c.getCapacidadMaxima());
            pstmt.setDouble(9, c.getPrecio());
            pstmt.setString(10, c.getObservaciones());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("[DB] Error al insertar curso (" + c.getCodigo() + "): " + ex.getMessage());
            return false;
        }
    }

    public List<Curso> listarTodos() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos ORDER BY idioma, nivel, nombre";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Curso(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("idioma"),
                    rs.getString("nivel"),
                    rs.getString("profesorDni"),
                    rs.getString("horario"),
                    rs.getInt("duracion"),
                    rs.getInt("capacidadMaxima"),
                    rs.getDouble("precio"),
                    rs.getString("observaciones")
                ));
            }
        } catch (SQLException ex) {
            System.err.println("[DB] Error al listar cursos: " + ex.getMessage());
        }
        return lista;
    }

    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM cursos WHERE codigo = ?";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("[DB] Error al eliminar curso: " + ex.getMessage());
            return false;
        }
    }
}
