package main;

import sprites.ImageLoader;
import structure.GameWindow;

public class Main {
    public static void main(String[] args) {
        Thread imageLoaderThread = new Thread(() -> ImageLoader.loadAllImages());
        imageLoaderThread.start();
        try {
            imageLoaderThread.join(); // Aguarda o carregamento das imagens antes de continuar
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new GameWindow();
    }
}