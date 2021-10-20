package ru.eu.games.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.eu.games.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture background, shuttle;
    private Vector2 touch, v, dest;
    private final int DEFAULT_WIDTH = 96;
    private final int DEFAULT_HEIGHT = 96;

    @Override
    public void show() {
        super.show();
        background = new Texture("space.jpg");
        shuttle = new Texture("ufo.png");
        touch = new Vector2(0,0);
        v = new Vector2();
        dest = new Vector2(0,0);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        v = touch.cpy().sub(dest.cpy()).nor().setLength(5.0f);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(shuttle, dest.x, dest.y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        if (dest.dst(touch) <= v.len()) {
            dest.set(touch);
        } else {
            dest.add(v);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        background.dispose();
        shuttle.dispose();
    }
}
