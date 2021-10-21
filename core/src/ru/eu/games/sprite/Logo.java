package ru.eu.games.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.eu.games.base.Sprite;
import ru.eu.games.math.Rect;

public class Logo extends Sprite {
    private Vector2 logoPos;
    private Vector2 destPos;
    private Vector2 velocity;

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        logoPos = new Vector2();
        destPos = new Vector2();
        velocity = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight() / 15);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                logoPos.x, logoPos.y,
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
        if (logoPos.dst(destPos) <= velocity.len()) {
            logoPos.set(destPos);
        } else {
            logoPos.add(velocity);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        destPos.set(touch);
        velocity.set(destPos.cpy().sub(logoPos.cpy()).nor().setLength(0.01f));
        return super.touchDown(touch, pointer, button);
    }
}
