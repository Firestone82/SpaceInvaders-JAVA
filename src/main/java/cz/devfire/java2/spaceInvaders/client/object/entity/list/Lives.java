package cz.devfire.java2.spaceInvaders.client.object.entity.list;

import cz.devfire.java2.spaceInvaders.client.enums.Constants;
import cz.devfire.java2.spaceInvaders.client.enums.Source;
import cz.devfire.java2.spaceInvaders.client.file.Language;
import cz.devfire.java2.spaceInvaders.client.interfaces.Drawable;
import cz.devfire.java2.spaceInvaders.client.object.Game;
import cz.devfire.java2.spaceInvaders.client.object.entity.Entity;
import cz.devfire.java2.spaceInvaders.client.object.other.Utils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lives extends Entity implements Drawable {
    private final int defaultLives = 3;
    private int lives = defaultLives;

    public Lives(Game game, Source source) {
        super(game, source, Utils.getPoint(230,Constants.GLOBAL_BORDER + Constants.FONT_SIZE / 2D));
    }

    @Override
    public void draw() {
        GraphicsContext gc = game.getController().getCanvas().getGraphicsContext2D();
        gc.save();
        gc.setFont(Font.font("ArcadeClassic", Constants.FONT_SIZE));

        gc.setFill(Color.GRAY);
        gc.fillText(Language.GAME_LIVES.get(), position.getX(), position.getY());

        for (int i = 0; i < lives; i++) {
            game.getController().getCanvas().getGraphicsContext2D().drawImage(Source.SHIP.getImage(),position.getX() + 100 + (i * (Source.SHIP.getWidth() + 10)), position.getY() - Source.SHIP.getHeight(),Source.SHIP.getWidth(), Source.SHIP.getHeight());
        }

        gc.restore();
    }
}
