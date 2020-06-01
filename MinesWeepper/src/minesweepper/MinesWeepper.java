package minesweepper;

import java.awt.Toolkit;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;

/**
 * @author David Santomé Galván - 43221079P
 * @author Mateo Javier Ramón Román - 43225388Q
 * https://www.youtube.com/watch?v=19GV3cdF7s8
 *
 * Esta práctica final trata de un juego llamado MinesWeeper (BuscaMinas) donde
 * en un tablero de 9x9 se reparten aleatoriamente 10 minas. Debes ir haciendo
 * click en las casillas intentando no dar en ninguna mina, en caso contrario,
 * perderás.
 *
 * Este juego también tiene implementada la opción de guardar la partida,
 * reanudarla cuando quieras y empezar una de nuevo.
 */
public class MinesWeepper extends JFrame implements MouseListener {

    // Variables Gráficas
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem savedGameItem, saveGameItem, restartItem, exitItem;
    Board board;
    JFileChooser fileChooser;

    /**
     * Método main.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MinesWeepper mW = new MinesWeepper();
        mW.setVisible(true);
    }

    /**
     * El constructor llama a un método para crear el tablero y llama a un
     * método para inicializar la barra del menú. El JFrame se ajusta a la
     * medida de ambos.
     *
     */
    public MinesWeepper() {
        // Iniciamos la barra de menu y sus componentes
        initComponents();

        // Inicializamos el tablero y se colocan aleatoriamente las bombas
        createBoard();
        board.setBombs();
    }

    /**
     * El método createBoard crea un nuevo tablero, añade un MouseListener y lo
     * añade al JFrame.
     *
     */
    private void createBoard() {
        board = new Board();
        board.addMouseListener(this);
        this.getContentPane().add(board);
        this.setSize(board.getPreferredSize());
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    /**
     * El método initComponents inicializa la barra del menu, el menú y sus
     * items junto a sus actionPerformed cuando las seleccionamos.
     *
     */
    private void initComponents() {
        // Inicialización de variables       
        menuBar = new JMenuBar();
        menu = new JMenu();
        savedGameItem = new JMenuItem();
        saveGameItem = new JMenuItem();
        restartItem = new JMenuItem();
        exitItem = new JMenuItem();
        fileChooser = new JFileChooser();

        // Añadimos el título de la ventana
        setTitle("Mines Weeper");

        // Declaramos el título del menú
        menu.setText("Options");

        // Declaraciones de cada item del menú
        savedGameItem.setText("Saved Game");
        savedGameItem.addActionListener((java.awt.event.ActionEvent evt) -> {
            try {
                savedGameActionPerformed(evt);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(MinesWeepper.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        menu.add(savedGameItem);

        saveGameItem.setText("Save Game");
        saveGameItem.addActionListener((java.awt.event.ActionEvent evt) -> {
            try {
                saveGameActionPerformed(evt);
            } catch (IOException ex) {
                Logger.getLogger(MinesWeepper.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        menu.add(saveGameItem);

        restartItem.setText("Restart Game");
        restartItem.addActionListener((java.awt.event.ActionEvent evt) -> {
            restartGameActionPerformed(evt);
        });
        menu.add(restartItem);

        exitItem.setText("Exit");
        exitItem.addActionListener((java.awt.event.ActionEvent evt) -> {
            exitGameActionPerformed(evt);
        });
        menu.add(exitItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    /**
     * El método mouseReleased define las acciones que se llevarán a cabo cuando
     * se suelta el botón del ratón en una casilla.
     *
     * @param me Tipo MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent me) {
        int x = 0, y = 0, i, j = 0;
        
        if (me.getButton() == MouseEvent.BUTTON1) {
            x = me.getX();
            y = me.getY();
            // Calcula el número de casilla del tablero según la x e y del 
            // JFrame 
            boolean found = false;
            for (i = 0; i < Board.DIMENSIONS && !found; i++) {
                for (j = 0; j < Board.DIMENSIONS && !found; j++) {
                    found = board.infoSquare(i, j, x, y);
                }
            }
            i--;
            j--;

            // Métodos que resuelven el tablero
            int win = board.checkBoard(i, j);
            board.repaint();
            result(win);
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    /**
     * El método result enseña un mensaje en caso de victoria/derrota.
     *
     */
    private void result(int win) {
        switch (win) {
            case -1:
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Has pisado una mina",
                        "Derrota", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 0:
                break;
            case 1:
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Has desbloqueado todas las\n"
                        + "casillas",
                        "Victoria", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    /**
     * El método savedGameActionPerformed gestiona abrir una partida previamente
     * guardada. Abre un JFileChooser, se inicializa el tablero y se colocan las
     * bombas donde estaban previamente y destapa las casillas que estaban
     * destapadas a la hora de guardar.
     *
     */
    private void savedGameActionPerformed(ActionEvent evt) throws IOException, ClassNotFoundException {
        int returnValue = fileChooser.showOpenDialog(savedGameItem);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Incializamos un tablero nuevo
            this.getContentPane().remove(board);
            createBoard();
            // Abrimos fichero
            String file = fileChooser.getSelectedFile().getName();
            BufferedReader br = new BufferedReader(new FileReader(file));
            // Leemos los int para inicializar el tablero como estaba antes
            // de guardar la partida
            for (int row = 0; row < Board.DIMENSIONS; row++) {
                for (int column = 0; column < Board.DIMENSIONS; column++) {
                    board.setId(row, column, (int) br.read());
                }
            }
            br.close();
            // Colocamos las imagenes con la configuración recogida
            board.restartSavedGameImages();
            System.out.println("Game loaded successfully");
            System.out.println("");
        }
    }

    /**
     * El método saveGameActionPerformed gestiona guardar una partida. Para ello
     * guardaremos la matriz con casillas: sin bombas-tapadas, con
     * bombas-tapadas, sin bombas-destapadas, con bombas-destapadas.
     *
     */
    private void saveGameActionPerformed(ActionEvent evt) throws IOException {
        // Leemos el nombre del fichero que vamos a guardar
        String s = readString("File Name: ");
        // Guardamos el array en el fichero
        BufferedWriter bw = new BufferedWriter(new FileWriter(s + ".dat"));
        for (int row = 0; row < Board.DIMENSIONS; row++) {
            for (int column = 0; column < Board.DIMENSIONS; column++) {
                bw.write(board.getId(row, column));
            }
        }
        bw.close();
        System.out.println("Game saved successfully");
        System.out.println("");
    }

    /**
     * El método restartGameActionPerformed incializa un nuevo tablero.
     *
     */
    private void restartGameActionPerformed(ActionEvent evt) {
        this.getContentPane().remove(board);
        createBoard();
        board.setBombs();
    }

    /**
     * El método exitGameActionPerformed cierra el juego.
     *
     */
    private void exitGameActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    /**
     * El método readString lee por teclado una cadena de caracteres y devuelve
     * un String.
     *
     * @param msg Tipo String mensaje a imprimir por pantalla
     */
    private static String readString(String msg) {
        String s = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(msg);
            s = in.readLine();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return s;
    }
}
