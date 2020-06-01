package minesweepper;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

/**
 * @author David Santomé Galván - 43221079P
 * @author Mateo Javier Ramón Román - 43225388Q
 * https://www.youtube.com/watch?v=19GV3cdF7s8
 *
 * La clase Board definida como un panel donde se define un tablero de 9x9
 * casillas.
 */
public class Board extends JPanel {

    // Variable Random
    private static final Random R = new Random();

    // Variables del tablero
    public static final int DIMENSIONS = 9;
    private static final int MAXIMUM = DIMENSIONS * 100;
    private static final int SIDE = MAXIMUM / DIMENSIONS;
    private static final int NUMBOMBS = 10;

    // Matriz de Casillas que compone el tablero
    private Square t[][];

    /**
     * El constructor define las casillas como rectángulos compuestos por una
     * imagen, un boolean si contiene bomba y un boolean de tapadas.
     *
     */
    public Board() {
        // Inicializamos los arrays
        t = new Square[DIMENSIONS][DIMENSIONS];

        // Inicializamos el tablero
        int y = 0;
        for (int row = 0; row < DIMENSIONS; row++) {
            int x = 0;
            for (int column = 0; column < DIMENSIONS; column++) {
                Rectangle2D.Float r
                        = new Rectangle2D.Float(x, y, SIDE, SIDE);
                t[row][column]
                        = new Square(r, false, new Piece(Piece.HIDDEN), true);
                x += SIDE;
            }
            y += SIDE;
        }
    }

    /**
     * El método setBombs coloca bombas de manera aleatoria por el tablero.
     * Antes de colocar una bomba se mira que no esté ya ocupada. Se colocan un
     * total de NUMBOMBS.
     *
     */
    public void setBombs() {
        int row, column, i = 0;

        while (i < NUMBOMBS) {
            // Miramos que no haya previamente una bomba en esta posición
            do {
                row = R.nextInt(DIMENSIONS);
                column = R.nextInt(DIMENSIONS);
            } while (t[row][column].getBombIn());
            // Ponemos una bomba
            setOccupied(row, column, true);
            i++;
        }
    }

    /**
     * El método clearSquare gira aquella casilla que se ha seleccionado. 
     * 
     * Si una casilla no tiene bombas cercanas liberará aquellas casillas 
     * que estén próximas a ella. Para ello se ha utilizado recursividad.
     *
     * @param row Tipo int que corresponde a la fila del tablero
     * @param column Tipo int que corresponde a la columna del tablero
     */
    public void clearSquare(int row, int column) {
        int row2, column2, numBombs = 0;
        
        // Si es una casilla que se puede destapar
        if (t[row][column].getCovered() == true) {
            t[row][column].setCovered(false);
            numBombs = getNearbyBombs(row, column);
            switch (numBombs) {
                case 0:
                    Set(Piece.N0, row, column);
                    for (row2 = max(0, row - 1); row2 <= min(DIMENSIONS - 1, row + 1); row2++) {
                        for (column2 = max(0, column - 1); column2 <= min(DIMENSIONS - 1, column + 1); column2++) {
                            if (!t[row2][column2].getBombIn()) {
                                clearSquare(row2, column2);
                            }
                        }
                    }
                    break;
                case 1:
                    Set(Piece.N1, row, column);
                    break;
                case 2:
                    Set(Piece.N2, row, column);
                    break;
                case 3:
                    Set(Piece.N3, row, column);
                    break;
                case 4:
                    Set(Piece.N4, row, column);
                    break;
                case 5:
                    Set(Piece.N5, row, column);
                    break;
                case 6:
                    Set(Piece.N6, row, column);
                    break;
                case 7:
                    Set(Piece.N7, row, column);
                    break;
                case 8:
                    Set(Piece.N8, row, column);
                    break;
            }
        }
    }

