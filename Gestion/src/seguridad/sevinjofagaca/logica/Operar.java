/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridad.sevinjofagaca.logica;

import seguridad.sevinjofagaca.controlador.UsuariosJpaController;
import seguridad.sevinjofagaca.modelo.Usuarios;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.Key;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import sun.misc.BASE64Encoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import seguridad.sevinjofagaca.controlador.ServicioJpaController;
import seguridad.sevinjofagaca.modelo.Servicio;

/**
 *
 * @author 204
 */
public class Operar {

    /**
     * Metodo principal que envia un objeto al servidor que es una operacion
     *
     * @param args
     */
    private Usuarios currentUser;
    private SecretKeySpec llaveDefinitiva;

    public boolean iniciarSesion(String user, String pass) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        String original = pass;
        SecretKeySpec secretKey = llaveDefinitiva;
        System.out.println("Este es el mensaje" + original);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encriptado = cipher.doFinal(original.getBytes());
        System.out.println("Mensaje Encriptado: " + asHex(encriptado));
        UsuariosJpaController ujc = new UsuariosJpaController(Persistence.createEntityManagerFactory("GestionPU"));
        Usuarios u = ujc.findUsuarioByLogin(user);
        if (u != null) {
            if (u.getUser().equals(user) && u.getContraseña().equals(asHex(encriptado))) {
                currentUser = u;
                return true;
            } else {
                currentUser = null;
                return false;
            }
        } else {
            currentUser = null;
            return false;
        }
    }

    public void cerrarSesion() {
        currentUser = null;
    }

    /*
    public String operacion(String ecuacion) {
        String ecu[] = ecuacion.split(" ");
        switch (ecu[1]) {
            case "+":
                return sumar(ecu[0], ecu[2]);

        }
        return "hola";
    }
    /*
    public String sumar(String num1, String num2) {
        Operacion operacion = new Operacion(Double.parseDouble(num1), Double.parseDouble(num2), '+', "");
        Socket clienteSocket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        // Utilizamos un try and catch para manejar excepciones al utilizar sockets
        try {
            clienteSocket = new Socket(HOST, PUERTO_BASICAS);
            oos = new ObjectOutputStream(clienteSocket.getOutputStream());
            oos.writeObject(operacion);
            ois = new ObjectInputStream(clienteSocket.getInputStream());
            operacion = (Operacion) ois.readObject();
            System.out.println(operacion.toString());
            ois.close();
            oos.flush();
            oos.close();
            registrarHistorial(operacion.getOpe() + "", operacion.getNum1(), operacion.getNum2(), operacion.getRes());
            return operacion.getRes() + "";
        } catch (IOException e) {
            return "Error de Conexion";
        } catch (ClassNotFoundException ex) {
            return "Error de tipo de dato";
        }

    }
     *//*
    private void registrarHistorial(String op, double num1, double num2, double res) {
        Historial registro = new Historial();
        registro.setOperacion(op);
        registro.setNum1(num1);
        registro.setNum2(num2);
        registro.setRes(res);
        registro.setUs(currentUser);
        HistorialJpaController hjc = new HistorialJpaController(Persistence.createEntityManagerFactory("clientePU"));
        hjc.create(registro);

    }
     */
    public boolean Registrar(String nombre, String usuario, String pass, String confirPass, String apellido, String cedula) {
        UsuariosJpaController ujc = new UsuariosJpaController(Persistence.createEntityManagerFactory("GestionPU"));
        Usuarios u = new Usuarios();
        try {
            u.setId(Integer.parseInt(cedula));
            u.setNombre(nombre);
            u.setUser(usuario);
            u.setApellido(apellido);
            Cipher cipher = Cipher.getInstance("AES");
            String original = pass;
            SecretKeySpec secretKey = llaveDefinitiva;
            System.out.println("Este es el mensaje" + original);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encriptado = cipher.doFinal(original.getBytes());
            System.out.println("Mensaje Encriptado: " + asHex(encriptado));
            u.setContraseña(asHex(encriptado));
            ujc.create(u);
            JOptionPane.showMessageDialog(null, "Se Guardaron Los datos");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "error el registro no funcionannnaaaa");
            return false;
        }

    }

    public static String asHex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i & 0xff]) < 0x10) {
                strbuf.append("0");

            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    public void generarClave() throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        SecretKey llave = kgen.generateKey();
        byte[] crudo = llave.getEncoded();
        SecretKeySpec secretKey = new SecretKeySpec(crudo, "AES");
        llaveDefinitiva = secretKey;
    }
    /*
    public List<Historial> obtenerHistorialDeUsuario(){        
       return currentUser.getHistorialList();       
    }
     */

    public boolean RegistrarServicio(String nombreServicio, String tipoServicio, String costoServicio, String plazoServicio) {
     
        ServicioJpaController ujc = new ServicioJpaController(Persistence.createEntityManagerFactory("GestionPU"));
        Servicio u = new Servicio();
        try {
            u.setNombre(nombreServicio);
            u.setTipoServicio(tipoServicio);
            u.setPrecio(Integer.parseInt(costoServicio));
            u.setPlazoPago(plazoServicio);
            u.setIdUser(currentUser.getId());
            ujc.create(u);
            JOptionPane.showMessageDialog(null, "Se Guardaron Los datos");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "error el registro no funcionannnaaaa");
            return false;
        }
    }
}
