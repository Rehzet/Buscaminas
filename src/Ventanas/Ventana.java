package Ventanas;

import javax.swing.*;


public class Ventana extends JFrame{

    // VARIABLES PRIVADAS

    // MÉTODOS PÚBLICOS

    public Ventana() {
        super();
        crearVentana("Nueva ventana", 600, 400);
    }

    public Ventana(String titulo, int x, int y){
        super();
        crearVentana(titulo, x, y);
    }


    // MÉTODOS PRIVADOS

    private void crearVentana(String titulo, int x, int y){
        this.setTitle(titulo);
        this.setSize(x, y);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        /*// Look and Feel
        try{
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
