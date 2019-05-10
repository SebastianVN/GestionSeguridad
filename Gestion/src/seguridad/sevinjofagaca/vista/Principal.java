/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridad.sevinjofagaca.vista;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JFrame;
import seguridad.sevinjofagaca.logica.Operar;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import seguridad.sevinjofagaca.controlador.UsuariosJpaController;

/**
 *
 * @author sevin
 */
public class Principal extends JFrame {
    //relaciones
    private Ventana ventana;
    private Login login;
    private Registro registro;
    private Operar operar;
    private Menu menu;
    private Auditoria auditoria;
    private Roles roles;
    private Inventario inventario;
    private Cifrado cifrado;
    private Administrador admin;
    String UserLogin;
    
    

    //componentes

    /**
     * metodo main
     *
     * @param args argumentos del programa
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Principal principal = new Principal();
    }

    /**
     * Metodo Constructor
     */
    public Principal() throws NoSuchAlgorithmException {
        operar = new Operar();
        operar.generarClave();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Aplicacion Sector eléctrico");
        this.setResizable(false);
        this.setVisible(false);
        //inicia caracteristicas del Frame
        this.irAInicioDeSesion();
    }

    /**
     * inicia los componentes del frame, en este caso el menu bar y sus botones
     * y sus eventos.
     */
    private void iniciarComponentesDePrincipal() {
        
    }

    //metodos de eventos del menu bar    

    //metodos de navegacion
    /**
     * Metodo para salir de la aplicación
     */
    protected void salir() {
        System.exit(0);
    }
    /**
     * Metodo para quitar el panel actual del marco
     */
    private void quitarPanelesDelMarco() {
        setVisible(false);
        if (auditoria!=null) {
            remove(auditoria);
            auditoria=null;
        }
        if (registro!=null) {
            remove(registro);
            registro=null;
        }
        if(cifrado!=null){
            remove(cifrado);
            cifrado=null;
        }
        if(menu!=null){
            remove(menu);
            menu=null;
        }
        if(inventario!=null){
            remove(inventario);
            inventario=null;
        }
        if(admin!=null){
            remove(admin);
            admin=null;
        }
        
        
        
        //hacer lo anterior parra cada panel que este en el marco
    }
    /**
     * Metodo para iniciar el marco
     */
    protected  void iniciarMarco(){
        //inicia los componentes del frame
        this.quitarPanelesDelMarco();
        this.iniciarComponentesDePrincipal();
        this.setVisible(true);
    }
    /**
     * Metodo para cerrar la ventana
     */
    protected void cerrarVentana() {
        ventana.setVisible(false);
        ventana = null;
    }
    /**
     * Metodo para abrir la ventana
     */
    private void iniciarVentana() {
        if (ventana!=null) {
            cerrarVentana();
        } 
        setVisible(false);
    }
    //metodos de navegacion
    /**
     * Metodo para pasar al panel de la calculadora
     */
    protected void irAAuditoria() {
        iniciarMarco();
        //agrega el panelinicial
        auditoria = new Auditoria(this);
        auditoria.setVisible(true);
        add(auditoria);
        //se ajusta el frame
        pack();
        //centra la aplicacion 
        setLocationRelativeTo(null);
    }
    protected void irAAdministrador() {
        iniciarMarco();
        //agrega el panelinicial
        admin = new Administrador(this);
        admin.setVisible(true);
        add(admin);
        //se ajusta el frame
        pack();
        //centra la aplicacion 
        setLocationRelativeTo(null);
    }
    
    protected void VolverAMenu() {
        iniciarMarco();
        //agrega el panelinicial
        menu = new Menu(this);
        menu.setVisible(true);
        add(menu);
        //se ajusta el frame
        pack();
        //centra la aplicacion 
        setLocationRelativeTo(null);
    }
    /**
     * Metodo para ir al panel de registrar
     */
    protected  void irARegistrar() {
        cerrarVentana();
        iniciarMarco();
        //agrega el panelinicial
        registro = new Registro(this);
        registro.setVisible(true);
        add(registro);
        //se ajusta el frame
        pack();
        //centra la aplicacion 
        setLocationRelativeTo(null);
    }
    /**
     * Metodo para ir al inicio de sesion
     */
    protected void irAInicioDeSesion() {
        operar.cerrarSesion();
        iniciarVentana();
        login = new Login(this);
        ventana = new Ventana(this, login, "Ventana", false, false, DO_NOTHING_ON_CLOSE);
    }
    
    /**
     * Metodo para ir al cifrado
     */
    protected void irACifrado() {
        iniciarMarco();
        //agrega el panelinicial
        cifrado = new Cifrado(this);
        cifrado.setVisible(true);
        add(cifrado);
        //se ajusta el frame
        pack();
        //centra la aplicacion 
        setLocationRelativeTo(null);
    }
    
    
    //metodos de logica
    /**
     * Metodo para validar el inicio de sesion de un usuario
     * @param usuario valor del usuario
     * @param pass valor contraseña
     * @return un verdadero o falso segun la condicion
     */
    protected  boolean validar(String usuario, String pass) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        UserLogin= usuario;
        return operar.iniciarSesion(usuario, pass);
    }/**
     * Metodo para validad el resgistro y agregar el nuevo usuario
     * @param nombre nombre del usuario
     * @param usuario valor del usuario
     * @param pass valor de la contraseña
     * @param confirPass valor de la constraseña para confirmarlo
     * @return un verdadero o falso segun la condicion
     */
    protected boolean validarRegistro(String nombre, String usuario, String pass, String confirPass,String apellido, String cedula){
        if(operar.Registrar(nombre, usuario, pass, confirPass,apellido,cedula)){
        
        return true;
        }else{
            return false;
        }
        
    }
    /*
    public List<Historial> obtenerHistorial(){
        return operar.obtenerHistorialDeUsuario();
    }

    String calcular(String ecuacion) {
        return "";
    }*/

    protected void irAMenu() {
      cerrarVentana();
        iniciarMarco();
        //agrega el panelinicial
        menu = new Menu(this);
        menu.setVisible(true);
        add(menu);
        //se ajusta el frame
        pack();
        //centra la aplicacion 
        setLocationRelativeTo(null);  
    }

    protected void ObtenerUsuarios(DefaultTableModel modelo2) {
        operar.ObtenerUsuarios(modelo2);
    }

    protected void ObtenerLogs(DefaultTableModel modelo2) {
        operar.obtenerLogs(modelo2);
    }

    protected void IrAInventario() {
        //cerrarVentana();
        iniciarMarco();
        //agrega el panelinicial
        inventario = new Inventario(this);
        inventario.setVisible(true);
        add(inventario);
        //se ajusta el frame
        pack();
        //centra la aplicacion 
        setLocationRelativeTo(null);
    }

}
