package ru.eu.games.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.eu.games.base.BaseButton;
import ru.eu.games.math.Rect;

public class ExitButton extends BaseButton {

    private static final float HEIGHT = 0.08f;
    private static final float PADDING = 0.04f;

    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("quitBtn"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setRight(worldBounds.getRight() - PADDING);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
