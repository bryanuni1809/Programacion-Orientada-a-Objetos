/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import util.DBUtil;

/**
 *
 * @author USER
 */
public class UsuarioDAO {

public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                     "usuario TEXT PRIMARY KEY," +
                     "contrasena_hash TEXT NOT NULL" +
                     ");";
        try (Connection conn = DBUtil.obtenerConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("[DB] Error al crear tabla usuarios: " + e.getMessage());
        }
    }

    public boolean registrar(String usuario, String hash) {
        String sql = "INSERT INTO usuarios(usuario, contrasena_hash) VALUES (?, ?)";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, hash);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[DB] Error al registrar usuario '" + usuario + "': " + e.getMessage());
            return false;
        }
    }

    public String obtenerHashPorUsuario(String usuario) {
        String sql = "SELECT contrasena_hash FROM usuarios WHERE usuario = ?";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("contrasena_hash");
            }
        } catch (SQLException e) {
            System.err.println("[DB] Error al buscar usuario '" + usuario + "': " + e.getMessage());
        }
        return null;
    }
}
