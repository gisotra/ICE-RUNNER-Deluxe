package main;

import sprites.ImageLoader;
import structure.GameWindow;

public class Main {
    public static void main(String[] args) {
        ImageLoader.loadAllImages();
        new GameWindow();
    }
}
