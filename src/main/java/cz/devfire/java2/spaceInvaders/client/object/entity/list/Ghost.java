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
import lombok.Setter;

import java.util.Collection;

public class Ghost extends Entity implements Drawable {
    @Setter private Direction direction = Direction.RIGHT;

    private boolean id = true;
    private long ticks = 0;

    public Ghost(Game game, Source source, double x, double y) {
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
            gc.drawImage(source.getImage(id ? 1 : 0), position.getX(), position.getY(), source.getWidth(), source.getHeight());

            if (ticks++ > 15) {
                id = !id;
                ticks = 0;
            }
        }

        gc.restore();
    }

    @Override
    public void simulate(double deltaT) {
        if (!active) return;

        Collection<Ghost> ghosts = game.getGhosts();
        int totalGhostCount = (int) ghosts.size();
        int aliveGhostCount = (int) ghosts.stream().filter(Ghost::isActive).count();

        // Move increment
        double inc = ((System.currentTimeMillis() - game.getLevelTime()) / 5000F) + (totalGhostCount - aliveGhostCount) / 100F;

        // Move
        switch (direction) {
            case LEFT -> {
                position = position.subtract((5 + inc) * deltaT,0);

                if (position.getX() < 0) {
                    position = new Point2D(0, position.getY());

                    for (Ghost ghost : game.getGhosts()) {
                        ghost.setDirection(Direction.RIGHT);
                        ghost.setPosition(ghost.getPosition().add(0,10));
                    }
                }
            }

            case RIGHT -> {
                position = position.add((5 + inc) * deltaT, 0);

                if (position.getX() + source.getWidth() > Constants.GAME_WIDTH) {
                    position = new Point2D(Constants.GAME_WIDTH - source.getWidth(), position.getY());

                    for (Ghost ghost : game.getGhosts()) {
                        ghost.setDirection(Direction.LEFT);
                        ghost.setPosition(ghost.getPosition().add(0,10));
                    }
                }
            }
        }

        // Chance to shoot
        int random = (((totalGhostCount - aliveGhostCount) * 50) + (100 * (int) ((System.currentTimeMillis() - game.getLevelTime()) / 5000F)));
        if (Utils.getRandom(Math.min(random, 0), totalGhostCount * 100) == totalGhostCount * 100) {
            game.getEntities().add(new AlienBullet(game, Source.ALIEN_BULLET, new Point2D(position.getX(), position.getY())));
        }
    }

    public int getPoints() {
        return switch (source) {
            case GHOST_A -> 40;
            case GHOST_B -> 20;
            case GHOST_C -> 10;
            default -> 0;
        };
    }
}
