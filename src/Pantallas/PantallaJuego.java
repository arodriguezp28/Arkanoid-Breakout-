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
import java.util.ArrayList;

import javax.imageio.ImageIO;

import principal.Constantes;
import principal.PanelJuego;
import principal.Pantalla;
import principal.Sprite;

public class PantallaJuego implements Pantalla {

    // Fuentes
    private Font fuenteTiempo = new Font("Arial", Font.BOLD, 50);
    private Font fuenteNumBloques = new Font("Arial", Font.BOLD, 30);

    // Bloques
    private int numeroBloques; // Irá contando el número de bloques que quedan
    private int num_Bloques = 0;
    private ArrayList<principal.Sprite> bloques;

    // Barra golpeadora, la dividiremos en 3, cada parte le dará una velocidad
    // distinta
    protected Sprite barraCentral;
    protected Sprite barraDerecha;
    protected Sprite barraIzquierda;

    // Bola, se moverá por todo el Frame
    private Sprite bola;
    private ArrayList<Sprite> bolas;

    // PanelJuego Referencia
    private PanelJuego panelJuego;

    // Contar tiempo
    private double contadorTiempo;
    private double tiempoTrascurrido;
    private DecimalFormat formateador = new DecimalFormat("0.00");

    // Puntuación
    private int puntuacion = 0;

    // Barra inferior para game over
    private Sprite barraGameOver;

