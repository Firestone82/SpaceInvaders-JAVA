package cz.devfire.java2.spaceInvaders.client.interfaces;

import cz.devfire.java2.spaceInvaders.client.Client;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public interface Controller {
    void init(Client client, Pane pane);
    Scene getScene();
    Canvas getCanvas();
}
