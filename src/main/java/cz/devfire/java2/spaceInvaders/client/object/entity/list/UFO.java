package cz.devfire.java2.spaceInvaders.client.object.entity.list;

import cz.devfire.java2.spaceInvaders.client.enums.Constants;
import cz.devfire.java2.spaceInvaders.client.enums.Direction;
import cz.devfire.java2.spaceInvaders.client.enums.Source;
import cz.devfire.java2.spaceInvaders.client.interfaces.Drawable;
import cz.devfire.java2.spaceInvaders.client.object.Game;
import cz.devfire.java2.spaceInvaders.client.object.entity.Entity;
import cz.devfire.java2.spaceInvaders.client.object.other.Utils;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class UFO extends Entity implements Drawable {
    private Direction direction = Direction.NONE;

    public UFO(Game game, Source source) {
        super(game, source, Utils.getPoint(0,0));
        this.hittable = true;
    }

    @Override
    public void draw() {
        GraphicsContext gc = game.getController().getCanvas().getGraphicsContext2D();
        gc.save();

        if (explode > 0) {
            explode--;

            gc.drawImage(Source.EXPLOSION.getImage(),position.getX() - 3,position.getY() - 3,source.getWidth() + 6,source.getHeight() + 6);
        } else if (active) {
            gc.drawImage(source.getImage(), position.getX(), position.getY(), source.getWidth(), source.getHeight());
        }

        gc.restore();
    }

    @Override
    public void simulate(double deltaT) {
        if (explode <= 0) {
            if (direction == Direction.RIGHT) {
                position = position.add(new Point2D(1, 0).multiply(100 * deltaT));

                if (position.getX() > Constants.GAME_WIDTH + source.getWidth()) {
                    reset();
                }
            } else if (direction == Direction.LEFT) {
                position = position.add(new Point2D(-1, 0).multiply(100 * deltaT));

                if (position.getX() < 0 - source.getWidth()) {
                    reset();
                }
            }
        }
    }

    public void execute() {
        this.direction = (Math.round(Math.random()) == 1 ? Direction.RIGHT : Direction.LEFT);
        this.active = true;

        if (direction == Direction.RIGHT) {
            this.position = Utils.getPoint(0 - source.getWidth(), Constants.SCORE_HEIGHT);
        } else {
            this.position = Utils.getPoint(Constants.GAME_WIDTH + source.getWidth(), Constants.SCORE_HEIGHT);
        }
    }

    public void reset() {
        this.active = false;
        this.position = defaultPosition;
        this.direction = Direction.NONE;
    }

    public int getPoints() {
        return 100;
    }
}