    public PantallaJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        numeroBloques = Constantes.num_Bloques;
        num_Bloques = numeroBloques;
        bolas = new ArrayList<Sprite>();
    }

    @Override
    public void inicializarPantalla() {
        // Inicializamos el ArrayList bloques
        bloques = new ArrayList<>();
        crearBloques();

        // Creamos la barraDeGameOver con su posición
        barraGameOver = new Sprite(Constantes.RUTA_LINEA_GAMEOVER, panelJuego.getWidth(), Constantes.ALTO_LINEA, 0,
                panelJuego.getHeight() - (Constantes.ALTO_LINEA * 2), 0, 0);

        // Creamos la barra con su posición que estará dividida en 3 partes
        barraCentral = new Sprite(Constantes.RUTA_BARRA_CENTRAL, Constantes.ANCHO_BARRA, Constantes.ALTO_BARRA,
                (panelJuego.getWidth() / 2) - (Constantes.ANCHO_BARRA / 2),
                (panelJuego.getHeight() - Constantes.ALTO_PANEL), 0, 0);
        barraDerecha = new Sprite(Constantes.RUTA_BARRA_DERECHA, Constantes.ANCHO_BARRA, Constantes.ALTO_BARRA,
                (panelJuego.getWidth() / 2) + (Constantes.ANCHO_BARRA / 2),
                (panelJuego.getHeight() - Constantes.ALTO_PANEL), 0, 0);
        barraIzquierda = new Sprite(Constantes.RUTA_BARRA_IZQUIERDA, Constantes.ANCHO_BARRA, Constantes.ALTO_BARRA,
                ((panelJuego.getWidth() / 2) - (Constantes.ANCHO_BARRA / 2)) - Constantes.ANCHO_BARRA,
                (panelJuego.getHeight() - Constantes.ALTO_PANEL), 0, 0);

        // Contador del timepo
        contadorTiempo = 0;
        contadorTiempo = System.nanoTime();

    }

    /**
     * Método para crear los bloques. Para alinearlos, creamos un entero con una
     * posición definida que se irá sumando con la longitud de los bloques Entonces
     * cada bloque lo pone uno seguido de otro y un If en el que el entero si es
     * mayor que el tamaño del panelJuego utiliza otro entero para colocarlo debajo.
     */
    private void crearBloques() {
        int posX = 20;
        int posY = Constantes.ALTO_PANEL;
        for (int i = 0; i < Constantes.num_Bloques; i++) {

            Sprite bloque = new Sprite(Constantes.RUTA_BLOQUE, Constantes.LADO_BLOQUE_X, Constantes.LADO_BLOQUE_Y, 0, 0,
                    0, 0);
            // Les ponemos posiciones
            if (posX > panelJuego.getWidth()) {
                posY += (Constantes.LADO_BLOQUE_Y + (Constantes.LADO_BLOQUE_X / 2));
                posX = 20;
            }
            bloque.setPosX(posX);
            posX += Constantes.LADO_BLOQUE_X + (Constantes.LADO_BLOQUE_X / 2);
            bloque.setPosY(posY);
            // Les añadimos al ArrayList
            bloques.add(i, bloque);
        }
    }

    @Override
    public void pintarPantalla(Graphics g) {
        // Insertamos en el fondo una imagen
        rellenarFondo(g);

        // Estampamos los bloques y si el arrayList no esta vacío, para que no de
        // excepciones
        if (!bloques.isEmpty()) {
            for (int i = 0; i < bloques.size(); i++) {
                bloques.get(i).estampar(g);
            }
        }

        // Si la barra del GAMEOVER esta creada, la estampamos
        if (barraGameOver != null) {
            barraGameOver.estampar(g);
        }

        // Si la barra esta creada, la estampamos
        if (barraCentral != null) {
            barraCentral.estampar(g);
            barraDerecha.estampar(g);
            barraIzquierda.estampar(g);
        }

        // Hacemos que la primera bola se estampe, solo si esta creada.
        if (!bolas.isEmpty()) {
            if (bolas.get(0) != null) {
                bolas.get(0).estampar(g);
            }
        }

        // Inicializamos el for a 1 ya que no queremos que la bola 1 que se encuentra en
        // la posicion 0 del array, le afecte y estampamos las demás bolas.
        if (!bolas.isEmpty()) {
            for (int i = 1; i < bolas.size(); i++) {
                if (bolas.get(i) != null) {
                    bolas.get(i).estampar(g);
                }
            }
        }

        // Pintar el tiempo
        g.setColor(Color.WHITE);
        g.setFont(fuenteTiempo);
        g.drawString(formateador.format(tiempoTrascurrido / 1e9), 60, 60);

        // Pintar el numero de bloques
        g.setColor(Color.WHITE);
        g.setFont(fuenteNumBloques);
        g.drawString("" + num_Bloques, panelJuego.getWidth() - 60, 60);

    }

    /**
     * Reescalar la imagen al tamaño actual del juego, y estamparla con ese tamaño
     * Para que se adapte a la pantalla
     * 
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

        contarTiempo();
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Si la bola está creada, la movemos y mientras comprobamos si hay colisiones
        if (!bolas.isEmpty()) {
            if (bolas.get(0) != null) {
                bolas.get(0).mover(panelJuego.getWidth(), panelJuego.getHeight());
                comprobarColisiones();
            }
        }
        // Si existen más bolas, las movemos
        if (!bolas.isEmpty()) {
            for (int i = 1; i < bolas.size(); i++) {
                if (bolas.get(i) != null) {
                    bolas.get(i).mover(panelJuego.getWidth(), panelJuego.getHeight());
                }
            }
        }

    }

    /**
     * Método para contar el tiempo que va pasando
     */
    private void contarTiempo() {
        tiempoTrascurrido = System.nanoTime() - contadorTiempo;
    }

    /**
     * Se crea una nueva pelota que va cayendo
     */
    private void bolaCayendo(Sprite bloque) {
        bolas.add(new Sprite(Constantes.RUTA_PELOTA, Constantes.ANCHO_BOLA, Constantes.ALTO_BOLA, bloque.getPosX(),
                bloque.getPosY(), 0, Constantes.VEL_Y_BOLA_POS));
    }

    /**
     * Método para comprobar si hay colisiones entre objetos o no
     */
    private void comprobarColisiones() {

        for (int i = 0; i < bloques.size(); i++) {
            if (!bolas.isEmpty()) {
                for (int j = 0; j < bolas.size(); j++) {
                    // En el caso de que hayan distintas bolas, si colisionan con la barraGameOver, se van eliminando
                    if (bolas.get(j).colisiona(barraGameOver)) {
                        bolas.remove(j);
                        j = 0;
                    }

                    if (!bolas.isEmpty()) {
                        // Si la bola colisiona con uno de los bloques, la velocidad cambia, borramos el
                        // bloque colisionado y sumamos puntos
                        if (bolas.get(j).colisiona(bloques.get(i))) {
                            // Si i es igual al número aleatorio que retorne el método, creará una bola nueva
                            if (i == numAleatorioBloque()) {
                                bolaCayendo(bloques.get(i));
                            }
                            bloques.remove(i);
                            i = 0;
                            bolas.get(j).setVelX(numAleatorioPelota());
                            bolas.get(j).setVelY(numAleatorioPelota());
                            num_Bloques--; // Se utiliza para ver los asteroides que van quedando
                            puntuacion++;
                        }
                        //Si la puntuación es igual al número de bloques, cambia a la pantalla victoria
                        if (puntuacion == numeroBloques) {
                            panelJuego.cambiarAPantallaVictoria(new PantallaVictoria(panelJuego, tiempoTrascurrido));
                        }
                        // Colisión de la bola con las partes de la barra

                        // Si la pelota colisiona con la barra central, la velocidad en ejeX es a 0 y en
                        // ejeY tiene valor negativo para que suba
                        if (bolas.get(j).colisiona(barraCentral)) {
                            bolas.get(j).setVelX(0);
                            bolas.get(j).setVelY(Constantes.VEL_Y_BOLA_NEG);
                        } else {
                            // Si la pelota colisiona con la barraIzquierda, la velocidad en ejeX es
                            // negativo para que vaya hacia la barraIzquierda
                            // y el ejeY es negativo para que suba
                            if (bolas.get(j).colisiona(barraIzquierda)) {
                                bolas.get(j).setVelX(Constantes.VEL_X_BOLA_NEG);
                                bolas.get(j).setVelY(Constantes.VEL_Y_BOLA_NEG);
                            } else {
                                // Si la pelota colisona con la barraDerecha, la velocidad en ejeX es positiva,
                                // para que vaya hacia la derecha, y
                                // en el ejeY negativo para que suba.
                                if (bolas.get(j).colisiona(barraDerecha)) {
                                    bolas.get(j).setVelX(Constantes.VEL_X_BOLA_POS);
                                    bolas.get(j).setVelY(Constantes.VEL_Y_BOLA_NEG);
                                }
                            }
                        }
                    }
                }
            } else {
                //Si no hay pelota cambia a la pantalla de game over
                panelJuego.cambiarAPantallaGameOver(new PantallaGameOver(panelJuego, tiempoTrascurrido));
            }
        }
    }

    /**
     * En colisión con el bloque la pelota cambia de velocidad según el aleatorio
     * Método que retorna un número aleatorio 0 y 1
     * En el caso de que el número aleatorio sea 0, la velocidad será negativa
     * En el caso de que el número aleatorio sea 1, la velocidad será positiva
     * @return
     */
    private int numAleatorioPelota() {
        int numAleatorio = (int) (Math.random() * 2);
        if (numAleatorio == 0) {
            numAleatorio = Constantes.VEL_X_BOLA_NEG;
        } else {
            numAleatorio = Constantes.VEL_X_BOLA_POS;
        }
        return numAleatorio;
    }

    /**
     * Método que escoge un número aleatorio entre 0 y la mitad de num_Bloques
     * Para elegir el bloque en el que se creará una nueva pelota.
     * @return
     */
    private int numAleatorioBloque() {
        return (int) (Math.random() * (Constantes.num_Bloques / 2));
    }

    @Override
    public void pulsarRaton(MouseEvent e) {
        // Si la barra está creada, y la bola no está creada, entonces creamos la bola,
        // un poquito por encima de la barra
        if (barraCentral != null) {
            if (bola == null) {
                bola = new Sprite(Constantes.RUTA_PELOTA, Constantes.ANCHO_BOLA, Constantes.ALTO_BOLA,
                        barraCentral.getPosX() + Constantes.ANCHO_BOLA,
                        barraCentral.getPosY() - Constantes.ALTO_BOLA * 2, numAleatorioPelota(),
                        Constantes.VEL_Y_BOLA_NEG);
                bolas.add(bola);
            }
        }
    }

    @Override
    public void moverRaton(MouseEvent e) {
        // Si la barra está creada vamos cambiando la posición de la barra según movemos ratón
        if (barraCentral != null) {
            barraCentral.setPosX(e.getX() - (Constantes.ANCHO_BARRA / 2));
            barraDerecha.setPosX(
                    e.getX() - (((Constantes.ANCHO_BARRA - Constantes.ANCHO_BARRA) - Constantes.ANCHO_BARRA) / 2));
            barraIzquierda.setPosX(e.getX() - (Constantes.ANCHO_BARRA + (Constantes.ANCHO_BARRA / 2)));
        }
    }

    public int getNumeroBloques() {
        return this.numeroBloques;
    }

    public void setNumeroBloques(int numeroBloques) {
        this.numeroBloques = numeroBloques;
    }

}
