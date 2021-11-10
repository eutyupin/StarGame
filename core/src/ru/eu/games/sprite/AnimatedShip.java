package ru.eu.games.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.eu.games.base.Sprite;
import ru.eu.games.math.Rect;

public class AnimatedShip extends Sprite {
    private Rect worldBounds;
    private static final float SHIP_DEFAULT_PADDING = 0.03f;
    private static final float SHIP_DEFAULT_HEIGHT = 0.1f;

    public AnimatedShip(TextureAtlas atlas, Rect worldBounds) {
        super(atlas.findRegion("ship"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(SHIP_DEFAULT_HEIGHT);
        setTop(worldBounds.getTop() + SHIP_DEFAULT_PADDING);
        setRight(worldBounds.getRight() + SHIP_DEFAULT_PADDING);
    }
}
