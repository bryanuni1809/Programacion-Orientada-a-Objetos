/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autenticacion;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author BRYAN
 */
public class Autenticacion {

    private static final String ARCHIVO="usuarios.txt";

    public Autenticacion(){
        File archivo=new File(ARCHIVO);
        try{
            if(archivo.createNewFile()){
                System.out.println("[AUTENTICACION] Archivo de usuarios creado.");
            }
        }catch(IOException e){
            System.out.println("[AUTENTICACION] Error al crear archivo: "+e.getMessage());
        }
    }
    private Map<String,String>cargarUsuarios(){
        Map<String,String> usuarios=new HashMap<>();
        try(BufferedReader reader=new BufferedReader(new FileReader(ARCHIVO))){
            String linea;
            while((linea=reader.readLine())!=null){
                String[]partes=linea.split(",");
                if(partes.length==2){
                    usuarios.put(partes[0].trim(),partes[1].trim());
                }
            }
        }catch(IOException e){
            System.out.println("[AUTENTICACION] Error al leer usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    public boolean validarCredenciales(String usuario,String contrasena){
        Map<String,String>usuarios=cargarUsuarios();
        String hashIngresado=hash(contrasena);
        return hashIngresado.equals(usuarios.get(usuario));
    }

    private boolean usuarioExiste(String usuario){
        return cargarUsuarios().containsKey(usuario);
    }

    public void registrarUsuario(String usuario,String contrasena) {
        if(usuarioExiste(usuario)){
            System.out.println("[AUTENTICACION] El usuario '"+usuario+"' ya existe.");
            return;
        }

        try(BufferedWriter writer=new BufferedWriter(new FileWriter(ARCHIVO,true))){
            writer.write(usuario+","+hash(contrasena));
            writer.newLine();
            System.out.println("[AUTENTICACION] Usuario registrado con éxito.");
        }catch(IOException e){
            System.out.println("[AUTENTICACION] Error al registrar usuario: "+e.getMessage());
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
