package gamestates;

public enum StateMachine {
    LOADING,
    MENU,
    TUTORIAL,
    ABOUT,
    PLAYING,
    GAME_OVER,
    MULTIPLAYER_MENU,
    LOCAL_MULTIPLAYER,
    END;

    public static StateMachine gamesstate = PLAYING;
}
