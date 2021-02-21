package Pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import principal.Constantes;
import principal.PanelJuego;
import principal.Pantalla;

public class PantallaVictoria implements Pantalla {

    // PanelJuego
    private PanelJuego panelJuego;

    // Las distintas fuentes
    private Font fuenteGrande = new Font("Arial", Font.BOLD, 30);
    private Font fuenteNormal = new Font("Arial", Font.BOLD, 20);

    // Tiempo
    private double tiempo;
    private DecimalFormat formateador = new DecimalFormat("0.00");


    public PantallaVictoria(PanelJuego panelJuego, double tiempoTrascurrido) {
        this.panelJuego = panelJuego;
        tiempo = tiempoTrascurrido;
    }

    @Override
    public void inicializarPantalla() {}

    @Override
    public void pintarPantalla(Graphics g) {
        // Pintamos el fondo
        rellenarFondo(g);

        // Pintamos el tiempo total
        g.setColor(Color.BLACK);
        g.setFont(fuenteGrande);
        g.drawString(formateador.format(tiempo / 1e9), panelJuego.getWidth() - 100, panelJuego.getHeight() - 300);

        // Texto de victoria
        g.setColor(Color.BLACK);
        g.setFont(fuenteGrande);
        g.drawString("¡¡VICTORY!!", panelJuego.getWidth() / 2 - 85, panelJuego.getHeight() / 2);

        // Texto para jugar de nuevo
        g.setColor(Color.BLACK);
        g.setFont(fuenteNormal);
        g.drawString(Constantes.JUGAR_DE_NUEVO, panelJuego.getWidth() / 2 - 100, panelJuego.getHeight() / 2 + 50);


        // Texto para salir
        g.setColor(Color.BLACK);
        g.setFont(fuenteNormal);
        g.drawString(Constantes.SALIR, panelJuego.getWidth() / 2 - 100, panelJuego.getHeight() / 2 + 100);
    }

    /**
     * Reescalar la imagen al tamaño actual del juego, y estamparla con ese tamaño
     * Para que se adapte a la pantalla
     * @param g
     */
    private void rellenarFondo(Graphics g) {
        BufferedImage imagen = null;

        try {
            imagen = ImageIO.read(new File(Constantes.RUTA_FONDO));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.drawImage(imagen.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
    }

    @Override
    public void ejecutarPaint() {}

    @Override
    public void pulsarRaton(MouseEvent e) {
        // Si pulsamos con el botón izquierdo del ratón, volvemos a jugar.
        if(SwingUtilities.isLeftMouseButton(e)){
            panelJuego.cambiarAPantallaJuego(new PantallaJuego(panelJuego));
        }else{
            // Si pulsamos con el botón derecho del ratón, sale del programa
            if(SwingUtilities.isRightMouseButton(e)){
                System.exit(0);
            }
        }

    }

    @Override
    public void moverRaton(MouseEvent e) {}

    public double getTiempo() {
        return this.tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    
}
