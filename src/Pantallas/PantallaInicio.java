package Pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import principal.Constantes;
import principal.PanelJuego;
import principal.Pantalla;


public class PantallaInicio implements Pantalla {

	// PanelJuego Referencia
	private PanelJuego panelJuego;

	// Cambiador de color
	private Color colorIntro = Color.BLACK;

	// Fuente de la pantalla de inicio
	private Font fuenteGrande = new Font("Arial", Font.BOLD, 30);


	public PantallaInicio(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
	}

	@Override
	public void inicializarPantalla() {
	}
	

	@Override
	public void pintarPantalla(Graphics g) {
		// Creamos el fondo con una imagen de galaxia
		rellenarFondo(g);


		// Escribimos un texto de bienvenida
		g.setColor(colorIntro);
		g.setFont(fuenteGrande);
		g.drawString("Bienvenidos Arkanoid", panelJuego.getWidth() / 2 - 165, 100);

		// Y escribimos otro texto donde le indicamos lo que tiene que hacer para
		// empezar a jugar
		g.setColor(colorIntro);
		g.setFont(fuenteGrande);
		g.drawString("Dale click para empezar", panelJuego.getWidth() / 2 - 165, (panelJuego.getHeight() / 2) + 100);
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
	public void ejecutarPaint() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Intercalamos el color que tendra el atributo colorIntro
		colorIntro = colorIntro == Color.RED ? Color.BLACK : Color.RED;

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		// Si se pulsa en la pantalla hacemos que se empieze a jugar
		panelJuego.cambiarAPantallaJuego(new PantallaJuego(panelJuego));
	}

	@Override
	public void moverRaton(MouseEvent e) {
	}

}
