package principal;

import java.awt.EventQueue;

/**
 * <h1> Arkanoid en Java </h1>
 * 
 * @author Alexandro Rodriguez Parron
 * @version 1.0
 * @category "Juego", "Entretenimiento"..
 * @since 21-02-2021
 * 
 * Si quieres saber información sobre el juego abre el archivo README.md
 */
public class Principal {
    
	public static void main(String[] args) {


		EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
				ventanaPrincipal.inicializar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});	
	}
}
