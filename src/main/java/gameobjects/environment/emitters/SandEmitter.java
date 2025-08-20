package gameobjects.environment.emitters;

import global.Universal;
import system.Movable;
import system.Renderable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SandEmitter implements Movable, Renderable {
    private List<SandParticle> sandstorm;
    private Random r = new Random();

    public SandEmitter(int numberOfSand){
        this.sandstorm = new ArrayList<>();
        createSandstorm(numberOfSand);
    }

    public void createSandstorm(int number){
        for(int i = 0; i < number; i++){
            sandstorm.add(new SandParticle());
        }
    }

    @Override
    public void update(float deltaTime){
        for(SandParticle sandP : sandstorm){
            if(sandP.getX() <= -1 * Universal.TILES_SIZE){
                sandP.setX(sandP.getSpawnPointX()); //pooling
                sandP.respawn();

            }
            sandP.update(deltaTime);
        }
    }

    @Override
    public void render(Graphics2D g2d){
        for(SandParticle sandP : sandstorm){
            if(sandP.getY() > 0 && sandP.getY() < Universal.GAME_HEIGHT && sandP.getX() > 0 && sandP.getX() < Universal.GAME_WIDTH){
                sandP.render(g2d);
            }

        }
    }

    public void resetSand(){
        for (SandParticle sandP : sandstorm) {
            sandP.reposition();
        }
    }


}
