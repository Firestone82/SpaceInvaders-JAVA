package cz.devfire.java2.spaceInvaders.client.object.controller.list;

import cz.devfire.java2.spaceInvaders.client.Client;
import cz.devfire.java2.spaceInvaders.client.interfaces.Controller;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class GameController implements Controller {
    @FXML Canvas gameCanvas;
    private Scene scene;

    public void init(Client client, Pane pane) {
        this.scene = new Scene(pane);
        this.scene.getStylesheets().add(getClass().getResource("/assets/application.css").toExternalForm());
        this.scene.addEventHandler(KeyEvent.ANY, (event) -> client.getMainController().getGame().getInputListener().fire(event));
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public Canvas getCanvas() {
        return gameCanvas;
    }
}
