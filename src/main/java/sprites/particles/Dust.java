package sprites.particles;

import gameobjects.GameObject;
import sprites.ImageLoader;
import sprites.Sprite;
import system.AnimationType;
import system.Movable;
import system.Renderable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Dust extends GameObject implements Movable, Renderable {
    private Random r = new Random();
    private float x;
    private float y;
    private float verticalSpeed;
    private float horizontalSpeed;
    private int altura = 7;
    private int largura = 7;
    private float rotation = 0;
    private float rotationSpeed;
    private BufferedImage dustimage;
    private Sprite<DustAnimation> sprite;

    public Dust(BufferedImage dustimage){
        this.dustimage = dustimage;
        initSprite();
    }

    @Override
    public void initSprite(){
        sprite = new Sprite<>(dustimage, dustimage.getHeight(), dustimage.getWidth(), DustAnimation.class, 1);
    }

    @Override
    public void update(float deltaTime){

    }

    @Override
    public void render(Graphics2D g2d){

    }

    /*Enum da sprite*/
    public enum DustAnimation implements AnimationType {
        STATIC;
        @Override
        public int getIndex(){
            return 0;
        }

        @Override
        public int getFrameCount(){
            return 1;
        }
    }

    @Override
    public void reposition(){
        //
    }
}
