package cz.devfire.java2.spaceInvaders.client.object.controller.list;

import cz.devfire.java2.spaceInvaders.client.Client;
import cz.devfire.java2.spaceInvaders.client.enums.GameState;
import cz.devfire.java2.spaceInvaders.client.enums.Source;
import cz.devfire.java2.spaceInvaders.client.file.Language;
import cz.devfire.java2.spaceInvaders.client.interfaces.Controller;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class MenuController implements Controller {
    private Client client;

    @FXML Canvas logoCanvas;
    @FXML TextField userNameInput;
    @FXML Label usernameLabel;
    @FXML Button newGameButton;
    @FXML Button exitButton;
    @FXML Button leaderBoardButton;

    private Scene scene;

    @Override
    public void init(Client client, Pane pane) {
        this.client = client;

        this.logoCanvas.getGraphicsContext2D().drawImage(Source.LOGO.getImage(),50,70);
        this.usernameLabel.setText(Language.GAME_USERNAME.get());
        this.newGameButton.setText(Language.BUTTON_NEW_GAME.get());
        this.exitButton.setText(Language.BUTTON_BACK.get());
        this.leaderBoardButton.setText(Language.BUTTON_LEADERBOARD.get());

        this.scene = new Scene(pane);
        this.scene.getStylesheets().add(getClass().getResource("/assets/application.css").toExternalForm());
        this.scene.addEventHandler(KeyEvent.ANY, (event) -> client.getMainController().getGame().getInputListener().fire(event));
    }

    @FXML
    public void startGame() {
        if (userNameInput.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Language.ERROR_TITLE.get());
            alert.setHeaderText(Language.ERROR_NO_NAME.get());
            alert.showAndWait();
        } else {
            client.getMainController().setState(GameState.PLAYING);
            client.getMainController().getGame().setPlayer(userNameInput.getText());
        }
    }

    @FXML
    public void exitGame() {
        client.getStage().close();
    }

    @FXML
    public void showLeaderboard() {
        client.getMainController().setState(GameState.LEADERBOARD);
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public Canvas getCanvas() {
        return logoCanvas;
    }
}
