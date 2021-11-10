package ru.eu.games.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.eu.games.base.BaseButton;
import ru.eu.games.math.Rect;
import ru.eu.games.screen.GameScreen;

public class NewGameButton extends BaseButton {

    private static final float HEIGHT = 0.1f;
    private static final float PADDING = 0.1f;

    public NewGameButton(TextureAtlas atlas) {
        super(atlas.findRegion("newGame"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        pos.set(worldBounds.pos);
        setTop(worldBounds.pos.y - PADDING);
    }

    private void newGame(GameScreen gameScreen) {
        gameScreen.setNewGame();
    }

    private void setPressed() {
        setHeightProportion(HEIGHT - 0.01f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (isMe(touch)) {
            setPressed();
        }
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public void action() {

    }

    public boolean touchUp(Vector2 touch, int pointer, int button, GameScreen gameScreen) {
        if (isMe(touch)) {
            newGame(gameScreen);
        }
        return super.touchUp(touch, pointer, button);
    }
}
