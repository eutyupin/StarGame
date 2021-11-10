package ru.eu.games.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.eu.games.base.Sprite;
import ru.eu.games.math.Rect;

public class GameOver extends Sprite {
        private static final float HEIGHT = 0.1f;

        public GameOver(TextureAtlas atlas) {
            super(atlas.findRegion("gameOver"));
        }

        @Override
        public void resize(Rect worldBounds) {
            setHeightProportion(HEIGHT);
            pos.set(worldBounds.pos);
        }
}
