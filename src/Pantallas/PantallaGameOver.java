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

public class PantallaGameOver implements Pantalla {

    //Atributos de la clase PantallaGameOver, necesario para implementar la interfaz
    private PanelJuego panelJuego;
    private Font fuenteGrande = new Font("Arial", Font.BOLD, 30);
    private Font fuenteNormal = new Font("Arial", Font.BOLD, 20);

    private double tiempo;
    private DecimalFormat formateador = new DecimalFormat("0.00");


    /**
     * Constructor Parametrizado de PantallaGameOver.
     */
    public PantallaGameOver(PanelJuego panelJuego, double tiempoTrascurrido) {
        this.panelJuego = panelJuego;
        if(tiempo < tiempoTrascurrido){
            tiempo = tiempoTrascurrido;
        }
        
    }

    @Override
    public void inicializarPantalla() {}

    @Override
    public void pintarPantalla(Graphics g) {
        //Creamos el fondo
        rellenarFondo(g);
        

        //Añadimos el tiempo en el que ha perdido personaje
        g.setColor(Color.BLACK);
        g.setFont(fuenteGrande);
        g.drawString(formateador.format(tiempo / 1e9), panelJuego.getWidth() - 100, panelJuego.getHeight() - 300);

        //Añadimos un texto GameOver
        g.setColor(Color.BLACK);
        g.setFont(fuenteGrande);
        g.drawString("¡¡GAME OVER!!", panelJuego.getWidth() / 2 - 100, panelJuego.getHeight() / 2);

        //Añadimos un texto jugar de nuevo
        g.setColor(Color.BLACK);
        g.setFont(fuenteNormal);
        g.drawString(Constantes.JUGAR_DE_NUEVO, panelJuego.getWidth() / 2 - 100, panelJuego.getHeight() / 2 + 50);

        //Añadir un texto salir
        g.setColor(Color.BLACK);
        g.setFont(fuenteNormal);
        g.drawString(Constantes.SALIR, panelJuego.getWidth() / 2 - 100, panelJuego.getHeight() / 2 + 100);

    }
    /**
     * Reescalar la imagen al tamaño actual del juego, y estamparla con ese tamaño.
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

        g.drawImage(imagen.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH), 0, 0,
                null);
    }

    @Override
    public void ejecutarPaint() {}

    @Override
    public void pulsarRaton(MouseEvent e) {
        //Si se pulsa el boton izquierdo del raton vuelve a empezar a jugar
        if(SwingUtilities.isLeftMouseButton(e)){
            panelJuego.cambiarAPantallaJuego(new PantallaJuego(panelJuego));
        }else{
            //Si se pulsa en el boton derecho del raton sale del programa
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
