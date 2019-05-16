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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import seguridad.sevinjofagaca.controlador.ServicioJpaController;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import seguridad.sevinjofagaca.controlador.LogsJpaController;
import seguridad.sevinjofagaca.modelo.Logs;

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
     private SecretKeySpec llaveDES;

    public static Date fechaActual() {
        Date fecha = new Date();
        return fecha;
    }

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
                agregarLogs("inicio de sesión exitoso");
                return true;
            } else {
                currentUser = null;
                agregarLogs("Inicio de sesión fallido");
                return false;
            }
        } else {
            agregarLogs("usuario existente, error pass");
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
    public void agregarLogs(String tipoEvento){
        LogsJpaController lg = new LogsJpaController(Persistence.createEntityManagerFactory("GestionPU"));
                Logs logs = new Logs();
                System.out.println("Esta es la fecha actual : " + fechaActual());
                try {
                    logs.setId(23);
                    logs.setTipoEvento(tipoEvento);
                    logs.setFecha(fechaActual());
                    logs.setIdUser(currentUser.getId());
                    lg.create(logs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
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
            agregarLogs("Registro exitoso");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en el resgitro intente de nuevo");
            agregarLogs("registro fallido");
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

    public String enviarClave() throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance("DES");
        kgen.init(56);
        SecretKey llave = kgen.generateKey();
        byte[] crudo = llave.getEncoded();
        SecretKeySpec secretKey = new SecretKeySpec(crudo, "DES");
        llaveDES = secretKey;
        return secretKey.toString();
    }

    public void ObtenerUsuarios(DefaultTableModel modelo2) {
        UsuariosJpaController ujc = new UsuariosJpaController(Persistence.createEntityManagerFactory("GestionPU"));
        Object O[] = null;
        List<Usuarios> user = ujc.findUsuariosEntities();
        for (int i = 0; i < user.size(); i++) {
            modelo2.addRow(O);
            modelo2.setValueAt(user.get(i).getId(), i, 0);
            modelo2.setValueAt(user.get(i).getUser(), i, 1);
            modelo2.setValueAt(user.get(i).getNombre(), i, 2);
            modelo2.setValueAt(user.get(i).getApellido(), i, 3);

        }
    }

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
            agregarLogs("Servicio creado exitosamente");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "error el registro no funcionannnaaaa");
            agregarLogs("Servicio no creado");
            return false;
        }
    }

    public void obtenerLogs(DefaultTableModel modelo2) {
        LogsJpaController lg = new LogsJpaController(Persistence.createEntityManagerFactory("GestionPU"));
        Object O[] = null;
        List<Logs> logs = lg.findLogsEntities();
        for (int i = 0; i < logs.size(); i++) {
            modelo2.addRow(O);
            modelo2.setValueAt(logs.get(i).getId(), i, 0);
            modelo2.setValueAt(logs.get(i).getTipoEvento(), i, 1);
            modelo2.setValueAt(logs.get(i).getFecha(), i, 2);
            modelo2.setValueAt(logs.get(i).getIdUser(), i, 3);
        }
    }

    public void eliminar(JTable Tabla) {
        try {
            UsuariosJpaController ujc = new UsuariosJpaController(Persistence.createEntityManagerFactory("GestionPU"));
            int u = (int) Tabla.getValueAt(Tabla.getSelectedRow(), 0);
            ujc.destroy(u);
            JOptionPane.showMessageDialog(null, "El usuario ha sido eliminado correctamente");
            agregarLogs("El usuario eliminado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e.getMessage());
             agregarLogs("El usuario no eliminado");
        }
    }

    public void Modificar(String id, String nombre, String apellido, String user, JTable Tabla, String pass) {
        try {
            UsuariosJpaController ujc = new UsuariosJpaController(Persistence.createEntityManagerFactory("GestionPU"));
            Usuarios u = (Usuarios) Tabla.getValueAt(Tabla.getSelectedRow(), 0);
            u.setId(Integer.parseInt(id));
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setContraseña(pass);
            u.setUser(user);
            ujc.edit(u);
            JOptionPane.showMessageDialog(null, "El usuario ha sido Modificado correctamente");
            agregarLogs("Usuario modificado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e.getMessage());
            agregarLogs("Usuario no modificado error");
        }
    }

    public String cifrarDES(String textoClaro) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("DES");
        String original = textoClaro;
        String clave = enviarClave();
        SecretKeySpec secretKey = llaveDES;
        System.out.println("Este es el mensaje:" + original);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encriptado = cipher.doFinal(original.getBytes());
        System.out.println("Mensaje Encriptado con el algortimo DES: " + asHex(encriptado));
        agregarLogs("Cifrado DES realizado");
        return asHex(encriptado);
    }

}
