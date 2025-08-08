package gameobjects;

import sprites.Sprite;

public abstract class GameObject {
    /*Classe mãe de todos os elementos do meu jogo*/

    /*Coordenadas*/
    protected float x, y;

    /*Dimensões*/
    protected int width, height;

    /*Controle de pooling*/
    protected boolean active = false;


    //-------------------------------------------------------
    /*Métodos abstratos*/
    public abstract void initSprite();
    public abstract void reposition();

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
        return active;
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
        this.active = active;
    }
}
