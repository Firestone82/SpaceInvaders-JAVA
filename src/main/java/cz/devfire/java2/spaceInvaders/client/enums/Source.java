package cz.devfire.java2.spaceInvaders.client.enums;

import cz.devfire.java2.spaceInvaders.App;
import javafx.scene.image.Image;

import java.util.ArrayList;

public enum Source {
    LOGO         (new String[]{"/images/logo.png"}),
    ICON         (new String[]{"/images/icon.png"}),
    GHOST_A      (new String[]{"/images/ghostA1.png", "/images/ghostA2.png"},16,16),
    GHOST_B      (new String[]{"/images/ghostB1.png", "/images/ghostB2.png"},22,16),
    GHOST_C      (new String[]{"/images/ghostC1.png", "/images/ghostC2.png"},24,16),
    SHIP         (new String[]{"/images/ship.png"},30,16),
    BLOCK        (new String[]{"/images/block.png"}),
    UFO          (new String[]{"/images/ufo.png"},32,14),
    SHIP_BULLET  (new String[]{},3,16),
    ALIEN_BULLET (new String[]{},3,16),
    EXPLOSION    (new String[]{"/images/explosion.gif"}),
    SCORE,
    LIVES,
    BLUR         (new String[]{"/images/blur.png"}),;

    private final ArrayList<Image> images = new ArrayList<>();
    private final double width;
    private final double height;

    Source() {
        this(null);
    }

    Source(String[] urlList) {
        this(urlList,-1,-1);
    }

    Source(String[] urlList, double width, double height) {
        if (urlList != null) {
            for (String image : urlList) {
                images.add(new Image(App.class.getResourceAsStream(image)));
            }
        }
        
        this.width = width;
        this.height = height;
    }

    public Image getImage() {
        return getImage(0);
    }
    
    public Image getImage(int id) {
        return images.get(id);
    }

    public Double getHeight() {
        return height == -1 ? getImage(0).getHeight() : height;
    }

    public Double getWidth() {
        return width == -1 ? getImage(0).getWidth() : width;
    }
}
