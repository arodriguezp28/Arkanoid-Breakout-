package principal;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * Métodos fundamentales de una pantalla
 */
public interface Pantalla {
    public void inicializarPantalla();
    public void pintarPantalla(Graphics g);
    public void ejecutarPaint();
    public void pulsarRaton(MouseEvent e);
    public void moverRaton(MouseEvent e);
}
