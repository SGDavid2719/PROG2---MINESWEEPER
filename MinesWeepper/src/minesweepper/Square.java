package minesweepper;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * @author David Santomé Galván - 43221079P
 * @author Mateo Javier Ramón Román - 43225388Q
 * https://www.youtube.com/watch?v=19GV3cdF7s8
 *
 * La clase Square define una casilla como un rectángulo con una imagen "Hidden"
 * inicialmente y si está ocupada por una bomba o no.
 *
 */
class Square {

    // Atributos
    private Rectangle2D.Float rectangle;
    private Boolean bombIn;
    private Piece piece;
    private Boolean covered;

    /**
     * El constructor define una casilla como un rectángulo con una imagen,
     * con un boolean indicando si contiene una bomba y con un boolean
     * que indica si está tapada.
     *
     * @param r Tipo Rectangle2D.Float que define un rectángulo
     * @param o Tipo Boolean que define si está ocupada esta casilla con bomba
     * @param p Tipoo Piece que contendrá la imagen inicial de esa casilla
     * @param c Tipo Boolean que indica si está tapada la casilla
     */
    public Square(Rectangle2D.Float r, Boolean o, Piece p, Boolean c) {
        this.rectangle = r;
        this.bombIn = o;
        this.piece = p;
        this.covered = c;
    }

    /**
     * El método paintComponent pinta el rectángulo de la casilla
     * correspondiente, en este caso, coloca una imagen.
     *
     * @param g Tipo Graphics
     */
    public void paintComponent(Graphics g) {
        this.piece.paintComponent(g, this.rectangle.x, this.rectangle.y);
    }

    /**
     * El método setPiece coloca una pieza en la casilla en cuestión.
     *
     * @param s Tipo Piece que se trata de la pieza que se quiere colocar
     */
    void setPiece(Piece s) {
        this.piece = s;
    }

    /**
     * Sets si una casilla está ocupada por una bomba.
     *
     * @param b Tipo Boolean que contiene si hay una bomba
     */
    public void setBombIn(Boolean b) {
        this.bombIn = b;
    }

    /**
     * Sets si una casilla está destapada.
     *
     * @param c Tipo Boolean que contiene el estado de la casilla
     */
    public void setCovered(Boolean c) {
        this.covered = c;
    }
    
    /**
     * Gets del rectangulo.
     *
     * @return rectangle Tipo Rectangle2D.Float
     */
    public Rectangle2D.Float getRec() {
        return rectangle;
    }

    /**
     * Gets del boolean bombIn.
     *
     * @return bombIn Tipo Boolean
     */
    public Boolean getBombIn() {
        return bombIn;
    }

    /**
     * Gets del boolean covered.
     *
     * @return covered Tipo Boolean
     */
    public Boolean getCovered() {
        return covered;
    }
}
