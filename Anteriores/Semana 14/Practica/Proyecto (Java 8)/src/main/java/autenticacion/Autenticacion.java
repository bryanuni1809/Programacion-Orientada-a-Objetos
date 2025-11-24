/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autenticacion;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import dao.UsuarioDAO;

/**
 *
 * @author BRYAN
 */
public class Autenticacion{

    private static final Autenticacion INSTANCIA = new Autenticacion();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public static Autenticacion getInstance() {
        return INSTANCIA;
    }
    
    private Autenticacion() {
         usuarioDAO.crearTabla();
    }
    
    
    public boolean validarCredenciales(String usuario, String contrasena) {
        String hashGuardado = usuarioDAO.obtenerHashPorUsuario(usuario);
        if (hashGuardado == null) return false;
        String hashIngresado = hash(contrasena);
        return hashIngresado.equals(hashGuardado);
    }

    
    public void registrarUsuario(String usuario, String contrasena) {
        if (usuarioDAO.obtenerHashPorUsuario(usuario) != null) {
            System.out.println("[AUTENTICACION] El usuario '" + usuario + "' ya existe.");
            return;
        }
        if (usuarioDAO.registrar(usuario, hash(contrasena))) {
            System.out.println("[AUTENTICACION] Usuario registrado con éxito.");
        } else {
            System.out.println("[AUTENTICACION] Error al registrar usuario.");
        }
    }

    private String hash(String contrasena){
        try{
            MessageDigest md=MessageDigest.getInstance("SHA-256");
            byte[]hash=md.digest(contrasena.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb=new StringBuilder();
            for(byte b:hash){
                sb.append(String.format("%02x",b));
            }
            return sb.toString();
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("Error en el hash de contraseña",e);
        }
    }
}
