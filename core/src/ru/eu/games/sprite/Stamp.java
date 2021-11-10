package ru.eu.games.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.eu.games.base.Sprite;
import ru.eu.games.math.Rect;

public class Stamp extends Sprite {

    private Rect worldBounds;
    private static final float HEIGHT = 0.5f;

    public Stamp(TextureAtlas atlas, Rect worldBounds) {
        super(atlas.findRegion("stamp"));
        this.worldBounds = worldBounds;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        pos.set(worldBounds.pos);
        pos.y = worldBounds.pos.y + 0.3f;
    }
}
