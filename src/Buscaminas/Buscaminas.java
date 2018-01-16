package Buscaminas;

import javax.swing.*;
import Ventanas.Ventana;

public class Buscaminas {

    private Ventana ventana;        // Ventana que contiene el juego y el menú de opciones.
    private JMenuBar menu;          // Barra del menú de opciones.
    private Partida partida;        // Objeto partida que contiene las casillas y t0do el código del juego


    public Buscaminas(){
        nuevaVentana();
    }

    /** Se crea el menú y se añade a la ventana. */
    private void crearMenu(){
        menu = new JMenuBar();

        JMenu menu1 = new JMenu("Juego");
        menu.add(menu1);

        JMenuItem mi1 = new JMenuItem("Nueva partida...");
        mi1.addActionListener(al -> {
            //new NuevaPartida();
            ventana.dispose();
            nuevaVentana();
        });
        menu1.add(mi1);


        JMenu menu2 = new JMenu("Ayuda");
        menu.add(menu2);

        JMenuItem mi2 = new JMenuItem("Instrucciones");
        mi2.addActionListener(al -> {

        });
        menu2.add(mi2);

        ventana.setJMenuBar(menu);
    }

    /** Se crea una nueva ventana de juego */
    private void nuevaVentana(){
        ventana = new Ventana("Buscaminas", 500, 400);
        crearMenu();
        partida = new Partida(ventana);
        ventana.setVisible(true);
    }


}