    /**
     * El método clearBoard gira todas las casillas cuando pierdes.
     *
     */
    public void clearBoard() {
        int numBombs = 0;
        
        for (int row = 0; row < DIMENSIONS; row++) {
            for (int column = 0; column < DIMENSIONS; column++) {
                // Si es una casilla que se puede destapar
                if (t[row][column].getCovered() == true) {
                    t[row][column].setCovered(false);
                    numBombs = getNearbyBombs(row, column);
                    switch (numBombs) {
                        case 0:
                            Set(Piece.N0, row, column);
                            break;
                        case 1:
                            Set(Piece.N1, row, column);
                            break;
                        case 2:
                            Set(Piece.N2, row, column);
                            break;
                        case 3:
                            Set(Piece.N3, row, column);
                            break;
                        case 4:
                            Set(Piece.N4, row, column);
                            break;
                        case 5:
                            Set(Piece.N5, row, column);
                            break;
                        case 6:
                            Set(Piece.N6, row, column);
                            break;
                        case 7:
                            Set(Piece.N7, row, column);
                            break;
                        case 8:
                            Set(Piece.N8, row, column);
                            break;
                        case 9:
                            Set(Piece.BOMB, row, column);
                            break;
                    }
                }
            }
        }
    }

    /**
     * El método restartSavedGameImages destapa todas aquellas casillas que
     * estaban destapadas según la partida que se ha guardado.
     *
     */
    void restartSavedGameImages(){
        int numBombs;
        
        for (int row = 0; row < DIMENSIONS; row++) {
            for (int column = 0; column < DIMENSIONS; column++) {
                // Si es una casilla que se puede destapar
                if (t[row][column].getCovered() == false) {
                    numBombs = getNearbyBombs(row, column);
                    switch (numBombs) {
                        case 0:
                            Set(Piece.N0, row, column);
                            break;
                        case 1:
                            Set(Piece.N1, row, column);
                            break;
                        case 2:
                            Set(Piece.N2, row, column);
                            break;
                        case 3:
                            Set(Piece.N3, row, column);
                            break;
                        case 4:
                            Set(Piece.N4, row, column);
                            break;
                        case 5:
                            Set(Piece.N5, row, column);
                            break;
                        case 6:
                            Set(Piece.N6, row, column);
                            break;
                        case 7:
                            Set(Piece.N7, row, column);
                            break;
                        case 8:
                            Set(Piece.N8, row, column);
                            break;
                        case 9:
                            Set(Piece.EXPLOSION, row, column);
                            break;
                    }
                }
            }
        }
    }
            
    /**
     * El método getNearbyBombs cuenta el número de bombas que rodean la casilla
     * apuntada por los parámetros.
     *
     * @param row Tipo int que corresponde a la fila del tablero
     * @param column Tipo int que corresponde a la columna del tablero
     *
     * @return numBombs Tipo int representando el número de bombas cercanas.
     */
    public int getNearbyBombs(int row, int column) {
        int row2, column2, numBombs = 0;
        
        // Incrementamos el número de minas cercanas en las casillas vecinas
        if (t[row][column].getBombIn()) {
            numBombs = 9;
        } else {
            for (row2 = max(0, row - 1); row2 <= min(DIMENSIONS - 1, row + 1); row2++) {
                for (column2 = max(0, column - 1); column2 <= min(DIMENSIONS - 1, column + 1); column2++) {
                    if (t[row2][column2].getBombIn()) {
                        numBombs++;
                    }
                }
            }
        }
        return numBombs;
    }

    /**
     * El método countClearedSquares cuenta el número de casillas que están
     * desbloqueadas.
     *
     * @return num Tipo int
     */
    public int countClearedSquares() {
        int row, column, num = 0;
        
        for (row = 0; row < DIMENSIONS; row++) {
            for (column = 0; column < DIMENSIONS; column++) {
                if (t[row][column].getCovered() == false) {
                    num++;
                }
            }
        }
        return num;
    }

    /**
     * El método checkBoard comprueba el número de casillas se han dado la
     * vuelta y devuelve el número. En caso de que se haya dado la vuelta a una
     * bomba devuelve el valor -1.
     *
     * @param row Tipo int que corresponde a la fila del tablero
     * @param column Tipo int que corresponde a la columna del tablero
     *
     * @return Tipo int
     */
    public int checkBoard(int row, int column) {
        if (t[row][column].getBombIn()) {
            Set(Piece.EXPLOSION, row, column);
            t[row][column].setCovered(false);
            clearBoard();
            return -1;
        } else {
            clearSquare(row, column);
            if(countClearedSquares() == ((DIMENSIONS*DIMENSIONS)-NUMBOMBS)){
                return 1;
            }
        }
        return 0;
    }

