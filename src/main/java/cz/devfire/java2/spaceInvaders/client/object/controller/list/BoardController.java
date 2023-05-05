package cz.devfire.java2.spaceInvaders.client.object.controller.list;

import cz.devfire.java2.spaceInvaders.client.Client;
import cz.devfire.java2.spaceInvaders.client.enums.GameState;
import cz.devfire.java2.spaceInvaders.client.file.Language;
import cz.devfire.java2.spaceInvaders.client.interfaces.Controller;
import cz.devfire.java2.spaceInvaders.server.database.model.GameScore;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class BoardController implements Controller {
    private Client client;
    private Pane pane;

    @FXML Button backButton;
    @FXML TableView<GameScore> table;
    @FXML TableColumn<GameScore, String> tableUsername;
    @FXML TableColumn<GameScore, Integer> tableScore;
    @FXML TableColumn<GameScore, String> tableTime;

    private Scene scene;

    public void init(Client client, Pane pane) {
        this.client = client;
        this.pane = pane;

        this.backButton.setText(Language.BUTTON_BACK.get());
        this.tableUsername.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        this.tableUsername.setText(Language.GAME_USERNAME.get());
        this.tableScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        this.tableScore.setText(Language.GAME_SCORE.get());
        this.tableTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        this.tableTime.setText(Language.GAME_TIME.get());

        this.scene = new Scene(pane);
        this.scene.getStylesheets().add(getClass().getResource("/assets/application.css").toExternalForm());
    }

    public void draw() {
        table.setItems(
                FXCollections.observableArrayList(
                        client.getMainController().getScoreController().getScoreList()
                                .stream()
                                .sorted((o1, o2) -> o2.getScore() - o1.getScore())
                                .limit(10).toList()
                )
        );
    }

    @FXML
    public void backToMenu() {
        client.getMainController().setState(GameState.MENU);
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public Canvas getCanvas() {
        return null;
    }
}
