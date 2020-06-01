package minesweepper;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

/**
 * @author David Santomé Galván     -   43221079P
 * @author Mateo Javier Ramón Román -   43225388Q
 * https://www.youtube.com/watch?v=19GV3cdF7s8
 * 
 * La clase Piece define las imagenes que aparecen habitualmente cuando
 * juegas a MinesWipper. 
 * 
 */
public class Piece {

    // Atributos
    public static final String HIDDEN = "pieces/Hidden.png";
    public static final String BOMB = "pieces/Bomb.png";
    public static final String EXPLOSION = "pieces/Explosion.png";
    public static final String N0 = "pieces/N0.png";
    public static final String N1 = "pieces/N1.png";
    public static final String N2 = "pieces/N2.png";
    public static final String N3 = "pieces/N3.png";
    public static final String N4 = "pieces/N4.png";
    public static final String N5 = "pieces/N5.png";
    public static final String N6 = "pieces/N6.png";
    public static final String N7 = "pieces/N7.png";
    public static final String N8 = "pieces/N8.png";

    private BufferedImage img;
    
    /**
     * El constructor lee el fichero correspondinte con la imagen.
     *
     * @param s Tipo String que indica el fichero
     */
    public Piece(String s) {
        try {
            img = ImageIO.read(new File(s));
        } catch (IOException e) {
        }
    }

    /**
     * Con el método paintComponent pinta la imagen en cuya posición ha sido 
     * indicada.
     *
     * @param g Tipo Graphics para dibujar la imagen
     * @param x Tipo Float que indica la coordenada x en el tablero.
     * @param y Tipo Float que indica la coordenada y en el tablero.
     */
    void paintComponent(Graphics g, float x, float y) {
        g.drawImage(img,(int) x, (int) y, null);
    }
}
