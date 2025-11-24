/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entidades.IdiomaNivel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DBUtil;

/**
 *
 * @author USER
 */
public class IdiomaNivelDAO {

    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS niveles_idioma (" +
                     "codigo TEXT PRIMARY KEY," +
                     "idioma TEXT NOT NULL," +
                     "nivel TEXT NOT NULL," +
                     "descripcion TEXT NOT NULL" +
                     ");";
        try (Connection conn = DBUtil.obtenerConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ex) {
            System.err.println("[DB] Error al crear tabla niveles_idioma: " + ex.getMessage());
        }
    }

    public boolean insertar(IdiomaNivel in) {
        String sql = "INSERT INTO niveles_idioma(codigo, idioma, nivel, descripcion) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, in.getCodigo());
            pstmt.setString(2, in.getIdioma());
            pstmt.setString(3, in.getNivel());
            pstmt.setString(4, in.getDescripcion());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("[DB] Error al insertar nivel de idioma (" + in.getCodigo() + "): " + ex.getMessage());
            return false;
        }
    }

    public List<IdiomaNivel> listarTodos() {
        List<IdiomaNivel> lista = new ArrayList<>();
        String sql = "SELECT * FROM niveles_idioma ORDER BY idioma, nivel";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new IdiomaNivel(
                    rs.getString("codigo"),
                    rs.getString("idioma"),
                    rs.getString("nivel"),
                    rs.getString("descripcion")
                ));
            }
        } catch (SQLException ex) {
            System.err.println("[DB] Error al listar niveles de idioma: " + ex.getMessage());
        }
        return lista;
    }

    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM niveles_idioma WHERE codigo = ?";
        try (Connection conn = DBUtil.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("[DB] Error al eliminar nivel de idioma: " + ex.getMessage());
            return false;
        }
    }
}
