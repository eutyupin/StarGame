package ru.eu.games.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.eu.games.base.BaseButton;
import ru.eu.games.math.Rect;
import ru.eu.games.screen.GameScreen;
import ru.eu.games.screen.MenuScreen;

public class PlayButton extends BaseButton {

    private static final float HEIGHT = 0.08f;
    private static final float PADDING = 0.04f;

    private final Game game;

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("playBtn"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setLeft(worldBounds.getLeft() + PADDING);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }
}
