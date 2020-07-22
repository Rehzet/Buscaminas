package Buscaminas;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Casilla extends JButton implements MouseListener{

    public static final int BLANCO = 0;
    public static final int MINA = 1;
    public static final int NUMERO = 2;

    public static final int TAMANO = 35;    // Tamaño en píxeles del botón. Tanto ancho como alto.

    private int estado;                     // Almacena los diferentes estados que puede tener la casilla.
    private int posX, posY;                 // Posición de la casilla dentro de la matriz.
    private int numero;                     // Número de minas adyacentes.
    private boolean oculto;                 // Sirve para saber si ya se ha activado la casilla o no.
    private Partida buscaminas;             // Objeto buscaminas que contiene la casilla.



    /** Constructor que recibea posición de la casilla y un objeto tipo 'Partida'. */
    public Casilla(int x, int y, Partida partida){
        this.setSize(TAMANO, TAMANO);
        this.setLocation(x * TAMANO, y * TAMANO);
        this.addMouseListener(this);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.buscaminas = partida;
        estado = BLANCO;
        posX = x;
        posY = y;
        oculto = true;
        numero = 0;
    }

    /** Sin uso. */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(!buscaminas.isFinPartida()) {    // Funciona si la partida no se ha acabado.

            switch (e.getButton()) {

                case MouseEvent.BUTTON1:    // Botón izquierdo.

                    if (oculto) {           // Funciona si la casilla no ha sido pulsada.
                        if (buscaminas.isPrimeraCasilla()) { // Si es la primera casilla que se pulsa, se generan las minas a partir de la posición de la casilla.
                            buscaminas.colocarMinas(getPosX(), getPosY());
                        } else {
                            switch (getEstado()) {
                                case BLANCO:    // Si la casilla es blanca, se muestran las casillas blancas consecutivas.
                                    buscaminas.mostrarCasillasBlancas(getPosX(), getPosY());
                                    break;
                                case MINA:      // Si la casilla es una mina, se colorea de rojo y se pierde la partida.
                                    this.setBackground(Color.RED);
                                    oculto = false;
                                    buscaminas.perder();
                                    break;
                                case NUMERO:   // Si la casilla es un número, se muestra el texto, se cambia el color de fondo y se comprueba la condición de victoria.
                                    this.setText(String.valueOf(numero));
                                    this.setBackground(Color.LIGHT_GRAY);
                                    oculto = false;
                                    buscaminas.comprobarVictoria();
                                    break;
                            }
                        }
                        oculto = false;
                    }
                    break;

                case MouseEvent.BUTTON3:    // Botón derecho.
                    if (this.getBackground() == Color.ORANGE) { // Funcionalidad de la banderita en el buscaminas original de Windows.
                        this.setBackground(null);
                    }
                    else {
                        this.setBackground(Color.ORANGE);
                    }

                    break;
            }
        }
    }

    /** Sin uso. */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /** Sin uso. */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /** Sin uso. */
    @Override
    public void mouseExited(MouseEvent e) {

    }


    /* GETTERS & SETTERS */

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }
}
