package sprites;

import system.Renderable;

import java.awt.*;

public class EffectDisplayer implements Renderable {

    private float x, y;
    private Sprite sprite;
    private boolean displaying = false;

    public EffectDisplayer(Sprite sprite){
        this.sprite = sprite;
    }

    public void displayEffect(Sprite sprite, float x, float y){
        if(displaying){
            return;
        } else {
            displaying = true;
            updatePosition(x, y);
        }
    }

    @Override
    public void render(Graphics2D g2d){
        if(displaying){
            sprite.render(g2d, (int)x, (int)y);
            if(sprite.getCurrentFrame() > sprite.getFrameCount()){
                displaying = false;
                //se ele deu display na animação inteira 1 vez, ele nao dá mais display
            }
        }
    }

    public void updatePosition(float X, float Y){
        this.x = X;
        this.y = Y;
    }
}
