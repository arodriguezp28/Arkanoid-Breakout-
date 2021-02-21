package principal;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
/**
 * Ventana Principal
 * @author Alexandro Rodriguez Parron
 * @see para ver con más detalle la documentación ve a {@link Principal}
 */
public class VentanaPrincipal {

	//La ventana principal, en este caso, guarda todos los componentes:
	private JFrame ventana;
	private PanelJuego panelJuego;
	private JButton elegirBloques;
	private JTextField numeroBloques;


	/**
     * Constructor, marca el tamaño y el cierre del frame
     */
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 50, 800, 600);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
     * Inicializa todos los componentes del frame
     */
	
	public void inicializarComponentes(){
		//Definimos el layout:
		ventana.setLayout(new GridBagLayout());
		
		panelJuego = new PanelJuego();

		elegirBloques = new JButton("Elegir número de Bloques");
		numeroBloques = new JTextField(""+Constantes.num_Bloques);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		ventana.add(elegirBloques, gbc);
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 8;
		gbc.fill = GridBagConstraints.BOTH;
		ventana.add(numeroBloques, gbc);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.weightx = 1;
		gbc.weighty = 10;
		gbc.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, gbc);
	}
	
	/**
     * Inicializa todos los lísteners del frame
     */
	public void inicializarListeners(){
		elegirBloques.addActionListener(e->{
			Constantes.num_Bloques = Integer.parseInt(numeroBloques.getText());
			numeroBloques.setText("");
		});
	}
  

	/**
	 * Método que realiza todas las llamadas necesarias para inicializar la ventana correctamente.
	 */
	public void inicializar(){
		ventana.setVisible(true);
		inicializarComponentes();	
		inicializarListeners();		
	}
}