    /**
     * El método paintComponent recorre el tablero poniendo las piezas.
     *
     * @param g Tipo Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        for (int i = 0; i < DIMENSIONS; i++) {
            for (int j = 0; j < DIMENSIONS; j++) {
                t[i][j].paintComponent(g);
            }
        }
    }

    /**
     * El método getPreferredSize devuelve el tamaño del fichero.
     *
     * @return Dimension Tipo Dimension
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(MAXIMUM, MAXIMUM);
    }

    /**
     * El método Set coloca una imagen en la casilla pasada por parámetros.
     *
     * @param s Tipo String que es la pieza en cuestión
     * @param i Tipo int que define la x en el array de casillas
     * @param i0 Tipo int que define la y en el array de casillas
     */
    void Set(String s, int i, int i0) {
        t[i][i0].setPiece(new Piece(s));
    }

    /**
     * El método infoSquare devuelve el contenido de la casilla en cuestión.
     *
     * @param i Tipo int que es la x en el array de casillas
     * @param j Tipo int que es la y en el array de casillas
     * @param x Tipo int que define la x en el tablero
     * @param y Tipo int que define la y en el tablero
     *
     * @return Tipo Boolean
     */
    boolean infoSquare(int i, int j, int x, int y) {
        return t[i][j].getRec().contains(x, y);
    }

    /**
     * El método setOccupied pone el contenido de la casilla en cuestión según
     * el boolean pasado por parámetros.
     *
     * @param i Tipo int que es la x en el array de casillas
     * @param j Tipo int que es la y en el array de casillas
     * @param b Tipo Boolean que contiene el estado de la casilla.
     */
    void setOccupied(int i, int j, Boolean b) {
        t[i][j].setBombIn(b);
    }

    /**
     * El método isOccupied devuelve si está ocupada la casilla en cuestión.
     *
     * @param i Tipo int que es la x en el array de casillas
     * @param j Tipo int que es la y en el array de casillas
     *
     * @return Tipo Boolean
     */
    boolean isOccupied(int i, int j) {
        return t[i][j].getBombIn();
    }

    /**
     * El método getRectangle devuelve el rectángulo de la casilla en cuestión.
     *
     * @param i Tipo int que es la x en el array de casillas
     * @param j Tipo int que es la y en el array de casillas
     *
     * @return Tipo Rectangle
     */
    Rectangle getRectangle(int i, int j) {
        return t[i][j].getRec().getBounds();
    }

    /**
     * El método getId obtiene el Id correspondiente según el estado de la 
     * casilla.
     *
     * @param row Tipo int que corresponde a la fila del tablero
     * @param column Tipo int que corresponde a la columna del tablero
     *
     * @return Tipo int
     */
    int getId(int row, int column){
        if(!t[row][column].getBombIn() && t[row][column].getCovered()){
            // No tiene bomba y está tapada
            return 0;
        }else if (t[row][column].getBombIn() && t[row][column].getCovered()){
            // Tiene bomba y está tapada
            return 1;
        }else if(!t[row][column].getBombIn() && !t[row][column].getCovered()){
            // No tiene bomba y no está tapada
            return 2;
        }else{
            // Tiene bomba y está destapada
            return 3;
        }
    }
    
    /**
     * El método setId define el estado de una casilla según el Id que se ha
     * obtenido.
     *
     * @param row Tipo int que corresponde a la fila del tablero
     * @param column Tipo int que corresponde a la columna del tablero
     * @param Id Tipo int que corresponde al estado de la casilla
     * 
     */
    void setId(int row, int column, int Id){
        switch(Id){
            case 0:
                break;
            case 1:
                t[row][column].setBombIn(true);
                break;
            case 2:
                t[row][column].setCovered(false);
                break;
            case 3:
                t[row][column].setBombIn(true);
                t[row][column].setCovered(false);
                break;
        }
    }
    
    /**
     * El método max devuelve el número más grande entre dos dados por
     * parámetros.
     *
     * @param num1 Tipo int
     * @param num2 Tipo int
     *
     * @return num Tipo int
     */
    public int max(int num1, int num2) {
        int num;
        if (num1 > num2) {
            num = num1;
        } else {
            num = num2;
        }
        return num;
    }

    /**
     * El método min devuelve el número más pequeño entre dos dados por
     * parámetros.
     *
     * @param num1 Tipo int
     * @param num2 Tipo int
     *
     * @return num Tipo int
     */
    public int min(int num1, int num2) {
        int num;
        if (num1 < num2) {
            num = num1;
        } else {
            num = num2;
        }
        return num;
    }
}
