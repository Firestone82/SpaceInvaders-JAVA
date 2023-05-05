package cz.devfire.java2.spaceInvaders.client;

import cz.devfire.java2.spaceInvaders.App;
import cz.devfire.java2.spaceInvaders.client.enums.Constants;
import cz.devfire.java2.spaceInvaders.client.enums.GameState;
import cz.devfire.java2.spaceInvaders.client.enums.Source;
import cz.devfire.java2.spaceInvaders.client.file.Language;
import cz.devfire.java2.spaceInvaders.client.object.controller.MainController;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

@Log4j2(topic = "Game - JavaFX-Game")
public class Client extends Application {
	private MainController mainController;
	private Stage primaryStage;
	private Properties config;
	private Logger logger;

	public void start(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		log.info("Starting application");

		try {
			config = new Properties();
			config.load(getClass().getResourceAsStream("/config.properties"));

			Language.reload(this);

			Font.loadFont(getClass().getResource("/assets/ARCADECLASSIC.TTF").toExternalForm(),100);
			Font.loadFont(getClass().getResource("/assets/ARCADE.TTF").toExternalForm(),100);

			mainController = new MainController(this, primaryStage);
			mainController.setState(GameState.MENU);

			primaryStage.resizableProperty().set(false);
			primaryStage.getIcons().add(Source.ICON.getImage());
			primaryStage.setTitle(Constants.TITLE);
			primaryStage.setOnCloseRequest(this::exitProgram);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		mainController.getGame().stop();
	}
	
	private void exitProgram(WindowEvent evt) {
		try {
			stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public MainController getMainController() {
		return mainController;
	}

	public Stage getStage() {
		return primaryStage;
	}

	public String getResourcePath() {
		return "src/main/resources";
	}

	public Properties getConfig() {
		return config;
	}
}