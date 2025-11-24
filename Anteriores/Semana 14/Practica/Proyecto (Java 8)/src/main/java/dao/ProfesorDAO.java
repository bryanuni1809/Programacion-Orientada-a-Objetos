/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entidades.Profesor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DBUtil;

/**
 *
 * @author USER
 */
public class ProfesorDAO {

    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS profesores (" +
                     "dni TEXT PRIMARY KEY," +
                     "nombres TEXT NOT NULL," +
                     "apellidos TEXT NOT NULL," +
                     "direccion TEXT NOT NULL," +
                     "telefono TEXT NOT NULL," +
                     "correo TEXT NOT NULL," +
                     "especialidad TEXT NOT NULL," +
                     "experiencia INTEGER NOT NULL" +
                     ");";
        try (Connection conn = DBUtil.obtenerConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ex) {
            System.err.println("[DB] Error al crear tabla profesores: " + ex.getMessage());
        }
    }

    public boolean insertar(Profesor p) {
        String sql = "INSERT INTO profesores(dni, nombres, apellidos, direccion, telefono, correo, especialidad, experiencia) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getDni());
            pstmt.setString(2, p.getNombres());
            pstmt.setString(3, p.getApellidos());
            pstmt.setString(4, p.getDireccion());
            pstmt.setString(5, p.getTelefono());
            pstmt.setString(6, p.getCorreo());
            pstmt.setString(7, p.getEspecialidad());
            pstmt.setInt(8, p.getExperiencia());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("[DB] Error al insertar profesor (" + p.getDni() + "): " + ex.getMessage());
            return false;
        }
    }

    public List<Profesor> listarTodos() {
        List<Profesor> lista = new ArrayList<>();
        String sql = "SELECT * FROM profesores ORDER BY apellidos, nombres";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Profesor(
                    rs.getString("dni"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("especialidad"),
                    rs.getInt("experiencia")
                ));
            }
        } catch (SQLException ex) {
            System.err.println("[DB] Error al listar profesores: " + ex.getMessage());
        }
        return lista;
    }

    public boolean eliminar(String dni) {
        String sql = "DELETE FROM profesores WHERE dni = ?";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("[DB] Error al eliminar profesor: " + ex.getMessage());
            return false;
        }
    }
}
