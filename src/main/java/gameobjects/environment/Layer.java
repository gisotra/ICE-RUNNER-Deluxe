package gameobjects.environment;

import gameobjects.GameObject;
import sprites.Sprite;
import system.AnimationType;

public class Layer extends GameObject {

    private Sprite<? extends AnimationType> sprite;
    private float layerspeed;

    /*-------------construtores em sobrecarga-------------*/

    /*Não animado*/
    public Layer(int width, int height, float layerspeed){

    }

    /*Animado*/
    public Layer(int width, int height, float layerspeed, Sprite<? extends AnimationType> sprite){

    }

    /*
    pode ser animado ou não pode ser animado
    usar lambda e sobrecarga de construtor pra isso
    */

    /*Métodos abstratos*/
    @Override
    public void initSprite(){

    }

    @Override
    public void reposition(){

    }

    /*Caso receba uma camada que não é animada*/
    public enum layerAnimation implements AnimationType{
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

    /*Caso a camada SEJA animada, eu vou desenvolver o enum de animação na própria classe GameScreen e só chamar no
    construtor de acordo. A velocidade será parametrizada, junto com a quantidade de tiles, comprimento e altura, dentro do construtor*/
}
