package structure;

import global.Universal;

public class GameEngine implements Runnable{
    /*Canvas principal*/
    private GameCanvas gc;

    /*Controle de Frames*/
    private final double frameTime = 1_000_000_000.0 / Universal.FPS_SET;
    private double lastFrame = System.nanoTime();
    private long nextFrame = System.nanoTime() + (long) frameTime;
    private double threadSleep;
    private long threadSleepMS;
    private int threadSleepNano;
    private long timer = System.currentTimeMillis();

    /*Controle de UPS*/
    private final float fixedStep = 1.0f / 60.0f;

    /*debug*/
    private int frames = 0;
    private int updates = 0;

    /*Construtor*/
    public GameEngine(GameCanvas gc){
        this.gc = gc;
    }

    /*Thread principal*/
    @Override
    public void run(){
        float accumulator = 0.0f;

        while(true){
            double now = System.nanoTime(); //diretamente do meu confiável JavaFX
            double framePeriod = (now - lastFrame) / 1_000_000_000.0;
            lastFrame = now;

            if(framePeriod > 0.25){
                framePeriod = 0.25;
            }

            accumulator += (float) framePeriod;

            while(accumulator >= fixedStep){
                update(fixedStep);
                updates++;
                accumulator -= fixedStep;
            }

            render();
            frames++;

            /*Controla o tempo de sono*/
            threadSleep = (nextFrame - System.nanoTime()) / 1_000_000.0;
            if(threadSleep < 0){
                threadSleep = 0;
            }
            threadSleepMS = (long) threadSleep;
            threadSleepNano = (int) ((threadSleep - threadSleepMS) * 1_000_000);

            try {
                Thread.sleep(threadSleepMS, threadSleepNano);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            nextFrame += (long) frameTime;

            //debug de FPS e UPS
            //gc.writeData(frames, updates);
            if (System.currentTimeMillis() - timer >= 1000) {
                frames = 0;
                updates = 0;
                timer += 1000;
            }
        }
    }


    public void update(float dT) {
        //gc.update(dT);
    }


    public void render() {
        gc.render();
    }

    public void sleepEngine() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        nextFrame = System.nanoTime() + (long) frameTime;
        lastFrame = System.nanoTime();
    }
}
