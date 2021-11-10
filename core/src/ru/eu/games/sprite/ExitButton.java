package ru.eu.games.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.eu.games.base.BaseButton;
import ru.eu.games.math.Rect;

public class ExitButton extends BaseButton {

    private static final float HEIGHT = 0.08f;
    private static final float MARGIN = 0.04f;

    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("exitBtn"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setRight(worldBounds.getRight() - MARGIN);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (isMe(touch)) {
            action();
        }
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }

    public static float getDefaultHEIGHT() {
        return HEIGHT;
    }

    public static float getDefaultMARGIN() {
        return MARGIN;
    }
}
