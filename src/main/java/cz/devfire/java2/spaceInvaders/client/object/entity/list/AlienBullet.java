package cz.devfire.java2.spaceInvaders.client.object.entity.list;

import cz.devfire.java2.spaceInvaders.client.enums.Constants;
import cz.devfire.java2.spaceInvaders.client.enums.Source;
import cz.devfire.java2.spaceInvaders.client.interfaces.Drawable;
import cz.devfire.java2.spaceInvaders.client.object.Game;
import cz.devfire.java2.spaceInvaders.client.object.entity.Entity;
import cz.devfire.java2.spaceInvaders.client.object.other.Utils;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AlienBullet extends Entity implements Drawable {

    public AlienBullet(Game game, Source source) {
        this(game, source, Utils.getPoint(0,0));
    }

    public AlienBullet(Game game, Source source, Point2D position) {
        super(game, source, position);
        active = true;
    }

    @Override
    public void draw() {
        if (!active) return;

        GraphicsContext gc = game.getController().getCanvas().getGraphicsContext2D();
        gc.save();
        gc.setFill(Color.rgb(255,0,33));

        gc.fillRect(position.getX(), position.getY(),3,16);

        gc.restore();
    }

    @Override
    public void simulate(double deltaT) {
        if (!active) return;

        position = position.subtract(new Point2D(0,-1).multiply(250 * deltaT));
        if (position.getY() > Constants.GAME_HEIGHT) {
            reset();
        }
    }

    public void reset() {
        active = false;
        position = defaultPosition;
    }
}
