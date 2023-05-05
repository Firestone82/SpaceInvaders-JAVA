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

@Setter
@Getter
public class Score extends Entity implements Drawable {
    private int points = 0;

    public Score(Game game, Source source) {
        super(game, source, Utils.getPoint(Constants.GLOBAL_BORDER,Constants.GLOBAL_BORDER + Constants.FONT_SIZE / 2D));
    }

    @Override
    public void draw() {
        GraphicsContext gc = game.getController().getCanvas().getGraphicsContext2D();
        gc.save();
        gc.setFont(Font.font("ArcadeClassic", Constants.FONT_SIZE));

        gc.setFill(Color.GRAY);
        gc.fillText(Language.GAME_SCORE.get(), position.getX(), position.getY());

        gc.setFill(Color.rgb(0,255,33));
        gc.fillText(String.valueOf(points), position.getX() + 95, position.getY());

        gc.restore();
    }
}
