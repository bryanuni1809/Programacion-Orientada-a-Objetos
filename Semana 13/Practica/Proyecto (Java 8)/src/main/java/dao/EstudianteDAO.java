/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entidades.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.DBUtil;
/**
 *
 * @author USER
 */
public class EstudianteDAO {

    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS estudiantes (\n" +
             "    dni TEXT PRIMARY KEY,\n" +
             "    nombres TEXT NOT NULL,\n" +
             "    apellidos TEXT NOT NULL,\n" +
             "    direccion TEXT NOT NULL,\n" +
             "    telefono TEXT NOT NULL,\n" +
             "    correo TEXT NOT NULL,\n" +
             "    fechaNacimiento TEXT NOT NULL,\n" +
             "    nivelEstudios TEXT NOT NULL\n" +
             ");";
        try (Connection conn = DBUtil.obtenerConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("[DB] Error al crear tabla estudiantes: " + e.getMessage());
        }
    }

    public boolean insertar(Estudiante e) {
        String sql = "INSERT INTO estudiantes(dni, nombres, apellidos, direccion, telefono, correo, "
           + "fechaNacimiento, nivelEstudios) "
           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, e.getDni());
            pstmt.setString(2, e.getNombres());
            pstmt.setString(3, e.getApellidos());
            pstmt.setString(4, e.getDireccion());
            pstmt.setString(5, e.getTelefono());
            pstmt.setString(6, e.getCorreo());
            pstmt.setString(7, e.getFechaNacimiento());
            pstmt.setString(8, e.getNivelEstudios());
            return pstmt.executeUpdate() > 0;
        }catch (SQLException ex) {
            System.err.println("[DB] Error al insertar estudiante: " + ex.getMessage());
            return false;
        }
    }

    public List<Estudiante> listarTodos() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes ORDER BY apellidos, nombres";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Estudiante(
                    rs.getString("dni"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("fechaNacimiento"),
                    rs.getString("nivelEstudios")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[DB] Error al listar estudiantes: " + e.getMessage());
        }
        return lista;
    }

    public boolean eliminar(String dni) {
        String sql = "DELETE FROM estudiantes WHERE dni = ?";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[DB] Error al eliminar estudiante: " + e.getMessage());
            return false;
        }
    }
}