package sprites;

import gameobjects.GameObject;
import system.AnimationType;

public class ParticleEmitter extends GameObject {
    private float durationTime;
    private float spawnX, spawnY;
    private int width, heigth;
    private float verticalSpeed, horizontalSpeed;
    private Sprite<DustAnimation>[] particles;
    private boolean emitting = false;

    public ParticleEmitter(){
        initSprite();
    }

    @Override
    public void initSprite(){
        particles = new Sprite[6];
        /*for(int i = 1; i <= particles.length; i++){
            particles[i] = new Sprite<>(ImageLoader.getImage("particles/dust/dustP" + String.valueOf(i) + ".png"),
                    (ImageLoader.getImage("particles/dust/dustP" + String.valueOf(i) + ".png").getHeight()),
                        (ImageLoader.getImage("particles/dust/dustP" + String.valueOf(i) + ".png").getWidth()),
                            DustAnimation.class, 1);
        }*/
    }

    public void emmitParticles(float x, float y, float durationTime){
        /*esse metodo vai ser chamado em condições específicas do player*/
        emitting = true;
    }

    public void launch(){

    }

    @Override
    public void reposition(){

    }


    public void update(float deltaTime){
        if(emitting){
            //desenvolvo a particula
        }
    }

    /*Enum da sprite*/
    public enum DustAnimation implements AnimationType{
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

}
