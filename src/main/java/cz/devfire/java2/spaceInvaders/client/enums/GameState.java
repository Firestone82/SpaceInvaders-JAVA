package cz.devfire.java2.spaceInvaders.client.enums;

public enum GameState {
    MENU("/scenes/Menu.fxml"),
    PLAYING("/scenes/Game.fxml"),
    PAUSED(null),
    OVER(null),
    LEADERBOARD("/scenes/Board.fxml");

    private final String fxml;

    GameState(String fxml) {
        this.fxml = fxml;
    }

    public String getFXML() {
        return fxml;
    }
}
