package ru.eu.games.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.eu.games.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture background;
    private Vector2 touch, v;

    @Override
    public void show() {
        super.show();
        background = new Texture("space.jpg");
        touch = new Vector2(0,0);
        v = new Vector2();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        background.dispose();
    }
}
