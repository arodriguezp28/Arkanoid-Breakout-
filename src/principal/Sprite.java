package principal;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Image;

public class Sprite {
    private BufferedImage buffer;
    private Color color = Color.RED;
    //Variables de dimension
    private int ancho;
    private int alto;
    //variables de colocación
    private int posX;
    private int posY;
    //Velocidades
    private int velX;
    private int velY;

    /**
     * Constructor parametrizado
     * 
     * @param rutaImagen
     * @param ancho
     * @param alto
     * @param posX
     * @param posY
     * @param velX
     * @param velY
     */
    public Sprite(String rutaImagen, int ancho, int alto, int posX, int posY, int velX, int velY) {
        this.ancho = ancho;
        this.alto = alto;
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        inicializarBuffer(rutaImagen);
    }

    public Sprite() {
        
	}

	protected void inicializarBuffer(String rutaImagen){
        try {
            buffer = ImageIO.read(new File(rutaImagen));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics g = buffer.getGraphics();
        g.dispose();
    }

    /**
     * Métodos para insertar los Graphics en el frame
     * @param g
     */
    public void estampar(Graphics g){
        g.drawImage(buffer.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), posX, posY, null);
    }


    //Setter & Getters
    public BufferedImage getBuffer() {
        return this.buffer;
    }

    public void setBuffer(BufferedImage buffer) {
        this.buffer = buffer;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getAncho() {
        return this.ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return this.alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }


    public int getVelX() {
        return this.velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }
    public int getVelY() {
        return this.velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    /**
     * Método mover, las posiciones van cambiando según la velocidad que cada objeto tenga
     * @param anchoVentana
     * @param altoVentana
     */
    public void mover(int anchoVentana, int altoVentana){

        posX += velX;
        posY += velY;

        //Comprobar que no sobre salga por la derecha
        if((posX + ancho) >= anchoVentana){

            velX = - Math.abs(velX); //Forzar a que la velocidad sea negativa
        }
        //Por la Izquierda
        if(posX < 0){
            velX = Math.abs(velX); //Forzar a que la velocidad sea positiva //Valor absoluto
        }
        //Por alto
        if((posY + alto) >= altoVentana){
            velY = - Math.abs(velY);
        }
        //Por bajo
        if(posY < 0){
            velY = Math.abs(velY); //Forzar a que la velocidad sea positiva //Valor absoluto
        }
    }

    /**
     * Método colisiona en el que se comprobará si hay colisiones en el ejeX o en el ejeY
     * @param otroSprite
     * @return
     */
    public boolean colisiona(Sprite otroSprite){
        boolean colisionEjeX = false;
        
        if(posX< otroSprite.getPosX()){
            colisionEjeX = posX+ancho >= otroSprite.posX;
        }
        else{
            colisionEjeX = otroSprite.getPosX() + otroSprite.getAncho() >= posX;
        }
        
        if(!colisionEjeX){
            return false;
        } 
        
        boolean colisionEjeY = false;

         if(posY< otroSprite.getPosY()){
            colisionEjeY = posY+alto >= otroSprite.posY;
        }
        else{
            colisionEjeY = otroSprite.getPosY() + otroSprite.getAlto() >= posY;
        }
            

        return colisionEjeX && colisionEjeY; 
    }

}
