package gameObjects;

import sprites.Sprite;

public abstract class GameObjects {
    /*Classe mãe de todos os elementos do meu jogo*/

    /*Coordenadas*/
    protected float x, y;

    /*Dimensões*/
    protected int width, height;

    /*Controle de pooling*/
    protected boolean isActive = false;

    /*Sprites*/
    protected Sprite sprite;
    protected float anchorX = 0.5f;
    protected float anchorY = 0.5f;

    //-------------------------------------------------------
    /*Métodos abstratos*/
    public abstract void initSprite();

    /*Getters*/
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isActive() {
        return isActive;
    }

    /*Setters*/
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
