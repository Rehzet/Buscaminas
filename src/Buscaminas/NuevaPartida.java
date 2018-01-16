package Buscaminas;

import Ventanas.Ventana;

import javax.swing.*;

/** ACTUALMENTE SIN USO **/

public class NuevaPartida {

    private Ventana v;

    public NuevaPartida(){
        v = new Ventana("Nueva partida...", 400, 250);
        v.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        v.setVisible(true);
    }

}
