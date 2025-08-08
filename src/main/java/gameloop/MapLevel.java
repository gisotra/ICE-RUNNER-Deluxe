package gameloop;

public class MapLevel {
    private WorldTheme theme;
    private int levelIndex;

    public MapLevel(WorldTheme theme, int levelIndex){
        this.theme = theme;
        this.levelIndex = levelIndex;
    }

    /*Getters & Setters*/
    public int getLevelIndex() {
        return levelIndex;
    }

    public void setLevelIndex(int levelIndex) {
        this.levelIndex = levelIndex;
    }

    public WorldTheme getTheme() {
        return theme;
    }

    public void setTheme(WorldTheme theme) {
        this.theme = theme;
    }
}
