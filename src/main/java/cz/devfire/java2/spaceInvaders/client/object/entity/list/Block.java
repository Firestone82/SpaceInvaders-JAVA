package cz.devfire.java2.spaceInvaders.client.object.entity.list;

import cz.devfire.java2.spaceInvaders.client.enums.Constants;
import cz.devfire.java2.spaceInvaders.client.enums.Source;
import cz.devfire.java2.spaceInvaders.client.interfaces.Drawable;
import cz.devfire.java2.spaceInvaders.client.object.Game;
import cz.devfire.java2.spaceInvaders.client.object.entity.Entity;
import cz.devfire.java2.spaceInvaders.client.object.other.Utils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Block extends Entity implements Drawable {
    private final int maxHealth = 15;
    private int health = maxHealth;

    public Block(Game game, Source source, double x, double y) {
        super(game, source, Utils.getPoint(x, y));
        this.active = true;
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

            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("ArcadeClassic", Constants.FONT_SIZE));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText(String.valueOf(health),position.getX() + 32,position.getY() + 30);
        }

        gc.restore();
    }

    public void execute() {
        health--;

        if (health <= 0) {
            setActive(false);
        }
    }

    public void reset() {
        active = true;
        health = maxHealth;
    }
}
