package cz.devfire.java2.spaceInvaders.client.object.controller;

import cz.devfire.java2.spaceInvaders.client.Client;
import cz.devfire.java2.spaceInvaders.client.enums.GameState;
import cz.devfire.java2.spaceInvaders.client.interfaces.Controller;
import cz.devfire.java2.spaceInvaders.client.object.Game;
import cz.devfire.java2.spaceInvaders.client.object.controller.list.BoardController;
import cz.devfire.java2.spaceInvaders.client.object.controller.list.GameController;
import cz.devfire.java2.spaceInvaders.client.object.controller.list.MenuController;
import cz.devfire.java2.spaceInvaders.client.object.controller.list.ScoreController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2(topic = "Game - MainController")
public class MainController {
    private final Client client;
    private final Stage primaryStage;

    private final Game game;
    private GameState state = GameState.MENU;
    private MenuController menuController;
    private GameController gameController;
    private BoardController boardController;
    private ScoreController scoreController;

    public MainController(Client client, Stage primaryStage) {
        this.client = client;
        this.primaryStage = primaryStage;

        try {
            FXMLLoader menuLoader = new FXMLLoader(Client.class.getResource(GameState.MENU.getFXML()));
            Pane menuPane = menuLoader.load();
            menuController = menuLoader.getController();
            menuController.init(client, menuPane);

            FXMLLoader gameLoader = new FXMLLoader(Client.class.getResource(GameState.PLAYING.getFXML()));
            Pane gamePane = gameLoader.load();
            gameController = gameLoader.getController();
            gameController.init(client, gamePane);

            FXMLLoader boardLoader = new FXMLLoader(Client.class.getResource(GameState.LEADERBOARD.getFXML()));
            Pane boardPane = boardLoader.load();
            boardController = boardLoader.getController();
            boardController.init(client, boardPane);

            scoreController = new ScoreController(client,this);
            scoreController.loadScore();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.game = new Game(client, gameController);
    }

    public void setState(GameState state) {
        GameState prevState = this.state;
        this.state = state;

        try {
            switch (state) {
                case MENU -> {
                    if (prevState == GameState.OVER) {
                        game.stop();
                    }

                    primaryStage.setScene(menuController.getScene());
                }

                case PLAYING -> {
                    primaryStage.setScene(gameController.getScene());
                    game.start();
                }

                case PAUSED -> {
                    game.pause();
                }

                case OVER -> {
                    game.stop();
                    game.save();
                }

                case LEADERBOARD -> {
                    primaryStage.setScene(boardController.getScene());
                    boardController.draw();
                }
            }

            primaryStage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("State changed to {}", state);
    }

    public Controller getController() {
        switch (state) {
            case MENU -> {
                return menuController;
            }

            case PLAYING, PAUSED, OVER -> {
                return gameController;
            }

            case LEADERBOARD -> {
                return boardController;
            }
        }

        return null;
    }

    public void exit() {
        menuController.exitGame();
    }
}
