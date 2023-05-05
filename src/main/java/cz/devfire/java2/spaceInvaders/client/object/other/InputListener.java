package cz.devfire.java2.spaceInvaders.client.object.other;

import cz.devfire.java2.spaceInvaders.client.Client;
import cz.devfire.java2.spaceInvaders.client.enums.Direction;
import cz.devfire.java2.spaceInvaders.client.enums.GameState;
import cz.devfire.java2.spaceInvaders.client.enums.Source;
import cz.devfire.java2.spaceInvaders.client.object.Game;
import cz.devfire.java2.spaceInvaders.client.object.controller.list.MenuController;
import cz.devfire.java2.spaceInvaders.client.object.controller.list.ScoreController;
import cz.devfire.java2.spaceInvaders.client.object.entity.list.Ship;
import cz.devfire.java2.spaceInvaders.client.object.entity.list.ShipBullet;
import cz.devfire.java2.spaceInvaders.client.object.entity.list.UFO;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.extern.log4j.Log4j2;

@Log4j2(topic = "Game - InputListener")
public class InputListener {
    private final Client client;
    private final Game game;

    private Direction direction = Direction.NONE;
    private boolean shoot = false;
    private boolean press = false;

    public InputListener(Client client, Game game) {
        this.client = client;
        this.game = game;
    }

    public void fire(KeyEvent event) {
        if (client.getMainController().getState() == GameState.OVER) {
            if (event.getCode() != KeyCode.ESCAPE) {
                client.getMainController().setState(GameState.MENU);
                client.getMainController().getGame().reset();
                return;
            }
        }

        switch (event.getCode()) {
            case NUMPAD4, DIGIT4 -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    press = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && press) {
                    client.getMainController().getScoreController().saveScores();
                    press = false;
                }
            }

            case NUMPAD3, DIGIT3 -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    press = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && press) {
                    game.speedUp();

                    log.info("Ghosts speeded up!");
                    press = false;
                }
            }

            case NUMPAD2, DIGIT2 ->  {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    press = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && press) {
                    ScoreController scoreController = client.getMainController().getScoreController();
                    scoreController.clearScore();
                    press = false;
                }
            }

            case NUMPAD1, DIGIT1 -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    press = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && press) {
                    UFO ufo = ((UFO) game.getObject(Source.UFO));

                    if (!ufo.isActive()) {
                        ufo.execute();

                        log.info("UFO Forced to go!");
                    }

                    press = false;
                }
            }

            case ENTER -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    press = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && press) {
                    if (client.getMainController().getState() == GameState.MENU) {
                        ((MenuController) client.getMainController().getController()).startGame();
                    }

                    press = false;
                }
            }

            case ESCAPE -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    press = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && press) {
                    switch (client.getMainController().getState()) {
                        case MENU, OVER -> client.getMainController().exit();
                        case LEADERBOARD -> client.getMainController().setState(GameState.MENU);
                        case PLAYING -> client.getMainController().setState(GameState.PAUSED);
                        case PAUSED -> client.getMainController().setState(GameState.PLAYING);
                    }

                    press = false;
                }
            }

            case W, UP, SPACE -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    shoot = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && shoot) {
                    ShipBullet bullet = ((ShipBullet) game.getObject(Source.SHIP_BULLET));

                    if (!bullet.isActive()) {
                        bullet.execute();
                    }

                    shoot = false;
                }
            }

            case A, LEFT -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    direction = Direction.LEFT;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && direction == Direction.LEFT) {
                    direction = Direction.NONE;
                }
            }

            case D, RIGHT -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    direction = Direction.RIGHT;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && direction == Direction.RIGHT) {
                    direction = Direction.NONE;
                }
            }
        }
    }

    public void tick(double deltaT) {
        ((Ship) game.getObject(Source.SHIP)).move(direction, deltaT);
    }
}
