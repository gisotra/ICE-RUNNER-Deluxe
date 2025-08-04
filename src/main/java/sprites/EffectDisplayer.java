package sprites;

import system.AnimationType;
import system.Renderable;

import java.awt.*;

public class EffectDisplayer implements Renderable {

    private float x, y;
    private Sprite<? extends AnimationType> sprite;
    private boolean displaying = false;

    public EffectDisplayer(Sprite<? extends AnimationType> sprite){
        this.sprite = sprite;
    }

    public void displayEffect(Sprite<? extends AnimationType> sprite, float x, float y){
        this.sprite = sprite;
        sprite.setCurrentFrame(0); //reinicio a animação
        updatePosition(x, y);
        displaying = true;
    }

    public void update(){
        if(displaying){
            sprite.update();
            if(sprite.getCurrentFrame() >= sprite.getFrameSpeed() - 1){
                    displaying = false;
            }
        }
    }

    @Override
    public void render(Graphics2D g2d){
        if(displaying){
            sprite.render(g2d, (int)x, (int)y);
        }
    }

    public void updatePosition(float X, float Y){
        this.x = X;
        this.y = Y;
    }
}
