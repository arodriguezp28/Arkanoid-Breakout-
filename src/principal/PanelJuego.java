package principal;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import Pantallas.PantallaGameOver;
import Pantallas.PantallaInicio;
import Pantallas.PantallaJuego;
import Pantallas.PantallaVictoria;

public class PanelJuego extends JPanel implements Runnable, MouseMotionListener, MouseListener, ComponentListener {

    private static final long serialVersionUID = -8433373221080444754L;
    private Pantalla pantallaActual;// Es la instacia de la interfaz pantalla
    
    /**
     * Constructor
     */
    public PanelJuego() {
        pantallaActual = new PantallaInicio(this);

        this.addComponentListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        new Thread(this).start();
    }

    

    /**
     * Método que se llama automáticamente para pintar el componente.
     */
    @Override
    public void paintComponent(Graphics g) {

        pantallaActual.pintarPantalla(g);

    }

    /**
     * Método para rellenar el fondo del componente.
     * 
     * @param g
     */
    @Override
    public void run() {
        pantallaActual.inicializarPantalla();
        while (true) {
            pantallaActual.ejecutarPaint();
            // Repintar
            repaint();
            Toolkit.getDefaultToolkit().sync();
        }

    }


    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        pantallaActual.moverRaton(e);
    }

    @Override
    public void componentResized(ComponentEvent e) {}

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        pantallaActual.pulsarRaton(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}


    /**
     * Método que cambia a la pantalla de juego
     * @param pantallaJuego
     */
	public void cambiarAPantallaJuego(PantallaJuego pantallaJuego) {
        pantallaJuego.inicializarPantalla();
        pantallaActual = pantallaJuego;
    }

    /**
     * Método que cambia a la pantalla de la victoria
     * @param pantallaVictoria
     */
    public void cambiarAPantallaVictoria(PantallaVictoria pantallaVictoria) {
        pantallaVictoria.inicializarPantalla();
        pantallaActual = pantallaVictoria;
    }

    /**
     * Método que cambia a la pantalla de GameOver
     * @param pantallaGameOver
     */
    public void cambiarAPantallaGameOver(PantallaGameOver pantallaGameOver) {
        pantallaGameOver.inicializarPantalla();
        pantallaActual = pantallaGameOver;
	}
}
