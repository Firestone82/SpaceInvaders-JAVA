package cz.devfire.java2.spaceInvaders.client.object.entity;

import cz.devfire.java2.spaceInvaders.client.enums.Source;
import cz.devfire.java2.spaceInvaders.client.interfaces.Drawable;
import cz.devfire.java2.spaceInvaders.client.object.Game;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Entity implements Drawable {
    protected final Game game;

    protected final Source source;
    protected final Point2D defaultPosition;
    protected Point2D position;

    protected boolean active = false;
    protected boolean hittable = false;
    protected long explode = 0L;

    public Entity(Game game, Source source, Point2D position) {
        this.game = game;
        this.source = source;
        this.defaultPosition = position;
        this.position = defaultPosition;
    }

    public void simulate(double deltaT) {
        // NOTHING
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), source.getWidth(), source.getHeight());
    }

    public boolean overlaps(Entity entity) {
        return getBoundingBox().intersects(entity.getBoundingBox()) && active;
    }

    public void explode() {
        explode = 18;
    }
}
