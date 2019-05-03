/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridad.sevinjofagaca.vista;

import java.awt.Image;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author Sebastian
 * @author Jose
 */
public class Ventana extends JDialog {
    /**
     * Variable para llamar al frame principal
     */
    private Principal principal;
    /**
     * VAriable para llamar el panel inicial
     */
    private JPanel jPanel;
    /**
     * Metodo constructor para la clase ventana
     * @param principal llama a la clase principal
     * @param jPanel llama a la clase principal
     * @param titulo recibe el nombre que le asignara a la ventana
     * @param modal recibe si muestra o no la ventana
     * @param marco recibe si muestra o no el marco
     * @param opcionDeCerrado  Recibe un numero con el cual se identifica si cierra o no la ventana 
     */
    public Ventana(Principal principal, JPanel jPanel, String titulo, boolean modal, boolean marco,  int opcionDeCerrado) {
        super(principal, titulo, modal);
        this.setDefaultCloseOperation(opcionDeCerrado);
        this.setUndecorated(marco);
        this.principal = principal;
        this.jPanel = jPanel;
        this.jPanel.setVisible(true);
        this.add(this.jPanel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(this.principal);
    }

}
