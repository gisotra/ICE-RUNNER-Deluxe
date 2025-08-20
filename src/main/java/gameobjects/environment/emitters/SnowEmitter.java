package gameobjects.environment.emitters;

import global.Universal;
import system.Movable;
import system.Renderable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnowEmitter implements Movable, Renderable {
    private List<SnowParticle> snow;
    private Random r = new Random(); //usado para respawnar os flocos horizontalmente de forma aleatoria no topo da tela

    public SnowEmitter(int particles) {
        this.snow = new ArrayList<>();
        createSnow(particles);
    }

    private void createSnow(int numberOfParticles){
        for(int i = 0; i < numberOfParticles; i++){
            snow.add(new SnowParticle());
        }
    }

    @Override
    public void update(float deltaTime){
        for(SnowParticle snowP : snow){
            if(snowP.getX() <= 0 || snowP.getY() > Universal.GAME_HEIGHT){
                snowP.setY(r.nextFloat() * -30f - 15f); //pooling
                snowP.setX(r.nextFloat() * Universal.GAME_WIDTH + 3 * Universal.TILES_SIZE);
            }
            snowP.update(deltaTime);
        }
    }

    @Override
    public void render(Graphics2D g2d){
        for(SnowParticle snowP : snow){
            if(snowP.getY() > 0 && snowP.getY() < Universal.GAME_HEIGHT && snowP.getX() > 0 && snowP.getX() < Universal.GAME_WIDTH){
                snowP.render(g2d);
            }
        }
    }

    public void resetSnow(){
        for (SnowParticle snowP : snow) {
            snowP.reposition();
        }
    }


}
