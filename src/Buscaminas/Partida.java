package Buscaminas;

import Ventanas.Ventana;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Partida{

    private Casilla[][] tablero;        // Matriz que contiene cada casilla del juego.

    private Random random;              // Objeto random que se utiliza para colocar las minas.

    private boolean primeraCasilla;     // Sirve para saber si se está tocando una casilla por primera vez o no.
    private boolean finPartida;         // Sirve para saber si se ha acabado la partida.

    private int numMinas;               // Contiene el número de minas que va a tener la partida.
    private int contVictoria;           // Contador de casillas restantes que quedan para ganar.

    private Ventana ventana;            // Ventana a la que se añade el tablero de juego.

    /** Constructor de Partida que recibe una ventana */
    public Partida(Ventana ventana){
        primeraCasilla = true;
        numMinas = 10;
        this.ventana = ventana;
        generarCasillas();
        contVictoria = tablero.length * tablero[0].length - numMinas;
        this.ventana.setVisible(true);
    }

    /** Coloca todas las casillas en la matriz y en la ventana de juego. */
    private void generarCasillas(){
        tablero = new Casilla[8][8];

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = new Casilla(i, j, this);
                ventana.add(tablero[i][j]);
            }
        }
        ventana.setSize(tablero.length * Casilla.TAMANO, tablero[0].length * Casilla.TAMANO + 48); // El +48 es debido al tamaño del menú.
    }

    /** Se colocan las minas en el tablero de manera aleatoria. */
    protected void colocarMinas(int noX, int noY){
        primeraCasilla = false;
        int minas = numMinas;                                   // Contador de minas restantes que quedan por colocar.
        random = new Random(System.currentTimeMillis());        // Se inicializa el objeto random, usando como semilla la hora actual.

        do{                                                     // Bucle que recorre la matriz y va colocando las minas, hasta que no quede ninguna por colocar.
            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero[i].length; j++) {
                    if(random.nextDouble() <= 0.2 && tablero[i][j].getEstado() != Casilla.MINA && minas > 0 && casillasVecinas(i, j, noX, noY)){
                        tablero[i][j].setEstado(Casilla.MINA);
                        minas--;
                    }
                }
            }
        }while(minas>0);

        colocarNumeros();
        mostrarCasillasBlancas(noX, noY);
    }

    /** Función que necesaria para la colocación de minas. Sirve para no generar ninguna mina en primera casilla que se pulsa, ni en su alrederor.
     *  Está en una función para ahorrar espacio en el 'if' de la función anterior. */
    private boolean casillasVecinas(int i, int j, int x, int y){
        return (Math.abs(x - i) > 1) || (Math.abs(y - j) > 1);
    }

    /** Coloca las casillas en las que se almacenan el número de minas cercanas. */
    private void colocarNumeros(){
        int minas;

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                minas = contarMinas(i, j);
                if(minas > 0 && tablero[i][j].getEstado() == Casilla.BLANCO){
                    tablero[i][j].setNumero(minas);
                    tablero[i][j].setEstado(Casilla.NUMERO);
                }
            }
        }
    }

    /** Cuenta el número de minas adyacentes a una casilla en concreto. */
    private int contarMinas(int x, int y){
        int cont = 0;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if(!(i < 0 || i >= tablero.length || j < 0 || j >= tablero[i].length)){
                    if(tablero[i][j].getEstado() == Casilla.MINA){
                        cont++;
                    }
                }
            }
        }

        return cont;
    }

    /** Muestra todas las casillas vacías que hay seguidas. Utiliza un algoritmo tipo 'Flood Fill' en su versión con cola. */
    protected void mostrarCasillasBlancas(int x , int y){
        LinkedList<Point> cola = new LinkedList<>();
        if (tablero[x][y].getEstado() != Casilla.BLANCO){
            return;
        }
        cola.add(new Point(x, y));

        while (!cola.isEmpty()){
            Point p = cola.remove();
            if (tablero[p.x][p.y].getEstado() == Casilla.BLANCO && tablero[p.x][p.y].isOculto()){
                tablero[p.x][p.y].setBackground(Color.LIGHT_GRAY);
                tablero[p.x][p.y].setOculto(false);
                contVictoria--;
                if(p.x - 1 >= 0)
                    cola.add(new Point(p.x-1, p.y));
                if(p.x + 1 < tablero.length)
                    cola.add(new Point(p.x+1, p.y));
                if(p.y - 1 >= 0)
                    cola.add(new Point(p.x, p.y-1));
                if(p.y + 1 < tablero[0].length)
                    cola.add(new Point(p.x, p.y+1));
            }
        }

        return;
    }

    protected boolean isPrimeraCasilla() {
        return primeraCasilla;
    }

    /** Función que muestra un mensaje de derrota y también enseña la localización de todas las minas. */
    protected void perder(){
        JOptionPane.showMessageDialog(null, "HAS PERDIDO", "Has perdido", JOptionPane.INFORMATION_MESSAGE);
        finPartida = true;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if(tablero[i][j].getEstado() == Casilla.MINA)
                    tablero[i][j].setBackground(Color.RED);
            }
        }
    }

    /** Comprueba si se ha ganado. En caso afirmativo, se muestra un mensaje de victoria. */
    protected void comprobarVictoria(){
        contVictoria--;

        if(contVictoria==0) {
            JOptionPane.showMessageDialog(null, "¡HAS GANADO!", "Has ganado", JOptionPane.INFORMATION_MESSAGE);
            finPartida = true;
        }
    }

    protected boolean isFinPartida() {
        return finPartida;
    }
}
